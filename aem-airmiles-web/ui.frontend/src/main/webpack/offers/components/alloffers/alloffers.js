import {envElements, modalElements} from "./envElements";
import {createElement, isAuthed} from "../../../dependencies/js/utils";
import {useFetch, abortAPIRequest} from "../../../dependencies/js/api";
import {regionListInitial} from "../offersregion/offersRegion";
import {
    defaultLocal,
    updatePayload,
    offersPayloadModel,
    getRequestApiReady,
    loadDetailOffers,
    duplicateLoaderCards,
    basicInitSetup,
    checkIsCLO,
    getActiveTab
} from "./support";
import { getItemByID, duplicateDetailCard, openDetailCard, cardGenerator, saveClick, hasAwaitingTasks} from "./detailFunc";
import { offersDataSync, pageloadAAEmit, unloadOfferPage} from "./offersanalytics";

//********************************
// Variable initial
//********************************
let _filterParam = envElements.mainContainer ? envElements.mainContainer.dataset.quickFilter || 'alloffers' : 'alloffers';
let _partnersInputs, _categoryInputs, _subCategoryInputs;
let _offersSubNavURL;
let _totalOffersNum, _offerCounter = 0;
let _offersData, _detailOffersData, _detailOffersPartners, _partners, _category, _subCategory = {};
let _filterExpand = false;
let _payload = [];
let _activeSlide;
let quickFilterInProgress = false;


//********************************
// Function setup
//********************************
function collapsePanel(){
    _filterExpand = false;
    envElements.showcase.classList.remove('panel-collapse');
    envElements.showcase.classList.remove('col-lg-9');
    envElements.filterBox.classList.remove('active');
    envElements.body.classList.remove('overflow-hidden');
    envElements.filterPanel.classList.remove('active');
    envElements.panelToggle.classList.remove('active');// To sync from mobile close action
}
function expandPanel(){
    _filterExpand = true;
    envElements.showcase.classList.add('panel-collapse');
    envElements.showcase.classList.add('col-lg-9');
    envElements.filterBox.classList.add('active');
    envElements.panelToggle.classList.add('active');
    if(document.documentElement.clientWidth < 1024){
        envElements.body.classList.add('overflow-hidden');
    }
    envElements.filterPanel.classList.add('active');
}

function clearQuickFilters(){
    for (const element of envElements.quickFilterTabs) {
        element.classList.remove('active');
    }
}

// This function for basic 4 filters: [partners, types, categories, subCategories]
function getFiltersVal(_filter){
    _payload['filters'][_filter] = [];
    const _checkboxes = document.querySelectorAll(`.${_filter}-input:checked`);
    for (const element of _checkboxes) {
        !_payload['filters'][_filter].includes(element.value) && _payload['filters'][_filter].push(element.value);
    }
    updatePayload(_payload);
}

function getFiltersSortedByVal(){
    _payload['pagination']['sortedBy'] = [];
    const sortByBox = document.querySelectorAll('.sort-by-input:checked')
    for (const element of sortByBox) {
        _payload['pagination']['sortedBy'] = element.value;
    }
    updatePayload(_payload);
}

export function checkOffersInDetail(_pId,_id,_extGrpOffers=null){
    duplicateLoaderCards(modalElements.detailSlide);
    loadDetailOffers(_payload, _pId, _id).then(detailData=>{
        modalElements.detailSlide.innerText='';
        _detailOffersData = detailData['offers'].filter(item => item.id !== _id).slice(0, 4); // remove repeated selected offer
        let _counter = 0;
        if(detailData['metadata']){
            _detailOffersPartners = detailData['metadata']['partners'];
            if(!_detailOffersPartners)return;
            for(const _elem of _detailOffersData){
                _counter++;
                duplicateDetailCard(_elem,_counter);
            }
        }
        const useOffers = _extGrpOffers ? _extGrpOffers : _offersData;
        const totalDetailOffers = 4 - _detailOffersData.length;
        const fixOffers = useOffers.slice(0, totalDetailOffers);
        for(const _elem of fixOffers){
            _counter++;
            duplicateDetailCard(_elem,_counter);
        }
    }).catch(error => {
        // Still loading original offers into detail slider when API return errors.
        modalElements.detailSlide.innerText='';
        const fixUpOffers = _offersData.slice(0, 4);
        let _counterEx = 0;
        for(const _elem of fixUpOffers){
            _counterEx++;
            duplicateDetailCard(_elem,_counterEx);
        }
        console.error('Loading Offers in detail offers slider. Error:', error);
    });
}

function duplicateCard(_data, _offerCounterIn){
    const regularOfferPage = getActiveTab().dataset.pageName;
    const clone = cardGenerator(_data, _offerCounterIn, regularOfferPage);
    if(clone){
        clone.addEventListener('click', (e)=>{
            const _saveEl = e.target.closest('.alloffers-offers__card__mechanisms__saved');
            if(_saveEl){
                saveClick(_saveEl, false, _data);
            }else{
                if(e.target.dataset.val==='cardLinked'){return;} // Don't open detail if target is saveHeart 'cardLinked'
                openDetailCard(_data);
                checkOffersInDetail(_data['partnerId'],_data['id']);
            }
        });
        envElements.showcase.appendChild(clone);
    }else{
        console.log('Due to partner does not exist, clone failed!!');
    }
}

function displayData(_data) {
    modalElements.detailSlide.innerText=''; // Reset minifeed slide in detail card everytime data loads
    for(const card of _data){
        _offerCounter++;
        duplicateCard(card,_offerCounter);
    }
}

function clearFilterAccordionActive(){
    for (const element of envElements.accordionButtons) {
        element.classList.remove('active');
    }
}

function toggleActiveClass() {
    const _accBtn = this.parentNode.parentNode.previousElementSibling;
    if(_accBtn.classList.contains('active')){
        _accBtn.classList.remove('active');
    }else{
        clearFilterAccordionActive();
        _accBtn.classList.add('active');
    }
}

// This is the counter for each item in the list appearing on the side. i.e. Grocery(XX)
function getFilterNum(_inputs, _group){
    if(!_inputs)return;
    for(const element of _inputs){
        let _selectedFilter;
        switch(_group) {
            case 'partners':
                _selectedFilter = getItemByID(_partners, 'id', element.value);
                break;
            case 'categories':
                _selectedFilter = getItemByID(_category, 'id', element.value);
                break;
            case 'subCategories':
                _selectedFilter = getItemByID(_subCategory, 'id', element.value);
                break;
        }
        if(_selectedFilter){
            const _numOffers = _selectedFilter['NumberOfOffers'] ? _selectedFilter['NumberOfOffers'] : _selectedFilter['count'] || 0;
            element.disabled = !_numOffers;
            // _numOffers ? element.parentNode.classList.remove('d-none') : element.parentNode.classList.add('d-none');
            const _count = element.parentElement.querySelector('.alloffers-filters__count');
            _count.textContent = `(${_numOffers})`;
        }
    }
}

function totalSelectedFilter(_filter){
    let _count = 0;
    _filter.forEach(element => {
        element.checked && _count++;
    });
    return _count;
}

// Update selected filters number on left filter panel
function checkQuickTags(){
    const _totalPartnersNum = totalSelectedFilter(envElements.filterBox.querySelectorAll('.partners-input'));
    const _totalTypesNum = totalSelectedFilter(envElements.filterBox.querySelectorAll('.types-input'));
    const _totalCategoriesNum = totalSelectedFilter(envElements.filterBox.querySelectorAll('.categories-input, .subCategories-input'));
    const _totalCheckedElements = _totalPartnersNum + _totalTypesNum + _totalCategoriesNum;
    envElements.totalPartnersNum.classList.toggle('d-none', _totalPartnersNum === 0);
    envElements.totalPartnersNum.textContent = _totalPartnersNum.toString();
    envElements.totalTypesNum.classList.toggle('d-none', _totalTypesNum === 0);
    envElements.totalTypesNum.textContent = _totalTypesNum.toString();
    envElements.totalCategoriesNum.classList.toggle('d-none', _totalCategoriesNum === 0);
    envElements.totalCategoriesNum.textContent = _totalCategoriesNum.toString();
    envElements.totalFiltersNum.classList.toggle('d-none', _totalCheckedElements === 0);
    envElements.totalFiltersNum.textContent = _totalCheckedElements.toString();
    envElements.tagContainer.classList.toggle("d-none", _totalCheckedElements === 0);
}

function qfPanel(hide=false){
    if(hide){
        envElements.panelToggle.classList.add('d-lg-none');
        envElements.panelToggle.classList.remove('d-lg-flex');
        envElements.filterBtn.classList.add('d-none');
        collapsePanel();
    }else{
        envElements.panelToggle.classList.remove('d-lg-none');
        envElements.panelToggle.classList.add('d-lg-flex');
        envElements.filterBtn.classList.remove('d-none');
    }
}

function clearAllFilters(){
    // Remove all quick tags
    const _tags = envElements.tagContainer.querySelectorAll('.tag');
    for (const element of _tags) {
        element.remove();
    }
    // Uncheck all category checkboxes in filter
    const _allCheckboxInputs = envElements.filterPanel.querySelectorAll("input.form-check-input[type='checkbox']");
    for (const element of _allCheckboxInputs) {
        element.checked=false;
    }
    // Reset payload
    if(!checkIsCLO()){
        _payload['filters']['partners']=[];
        _payload['filters']['categories']=[];
        _payload['filters']['subCategories']=[];
        _payload['filters']['types']=[];
    }
    updatePayload(_payload);
    checkQuickTags();
    // Hide the row since no tags exists but only clear button
    envElements.tagContainer.classList.add('d-none');
    _activeSlide.hasAttribute('data-panel-hide') ? qfPanel(true) : qfPanel(false);
}
function clearDetailTag(_tag){
    // Uncheck the input from category filter
    const _filterBy = document.querySelector('.alloffers-filters__filter-by');
    const _quickTags = _filterBy.querySelectorAll(`input[value="${_tag.dataset.code}"]`);
    for(const _qTag of _quickTags){
        _qTag.checked = false;
    }
    // update payload
    getFiltersVal('partners');
    getFiltersVal('types');
    getFiltersVal('categories');
    getFiltersVal('subCategories');
    updatePayload(_payload);
    checkQuickTags();
    // hide the tag
    _tag.remove();
    // hide the row if no other tags exists
    const _chkTags = envElements.tagContainer.querySelectorAll('.tag');
    if(_chkTags.length < 1){
        envElements.tagClearBtn.click();
    }
}

export function setNoResultMessage(isShow, showBlock = null, custom= null){
    if(isShow){
        envElements.showcase.innerText='';
        envElements.resultContainer.classList.remove('d-none');
        custom && (showBlock.textContent = custom);
        showBlock.classList.remove('d-none');
        qfPanel(true);
        console.log("Error MSG Block:::",showBlock,custom);
    }else{
        envElements.noOffers.classList.add('d-none');
        envElements.apiFails.classList.add('d-none');
        envElements.noSavedOffers.classList.add('d-none');
        envElements.noOffersForYou.classList.add('d-none');
        envElements.customError.classList.add('d-none');
        envElements.resultContainer.classList.add('d-none');
    }
}

function setPayloadCLO(){
    const elementCLO = document.getElementById('offers-query-parameter');
    _payload['filters']['massOffer']=elementCLO.dataset.massOffer;
    _payload['filters']['partners']=elementCLO.dataset.partnerId;
    _payload['filters']['categories']=elementCLO.dataset.categoryId;
    _payload['filters']['subCategories']=elementCLO.dataset.subcategoryId;
    _payload['filters']['programType']=elementCLO.dataset.programType;
    _payload['filters']['promotionId']=elementCLO.dataset.promotionId;
    _payload['pagination']['sortedBy']=elementCLO.dataset.sortOptions;
    _payload['pagination']['offersPerPage']=elementCLO.dataset.limit;
    updatePayload(_payload);
}

function initPayload(){
    const storedPayloadString = sessionStorage.getItem('am_OSCP');
    if(storedPayloadString){
        _payload = JSON.parse(storedPayloadString);
        _payload['pagination']['page'] = 1;
        if(isAuthed() && sessionStorage.getItem('am_locMutated')!=='true'){
            _payload['filters']['region'] = defaultLocal;
        }
    }else{
        _payload = offersPayloadModel(defaultLocal, envElements.offersNum.dataset.shownum);
        updatePayload(_payload);
    }
    if(!_payload['filters']['region'] || _payload['filters']['region']==='null'){
        _payload['filters']['region'] = defaultLocal;
    }
    checkIsCLO() && setPayloadCLO();
}

async function loadNewOffers(){
    const currentOfferPage = getActiveTab();
    if(currentOfferPage.dataset.type !== 'mini-feed') _payload['pagination']['page']++;
    const _req = getRequestApiReady(_payload,null);
    await useFetch(_req.apiURL, true, _req.sentPayload,'offers', null)
        .then(data => {
            if(data){
                // Adding new offers when scrolling to bottom;
                _offersData = _offersData.concat(data['offers']);
                offersDataSync(_offersData);
                displayData(data['offers']); // load new offers
            }
        })
        .catch(error => {
            console.error('Continues fetch Error:', error);
        });
}
// Add observer when scroll to last card
const lastCardObserver = new IntersectionObserver(entries=>{
    const lastCard = entries[0];
    const _currentPage = _payload['pagination']['page'];
    if(!lastCard.isIntersecting){return;}
    // After lastCard intersect triggered, stop observing
    lastCardObserver.unobserve(lastCard.target);
    const _offerPerPage = parseInt(envElements.offersNum.dataset.shownum, 10);
    const _currentOffset = _offerPerPage * _currentPage;
    if(_currentOffset < _totalOffersNum) {
        console.log("Additional offers loaded:", _currentOffset, " of ", _totalOffersNum);
        // ('At bottom now. Load new offers!!!!! Now start new observing the last card');
        loadNewOffers().then(() => {
            lastCardObserver.observe(envElements.showcase.querySelector('.alloffers-offers__card:last-child'));
        });
    }
},{
    // Load new offers before scroll to bottom. Get the approx height of offer card.
    rootMargin: '120px'
})

function makeFilterItem(filterContainer, _name, genID, genName,sub=null){
    const ranVar = Math.floor(Math.random()*90000) + 10000;
    const _genItem = createElement('div', ['alloffers-filters__slides__filter', 'p-2'], filterContainer, {'data-key': genID}, '');
    const nameId = _name+genID.replace(/[-\s]/g, '').slice(0, 20);
    createElement('input', ['form-check-input', `${_name}-input`], _genItem, {'type': 'checkbox','id': `${nameId}-${ranVar}`,'name':`${genName}`,'value': genID}, '');
    const _label = createElement('label', ['form-check-label'], _genItem, {'for': `${nameId}-${ranVar}`}, '');
    createElement('span', [`alloffers-filters__name`], _label, {}, genName);
    createElement('span', ['alloffers-filters__count'], _label, {}, '');
    sub && _genItem.classList.add('ps-4');
}

function filterItemsGen(_group, _name){
    const filterContainer = document.querySelector(`.filter-by__${_name}`).querySelector('.alloffers-filters__container');
    filterContainer.innerText = '';
    if(!_group){return;}
    for (const element of _group) {
        const genID = element['id'];
        const genName = element['label'];
        makeFilterItem(filterContainer,_name,genID,genName);
        const _child = element['subCategories'];
        if(_child){
            for (const childElem of _child) {
                makeFilterItem(filterContainer, `subCategories`, childElem['id'], childElem['label'], 'ps-3');
            }
        }
    }
}

function createTag(_name, _id){
    const clone = envElements.tabTemplate.content.cloneNode(true);
    const cloneTab = clone.querySelector('.alloffers-tag');
    cloneTab.setAttribute('data-code', _id);
    cloneTab.querySelector('.alloffers-tag__text').textContent = _name;
    envElements.tagContainer.insertBefore(cloneTab, envElements.tagClearBtn);
    cloneTab.addEventListener('click', ()=>{
        clearDetailTag(cloneTab);
        getDefaultDataInit('viewResult').then(() => {
            _filterExpand ? expandPanel() : collapsePanel();
            console.log('filter new search');
        });
    });
}

function quickTagsRemove(_val){
    const _tagsTemp = envElements.tagContainer.querySelectorAll(`.alloffers-tag.tag[data-code="${_val}"]`);
    for(const _tagTemp of _tagsTemp){
        _tagTemp.remove();
    }
}
function quickSubCatTagsSYNC(_val, _chk){
    const _subCatCheckOnly = envElements.filterPanel.querySelectorAll(`.subCategories-input[value="${_val}"]`);
    for(const dupBox of _subCatCheckOnly){
        dupBox.checked = _chk;
    }
}
function updateQuickTags(_name, _val, _chk){
    quickSubCatTagsSYNC(_val, _chk);
    _chk ? createTag(_name, _val) : quickTagsRemove(_val);
    checkQuickTags();
}

function checkFilterStats(_group, _groupName){
    if (!_group || !_payload['filters'][_groupName]) return;
    for (const element of _group) {
        if (_payload['filters'][_groupName].includes(element.value)) {
            element.checked = true;
        }
        element.addEventListener('click', ()=>{
            updateQuickTags(element.parentNode.querySelector('.alloffers-filters__name').textContent, element.value,element.checked);
            getFiltersVal(_groupName);
        });
    }
}

function errorMessage(_eventName, _req) {
    if (_eventName === 'viewResult') {
        setNoResultMessage(true, envElements.noOffers);
        qfPanel(false);
        _filterExpand = true;
    } else if (_req.sentPayload && _req.sentPayload.filters.activityType) {
        setNoResultMessage(true, envElements.noOffersForYou);
    } else if (_req.sentPayload && _req.sentPayload.filters.states) {
        setNoResultMessage(true, envElements.noSavedOffers);
    } else {
        setNoResultMessage(true, envElements.apiFails);
    }
    return;
}

function getSubCategory(data){
    return data['metadata']['categories']
        .filter(categoryItem => categoryItem.subCategories) // Filter out categories without subCategories
        .reduce((accumulator, categoryItem) => {
            categoryItem.subCategories.forEach(subCategoryItem => {
                // Check if subCategoryItem with the same id already exists in accumulator
                const existingSubCategoryItem = accumulator.find(
                    existing => existing.id === subCategoryItem.id
                );
                // If not found, add the subCategoryItem to accumulator
                if (!existingSubCategoryItem) {
                    accumulator.push(subCategoryItem);
                }
            });
            return accumulator;
        }, []);
}

async function getDefaultDataInit(_eventName) {
    _filterExpand = false;
    if(!_activeSlide){ return; }
    const _dataType = _activeSlide.getAttribute('data-type');
    quickFilterInProgress = true;
    setNoResultMessage(false);
    duplicateLoaderCards(envElements.showcase);
    _payload['pagination']['page'] = 1;
    const _req = getRequestApiReady(_payload,null);
    try {
        const data = await useFetch(_req.apiURL, true, _req.sentPayload, 'offers');
        if(!data['metadata'] || !data['offers'] || data['error']){
            quickFilterInProgress = false;
            // Uncomment following line if more fail detail needed technically or use general API fail message.
            // data['error']==='network' ? setNoResultMessage(true, envElements.apiFails) : setNoResultMessage(true, envElements.customError, data['error'] || 'Unknown errors.');
            envElements.offersNum.textContent = '0';
            errorMessage(_eventName, _req);
        }
        _offersData = data['offers'];
        _totalOffersNum = data['metadata']['total'] || _offersData.length;
        // Initial filters data
        const isAllOffers = _activeSlide.dataset.type === 'all-offers';
        const trendMax = parseInt(envElements.offersNum.dataset.shownumTrending,10);
        if (_activeSlide.dataset.type === 'mini-feed'){
            _totalOffersNum = Math.min(parseInt(data.metadata.total, 10), trendMax);
        }
        _partners = data['metadata']['partners'];
        _category = isAllOffers && data['metadata']['categories'];
        _subCategory = isAllOffers && getSubCategory(data);
        _offerCounter = 0;
        envElements.showcase.innerText='';
        displayData(_offersData); // initial load offer
        envElements.offersNum.textContent = _totalOffersNum || _offersData.length; // Was blank and dynamically updated in displayData() function in last line
        // console.warn(`${parseInt(data['metadata']['TotalOffers'],10)-_totalOffersNum} mismatched partner skipped.`);
        filterItemsGen(_partners, 'partners');
        filterItemsGen(_category, 'categories');

        _partnersInputs = document.querySelectorAll('.partners-input');
        checkFilterStats(_partnersInputs, 'partners');
        getFilterNum(_partnersInputs,'partners');
        _categoryInputs = document.querySelectorAll('.categories-input');
        checkFilterStats(_categoryInputs, 'categories');
        getFilterNum(_categoryInputs, 'categories');
        _subCategoryInputs = document.querySelectorAll('.subCategories-input');
        checkFilterStats(_subCategoryInputs, 'subCategories');
        getFilterNum(_subCategoryInputs, 'subCategories');

        const _lastOffer = envElements.showcase.querySelector('.alloffers-offers__card:last-child');
        if(_dataType!=='mini-feed'){ // Do not load more offers for mini-feed(trending/personalized)
            _lastOffer && lastCardObserver.observe(_lastOffer);
        }
        if(_totalOffersNum <= 0){
            errorMessage(_eventName, _req);
        }
        quickFilterInProgress = false;
    } catch (error) {
        errorMessage(_eventName, _req);
        quickFilterInProgress = false;
        console.error('Init API error:', error);
    }
}

export function reloadPage(aaTracking=true){
    getDefaultDataInit().then(() => {
        offersDataSync(_offersData);
        aaTracking && pageloadAAEmit();
    })
}
function checkSavedPageSubNav(_type){
    const offersSubNav = document.querySelector(".offers-subnav");
    if(!offersSubNav){return;}
    const savedOfferCTA = offersSubNav.querySelector('.cmp-button.ctabuttonroundhalo');
    _type==='saved' ? savedOfferCTA.classList.add("disabled") : savedOfferCTA.classList.remove("disabled");
}
function initialQuickFilters(){
    for (const _element of envElements.quickFilterTabs) {
        const _dataKey = _element.dataset.key;
        const _status = _element.dataset.status;
        (_filterParam === _dataKey) && _element.classList.add('active');
        _element.addEventListener('click', ()=>{
            if(quickFilterInProgress){
                console.log("Rendering in progress...");
                return;
            }
            const currentOfferPage = getActiveTab();
            if(!currentOfferPage || currentOfferPage.dataset.type !== _element.dataset.type){
                checkSavedPageSubNav(_element.dataset.type)
                abortAPIRequest();
                clearQuickFilters();
                clearAllFilters();
                _element.classList.add('active');
                _activeSlide = _element;
                _activeSlide.hasAttribute('data-panel-hide') ? qfPanel(true) : qfPanel(false);
                reloadPage();
            }
        });
        (isAuthed() && _status==='unauthed') || (!isAuthed() && _status==='authed') ? _element.remove() : _element.classList.remove('d-none');
    }
    _activeSlide = getActiveTab();
    if(_activeSlide){
        _activeSlide.hasAttribute('data-panel-hide') ? qfPanel(true) : qfPanel(false);
    }else{
        console.log("No active quick filter detected. Set first tab active");
        _activeSlide = document.querySelector('.alloffers-tabs__slides__tab');
        _activeSlide.classList.add('active');
        _activeSlide.hasAttribute('data-panel-hide') ? qfPanel(true) : qfPanel(false);
    }

    const offersSubNav = document.querySelector(".offers-subnav");
    if(offersSubNav){
        const savedOfferCTA = offersSubNav.querySelector('.cmp-button.ctabuttonroundhalo');
        _offersSubNavURL = savedOfferCTA.getAttribute('href');
    }
    checkSavedPageSubNav(_activeSlide.dataset.type);
}

function resetFiltersPanel(){
    envElements.filterBtn.addEventListener('click', ()=>{
        _filterExpand ? collapsePanel() : expandPanel();
    });
    envElements.panelToggle.addEventListener('click', ()=>{
        _filterExpand ? collapsePanel() : expandPanel();
    });
    envElements.tabletFilterBG.addEventListener('click', (e)=>{
        e.target.classList.contains('alloffers-offers__filter-bg') && collapsePanel();
    });
    envElements.closeBtnPanel.addEventListener('click', ()=>{
        collapsePanel();
    });
    envElements.filterViewResultBtn.addEventListener('click',()=>{
        getDefaultDataInit('viewResult').then(() => {
            _filterExpand ? expandPanel() : collapsePanel();
            console.log('Criteria sent for search new offers result!!!');
        });
    });
    envElements.filterResetBtn.addEventListener('click', ()=>{
        clearAllFilters();
        getDefaultDataInit().then(() => {
            collapsePanel();
        });
    });
    // const regionBtn = document.querySelector('.alloffers-tabs__apart__location__region');
    // regionBtn.querySelector('.region-name').innerText = _payload['filters']['region'] ? regionData[_payload['filters']['region']] : regionData[defaultLocal];

    checkFilterStats(envElements.typesInputs, 'types');

    envElements.tagClearBtn.addEventListener('click',()=>{
        clearAllFilters();
        getDefaultDataInit().then(() => {
            // tag cleared
        });
    });
    envElements.tagContainer.appendChild(envElements.tagClearBtn);
    checkQuickTags();
}
function lowerCaseNoSpace(inputString) {
    return inputString.toLowerCase().replace(/\s/g, '');
}

function mapSortedByValue(sortedBy) {
    sortedBy = lowerCaseNoSpace(sortedBy);
    if (sortedBy === "massOffer,partnerid,-displaypriority,enddate" || sortedBy === "default") {
        return "default";
    }else if (sortedBy === "enddate,partnerid,-displaypriority" || sortedBy === "endingSoonest") {
        return "endingSoonest";
    }
    return "default";
}

function initialFiltersPanel(){
    const _sortByInputs = document.querySelectorAll('.sort-by-input');
    for (const element of _sortByInputs) {
        const _sortByCV = mapSortedByValue(_payload['pagination']['sortedBy']);
        if (_sortByCV === element.value.toLowerCase()){ element.checked = true; }
        element.addEventListener('click', () => getFiltersSortedByVal());
    }
    for (const element of envElements.accordionButtons) {
        element.addEventListener('click', () => {
            if(element.classList.contains('active')){
                element.classList.remove('active');
            }else{
                clearFilterAccordionActive();
                element.classList.add('active');
            }
        });
    }
    for (const element of envElements.filterBackButtons) {
        element.addEventListener('click', toggleActiveClass);
    }
}

function initResizeEvent(){
    const allOffersObserver = new ResizeObserver(() => {
        const viewWidth = document.documentElement.clientWidth;
        if(viewWidth > 1024 && _filterExpand){
            envElements.body.classList.remove('overflow-hidden');
        }
    });
    allOffersObserver.observe(envElements.filterPanel);
}

function commonExec(){
    initialQuickFilters();
    initPayload();
    initialFiltersPanel();
    regionListInitial(_payload);
    clearAllFilters();
    resetFiltersPanel();
    basicInitSetup();
    getDefaultDataInit().then(() => {
        offersDataSync(_offersData);
        pageloadAAEmit();
        unloadOfferPage();
    })
    .catch(error => {
        console.error('getDefaultDataInit() Init Error:', error);
    });
}

(() => {
    document.addEventListener("DOMContentLoaded", () => {
        if(!document.querySelector('.alloffers')){ return; }
        // Initial ********************************
        if(isAuthed()){
            if(sessionStorage.getItem('am_savedOffer')){
                hasAwaitingTasks().then(() => {
                    _filterParam = "savedoffers";
                    console.log("Process saving offer event...");
                    commonExec();
                })
            }else{
                commonExec();
            }
            (!sessionStorage.getItem('am_locMutated')) && sessionStorage.setItem('am_locMutated', 'false');
        }else{
            commonExec();
        }
        initResizeEvent();
    });
})();
