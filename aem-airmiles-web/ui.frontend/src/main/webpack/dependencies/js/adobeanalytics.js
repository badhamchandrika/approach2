import {getCookie, getMemdeets, getBodyAttr, isAuthed, setWebCookie, offerInfo, detectDigiGhost} from "./utils";
import {useFetch, apiURL, getApiHost} from "./api";

(() => {
    document.addEventListener("DOMContentLoaded", function () {
        // All page properties from <body> data attributes

        const tierMap = new Map([
            ['B', 'blue'],
            ['G', 'gold'],
            ['O', 'onyx'],
        ]);

        const EMPTY = '';

        const _authed = isAuthed();

        const _memdeetData = getMemdeets();
        const _aaPageName = getBodyAttr('aaPageName') || '';
        let _amCashRatio = EMPTY;
        let _amUserEmail = EMPTY;
        let _amLastName = EMPTY;
        let _amHomePhone = EMPTY;
        let _amMobilePhone = EMPTY;
        let _amEmailStatus = EMPTY;

        // Carousel-Banner impressions
        // let observedCarouselSlide;
        const carouselSlideArray = [];

        function emitCarouselAA(carouselSlide){
            // Get dataset from CTA button(cmp-link) which carries dataset for name, category and location value.
            const _button = carouselSlide.querySelector('.cmp-link');
            const payload = {
                web: {},
            };
            payload.event = 'bannerImpressions';
            payload.web.banner = {
                bannerName: _button.dataset.bannerName,
                bannerCategory: _button.dataset.bannerCategory,
                bannerLocation: _button.dataset.bannerLocation
            };
            //This code removes duplicate banner impressions
            let duplicateFound=0;
            for (const element of adobeDataLayer) {
                if (element.event === 'bannerImpressions' && element.web != null) {
                    if (element.web.banner != null) {
                        const t = element.web.banner;
                        const p = payload.web.banner;
                        if(t.bannerName === p.bannerName && t.bannerCategory === p.bannerCategory && t.bannerLocation === p.bannerLocation){
                            duplicateFound = 1;
                            break;
                        }
                    }
                }
            }
            if(duplicateFound !== 1){
                adobeDataLayer.push(payload);
            }
        }

        function emitCarouselBanner(){

            function resetImageTextPositionNum(_carouselSlide,_count){
                const _imageText = _carouselSlide.querySelector('.image-text');
                const alink = _imageText.querySelector('.cmp-link');
                const category = alink.dataset.bannerCategory;
                alink.dataset.bannerLocation = `${category}-pos-${_count}`;
            }

            const mutationObserver = new MutationObserver(entries =>{
                for(const entry of entries){
                    const mutateSlide = entry.target;
                    if(mutateSlide.classList.contains("custom-carousel__item--active") && !carouselSlideArray.includes(mutateSlide)){
                        carouselSlideArray.push(mutateSlide); // Add the carousel slide to the array if it doesn't already exist. Slide in array won't trigger impression event anymore.
                        emitCarouselAA(mutateSlide);
                    }
                }
            });
            const carousels = document.querySelectorAll('.custom-carousel__item');
            let count = 0;
            for(const carousel of carousels){
                if(carousel.querySelector('.image-text')){ // Check if carousel contains Image-Text component
                    count++;
                    resetImageTextPositionNum(carousel,count);
                    mutationObserver.observe(carousel,{
                        subtree: true,
                        attributes: true
                    });
                }
            }
        }

        function emitCarouselImpressionOnPageLoad(){
            let activeCarouselCount = 0;
            const activeCarouselSlides = document.querySelectorAll('.custom-carousel__item--active');
            if(activeCarouselSlides != null && activeCarouselCount === 0){
                for(const carousel of activeCarouselSlides) {
                    emitCarouselAA(carousel);
                }
                activeCarouselCount++;
            }
        }
        // Global alert impressions
        function emitGlobalAlert(){
            const globalAlerts = document.querySelectorAll('.globalalert-display__slide');
            for(const globalAlert of globalAlerts){
                const payload = {
                    web: {},
                };
                payload.event = 'GlobalAlertImpressions';
                payload.web.GlobalAlertImpressions = {
                    alertName: globalAlert.dataset.alertName,
                    alertCategory: globalAlert.dataset.alertCategory,
                    alertLocation: globalAlert.dataset.alertPos
                };
                adobeDataLayer.push(payload);
            }
        }

        function emitWebBanners(){
            const webBanners = document.querySelectorAll('.web_banner_promo_unit a');
            let count = 0;
            for(const webBanner of webBanners){
                count++;
                if(webBanner.dataset !== undefined){
                    webBanner.dataset.bannerLocation = 'webbanner-pos-'+count;
                }
                const payload = {
                    web: {},
                };
                payload.event = 'WebBannerImpressions';
                payload.web.banner = {
                    bannerName: webBanner.dataset.bannerName,
                    bannerCategory: webBanner.dataset.bannerCategory,
                    bannerLocation: webBanner.dataset.bannerLocation
                };
                adobeDataLayer.push(payload);
            }
        }

        function pushEnrolmentConversionSucessCompleteADL(collectorData){
            const payload ={
                event:'accountCreationComplete',
                web: {
                    collector:{
                        'id':collectorData.id,
                        'tier':collectorData.tier,
                        'login_status':collectorData.login_status,
                    }
                },
            }
            adobeDataLayer.push(payload);
        }

        function emitAAEvent(){
            emitGlobalAlert();
            emitWebBanners();
    
            // skip offers pages
            const _execludeOffer = document.querySelector('.alloffers');
            if(_execludeOffer) return;

            const _collectorData = {
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

            window.adobeDataLayer = window.adobeDataLayer || [];
            let subsectionString = subSection1 || '';
            subsectionString += subSection2 ? ':'+subSection2 : '';
            subsectionString += subSection3 ? ':'+subSection3 : '';
            const AAEvent = {
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
                    collector: _collectorData,
                    siteDetails: {
                        'domain': window.location.host,
                        'section': siteSection1+":"+siteSection2,
                        'subsection': subsectionString,
                        'language': getBodyAttr('lang'),
                        'screen_height': document.documentElement.clientHeight,
                        'screen_width': document.documentElement.clientWidth,
                    }
                }
            }
            adobeDataLayer.push(AAEvent);
            if(_authed && getCookie('authenticated') !== "true"){
                const AAAuthCompleteEvent = {
                    event: 'userLoginComplete',
                    web: {
                        collector: {
                            id: _authed ? String(_memdeetData['cardNumber']):'',
                            tier: _authed ? tierMap.get(_memdeetData['tier']):'',
                            'login_status': 'logged_in',
                        },
                    }
                }
                adobeDataLayer.push(AAAuthCompleteEvent);
                setWebCookie('authenticated','true',1);
            }

            // If Silent-Login Succesfull and enrollment completed & landed on Success Page.
            // Id given to text-contianer of alert.
            if(document.getElementById('accountCreationCompleteSuccess'))
            {
                pushEnrolmentConversionSucessCompleteADL(_collectorData);
            }
            emitCarouselBanner();
            emitCarouselImpressionOnPageLoad();
        }
                
        // Fetch user profile from BFF endpoint/profile *********************************
        const profileURLHead = getApiHost() + apiURL.profile;
        
        async function getCashMilesPref() {
            await useFetch(profileURLHead, true)
                .then((data) => {
                    detectDigiGhost(data.profile);
                    _amCashRatio = data.profile['preferenceDetails']['amCashRatio'];
                    _amUserEmail = data.profile['contactDetails']['email'];
                    _amLastName = data.profile['personalDetails']['lastName'];
                    _amHomePhone = data.profile['contactDetails']['homePhone'];
                    _amMobilePhone = data.profile['contactDetails']['mobilePrimary'];
                    _amEmailStatus = data.profile['contactDetails']['emailStatus'];
                    sessionStorage.setItem('am_cashRatio', _amCashRatio);
                    emitAAEvent();
                    //Setting email & last name in the session storage
                    sessionStorage.setItem('am_userEmail', _amUserEmail);
                    sessionStorage.setItem('am_lastName', _amLastName);
                    sessionStorage.setItem('am_homePhone', _amHomePhone);
                    sessionStorage.setItem('am_mobilePhone', _amMobilePhone);
                    sessionStorage.setItem('am_emailStatus', _amEmailStatus);
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        }
        function hrefValCheck(_href){
            if(!_href)return '';
            _href = _href.trim().toLowerCase();
            (_href.includes('javascript:void') || _href === '#') && (_href = '');
            return _href;
        }
        // Global click event - Adobe Analytics
        const onCTAClick = (e) => {
            const element = e.target;
            const _cta = element.closest('a, button, li, input');
            if (!_cta){ return; }
            const payload = {
                web: {}
            };
            if(_cta.dataset.trackType === 'banner') {
                if (!_cta.dataset.bannerName || !_cta.dataset.bannerCategory || !_cta.dataset.bannerLocation) return;
                payload.event = 'bannerClicks';
                payload.web.banner = {
                    bannerName: _cta.dataset.bannerName,
                    bannerCategory: _cta.dataset.bannerCategory,
                    bannerLocation: _cta.dataset.bannerLocation
                };
            }else if(_cta.dataset.isOfferCard){
                payload.event = 'CTAClick';
                payload.web.CTAClick = {
                    linkName: _cta.dataset.trackId,
                    linkHref: hrefValCheck(_cta.href),
                    linkLocation: _cta.dataset.trackClick,
                    pageDetails: {
                        name:_cta.dataset.isOfferCard
                    }
                };
                payload.web.productListItems = [offerInfo(_cta.dataset.trackJson,_cta.dataset.count)];
            }else{
                if (!_cta.dataset.trackClick || !_cta.dataset.trackId || !_cta.dataset.trackType) return;
                switch (_cta.dataset.trackType) {
                    case "internal":
                        payload.event = 'internalLinkClick';
                        payload.web.internalLink = {
                            linkName: _cta.dataset.trackId,
                            linkLocation: _cta.dataset.trackClick,
                            linkhref: hrefValCheck(_cta.href)
                        };
                        break;
                    case "exit":
                        payload.event = 'exitLinkClick';
                        payload.web.exitLink = {
                            linkName: _cta.dataset.trackId,
                            linkhref: hrefValCheck(_cta.href)
                        };
                        break;
                    case "social":
                        payload.event = 'socialLinkClick';
                        payload.web.socialLink = {
                            socialLinkPlatformName: _cta.dataset.trackId,
                            linkName: _cta.dataset.trackId,
                            linkhref: hrefValCheck(_cta.href)
                        };
                        break;
                    case "button":
                        payload.event = 'CTAClick';
                        payload.web.CTAClick = {
                            linkName: _cta.dataset.trackId,
                            linkLocation: _cta.dataset.trackClick,
                            linkHref: hrefValCheck(_cta.href)
                        };
                        break;
                    case "input":
                        payload.event = 'InputClick';
                        payload.web.InputClick = {
                            linkName: _cta.dataset.trackId,
                            linkLocation: _cta.dataset.trackClick
                        };
                        break;
                }
            }
            adobeDataLayer.push(payload);
        };

        // Run following if AA enabled
        if(getBodyAttr('aaEnabled')==='true'){
            !_authed ? emitAAEvent() : getCashMilesPref();
            window.addEventListener('click', onCTAClick);
        }

    });
})();
