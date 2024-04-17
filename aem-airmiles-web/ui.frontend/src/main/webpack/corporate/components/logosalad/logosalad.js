
(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const logoSaladComponent = document.querySelector(".logo_salad");
        if (!logoSaladComponent) {
            return;
        }
        const visibleMdLogos = document.querySelectorAll('.logo_salad__container .visible-md-logo');
        const visibleSmLogos = document.querySelectorAll('.logo_salad__container .visible-sm-logo');
        const visibleLgLogos = document.querySelectorAll('.logo_salad__container .visible-lg-logo');
        if(visibleMdLogos != null){
            visibleMdLogos.forEach((element, index) => {
                if (index % 2 == 0) {
                    element.classList.add('md-odd');
                }
            });
        }
        if(visibleSmLogos != null){
            visibleSmLogos.forEach((element, index) => {
                if (index % 2 == 0) {
                    element.classList.add('sm-odd');
                }
            });
        }

        if(visibleLgLogos != null){
            visibleLgLogos.forEach((element, index) => {
                if (index % 2 == 0) {
                    element.classList.add('lg-odd');
                }
            });
        }
    });
})();