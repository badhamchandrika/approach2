import {isAuthed} from "../../../js/utils";
import {apiURL, checkCollectorEligibility, getDomBFFApiHost, useFetch} from "../../../js/api";
(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const topNav = document.querySelector('.topnav');
        if(!topNav){return;}
        const navMobileMenu = document.querySelector('.headnav__mobile-menu');
        const mbSpace = document.querySelector('.headnav__mobile-mbSpacing');

        const _navMobileMenu = document.querySelector('.headnav__mobile-menu');
        const _memberProfile = document.getElementById("member-profile");
        const navItems = navMobileMenu.querySelectorAll('.headnav__item-mobile');
        let mBanners = document.querySelectorAll('.memberbanner-item');
        let isEligible = sessionStorage.getItem('am_tb');
        function closeAllMemberBanner(){
            for(const _banner of mBanners){
                _banner.classList.remove('mb-active');
            }
            mbSpace.style.height = 0;
        }

        function closeAllMenu(){
            for(const navItem of navItems) {
                navItem.classList.remove('active');
            }
            closeAllMemberBanner();
        }

        async function checkEligibility(_myAPI, _header){
            try {
                if(isEligible == null || isEligible == "undefined") {
                    await checkCollectorEligibility(_myAPI, _header);
                    isEligible = sessionStorage.getItem('am_tb');
                }
                if (isEligible == "true") {
                    document.querySelector('.memberbanner-item #transfer-accounts').classList.add('d-none');
                }
                else{
                    document.querySelector('.memberbanner-item #transfer-balance').classList.add('d-none');
                }
            } catch (error) {
                console.error("error", error);
            }
        }

        for(const navItem of navItems) {
            navItem.onclick = () => {
                const _noLink = navItem.getAttribute('link');
                if(_noLink==='haslink'){
                    if(navItem.classList.contains('active')){
                        navItem.classList.remove('active');
                    }else{
                        closeAllMenu();
                        navItem.classList.add('active');
                    }
                }
            };
        }

        if (isAuthed()) {
            checkEligibility(getDomBFFApiHost()+apiURL.eligibility,false);
            const _memberProfileMobile = _memberProfile.cloneNode(true);
            _memberProfileMobile.setAttribute('id', 'member-profile-mobile');
            const _container = _navMobileMenu.querySelector(".headnav__mobile-container");
            _memberProfileMobile.classList.remove('d-none');
            _container.insertBefore(_memberProfileMobile, _container.firstChild);
            // re-capture memberBanner-item after duplicate for mobile
            mBanners = document.querySelectorAll('.memberbanner-item');
            for (const _banner of mBanners) {
                _banner.querySelector('.memberbanner-item__wrapper').onclick = () => {
                    if(_banner.classList.contains('mb-active')) {
                        _banner.classList.remove('mb-active');
                    }else{
                        closeAllMemberBanner();
                        _banner.classList.add('mb-active');
                    }
                    if (window.innerWidth < 768) {
                        mbSpace.style.height = _banner.querySelector('.memberbanner-item__modal').offsetHeight - 10 + 'px';
                    }
                };
                const _modalClose = _banner.querySelector(".js-close");
                _modalClose.onclick = () => {
                    if (_modalClose.classList.contains('js-close') || _modalClose.classList.contains('am-icon-functional-secondary-close')) {
                        closeAllMemberBanner();
                    }
                };
            }

            // Close all modals if click outside area
            function checkMemberModal(evt){
                const _mbModal = evt.target.closest(".memberbanner-item__modal");
                const _mbItem = evt.target.closest(".memberbanner-item");
                if(!_mbModal && !_mbItem || !_mbItem.classList.contains('mb-active')){
                    closeAllMemberBanner();
                }
            }
            window.addEventListener('click', checkMemberModal);
        }
        else{
            //clearing eligibility session value for transfer balance flow
            sessionStorage.removeItem('am_tb');
        }

        const hiddenElements = topNav.querySelectorAll(".topnav__links.d-none");
        for (const _el of hiddenElements) {
            _el.remove();
        }

    });
})();
