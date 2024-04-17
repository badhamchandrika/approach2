import {isAuthed, getMemdeets} from "../../../dependencies/js/utils";
import {apiURL, getApiHost, useFetch} from "../../../dependencies/js/api";
(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const mockup = document.getElementById('js-welcome-banner-json');
        const welcomeWrapper = document.querySelector(".cmp-welcome-banner-wrapper");
        const wishData = document.querySelector(".cmp-welcome-banner-wrapper .wish-text");
        if (!welcomeWrapper || !wishData) {
            return;
        }
        // const apiUrl = welcomeWrapper.getAttribute('data-api');
        const morningText = wishData.getAttribute('data-morningText');
        const afternoonText = wishData.getAttribute('data-afternoonText');
        const eveningText = wishData.getAttribute('data-eveningText');

        let cashMilesPercentageVal;
        let dreamMilesPercentageVal;

        /*Showing message based on system time*/
        const date = new Date();
        const hours = date.getHours();
        let finalWishText;
        if (hours < 12) {
            finalWishText = morningText;
            welcomeWrapper.querySelector('.welcome-text-section .am-icon2-morning').classList.remove('d-none');
        } else if (hours >= 12 && hours < 17) {
            finalWishText = afternoonText;
            welcomeWrapper.querySelector('.welcome-text-section .am-icon2-day').classList.remove('d-none');
        } else {
            finalWishText = eveningText;
            welcomeWrapper.querySelector('.welcome-text-section .am-icon2-night').classList.remove('d-none');
        }

        const benefitsCta = document.querySelector(".benefits-link .ctalink .cmp-link");
        if (benefitsCta != null) {
            benefitsCta.innerHTML += '<span class="am-icon2 am-icon2-arrow-right"></span>';
        }

        const memberBanner = document.getElementById('member-profile');
        function syncMilesWithMemberBanner(){
            const _cashVal = memberBanner.querySelector(".memberbanner-item__cashNum").innerText;
            const _dreamVal = memberBanner.querySelector(".memberbanner-item__dreamNum").innerText;
            document.querySelector('.mile-points.cash-miles').innerText = _cashVal;
            document.querySelector('.mile-points.dream-miles').innerText = _dreamVal;
        }

        // Observe mutation from memberBanner just in case.
        const mutationObserver = new MutationObserver(entries =>{
            syncMilesWithMemberBanner();
            // mutationObserver.disconnect(); // Keep observing as default. Un-comment this line to observe only once.
        });
        mutationObserver.observe(memberBanner,{
            subtree: true,
            characterData: true
        });

        function generatedata(profile) {
            syncMilesWithMemberBanner();
            if (profile != null) {
                if (profile['personalDetails'] != null && profile['personalDetails'].firstName != null) {
                    const firstName = profile['personalDetails'].firstName;
                    const firstLetter = firstName.charAt(0).toUpperCase();
                    const finalName = firstLetter+firstName.substring(1).toLowerCase();
                    document.querySelector('.cmp-welcome-banner-wrapper .wish-text').innerText= finalWishText;
                    document.querySelector('.cmp-welcome-banner-wrapper .collector-name').innerText = finalName;
                }
                if (profile['preferenceDetails'] != null && profile['preferenceDetails'].amCashRatio != null) {
                    cashMilesPercentageVal = profile['preferenceDetails'].amCashRatio;
                    dreamMilesPercentageVal = 100 - cashMilesPercentageVal;
                    document.querySelector(".miles-line-bar hr.cash-bar").style.width= cashMilesPercentageVal+ '%';
                    document.querySelector(".miles-line-bar hr.dream-bar").style.width= dreamMilesPercentageVal+ '%';
                    if(cashMilesPercentageVal == 0){
                        document.querySelector('.miles-line-bar hr.cash-bar').classList.add('d-none');
                    }
                    if(dreamMilesPercentageVal == 0){
                        document.querySelector('.miles-line-bar hr.dream-bar').classList.add('d-none');
                    }
                    document.querySelector(".miles-percentage-text .cash-miles-percentage").innerText = cashMilesPercentageVal + '%';
                    document.querySelector(".miles-percentage-text .dream-miles-percentage").innerText = dreamMilesPercentageVal + '%';
                }
                if (profile['tier'] != null) {
                    const tierName = profile['tier'];
                    const tierColors = document.querySelector('.collector-type');
                    if (tierName.toUpperCase() === "B") {
                        document.querySelector('.collector-type .bc-user').classList.remove('d-none');
                        document.querySelector('.collector-type').style.background = tierColors.getAttribute('data-blueTierColor');
                    } else if (tierName.toUpperCase() === "G") {
                        document.querySelector('.collector-type .gc-user').classList.remove('d-none');
                        document.querySelector('.collector-type').style.background = tierColors.getAttribute('data-goldTierColor');
                    } else if (tierName.toUpperCase() === "O") {
                        document.querySelector('.collector-type .oc-user').classList.remove('d-none');
                        document.querySelector('.collector-type').style.background = tierColors.getAttribute('data-onyxTierColor');
                    }
                }

            }
        }


        if (mockup) {
            const profileData = {
                "profile": {
                    "personalDetails": {
                        "firstName": "TOM",
                    },
                    "preferenceDetails": {
                        "amCashRatio": 30,
                    },
                    "tier": "O",
                }
            };
            generatedata(profileData['profile']);
        } else {
            if (isAuthed()) {
                useFetch(getApiHost() + apiURL.profile, true)
                    .then((data) => {
                        const profile = data['profile'];
                        generatedata(profile);
                    })
                    .catch((error) => {
                        console.error('Error:', error);
                    });
            }
        }
    });
})();
