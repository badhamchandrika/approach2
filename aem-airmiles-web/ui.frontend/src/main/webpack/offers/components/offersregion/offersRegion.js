import {isAuthed} from "../../../dependencies/js/utils";
import {initPartnerLogos} from "../offerspartners/suggestPartners";
import {defaultLocal, updatePayload} from "../alloffers/support";
import {reloadPage} from "../alloffers/alloffers";
import {updateCarousel} from "../offerscarousel/offerscarousel";

const regionMask = document.querySelector('.offers-region__mask');
const regionBtn = document.querySelector('.offers-region__location__region');
const regionItems = document.querySelectorAll('.offers-region__regionlist__item');

let _regionOpen = false;

export const regionData = {};
//Getting regions from HTML which generated from dialog.
for (const element of regionItems) {
    const _regCode = element.dataset.code;
    regionData[_regCode] = element.textContent;
}

function regionSelectOpen(_cond){
    if(_cond){
        regionBtn.classList.add('active');
        regionMask.classList.add('d-block');
    }else{
        regionBtn.classList.remove('active');
        regionMask.classList.remove('d-block');
    }
    _regionOpen = _cond;
}

function regionItemClick(_item, _payload){
    regionSelectOpen(false);
    for (const element of regionItems) {
        element.classList.remove('active');
    }
    _item.classList.add('active');
    regionBtn.querySelector('.region-name').innerText = _item.innerText;

    if (!regionBtn.hasAttribute('data-loc-announced')) {
        regionBtn.setAttribute('aria-live', 'polite');
        regionBtn.setAttribute('data-loc-announced', 'true');
        // Clear the region announcement and reset the attribute after a brief delay
        setTimeout(() => {
            regionBtn.setAttribute('aria-live', 'off');
            regionBtn.removeAttribute('data-loc-announced');
        }, 1000);
    }

    _payload['filters']['region'] = _item.dataset.code;
    if(isAuthed() && sessionStorage.getItem('am_locMutated')!=='true'){
        sessionStorage.setItem('am_locMutated', 'true');
    }
    isAuthed() && initPartnerLogos(_item.dataset.code);
    updatePayload(_payload);
    reloadPage();
    updateCarousel(_item.dataset.code);
}

export function regionListInitial(_payload){
    for (const element of regionItems) {
        if(element.dataset.code === _payload['filters']['region']){
            element.classList.add('active');
            regionBtn.querySelector('.region-name').innerText = element.textContent;
        }else{
            element.classList.remove('active');
        }
        element.addEventListener('click', ()=>{
            regionItemClick(element,_payload);
        });
    }
}

(() => {
    document.addEventListener("DOMContentLoaded", () => {
        regionBtn.querySelector('.region-name').innerText = regionData[defaultLocal];
        regionBtn.addEventListener('click', ()=>{
            _regionOpen = !_regionOpen;
            regionSelectOpen(_regionOpen);
        });
        regionMask.addEventListener('mouseover',()=>{
            regionSelectOpen(false);
        });
    });
})();
