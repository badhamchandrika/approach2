import {apiURL, getDomBFFApiHost, checkCollectorEligibility} from "../../../dependencies/js/api";

(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const quickLinkWrappers = document.querySelectorAll(".cmp-quick-link-wrapper");
        if (!quickLinkWrappers.length) return;

        const transferBalanceQuickLink = document.querySelector("#transfer-balance");
        let isEligible = sessionStorage.getItem('am_tb');

        async function displayTransferBalanceQuickLink(){
            if(transferBalanceQuickLink != null){
                if(isEligible == null || isEligible == "undefined"){
                    await checkCollectorEligibility(getDomBFFApiHost()+apiURL.eligibility,false);
                    isEligible = sessionStorage.getItem('am_tb');
                }
                if(isEligible == "false" || isEligible == "undefined"){
                    transferBalanceQuickLink.classList.add('d-none');
                }
            }
        }
        displayTransferBalanceQuickLink();

        //Quicklink expand/collpase items functionality
        quickLinkWrappers.forEach(quickLinkWrapper => {
            const quicklinkList = quickLinkWrapper.querySelectorAll('.quicklink__items>div');
            const expandCollapseButton = quickLinkWrapper.querySelector('.quicklink__expand-tools span');
            const expandButtonText = quickLinkWrapper.querySelector('.quicklink__expand-tools').getAttribute('data-expand-text');
            const collapseButtonText = quickLinkWrapper.querySelector('.quicklink__expand-tools').getAttribute('data-collapse-text');
            let text = collapseButtonText;

            expandCollapseButton.addEventListener('click', (e) => {
                quicklinkList.forEach((item, index) => {
                    if(index >= 4) {
                        item.classList.toggle('hide-on-mobile');
                    }
                })
                expandCollapseButton.innerText = text;
                expandCollapseButton.classList.toggle('collapse-tools');
                text = (text === collapseButtonText) ? expandButtonText : collapseButtonText;

                e.preventDefault();
                quickLinkWrapper.querySelector('.quicklink').scrollIntoView({
                    behavior: 'smooth'
                });
            });
        });
    });
})();
