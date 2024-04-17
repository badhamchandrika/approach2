import {envElements, modalElements} from "./envElements";
import {saveOfferFromPageLoad, optInOffersHandler, saveOffersHandler} from "./support";
import {checkOffersInDetail, reloadPage, setNoResultMessage} from './alloffers';
import {createElement, isAuthed, sanitizeHTML, shortDate} from "../../../dependencies/js/utils";
import {bodyAttr, offerObserver, pageloadAAEmit} from "./offersanalytics";
import JsBarcode from "jsbarcode";
import {showToast} from "../../../dependencies/components/common/amtoast/v1/amtoast";

const _mainContainer = envElements.mainContainer;
const _loginPage = _mainContainer ? _mainContainer.dataset.loginPage :'';
let inSaving = false;
export let rdy2use, optInText, useCardText;
export let activeOfferData;

function getMechanismConfig() {
    if (document.querySelector('.alloffers')) {
        return 'alloffers-mechanisms';
    } else if (document.querySelector('.offerscarousel')) {
        return 'carousel-mechanisms';
    } else {
        return null;
    }
}

export function getMechTable(elementID){
    if(!elementID)return;
    const mechElement = document.querySelector(`#${elementID}`);
    if(mechElement) {
        const tempElement = mechElement.content;
        const spanElements = tempElement.querySelectorAll('span');
        const jsonData = {};
        spanElements.forEach(function (spanElement) {
            const dataCode = spanElement.getAttribute('data-code');
            const dataName = spanElement.getAttribute('data-name');
            (dataCode==='noAction') && (rdy2use = dataName);
            (dataCode==='optIn') && (optInText = dataName);
            (dataCode==='cardLinkedMastercard') && (useCardText = dataName);
            if (dataCode && dataName) {
                jsonData[dataCode] = dataName;
            }
        });
        return jsonData;
    }
}

const _mechanismTable = getMechTable(getMechanismConfig());
export function getMechType(_mechType){
    const typeMap = {
        'noAction': [_mechanismTable["noAction"], 'am-icon2-task_alt'],
        'button': [_mechanismTable["button"], 'am-icon2-external_link'],
        'coupon': [_mechanismTable["coupon"], 'am-icon2-person'],
        'barcode': [_mechanismTable["barcode"], 'am-icon2-barcode_scanner'],
        'optIn': [_mechanismTable["optIn"], 'am-icon2-heart-empty'],
        'plu': [_mechanismTable["plu"], 'am-icon2-person'],
        'optinValue': [_mechanismTable["optinValue"]],
        'cardLinked': [_mechanismTable["cardLinked"], 'am-icon2-explore-credit-cards'],
        'cardLinkedMastercard': [_mechanismTable["cardLinkedMastercard"]],
        'cardLinkedOptInOffer': [_mechanismTable["cardLinkedOptInOffer"]],
        'cardLinkedOptIn': [_mechanismTable["cardLinkedOptIn"]]
    };
    const defaultVal = [_mechanismTable["noAction"], 'am-icon2-task_alt'];
    // return typeMap[_mechType] || defaultVal;
    for (const key in typeMap) {
        if (_mechType.includes(key)) {
            return typeMap[key];
        }
    }
    return defaultVal;
}

export function savedChecker(_stat, _container, isOptIn=false){
    const _icon = _container.querySelector('.am-icon2');
    const _flip = _stat === 'SAVED';
    if(_flip || isOptIn){
        _icon.classList.remove('am-icon2-heart-empty');
        _icon.classList.remove('full');
        _icon.classList.add('am-icon2-heart-full');
        _icon.classList.add('transition');
        setTimeout(() => {
            _icon.classList.remove('transition');
        }, 2000);
    }else{
        _icon.classList.remove('am-icon2-heart-full');
        _icon.classList.remove('transition');
        _icon.classList.add('am-icon2-heart-empty');
        _icon.classList.add('full');
        setTimeout(() => {
            _icon.classList.remove('full');
        }, 2000);
    }
}

export function getItemByID(array, idKey, idValue) {
    if(!array) return;
    return array.find(item => item[idKey] === idValue);
}

const updateStateOnFly = (name, value, cardInfo) => {
    const currentDate = new Date().toISOString();
    if (cardInfo.states && Array.isArray(cardInfo.states)) {
        const stateIndex = cardInfo.states.findIndex(s => s.name === name);

        if (stateIndex !== -1) {
            // State with the given name already exists, update it
            const state = cardInfo.states[stateIndex];
            state.value = value;
            state.updatedAt = currentDate;
        } else {
            // State with the given name doesn't exist, add a new state
            cardInfo.states.push({ name, value, updatedAt: currentDate });
        }
    } else {
        // If cardInfo.states is not an array or doesn't exist, create a new array
        cardInfo.states = [{ name, value, updatedAt: currentDate }];
    }
};
export function isCardLinked(_offerData){
    return _offerData && _offerData['programType'] && _offerData['programType'].toLowerCase() === 'cardlinked';
}
export function optInLabelUpdater(_id, _cardInfo, _mechanism){
    const mechLabels = document.querySelectorAll(`.alloffers-offers__card__mechanisms__label[data-pid="${_id}"]`);
    if(_mechanism === 'optIn' && !isCardLinked(_cardInfo)){
        _cardInfo['mechanisms'][0]['mechanismType']='noAction';
        for(const mechLabel of mechLabels){
            mechLabel.dataset.type = 'noAction';
            const _mechType = getMechType('noAction');
            const _icon = mechLabel.querySelector('.alloffers-offers__card__mechanisms__icon');
            _icon.classList.remove('am-icon2-heart-empty');
            _icon.classList.add(_mechType[1]);
            const _text = mechLabel.querySelector('.alloffers-offers__card__mechanisms__label__text');
            _text.innerText = _mechType[0];
        }
    }else if(_mechanism === 'isCardLinkedCTA'){
        // update or add properties in [states].
        _cardInfo.states = _cardInfo.states || []; // Initialize to an empty array if undefined or null.
        updateStateOnFly("SAVE", "SAVED", _cardInfo);
        updateStateOnFly("OPT_IN", "OPTED_IN", _cardInfo);
    }
    const currentDetail = modalElements.detailTemplate.querySelector('.alloffers-detail__tag');
    const ctaButton = currentDetail.querySelector('.ctabutton');
    ctaButton && ctaButton.remove();
    if(currentDetail.dataset.pid === _id){
        modalElements.detailTemplate.querySelector('.alloffers-detail__tab__text').textContent= _mechanism === 'optIn' ? rdy2use : useCardText;
    }
}
function cardLinkedCheck(elem, _offerData, isInDetail){
    const mechanismCardLabel = elem.parentElement.querySelector('.alloffers-offers__card__mechanisms__label');
    const mechanismDetailArea = elem.parentElement.querySelector('.alloffers-detail__tag');
    if(isCardLinked(_offerData)) {
        updateStateOnFly("OPT_IN", "OPTED_IN", _offerData);
    }
    if(!isInDetail && mechanismCardLabel){ // from label button of offer card
        const clickType = mechanismCardLabel.dataset.type;
        if(clickType==='optIn'){
            optInLabelUpdater(_offerData['id'], _offerData , 'optIn');
        }
    }else if(mechanismDetailArea){ // from detail mechanism area
        const clickType = mechanismDetailArea.dataset.type;
        const optInBtn = mechanismDetailArea.querySelector('a.non-clo');
        if(clickType==='optIn' && optInBtn){
            console.log("clickType==='optIn' && optInBtn",optInBtn);
        }
    }
}
function setSessionSaveOffer(_id,_offerData){
    sessionStorage.setItem('am_savedOffer',btoa(_id));
    sessionStorage.setItem('am_optInOffer', isCardLinked(_offerData));
}
function removeSaveOfferSessions(_id,_offerData){
    sessionStorage.removeItem('am_savedOffer');
    sessionStorage.removeItem('am_optInOffer');
}
function checkSaveIconStatus(_saveHeartBtn){
    const _heartIcon = _saveHeartBtn.querySelector('.saved-heart');
    _saveHeartBtn.dataset.trackId = _heartIcon.classList.contains('am-icon2-heart-full') ? 'offer-unsave-icon': 'offer-save-icon';
}
function isSaved(_saveHeartBtn){
    const _heartIcon = _saveHeartBtn.querySelector('.saved-heart');
    return !_heartIcon.classList.contains('am-icon2-heart-full');
}
export function saveClick(elem, isInDetail = false, _offerData = null){
    if(isAuthed()) {
        if(!inSaving){
            inSaving = true;
            showToast(isSaved(elem) ? 'offerSaveMsg' : 'offerUnSaveMsg'); // message value from Toast component
            checkSaveIconStatus(elem);
            saveOffersHandler(elem, _offerData).then(res => {
                inSaving = false;
                if (res.error) return;
                const saveIcons = document.querySelectorAll(`[data-saved-id="${res['id']}"]`);
                for(const saveIcon of saveIcons){
                    savedChecker(res['state'], saveIcon, false);
                }
                cardLinkedCheck(elem, _offerData, isInDetail);

                // Remove the card when click on save(heart icon) in Saved Offer page(must be un-save states)
                const currentTab = document.querySelector('.alloffers-tabs__slides__tab.active');
                if(currentTab.dataset.type === 'saved'){
                    const showNumber = parseInt(document.getElementsByClassName('alloffers-showing__numbers')[0].innerText, 10);
                    const _offerCard = document.getElementById(`saved_offers-${res['id']}`);
                    _offerCard.remove();
                    document.getElementsByClassName('alloffers-showing__numbers')[0].innerText = showNumber - 1;
                    const offerQuantity = document.getElementsByClassName('alloffers-showing__numbers')[0].innerText;
                    if(offerQuantity === '0') {
                        setNoResultMessage(true, envElements.noSavedOffers);
                    }
                }
            })
        }else{
            console.log("Saving in progress");
        }
    }else{
        setSessionSaveOffer(elem.closest('a').dataset.savedId,_offerData);
        window.open(_loginPage, '_self');
    }
}

function optInClickExt(optInBtn, _cardInfo, _mechanism){
    if(isAuthed()) {
        optInOffersHandler(optInBtn).then(res => {
            if (res.error) return;
            const saveIcons = document.querySelectorAll(`[data-saved-id="${res['id']}"]`);
            for(const saveIcon of saveIcons){
                savedChecker('SAVED', saveIcon, true);
                optInLabelUpdater(res['id'], _cardInfo, _mechanism);
            }
        })
    }else{
        setSessionSaveOffer(_cardInfo.id,_cardInfo);
        window.open(_loginPage, '_self');
    }
}

function detailLoaderHide(hide = false){
    const _loaderElements = modalElements.detailTemplate.querySelectorAll('.loader-shin');
    for(const _element of _loaderElements){
        hide ? _element.classList.add('d-none') : _element.classList.remove('d-none');
    }
}

function heartIconFull(_icon, isSaved = false){
    if(isSaved){
        _icon.classList.add('am-icon2-heart-full');
        _icon.classList.add('full');
        _icon.classList.remove('am-icon2-heart-empty');
    }else{
        _icon.classList.remove('am-icon2-heart-full');
        _icon.classList.remove('full');
        _icon.classList.add('am-icon2-heart-empty');
    }
}

function checkOptInReady(_data){
    // Checking if mechanismType = optIn & states contains SAVE
    if(_data['mechanisms'][0]['mechanismType']==='optIn' && _data['states'] && (!_data['programType'] || _data['programType'].toLowerCase() !== 'cardlinked')){
        for (const key of _data['states']) {
            if (key['name'].toUpperCase()==='SAVE') {
                return true;
            }
        }
    }
    return false;
}
function checkOptedIn(_data){
    if(_data){
        for (const element of _data) {
            const entry = element;
            if (entry['name'] === "OPT_IN" && entry['value'] === "OPTED_IN") {
                return true;
            }
        }
    }
    return false;
}

function checkTimestamp(startDateString, endDateString){
    const startDate = new Date(startDateString);
    const endDate = new Date(endDateString);
    const currentDate = new Date();
    const _startEl= modalElements.detailTemplate.querySelector('.alloffers-detail__start-text');
    const _endEl= modalElements.detailTemplate.querySelector('.alloffers-detail__end-text');
    _startEl.classList.add('d-none');
    _endEl.classList.add('d-none');
    if (currentDate < startDate) {
        _startEl.classList.remove('d-none');
        return startDateString;
    } else if (currentDate >= startDate && currentDate <= endDate) {
        _endEl.classList.remove('d-none');
        return endDateString;
    } else {
        _endEl.classList.remove('d-none');
        // Pass the due date
        return endDateString;
    }
}

function createBarcode(_mDetailType, _cardInfo, _sTag){
    const cloneBarcode = envElements.barcodeTemplate.content.cloneNode(true);
    const bcSVG = cloneBarcode.querySelector('.alloffers-barcode');
    const barcodeType = _mDetailType.includes("CODE128") ? 'code128' : 'upc';
    bcSVG.setAttribute('id',`bc_${_cardInfo['id']}`);
    bcSVG.dataset.format = barcodeType; // either using code128 or upc
    bcSVG.dataset.width = '2';
    bcSVG.dataset.height = '36';
    bcSVG.dataset.value = sanitizeHTML(_cardInfo['mechanisms'][0]['mechanismValue']);
    bcSVG.dataset.textmargin = '0';
    bcSVG.dataset.fontoptions = 'normal';
    _sTag.appendChild(bcSVG);
    JsBarcode(`#bc_${_cardInfo['id']}`).init();
    bcSVG.setAttribute('width', '100%');
}
function createCLOMechanism(tagWrapper, isMasterCard=false){
    const _mechTable = getMechType('cardLinked');
    createElement('span', ['am-icon2', _mechTable[1]],tagWrapper,{},'');
    createElement('span', ['alloffers-detail__tab__text','detail-cardlinked'],tagWrapper,{},isMasterCard ? _mechanismTable['cardLinkedMastercard']:_mechanismTable['cardLinkedOptIn']);
}
function createDetailCTA(_mechanism, _sTag, _cardInfo, jsonData){
    const _mList = document.querySelector(`#${getMechanismConfig()}`);
    let _attr, _className, _innerText;
    if(_mechanism === "button"){
        _className = ['cmp-button', 'ctabuttonround', 'non-clo', 'w-100'];
        _attr = {"data-track-type":"button", "data-track-click":"screen",
            "data-track-id":"shop-now-btn",
            "href":_cardInfo['mechanisms'][0]['mechanismValue'],
            "data-track-json":jsonData};
        _innerText = _cardInfo['mechanisms'][0]['mechanismLabel'];
    }else if(_mechanism === "optIn"){
        _className = ['cmp-button', 'ctabuttonround', 'm-0', 'non-clo', 'w-100'];
        _attr = {"data-track-type":"button", "data-track-click":"screen",
            "data-track-id":"opt-in-now-btn", "data-track-json":jsonData};
        _innerText = getMechType("optinValue");
    }else if(_mechanism === "isCardLinkedCTA"){
        _className = ['cmp-button', 'ctabuttonround', 'm-0', 'is-clo', 'w-100'];
        _attr = {"data-track-type":_mList.dataset.cloOptinTrackType, "data-track-click":_mList.dataset.cloOptinClickId,
            "data-track-id":_mList.dataset.cloOptinTrackId, "href":"javascript:void(0);", "target":_mList.dataset.cloOptinTarget};
        _innerText = _mechanismTable['cardLinkedOptInOffer'];
    }
    const ctaDiv =  createElement('div', ['ctabutton', 'pt-3'], _sTag, {}, '');
    const ctaLink = createElement('a', _className, ctaDiv, _attr, _innerText);
    ctaLink.style.color = '#FFF';
    ctaLink.style.borderColor = '#1f68da';
    ctaLink.style.backgroundColor = '#1f68da';
    ctaDiv.style.cursor = "pointer";
    if(_mechanism === "optIn" || _mechanism === "isCardLinkedCTA"){
        const clickHandler = () =>  optInClickExt(_sTag, _cardInfo, _mechanism);
        ctaDiv.addEventListener('click', clickHandler);
    }
}
function createLinkCLO(_sTag, _cardInfo){
    const _mList = document.querySelector(`#${getMechanismConfig()}`);
    const _className = ['cmp-link'];
    const _innerText = _mList.dataset.cloCtaText;
    const _attr = {"data-track-type":_mList.dataset.cloCtaTrackType,
        "data-track-click":_mList.dataset.cloCtaClickId, "data-track-id":_mList.dataset.cloCtaTrackId,
        "href":_mList.dataset.cloCtaLink, "target":_mList.dataset.cloCtaTarget};
    const _ctaDiv =  createElement('div', ['ctalink', 'ctalink--arrowlink-new', 'pt-3'], _sTag, {}, '');
    const _ctaLink = createElement('a', _className, _ctaDiv, _attr, _innerText);
    createElement('em', ['am-icon2', 'am-icon2-arrow-right'], _ctaLink, {}, '');
    _ctaLink.style.color = '#1f68da';
}
function mechanismDetailTabHandler(_cardInfo){
    const _mDetailType = _cardInfo['mechanisms'][0]['mechanismType'];
    const _programType = _cardInfo['programType'];
    const _mechType = getMechType(_mDetailType);
    const _sTag = modalElements.detailTemplate.querySelector('.alloffers-detail__tag');
    _sTag.dataset.pid = _cardInfo['id'];
    _sTag.dataset.type = _mDetailType;
    const jsonData=JSON.stringify(_cardInfo);
    _sTag.textContent = '';
    const tagWrapper = createElement('div', ['alloffers-detail__tag__wrapper'],_sTag,{},'');
    if (_mDetailType === 'noAction') {
        if(_programType && _programType.toLowerCase() === 'cardlinked'){
            createCLOMechanism(tagWrapper, true);
            createLinkCLO(_sTag, _cardInfo);
        }else{
            createElement('span', ['am-icon2', _mechType[1]],tagWrapper,{},'');
            createElement('span', ['alloffers-detail__tab__text','detail-ready'],tagWrapper,{},_mechType[0]);
        }
    }else if(_mDetailType === 'button'){ // The CTA button of SHOP NOW
        createElement('span', ['am-icon2', _mechType[1]],tagWrapper,{},'');
        createElement('span', ['alloffers-detail__tab__text', 'detail-online'], tagWrapper, {}, _mechType[0]);
        createDetailCTA('button', _sTag, _cardInfo, jsonData);
    }else if(_mDetailType.startsWith("coupon")){
        createElement('span', ['am-icon2', _mechType[1]],tagWrapper,{},'');
        createElement('span', ['alloffers-detail__tab__text','detail-scanreceipt'],tagWrapper,{},_mechType[0]);
        createElement('span', ['alloffers-detail__tab__text','detail-coupon'],_sTag,{},_cardInfo['mechanisms'][0]['mechanismValue']);
    }else if(_mDetailType.startsWith("barcode")){
        createElement('span', ['am-icon2', _mechType[1]],tagWrapper,{},'');
        createElement('span', ['alloffers-detail__tab__text','detail-usecode'],tagWrapper,{},_mechType[0]);
        createBarcode(_mDetailType, _cardInfo, _sTag);
    }else if(_mDetailType === 'optIn') {
        if (checkOptInReady(_cardInfo) && (!_programType || _programType.toLowerCase() !== 'cardlinked')) {
            createElement('span', ['am-icon2', 'am-icon2-task_alt'],tagWrapper,{},'');
            createElement('span', ['alloffers-detail__tab__text', 'detail-ready'], tagWrapper, {}, rdy2use);
        } else if (_programType && _programType.toLowerCase() === 'cardlinked'){
            createCLOMechanism(tagWrapper, checkOptedIn(_cardInfo['states']));
            !checkOptedIn(_cardInfo['states']) && createDetailCTA('isCardLinkedCTA', _sTag, _cardInfo, jsonData); // Display CLO - CTA if not [states][OPTED_IN]
            createLinkCLO(_sTag, _cardInfo);
        } else { // The CTA for OPT IN
            createElement('span', ['am-icon2', _mechType[1]], tagWrapper, {}, '');
            createElement('span', ['alloffers-detail__tab__text', 'detail-optin'], tagWrapper, {}, _mechType[0]);
            createDetailCTA('optIn', _sTag, _cardInfo, jsonData);
        }
    }else if(_mDetailType === 'plu'){
        createElement('span', ['am-icon2', _mechType[1]],tagWrapper,{},'');
        createElement('span', ['alloffers-detail__tab__text','detail-masterCard'],tagWrapper,{},_mechType[0]);
        createElement('span', ['alloffers-detail__tab__text','detail-coupon'],_sTag,{},_cardInfo['mechanisms'][0]['mechanismValue']);
    }else{
        createElement('span', ['am-icon2', _mechType[1]],tagWrapper,{},'');
        createElement('span', ['alloffers-detail__tab__text','detail-default'],tagWrapper,{},_mechType[0]);
    }
}
function alterName(_name){
    if(_name === "carousel"){
        return 'clo';
    }
    return _name;
}
export function cardGenerator(gData, _offerCounter = 0, _source= null){
    const cloneTemp = envElements.cardTemplate.content.cloneNode(true);
    const cloneCard = cloneTemp.querySelector('.alloffers-offers__card');
    const pageNameOnCard = alterName(_source);
    cloneCard.id = _source ? `${_source}-${gData['id']}` : gData['id'];
    cloneCard.dataset.offerId = gData['id'];
    cloneCard.dataset.count = _offerCounter.toString();
    cloneCard.dataset.trackJson = JSON.stringify(gData);
    cloneCard.dataset.trackId = 'offer-detail';
    cloneCard.dataset.trackClick = 'offer-card';
    cloneCard.dataset.trackType = 'button';
    cloneCard.dataset.isOfferCard = pageNameOnCard;
    // Partner Detail Init. *** miniFeed cards loads different partner set ***
    const defaultLogo = '/etc.clientlibs/aem-airmiles-web/clientlibs/clientlib-offers-site/resources/images/AIRMILES_PLANE_WHT_RGB_E.svg';
    const partnerLogo = cloneCard.querySelector('.alloffers-offers__card__partner__logo').querySelector('img');
    const logoPath = gData['partnerLogo'] && gData['partnerLogo']['url'] ? gData['partnerLogo']['url'] : defaultLogo;
    partnerLogo.setAttribute('src',logoPath);
    partnerLogo.setAttribute('alt',gData['partnerLabel']);
    cloneCard.querySelector('.alloffers-offers__card__partner__name').innerText = gData['partnerLabel'];

    // Card Content Init
    const image = cloneCard.querySelector('.alloffers-offers__card__image');
    image.setAttribute('src',gData['image']['url']);
    image.setAttribute('alt',gData['awardType']);
    cloneCard.querySelector('.alloffers-offers__card__title').innerText = gData['awardShort'];
    cloneCard.querySelector('.alloffers-offers__card__description').innerText = gData['qualifierShort'];
    cloneCard.querySelector('.alloffers-offers__card__date-text').innerText = shortDate(gData['endDate'],envElements.lang === 'en'? 'E':'F');

    // Mechanisms Detail Init
    const CLO_MECH = getMechType('cardLinked');
    const _mechType = isCardLinked(gData) ? CLO_MECH : getMechType(gData['mechanisms'][0]['mechanismType']);
    cloneCard.querySelector('.alloffers-offers__card__mechanisms__icon').classList.add( _mechType[1] );
    cloneCard.querySelector('.alloffers-offers__card__mechanisms__label__text').innerText = checkOptInReady(gData) ? rdy2use : _mechType[0];

    const _saveHeartBtn = cloneCard.querySelector('.alloffers-offers__card__mechanisms__saved');
    const _label = cloneCard.querySelector('.alloffers-offers__card__mechanisms__label');
    _label.dataset.pid = gData['id'];
    _label.dataset.label = isCardLinked(gData) ? CLO_MECH[0] : gData['mechanisms'][0]['mechanismLabel'];
    _label.dataset.type = checkOptInReady(gData) ? 'noAction' : gData['mechanisms'][0]['mechanismType']; // If 'states' available and contain "OPTED_IN"
    _label.dataset.val = isCardLinked(gData) ? 'cardLinked' : gData['mechanisms'][0]['mechanismValue'];
    // below: OptIn mechanism button should only execute once. function 'optInClickHandler' removed after clicked;
    function optInClickHandler(e) {
        e.stopPropagation();
        if(isAuthed()){
            optInOffersHandler(_label).then(res => {
                if (res.error) return;
                const saveIcons = document.querySelectorAll(`[data-saved-id="${res['id']}"]`);
                for(const saveIcon of saveIcons) {
                    savedChecker('SAVED', saveIcon, true);
                    optInLabelUpdater(gData['id'], gData , isCardLinked(gData) ? 'isCardLinkedCTA':'optIn');
                }
            })
            _label.removeEventListener('click',optInClickHandler);
        }else{
            setSessionSaveOffer(gData['id'],gData);
            window.open(_loginPage, '_self');
        }
    }
    const _isOptIn = gData['mechanisms'][0]['mechanismType'].trim().toLowerCase()==='optin';
    if(_isOptIn){ // Only OptIn can be clicked // || (_isOptIn && isCardLinked(gData))
        _label.style.cursor = 'pointer';
        _label.addEventListener('click',optInClickHandler);
    }

    // Check if ID match in promotion
    const _promotionElm = cloneCard.querySelector('.alloffers-offers__card__alert');
    if(gData['promotionLabel']){
        _promotionElm.classList.remove('d-none');
        _promotionElm.textContent = gData['promotionLabel'];
    }

    // saved offers init
    const _heartIcon = _saveHeartBtn.querySelector('.saved-heart');
    // Checking Saved states and update the icon and tracking ID
    if(isAuthed() && gData['states']) {
        const saveItem = gData['states'].find(state => state.name.toUpperCase() === "SAVE");
        if(!saveItem)return;
        saveItem['value'].toUpperCase() === 'SAVED' ? heartIconFull(_heartIcon,true) : heartIconFull(_heartIcon,false);
    }
    const _saved = _heartIcon.classList.contains('am-icon2-heart-full') ? 'offer-unsave-icon': 'offer-save-icon';
    _saveHeartBtn.dataset.savedId = gData['id'];
    _saveHeartBtn.dataset.trackId = _saved;
    _saveHeartBtn.dataset.trackClick = "offer-card";
    _saveHeartBtn.dataset.trackType = "button";

    (bodyAttr.enable==='true') && offerObserver.observe(cloneCard);
    return cloneCard;
}
export function openDetailCard(_cardInfo, _src=null){
    activeOfferData = _cardInfo;
    detailLoaderHide(false);
    const _promoRibbon = modalElements.detailTemplate.querySelector('.alloffers-detail__promotion');
    _promoRibbon.classList.remove('active');

    if(_cardInfo['promotionLabel']){
        _promoRibbon.classList.add('active');
        _promoRibbon.textContent = _cardInfo['promotionLabel'];
    }
    const _bodyContent = modalElements.detailTemplate.querySelector('.alloffers-detail__tiers');
    _bodyContent.innerText="";
    const _tiers = _cardInfo['tiers'];
    for(const element of _tiers){
        createElement('h2',['alloffers-detail__title'],_bodyContent,'',element['awardLong']);
        createElement('div',['alloffers-detail__body'],_bodyContent,'',element['qualifierLong']);
    }

    const _fullContent =  modalElements.detailTemplate.querySelector('.alloffers-detail__full');
    const _description = modalElements.detailTemplate.querySelector('.alloffers-detail__full__content');
    const _button = _fullContent.querySelector('a');
    _fullContent.classList.add('d-none');
    _description.classList.add('d-none');
    _button.classList.remove('d-none');

    if(_cardInfo['description']){
        _fullContent.classList.remove('d-none');
        _description.textContent = _cardInfo['description'];
    }

    const saveIcon = document.querySelector(`[data-saved-id="${_cardInfo['id']}"]`);
    const state = saveIcon.querySelector('.saved-heart').classList.contains('am-icon2-heart-full');
    heartIconFull(modalElements.detailedSaveBtn.querySelector('.saved-heart'),state);
    const _sImage = modalElements.detailTemplate.querySelector('.alloffers-detail__image').querySelector('img');
    _sImage.setAttribute('src',_cardInfo['image']['url']);
    _sImage.setAttribute('alt','product image:'+_cardInfo['id']);

    const _heart = modalElements.detailedSaveBtn.querySelector('.saved-heart');
    const _fSaved = _heart.classList.contains('am-icon2-heart-full') ? 'offer-unsave-icon' : 'offer-save-icon';
    modalElements.detailedSaveBtn.dataset.savedId = _cardInfo['id'];
    modalElements.detailedSaveBtn.dataset.trackId = _fSaved;
    modalElements.detailedSaveBtn.dataset.trackClick = "offer-card";
    modalElements.detailedSaveBtn.dataset.trackType = "button";

    const defaultLogo = '/etc.clientlibs/aem-airmiles-web/clientlibs/clientlib-offers-site/resources/images/AIRMILES_PLANE_WHT_RGB_E.svg';
    const logoPath = _cardInfo['partnerLogo'] && _cardInfo['partnerLogo']['url'] ? _cardInfo['partnerLogo']['url'] : defaultLogo;
    const partnerName = _cardInfo['partnerLabel'];
    const partnerURL = _cardInfo['partnerProfileURL'];

    const _sPartnerLogo = modalElements.detailTemplate.querySelector('.alloffers-detail__partner__logo').querySelector('img');
    const _sPartnerSmall = modalElements.detailTemplate.querySelector('.alloffers-detail__partner__slogo').querySelector('img');
    if(logoPath.length > 0){ // ACO-218 If 'fullLogo' not exist, generator will fail.
        _sPartnerLogo.setAttribute('src', logoPath);
        _sPartnerLogo.setAttribute('alt', partnerName);
        modalElements.detailTemplate.querySelector('.alloffers-detail__partner__name').textContent = partnerName;
        _sPartnerSmall.setAttribute('src', logoPath);
        _sPartnerSmall.setAttribute('alt', partnerName);
    }else{
        _sPartnerLogo.classList.add('d-none');
        _sPartnerSmall.classList.add('d-none');
    }
    if(partnerURL){
        const _partnerOfferLink = modalElements.detailTemplate.querySelector('.alloffers-detail__partner__link');
        _partnerOfferLink.setAttribute('href',partnerURL);
        _partnerOfferLink.dataset.trackId=`partner_link_${_cardInfo['partnerId']}`;
        _partnerOfferLink.dataset.trackClick="offer_detail";
        _partnerOfferLink.dataset.trackType="internal";
    }

    mechanismDetailTabHandler(_cardInfo);
    const offerDate = checkTimestamp(_cardInfo['startDate'],_cardInfo['endDate']);
    modalElements.detailTemplate.querySelector('.alloffers-detail__date').textContent = shortDate(offerDate,envElements.lang === 'en'? 'E':'F');
    modalElements.detailTermsBody.textContent = _cardInfo['legalText'];
    modalElements.detailTemplate.classList.add('active');
    envElements.body.classList.add('overflow-hidden');
    setTimeout(function() {
        detailLoaderHide(true);
        pageloadAAEmit(true, _cardInfo['id'], _src);
    }, 1000);
}

export function duplicateDetailCard(_data,_counter){
    const clone = cardGenerator(_data, _counter, 'detail');
    if(!clone)return;
    clone.addEventListener('click', (e)=>{
        modalElements.detailTemplate.scrollTo({
            top: 0,
            behavior: 'smooth'
        });
        if(e.target.dataset.val==='cardLinked'){return;} // Don't open detail if target is saveHeart 'cardLinked'
        openDetailCard(_data); // Replace detail
        checkOffersInDetail(_data['partnerId'],_data['id']);
    });
    // Unsaved Offers - When unsave the offer in detail offer then the offer in Save Offers is removed
    const _saveDetailBtn = clone.querySelector('.alloffers-offers__card__mechanisms__saved');
    _saveDetailBtn.dataset.saveState = 'true';
    _saveDetailBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        saveClick(_saveDetailBtn, false, _data);
    });
    modalElements.detailSlide.appendChild(clone);
}

export async function hasAwaitingTasks(){
    const _savedNum = sessionStorage.getItem('am_savedOffer');
    if(!_savedNum){return;}
    const _savedID = atob(_savedNum);
    if(_savedNum){
        await saveOfferFromPageLoad(_savedID).then(RES => {
            if (RES.error) return;
            reloadPage(false);
            const savedPagePill = document.querySelector('.alloffers-tabs__slides__tab[data-type="saved"]');
            savedPagePill && savedPagePill.click();
            removeSaveOfferSessions();
        })
    }
}
