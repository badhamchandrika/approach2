import {createElement} from "../../../dependencies/js/utils";
import {setNoResultMessage} from "../alloffers/alloffers";
import {envElements} from "../alloffers/envElements";
import {apiURL, getDomBFFDomainUrl} from "../../../dependencies/js/api";
import {fetchGetData} from "../alloffers/support";

function filterEntries(data, _region, limit) {
    return data.filter(entry => {
        const regions = entry['regions'].map(_reg => _reg.toLowerCase());
        return regions.includes('national') || regions.includes('worldwide') || regions.includes(_region.toLowerCase());
    }).slice(0, limit);
}

export function getSuggestedPartners(_partnersList, _region) {
    const _partnerLogoContainer = document.querySelector('.partner-logo-container');
    if(!_partnerLogoContainer)return;
    _partnerLogoContainer.setAttribute("role", "region");
    _partnerLogoContainer.setAttribute( "aria-label","Partners Logos");
    _partnerLogoContainer.innerText = '';
    const partnerLogoComponent = document.querySelector('.offers-top-partners');
    const _logoContainer = createElement('div', ['partner-logo', 'd-flex', 'py-3', 'px-1', 'px-md-2'], _partnerLogoContainer, {'style':'overflow:auto'},'' );
    const filtered = _partnersList.filter(entry =>
        entry.hasOwnProperty('url') &&
        entry.hasOwnProperty('name') &&
        entry.hasOwnProperty('regions') &&
        entry.hasOwnProperty('priority') &&
        entry.hasOwnProperty('fullLogo') &&
        entry['url'] !== "" &&
        entry['name'] !== "" &&
        entry['regions'] !== "" &&
        entry['priority'] !== "" &&
        entry['fullLogo'] !== "" &&
        Array.isArray(entry.fullLogo) &&
        entry.fullLogo.length > 0 &&
        entry.fullLogo.every(logo => logo.hasOwnProperty('file') && logo.file.hasOwnProperty('url'))
    ).sort((a, b) => a.priority - b.priority);
    const _limit = partnerLogoComponent.dataset.plogoNum || 10; // Default limit set to 10 if no limit number defined
    const partnersList = filterEntries(filtered, _region, _limit);
    _logoContainer.innerText = '';
    for (const element of partnersList){
        const _logoUrl = element['fullLogo'][0]['file']['url'] ? element['fullLogo'][0]['file']['url'] : '';
        const _logoBox = createElement('div', ['d-flex','flex-column','align-items-center','offerspartners__box'], _logoContainer, {},'' );
        const _aHref = createElement('a', ['offerspartners__logo'], _logoBox, {'href':element['url'],'target':'_self', 'position':'relative',
            'data-track-id':element['name'], 'data-track-click':'partners-you-might-like',
            'data-track-type':'internal','data-track-filter': element['regions'],
            'data-bs-toggle':'tooltip', 'title':element['name']
        },'',);
        createElement('img', [], _aHref, {'src':_logoUrl, 'alt':element['name']},'' );
        createElement('p', ['offerspartners__name'], _logoBox, {},element['name'] );
    }
    partnerLogoComponent.classList.remove('d-none');
}

async function initialProCatData(_apiURL){
    try {
        return await fetchGetData(_apiURL);
    } catch (error){
        setNoResultMessage(true, envElements.customError, "Error: Categories or Partners data failed.");
        return error;
    }
}

export function initPartnerLogos(_logoRegion){
    initialProCatData(getDomBFFDomainUrl() + apiURL.partners).then((pData)=>{
        getSuggestedPartners(pData.results, _logoRegion);
    });
}