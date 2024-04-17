import {getBodyAttr, getMemdeets, isAuthed, offerInfo} from "../../../dependencies/js/utils";

const _amCashRatio = sessionStorage.getItem('am_cashRatio') || '';

let offersTrackingQueue = [];
let importedOfferData;
export function offersDataSync(_newData){
    importedOfferData = _newData;
}
export const bodyAttr = {
    "enable":getBodyAttr('aaEnabled') || null,
    "pageName":getBodyAttr('aaPageName' || null),
    "category":getBodyAttr('aaCategory' || null)
}

function emitOffersAA() {
    if(offersTrackingQueue.length > 0){
        const payload = {
            web: {},
        };
        payload.event = 'productImpressions';
        payload.web.productListItems = offersTrackingQueue;
        adobeDataLayer.push(payload);
        offersTrackingQueue = [];
    }
}

setInterval(emitOffersAA, 10000);

function handleImpression(_offerCard) {
    offersTrackingQueue.push(offerInfo(_offerCard.dataset.trackJson,_offerCard.dataset.count,_offerCard.dataset.isOfferCard));
}

export const offerObserver = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            handleImpression(entry.target); // offer card is in view, handle impression
            observer.unobserve(entry.target); // Stop observing after offer impression
        }
    });
}, {threshold: 0.3}); // Firing impression when X(0.0~1.0) portion of card is visible.

// Page load tracking

const _authed = isAuthed();
const _memdeetData = getMemdeets();
const tierMap = new Map([
    ['B', 'blue'],
    ['G', 'gold'],
    ['O', 'onyx'],
]);

function navOutOffersCheck() {
    emitOffersAA();
}

export function unloadOfferPage(){
    const currentOffersPagePath = window.location.pathname;
    if (currentOffersPagePath.includes("/offers/" || currentOffersPagePath.includes("/offres/"))) {
        window.addEventListener('beforeunload', navOutOffersCheck);
    }
}

export function pageloadAAEmit(isDetail=false, cardID = null, _src = null){
    const _currentOfferPage = document.querySelector('.alloffers-tabs__slides__tab.active');
    const _pageName = _currentOfferPage ? _currentOfferPage.dataset.pageName : _src;
    const _pageAttr = getBodyAttr('aaPageName')+':'+_pageName || ''
    const _aaPageName = isDetail ? _pageAttr+":offer_details" : _pageAttr;
    const _collectorData2 = {
        'id': _authed ? String(_memdeetData['cardNumber']):'',
        'tier': _authed ? tierMap.get(_memdeetData['tier']):'',
        'cash_miles_pref': _authed ? String(_amCashRatio):'',
        'login_status': _authed ? "logged_in":"not_logged_in",
        'cash_miles': _authed ? String(_memdeetData['cashBalance']):'',
        'dream_miles': _authed ? String(_memdeetData['dreamBalance']):''
    };
    const [
        siteSection1,
        siteSection2,
        subSection1,
        subSection2,
        subSection3,
    ] = _aaPageName.split(':');
    let subsectionString = subSection1 || '';
    subsectionString += subSection2 ? ':'+subSection2 : '';
    subsectionString += subSection3 ? ':'+subSection3 : '';

    const offerAAEvent = {
        event: 'pageViews',
        web: {
            pageDetails: {
                'name': _aaPageName,
                'title': document.title,
                'url': getBodyAttr('canonical'),
                'subSection1': subSection1 || '',
                'subSection2': subSection2 || '',
                'subSection3': subSection3 || '',
                'category': getBodyAttr('aaCategory'),
            },
            collector: _collectorData2,
            siteDetails: {
                'domain': window.location.host,
                'section': siteSection1+":"+siteSection2,
                'subsection': subsectionString,
                'language': getBodyAttr('lang'),
                'screen_height': document.documentElement.clientHeight,
                'screen_width': document.documentElement.clientWidth,
            },
            prodView: isDetail ?1:0
        }
    }
    if(isDetail && cardID){
        const _detailCard = document.getElementById(cardID);
        if(!_detailCard)return;
        const _detailCardData = [offerInfo(_detailCard.dataset.trackJson,1)];
        offerAAEvent.web.productListItems = _detailCardData || [];
    }
    adobeDataLayer.push(offerAAEvent);
}