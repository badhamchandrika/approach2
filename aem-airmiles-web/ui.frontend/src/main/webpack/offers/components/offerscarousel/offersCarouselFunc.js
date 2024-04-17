import {cardGenerator, openDetailCard, saveClick} from "../alloffers/detailFunc";
import {checkOffersInDetail} from "../alloffers/alloffers";

function duplicateCarouselCard(_cardData, _mpartners, _carouselSlide, _data, _offerCounterIn){
    const clone = cardGenerator(_cardData, _offerCounterIn, 'carousel');
    if(clone){
        clone.addEventListener('click', (e)=>{
            const _saveEl = e.target.closest('.alloffers-offers__card__mechanisms__saved');
            if(_saveEl){
                saveClick(_saveEl, false, _cardData);
            }else{
                if(e.target.dataset.val==='cardLinked'){return;} // Don't open detail if target is saveHeart 'cardLinked'
                openDetailCard(_cardData, 'carousel');
                checkOffersInDetail(_cardData['partnerId'],_cardData['id'],_data['offers']);
            }
        });
        _carouselSlide.appendChild(clone);
    }else{
        console.log('[Carousel] Due to partner does not exist, clone failed!!');
    }
}
function checkCarouselPos(_id){
    const _carousel = document.querySelector(`[data-carousel-id='${_id}']`);
    const _showcase = _carousel.querySelector('.offerscarousel__showcase');
    const arrowLeft = _carousel.querySelector(".offerscarousel__arrow.left");
    const arrowRight = _carousel.querySelector(".offerscarousel__arrow.right");
    const scrollableWidth = _showcase.scrollWidth - _showcase.clientWidth;
    _showcase.scrollLeft >= scrollableWidth-2 ? arrowRight.classList.add('d-none') : arrowRight.classList.remove('d-none');
    _showcase.scrollLeft > 0 ? arrowLeft.classList.remove('d-none') : arrowLeft.classList.add('d-none');
}
function arrowClickEvent(_id, _arrow) {
    const _carousel = document.querySelector(`[data-carousel-id='${_id}']`);
    const _container = _carousel.querySelector('.offerscarousel__container');
    const _showcase = _carousel.querySelector('.offerscarousel__showcase');
    const _rollingUnit = parseInt(_container.dataset.carouselScrollNumber, 10) || 4;
    const computedStyle = window.getComputedStyle(_showcase);
    const _gap = parseInt(computedStyle.getPropertyValue('gap'));
    const _cardWidth = _showcase.querySelector('.alloffers-offers__card__wrapper').clientWidth;
    const slideStep = (_gap + _cardWidth) * _rollingUnit;
    _showcase.scrollLeft += (_arrow === 'left') ? -slideStep : slideStep;
    setTimeout(function(){checkCarouselPos(_id)}, 700);
}

function addLastCard(_id, _carouselSlide,_replacement){
    const originalLastCard = document.querySelector(`.carousel-last-card[data-carousel-lastcard-id="${_id}"]`);
    const _cloneTemp = originalLastCard.cloneNode(true);
    if(!_cloneTemp)return;
    const _paragraph = _cloneTemp.querySelector('.carousel-last__card__text');
    _paragraph.innerText = _paragraph.innerText.replace(/\$\{[^}]*\}/, _replacement);
    _cloneTemp.removeAttribute('data-carousel-lastcard-id');
    _cloneTemp.classList.remove('d-none');
    _carouselSlide.appendChild(_cloneTemp);
}

function getTotalOfferNumber(_limit, _total){
    return _limit > _total ? _total : _limit;
}

export function displayCarouselData(_id, _data, _limit) {
    const _carousel = document.querySelector(`[data-carousel-id='${_id}']`);
    const carouselSlide = _carousel.querySelector('.offerscarousel__showcase');
    const cards = _data.offers;
    const partners = _data.metadata.partners;
    carouselSlide.innerText='';
    let _offerCounter = 0;
    for(const card of cards){
        _offerCounter++;
        duplicateCarouselCard(card,partners,carouselSlide,_data,_offerCounter);
    }
    addLastCard(_id, carouselSlide, getTotalOfferNumber(_limit, _data.metadata.total));
    _carousel.classList.remove('d-none');
}

export function navArrowClickInit(_id){
    const _carousel = document.querySelector(`[data-carousel-id='${_id}']`);
    const arrowLeft = _carousel.querySelector(".offerscarousel__arrow.left");
    const arrowRight = _carousel.querySelector(".offerscarousel__arrow.right");
    const _showcase = _carousel.querySelector('.offerscarousel__showcase');
    _showcase.scrollLeft = 0;
    arrowRight.classList.remove('d-none');
    setTimeout(function(){checkCarouselPos(_id)}, 1000);
    if(!_carousel.hasAttribute('data-has-click')){
        arrowLeft.addEventListener('click', ()=>{
            arrowClickEvent(_id,'left');
        });
        arrowRight.addEventListener('click', ()=>{
            arrowClickEvent(_id,'right');
        });
    }
    _carousel.setAttribute('data-has-click', '');
}
