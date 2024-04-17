import {defaultLocal} from "../alloffers/support";
import {initPartnerLogos} from "./suggestPartners";
import {isAuthed} from "../../../dependencies/js/utils";

(() => {
    document.addEventListener("DOMContentLoaded", () => {
        const partnerLogoComponent = document.querySelector('.offers-top-partners');
        if(!partnerLogoComponent)return;
        const _standalone = partnerLogoComponent.hasAttribute("data-standalone");
        if(isAuthed() && _standalone){ // Load Partners You may like logos
            initPartnerLogos(defaultLocal);
        }
    });
})();