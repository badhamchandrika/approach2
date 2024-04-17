import {getLocale, isAuthed, sanitizeHTML, htmlDecode, sanitizeJSON} from "./utils";
import {v4 as uuidv4} from "uuid";

export const apiURL = {
    memberBanner: '/services/airmiles/sling/no-cache/member-banner',
    transaction: '/services/airmiles/sling/no-cache/transactions',
    profile: '/profile',
    offers: `/services/airmiles/sling/no-cache/GetAllOffersServlet.json`,
    // OffersV0 is most up to date version for some reason
    domBffOffers: `/dombff-offers/offers`,
    categories: `/services/airmiles/sling/no-cache/catspromos`,
    partners: `/dombff-partners/services/airmiles/sling/no-cache/partners`,
    offersMechanisms: `/api/mechanisms`,
    nationalPromotions: `/api/nationalpromotions`,
    offerErrors: `/api/offererrors`,
    transferBalance: '/transfer',
    eligibility: '/eligibility',
    profileOptin: '/dombff-profile/profile-optin',
    bmoOptin: '/dombff-profile/bmo-optin',
    merchandiseIssue: '/merch-issue',
    nameChange: '/name-change',
    imageUpload: '/dombff-dsse/merch-issue/image'

    // offersV0: `/offers`,
    // offersMiniFeed: `/mini-feed`,
    // saveOffer: `/services/airmiles/sling/no-cache/card/offers`,
    // offersMechanisms: `${hostName}/api/mechanisms`,
    // nationalPromotions: `${hostName}/api/nationalpromotions`,
    // offerErrors: `${hostName}/api/offererrors`,
    // pdpContent: `${hostName}/api/assets/airmiles/pdp-content`,
    // offerDetailAlerts: `${hostName}/api/assets/airmiles/offer-detail-alerts`,
    // featuredOffers: `${hostName}/services/airmiles/sling/no-cache/partneroffers.json`,
    // offersV2: `${hostName}/services/airmiles/sling/no-cache/v2/offers.json`,
    // getOfferById: `${hostName}/offer`,
    // partners: `${hostName}/services/airmiles/sling/no-cache/partners?filterType=region&region`,
    // partners2: `${hostName}/services/airmiles/sling/no-cache/partners`,
    // partnersListing: `${hostName}/services/airmiles/sling/no-cache/partnersListing.json`,
    // addressLookup: `${hostName}/services/airmiles/sling/no-cache/address/find`,
    // addressRetrieve: `${hostName}/services/airmiles/sling/no-cache/address/retrieve`,
    // transactions: `${hostName}/services/airmiles/sling/no-cache/transactions`,
    // orderCard: `${hostName}/services/airmiles/sling/no-cache/order-card`,
    // search: `${hostName}/services/airmiles/sling/no-cache/search`,
    // memberBanner: `${hostName}/services/airmiles/sling/no-cache/member-banner`,
    // missingMiles: `${hostName}/v1/missing-miles`,
    // bonusCard: `${hostName}/v1/ambc/deposit`

}
export const getApiHost = () =>{
    return document.querySelector('body').dataset['api'];
}
export const getDomBFFApiHost = () =>{
    return document.querySelector('body').dataset['domBffApi'];
}
export const getDomBFFDomainUrl = () =>{
    return document.querySelector('body').dataset['domBffDomainUrl'];
}

export const getHeaders = () => {
    return {
        'Accept-Language': getLocale(),
        'Content-Type': 'application/json'
    };
};

let controller = new AbortController();
let signal = controller.signal;
let _errorMsg = '';

export async function useFetch(url = '',requireLocale = false, _payload = null, _origin = '', _extra = null) {
    const _headerSimple = {method: 'GET', credentials: 'include'};
    const _headerLocale = {method: 'GET', credentials: 'include', headers: getHeaders()};
    const uuidVal = uuidv4();
    const allOfferComp = document.querySelector('.alloffers-container');
    (_origin === "offers") && (_headerLocale.headers = {
        ..._headerLocale.headers,
        'x-origin-client': 'internal:amrp:web',
        'X-correlation-id': uuidVal
    });
    try {
        const response = await fetch(url, requireLocale ? _headerLocale : _headerSimple);
        if (signal.aborted) {
            _errorMsg = allOfferComp ? allOfferComp.dataset.errMsgAbort : 'Request was aborted';
            return {'error':_errorMsg}
        }
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return await response.json();
    } catch (error) {
        if (error.name === 'AbortError') {
            _errorMsg = allOfferComp ? allOfferComp.dataset.errMsgAbort : 'Request was aborted';
            return {'error':_errorMsg}
        } else {
            return {'error':error}
        }
    }
}

export async function useFetchPost(
    url = '',
    requireLocale = false,
    payload = null,
    _method = 'POST',
    _origin = '',
    _extra = null,
    _isJSON = true) {
    const uuidVal = uuidv4();
    const _pl = sanitizeHTML(JSON.stringify(payload));
    const _headerSimple = {method: _method, credentials: 'include', headers: { 'Content-Type': 'application/json', 'X-Content-Type-Options': 'nosniff'}};
    const _lang = document.querySelector('body').dataset.lang;
    _isJSON && (_headerSimple.headers['Content-Type'] = 'application/json');
    requireLocale && (_headerSimple.headers['Accept-Language'] = _lang === 'fr'?'fr-CA':'en-CA');
    (_method === "POST" || _method === "PUT") && (_headerSimple.body = htmlDecode(_pl));
    (_origin === "transfer-balance") && (_headerSimple.headers['X-Idempotency-Id'] = uuidVal);
    (_origin === "image-upload") && (_headerSimple.body = payload);
    (_origin === "image-upload") && (delete _headerSimple.headers['Content-Type']);
    (_origin === "saving-offer") && (_headerSimple.headers = {
        ..._headerSimple.headers,
        'x-origin-client': 'internal:amrp:web',
        'X-correlation-id': uuidVal
    });
    try {
        const response = await fetch(url,{ ..._headerSimple, signal });
        if (signal.aborted) {
            return {'error':'Request was aborted'}
        }
        if (!response.ok) {
            return {'error':'network'}
        }
        if(_isJSON){
            return await sanitizeJSON(response.json());
        }else{
            return sanitizeHTML(response);
        }

    } catch (error) {
        if (error.name === 'AbortError') {
            return {'error':'Request was aborted'}
        } else {
            return {'error':error}
        }
    }
}

export async function checkCollectorEligibility(_myAPI, _header){
    try {
        if(isAuthed() && (sessionStorage.getItem('am_tb') == null || sessionStorage.getItem('am_tb') === "undefined")) {
            const data = await useFetch(_myAPI, _header);
            sessionStorage.setItem('am_tb', data['eligible']);
        }
        else{
            sessionStorage.removeItem('am_tb');
        }
    } catch (error) {
        console.error("error", error);
    }
}
// API abortion added in case to cancel the request
export function abortAPIRequest() {
    controller.abort();
    controller = new AbortController();
    signal = controller.signal;
}