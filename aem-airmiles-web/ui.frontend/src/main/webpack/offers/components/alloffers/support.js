import {envElements, modalElements} from "./envElements"
import {apiURL, getDomBFFDomainUrl, getHeaders, useFetch, useFetchPost} from "../../../dependencies/js/api";
import {isAuthed, getMemdeets, getCookie} from "../../../dependencies/js/utils";
import {saveClick, optInLabelUpdater, activeOfferData, isCardLinked} from "./detailFunc";
// import {regionData} from "../offersregion/offersRegion";
let basicInitialized = false; //ensure basicInitSetup() only triggered once if multiple calls

const cookieProv = getCookie('province') || 'ON'; // Get region from cookie 'region'
// cookieProv = regionData.hasOwnProperty(cookieProv) ? cookieProv : 'ON'; // If 'Province' cookie value doesn't contain matched region, assign 'ON' as default.
export const defaultLocal = getMemdeets() ? getMemdeets()['region'] : cookieProv; // If not authenticated(memdeet), use default region 'cookieProv'.

export function offersPayloadModel(_region, _num){
    return {
        "filters": {
            "region": _region,
            "partners": [],
            "categories": [],
            "subCategories": [],
            "types": []
        },
        "pagination": {
            "sortedBy": "Default",
            "offersPerPage": parseInt(_num, 10),
            "page": 1
        }
    }
}

export function getSaveModel(_stat){
    _stat = !_stat ? 'SAVED' : _stat;
    return {
        "states": [
            {
                "name": "SAVE",
                "value": _stat
            }
        ]
    }
}
export function getOptInModel(_stat){
    _stat = !_stat ? 'SAVED' : _stat;
    const _states = [{
            "name": "SAVE",
            "value": _stat
        }];
    if(_stat !== 'UNSAVED'){
        _states.push({
           "name": "OPT_IN",
           "value":"OPTED_IN"
        });
    }
    return {
        "states": _states
    }
}

export function getActiveTab(){
    return document.querySelector('.alloffers-tabs__slides__tab.active');
}

export function updatePayload(_payloadInput){
    sessionStorage.setItem('am_OSCP', JSON.stringify(_payloadInput));
}

export function checkIsCLO(){
    if(!getActiveTab()){
        return false;
    }
    return getActiveTab().dataset.type === 'clo-offers';
}

export async function fetchGetData(apiUrl) {
    try {
        const response = await fetch(apiUrl, { method: 'GET', credentials: 'include' , headers: getHeaders()});
        if (!response.ok) {
            return {'error':'network issue'}
        }
        return await response.json();
    } catch (error) {
            return {'error':error}
    }
}
function addArrayInParameter(parKey, parArray){
    if (parArray && parArray.length > 0){
        const arrString = parArray.map(item => encodeURIComponent(item)).join(',');
        return `&${parKey}=${arrString}`;
    }else{
        return '';
    }
}
function addStringInParameter(parKey, parString){
    if (parString && parString.length > 0){
        return `&${parKey}=${parString}`;
    }else{
        return '';
    }
}
function determineSort(dataType, payload) {
    let sort = 'regionrelevance';
    if (dataType === 'mini-feed' && isAuthed()) {
        const userRegion = getMemdeets()['region'].toUpperCase();
        const payloadRegion = payload['filters']['region'].toUpperCase();
        if (userRegion === payloadRegion) {
            sort = 'collectorrelevance';
        }
    }
    return sort;
}

function closeDetail(){
    envElements.body.classList.remove('overflow-hidden');
    const _sImage = modalElements.detailTemplate.querySelector('.alloffers-detail__image').querySelector('img');
    _sImage.setAttribute('src','');
    modalElements.detailTemplate.classList.remove('active');
}

function detailOfferCardInit(){
    const _termBtn = modalElements.detailTemplate.querySelector('.alloffers-detail__termsTitle');
    const _termDropIcon = _termBtn.querySelector('.am-icon');
    _termBtn.addEventListener('click', ()=>{
        if (modalElements.detailTermsBody.classList.contains('d-none')) {
            modalElements.detailTermsBody.classList.remove('d-none');
            _termDropIcon.classList.add('am-icon-functional-arrow-up');
            _termDropIcon.classList.remove('am-icon-functional-arrow-down');
        } else {
            modalElements.detailTermsBody.classList.add('d-none');
            _termDropIcon.classList.add('am-icon-functional-arrow-down');
            _termDropIcon.classList.remove('am-icon-functional-arrow-up');
        }
    });

    function detailSaveBtnHandler(){
        const pickedOfferId = modalElements.detailedSaveBtn.dataset.savedId;
        const _offerHeart = document.querySelector(`[data-saved-id="${pickedOfferId}"]`);
        if(activeOfferData['mechanisms'][0]['mechanismType']==="optIn"){
            const _mechanism = activeOfferData['programType'].toLowerCase()==='cardlinked' ? 'isCardLinkedCTA' : 'optIn';
            optInLabelUpdater(pickedOfferId, activeOfferData, _mechanism);
        }
        saveClick(_offerHeart, true, activeOfferData);
    }
    modalElements.detailedSaveBtn.addEventListener('click', detailSaveBtnHandler);
}

export function basicInitSetup(){
    if(!modalElements.detailTemplate || basicInitialized)return;
    basicInitialized=true;
    modalElements.detailTemplate.addEventListener('click',(e)=>{
        e.target.classList.contains('alloffers-detail') && closeDetail();
    });

    const _fullContent =  modalElements.detailTemplate.querySelector('.alloffers-detail__full');
    const _description = modalElements.detailTemplate.querySelector('.alloffers-detail__full__content');
    const _button = _fullContent.querySelector('a');
    _button.addEventListener('click',()=>{
        _description.classList.remove('d-none');
        _button.classList.add('d-none');
    });

    detailOfferCardInit();
    modalElements.detailBackBtn.addEventListener('click',()=>{
        closeDetail();
    });
}

function apiParameterSetup(_payload,_apiURL,_src){
    let sortVar = '';
    if(_payload['pagination']['sortedBy'].toLowerCase() === 'default'){
        sortVar = 'massOffer,partnerId,-displayPriority,endDate';
    }else if(_payload['pagination']['sortedBy'].toLowerCase() === 'endingsoonest'){
        sortVar = 'endDate,partnerId,-displayPriority';
    }else{
        sortVar = _payload['pagination']['sortedBy'];
    }
    _apiURL += `sort=${sortVar}`;

    const massOfferFilter = _payload['filters']['massOffer'];
    if (massOfferFilter) {
        const moArray = massOfferFilter.split(',');
        const hasMassOffers = moArray.includes('massOffers');
        const hasTargetedOffers = moArray.includes('targetedoffers');
        if (hasMassOffers && hasTargetedOffers) {
            _apiURL += '';
        } else if (hasMassOffers || hasTargetedOffers) {
            _apiURL += `&massOffer=${!hasTargetedOffers}`;
        }
    }
    if(_src === "all-offers"){
        _apiURL += addArrayInParameter('type', _payload['filters']['types']) || '';
        _apiURL += addArrayInParameter('partner_id', _payload['filters']['partners']) || '';
        _apiURL += addArrayInParameter('category_id', _payload['filters']['categories']) || '';
        _apiURL += addArrayInParameter('subcategory_id', _payload['filters']['subCategories']) || '';
    }else if(_src === "carousel" || _src === "clo-offers"){
        _apiURL += addStringInParameter('type',_payload['filters']['types'] );
        _apiURL += addStringInParameter('partner_id',_payload['filters']['partners'] );
        _apiURL += addStringInParameter('category_id',_payload['filters']['categories'] );
        _apiURL += addStringInParameter('subcategory_id',_payload['filters']['subCategories'] );
        _apiURL += addStringInParameter('program_type',_payload['filters']['programType'] );
        _apiURL += addStringInParameter('promotion_id',_payload['filters']['promotionId'] );
    }

    return _apiURL;
}
export function getRequestApiReady(_payload,_src = null){
    const _dataType = _src || getActiveTab().getAttribute('data-type');
    const _page = parseInt(_payload['pagination']['page'], 10);
    const _method = 'GET';
    const _showNum = envElements.offersNum && parseInt(envElements.offersNum.dataset.shownum, 10);
    let _offset = 0;
    let _limit = _showNum ? _showNum : 10;
    let _apiURL = `${getDomBFFDomainUrl() + apiURL.domBffOffers + '?'}`;
    if(_dataType==='mini-feed'){
        const sort = determineSort(_dataType, _payload);
        _limit = parseInt(envElements.offersNum.dataset.shownumTrending, 10);
        _apiURL += `sort=${sort}`;
    }else if(_dataType==='all-offers'){
        delete _payload['filters']['massOffer'];
        delete _payload['filters']['programType'];
        delete _payload['filters']['promotionId'];
        _apiURL = apiParameterSetup(_payload,_apiURL,_dataType);
        _offset = (_page - 1) * _limit;
    }else if(_dataType==='carousel'){
        _apiURL = apiParameterSetup(_payload,_apiURL,_dataType);
        _limit = _payload['pagination']['offersPerPage'];
        _offset = 0;
    }else if(_dataType==='clo-offers'){
        _apiURL = apiParameterSetup(_payload,_apiURL,_dataType);
        _limit = _payload['pagination']['offersPerPage'];
        _offset = (_page - 1) * _limit;
    }else if(_dataType==='saved'){
        // temporary using sort=saved to fetch saved offers for authenticated users
        _apiURL += `states=SAVED`;
        _offset = (_page - 1) * _limit;
    }
    _apiURL += `&region=${_payload['filters']['region']}`;
    _apiURL += `&extended_metadata=true`;
    _apiURL += `&offset=${_offset}`;
    _apiURL += `&limit=${_limit}`;
    return {
        dataType: _dataType,
        apiURL: _apiURL,
        method: _method,
        sentPayload: _payload
    };
}

export function loadDetailOffers(_payload, partnerID){
    let _apiURL = `${getDomBFFDomainUrl() + apiURL.domBffOffers + '?'}`;
    const _region = _payload && _payload.filters && _payload.filters.region ? _payload.filters.region : defaultLocal;
    _apiURL += `region=${_region}`;
    _apiURL += `&extended_metadata=true`;
    _apiURL += `&offset=1`;
    _apiURL += `&limit=8`;
    _apiURL += `&sort=massOffer,partnerId,-displayPriority,endDate`;
    _apiURL += addArrayInParameter('partner_id',[partnerID]) || '';
    return useFetch(_apiURL, true, null,'offers')
        .then(detailData => {
            if (detailData) {
                return detailData
            }
        })
        .catch(error => {
            console.error('Offer Detail Data Error:', error);
            return error;
        });
}

export function duplicateLoaderCards(_loaderContainer){
    _loaderContainer.innerText = '';
    const _num = document.documentElement.clientWidth > 1024 ? 4 : 6;
    for(let i = 0; i < _num; i++){
        const cloneLoaderTemp = envElements.loaderCardTemplate.content.cloneNode(true);
        _loaderContainer.appendChild(cloneLoaderTemp);
    }
}
export function getCurrentRegion(){
    const currentTab = document.querySelector(".alloffers-tabs__apart__regionlist__item.active")
    return currentTab ? currentTab.dataset.code : defaultLocal;
}

function getRequestConfig(_id, _type, _val=null){
    return {
        "apiURL": `${getDomBFFDomainUrl()+apiURL.domBffOffers}/${_id}/states`,
        "method": "PUT",
        "sentPayload": _type === 'optedIn' ? getOptInModel(_val) : getSaveModel(_val),
        "dataType": _type,
        "region": getCurrentRegion().toLowerCase()
    }
}
export async function saveOffersHandler(_elem, _offerData = null){
    const heartEmpty = _elem && _elem.querySelector('.am-icon2-heart-empty');
    const isSaveVal = heartEmpty ? 'SAVED' : 'UNSAVED'; // If empty-heart, saved!! else unsaved
    const saveType = isCardLinked(_offerData) ? 'optedIn' : 'saved';
    const _req = getRequestConfig(_elem.dataset.savedId, saveType, isSaveVal);
    let returnObj
    await useFetchPost(_req.apiURL, false, _req.sentPayload, _req.method, 'saving-offer', _req.region)
        .then(() => {
            returnObj = {"id":_elem.dataset.savedId,"state":isSaveVal}
        })
        .catch(error => {
            returnObj = {"id":_elem.dataset.savedId,"state":isSaveVal,"error":error}
        })
        .finally(()=>{
            // Do next action after save event completed.
        });
    return returnObj;
}

export async function saveOfferFromPageLoad(_id){
    const _isOptInItem = sessionStorage.getItem('am_optInOffer');
    const _req = getRequestConfig(_id, _isOptInItem==='true' ? 'optedIn' : 'saved');
    let returnObj
    await useFetchPost(_req.apiURL, false, _req.sentPayload, _req.method, 'saving-offer', _req.region)
        .then(() => {
            returnObj = {"id":_id}
        })
        .catch(error => {
            returnObj = {"id":_id,"error":error}
        });
    return returnObj;
}
export async function optInOffersHandler(_opItem){
    const _req = getRequestConfig(_opItem.dataset.pid, 'optedIn');
    let returnObj;
    await useFetchPost(_req.apiURL, false, _req.sentPayload, _req.method, 'saving-offer', _req.region)
        .then(() => {
            returnObj = {"id":_opItem.dataset.pid}
        })
        .catch(error => {
            returnObj = {"id":_opItem.dataset.pid,"error":error}
        })
        .finally(()=>{
            // Do next action after save event completed.
        });
    return returnObj;
}

function checkDuplicatedItem(combinedData,key,item,subItem=false){
    const combinedOffers = subItem ? [...combinedData[0][item][subItem], ...combinedData[1][item][subItem]] : [...combinedData[0][item], ...combinedData[1][item]];
    const uniqueOffersMap = new Map();
    combinedOffers.forEach(offer => {
        uniqueOffersMap.set(offer[key], offer);
    });
    return [...uniqueOffersMap.values()];
}
export function combineAndFilter(combinedData) {
    const combinedOffersArray = checkDuplicatedItem(combinedData,'id','offers',false);
    const combinedPartnersArray = checkDuplicatedItem(combinedData,'PartnerId','metadata','Partners');
    const combinedCate1Array = checkDuplicatedItem(combinedData,'CategoryId','metadata','OfferCategory1');
    const combinedCate2Array = checkDuplicatedItem(combinedData,'CategoryId','metadata','OfferCategory2');
    return {
        offers: combinedOffersArray,
        metadata: {
            Partners: combinedPartnersArray,
            OfferCategory1: combinedCate1Array,
            OfferCategory2: combinedCate2Array,
            TotalOffers: combinedOffersArray.length
        }
    };
}