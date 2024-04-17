import {displayCarouselData, navArrowClickInit} from "./offersCarouselFunc";
import {defaultLocal, getRequestApiReady, offersPayloadModel, basicInitSetup} from "../alloffers/support";
import {useFetch} from "../../../dependencies/js/api";
import {v4 as uuidv4} from "uuid";

const _carouselPayloads = [];
let carouselsHolder;

function nullPrevent(_var, _preset){
    return _var || _preset;
}

function initCarouselPayload(_region = null){
    for(const carouselMain of carouselsHolder) {
        const _id = carouselMain.dataset.carouselId;
        const carouselContainer = carouselMain.querySelector('.offerscarousel__container');
        const _limit = carouselContainer.dataset.carouselLimit;
        const _massOffer = carouselContainer.dataset.carouselMassOffer;
        const _programType = carouselContainer.dataset.carouselProgramType;
        const _partnerId = carouselContainer.dataset.carouselPartnerId;
        const _categoryId = carouselContainer.dataset.carouselCategoryId;
        const _subcategoryId = carouselContainer.dataset.carouselSubcategoryId;
        const _promotionId = carouselContainer.dataset.carouselPromotionId;
        const _sortOptions = carouselContainer.dataset.carouselSortOptions;

        _carouselPayloads[_id] = offersPayloadModel(_region, _limit);
        _carouselPayloads[_id]['filters']['region'] = _region;
        _carouselPayloads[_id]['filters']['partners'] = nullPrevent(_partnerId, []);
        _carouselPayloads[_id]['filters']['categories'] = nullPrevent(_categoryId, []);
        _carouselPayloads[_id]['filters']['subCategories'] = nullPrevent(_subcategoryId, []);
        _carouselPayloads[_id]['filters']['sort'] = nullPrevent(_sortOptions, []);

        _carouselPayloads[_id]['filters']['programType'] = nullPrevent(_programType, []);
        _carouselPayloads[_id]['filters']['promotionId'] = nullPrevent(_promotionId, []);
        _carouselPayloads[_id]['filters']['massOffer'] = nullPrevent(_massOffer, []);

        _carouselPayloads[_id]['pagination']['sortedBy'] = nullPrevent(_sortOptions, []);
        _carouselPayloads[_id]['pagination']['offersPerPage'] = nullPrevent(_limit, 10);
    }
}

function removeCarousel(_id){
    for(const _offersCarousel of carouselsHolder){
        const _cid = _offersCarousel.dataset.carouselId;
        if(_cid === _id){
            _offersCarousel.classList.add('d-none');
            delete _carouselPayloads[_id];
        }
    }
}

async function loadCarouselOffers(_id){
    const _req = getRequestApiReady(_carouselPayloads[_id],'carousel');
    await useFetch(_req.apiURL, true, _req.sentPayload,'offers', null)
        .then(data => {
            if(data.offers.length >= 3){
                displayCarouselData(_id,data,_carouselPayloads[_id].pagination.offersPerPage);
                navArrowClickInit(_id);
            }else{
                removeCarousel(_id);
            }
        })
        .catch(error => {
            removeCarousel(_id);
            console.error('Carousel offers fetch error:', error);
        });
}

export function updateCarousel(_region){
    initCarouselPayload(_region);
    for(const carouselMain of carouselsHolder) {
        const _id = carouselMain.dataset.carouselId;
        loadCarouselOffers(_id).then(() => {
            // updated from region updates
        });
    }
}
function newID(idVal){
    const _regex = /[^a-z0-9]/g;
    idVal = idVal.toLowerCase();
    idVal = idVal.toString().replace(_regex, '-');
    return idVal;
}
function resetId(){
    const randomId = uuidv4();
    for(const _carousel of carouselsHolder) {
        const _idVal = _carousel.dataset.carouselId || randomId;
        _carousel.dataset.carouselId = newID(_idVal);
    }
    const _lastCards = document.querySelectorAll('.carousel-last-card');
    for(const _lastCard of _lastCards) {
        const _lastIdVal = _lastCard.dataset.carouselLastcardId || randomId;
        _lastCard.dataset.carouselLastcardId = newID(_lastIdVal);
    }
}

(() => {
    document.addEventListener("DOMContentLoaded", () => {
        carouselsHolder = document.querySelectorAll('.offerscarousel');
        if(carouselsHolder && carouselsHolder.length > 0) {
            // Initial ********************************
            let _region = defaultLocal;
            const storedPayloadString = sessionStorage.getItem('am_OSCP');
            if(storedPayloadString){
                const _storedPayloadTemp = JSON.parse(storedPayloadString);
                _region = _storedPayloadTemp['filters']['region'];
            }
            resetId();
            initCarouselPayload(_region);
            basicInitSetup();
            for(const carouselMain of carouselsHolder) {
                const _id = carouselMain.dataset.carouselId;
                loadCarouselOffers(_id).then(() => {
                    //navArrowClickInit(_id);
                });
            }
        }else{
            console.log("NO OFFER CAROUSELS FOUND");
        }
    });
})();
