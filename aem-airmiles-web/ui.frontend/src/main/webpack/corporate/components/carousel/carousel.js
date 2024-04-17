(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const _carousel = document.querySelector(".custom-carousel");
        if(!_carousel){return;}
        const allCarousels = document.querySelectorAll(".custom-carousel");
        let activeItem = 0;
        function updateCarouselNumber(carouselId, numberOfItems){
            if(numberOfItems > 1) {
                let activeCarouselItem = 0;
                const carouselNum = document.getElementById(carouselId).getElementsByClassName('carousel_number');
                const carouselItems = document.getElementById(carouselId).getElementsByClassName('custom-carousel__item');
                if (carouselNum.length > 0) {
                    carouselNum[0].remove();
                }
                for (let i = 0; i < carouselItems.length; i++) {
                    if (carouselItems[i].className.indexOf('custom-carousel__item--active') != -1) {
                        activeCarouselItem = i + 1;
                        const carouselNum = document.getElementById(carouselId).getElementsByClassName('carousel_number');
                        if (carouselNum.length > 0) {
                            carouselNum[0].remove();
                        }
                        const carouselNumber = document.createElement("p");
                        carouselNumber.classList.add("carousel_number");
                        carouselNumber.textContent = activeCarouselItem + '/' + numberOfItems;
                        const b = document.getElementById(carouselId).getElementsByClassName('custom-carousel__actions')[0];
                        b.insertBefore(carouselNumber, b.firstChild);
                        break;
                    }
                }
            }
            else{
                if(document.getElementById(carouselId).getElementsByClassName('custom-carousel__actions').length > 0){
                    document.getElementById(carouselId).getElementsByClassName('custom-carousel__actions')[0].style.display = 'none';
                }
            }
        }
        function goToNextSlide(e, carouselId, numberOfItems){
            if(numberOfItems > 1){
                if (e.currentTarget.classList.contains('custom-carousel__action--previous')) {
                    document.getElementById(carouselId).getElementsByClassName("custom-carousel__action--previous")[0].style.backgroundColor = '#1A294D';
                    document.getElementById(carouselId).getElementsByClassName("custom-carousel__action--next")[0].style.backgroundColor = '#1F68DA';
                } else {
                    document.getElementById(carouselId).getElementsByClassName("custom-carousel__action--next")[0].style.backgroundColor = '#1A294D';
                    document.getElementById(carouselId).getElementsByClassName("custom-carousel__action--previous")[0].style.backgroundColor = '#1F68DA';
                }
            }
            const carouselItems = document.getElementById(carouselId).getElementsByClassName('custom-carousel__item');
            carouselItems[activeItem].classList.remove('custom-carousel__item--active');
            if(activeItem == (numberOfItems-1)){
                activeItem = 0;
            }
            else{
                activeItem = activeItem + 1;
            }
            carouselItems[activeItem].classList.add('custom-carousel__item--active');
            updateCarouselNumber(carouselId, numberOfItems);
        }

        function goToPrevSlide(e, carouselId, numberOfItems){
            if(numberOfItems > 1){
                if (e.currentTarget.classList.contains('custom-carousel__action--previous')) {
                    document.getElementById(carouselId).getElementsByClassName("custom-carousel__action--previous")[0].style.backgroundColor = '#1A294D';
                    document.getElementById(carouselId).getElementsByClassName("custom-carousel__action--next")[0].style.backgroundColor = '#1F68DA';
                } else {
                    document.getElementById(carouselId).getElementsByClassName("custom-carousel__action--next")[0].style.backgroundColor = '#1A294D';
                    document.getElementById(carouselId).getElementsByClassName("custom-carousel__action--previous")[0].style.backgroundColor = '#1F68DA';
                }
            }
            const carouselItems = document.getElementById(carouselId).getElementsByClassName('custom-carousel__item');
            carouselItems[activeItem].classList.remove('custom-carousel__item--active');
            if(activeItem == 0){
                activeItem = numberOfItems -1;
            }
            else{
                activeItem = activeItem - 1;
            }
            carouselItems[activeItem].classList.add('custom-carousel__item--active');
            updateCarouselNumber(carouselId, numberOfItems);
        }

        allCarousels.forEach(carousel => {
                //Getting carousel id
                const carouselId = carousel.getAttribute('id');
                const carouselItems = document.getElementById(carouselId).getElementsByClassName('custom-carousel__item');
                const parentElement = document.getElementById(carouselId).querySelector('.custom-carousel__content');
                let numberOfItems;
                if (parentElement) {
                    const childElements = parentElement.querySelectorAll('.custom-carousel__item');
                    numberOfItems = childElements.length;
                }
                if(carouselItems != null && carouselItems.length >= 1){
                    carouselItems[0].classList.add('custom-carousel__item--active');
                    for(let i=1;i<numberOfItems;i++){
                        carouselItems[i].classList.remove('custom-carousel__item--active');
                    }
                }
                else {
                    document.getElementById(carouselId).getElementsByClassName('h2-title-v2')[0].style.display = 'none';
                }
                const observer = new MutationObserver(function(mutationsList) {
                    for (const mutation of mutationsList) {
                        if (mutation.type === 'childList' && (mutation.addedNodes.length > 0 || mutation.removedNodes.length > 0)) {
                            if (parentElement) {
                                const childElements = parentElement.querySelectorAll('.custom-carousel__item');
                                numberOfItems = childElements.length;
                            }
                            if(carouselItems != null && carouselItems.length >= 1){
                                carouselItems[0].classList.add('custom-carousel__item--active');
                                for(let i=1;i<numberOfItems;i++){
                                    carouselItems[i].classList.remove('custom-carousel__item--active');
                                }
                            }
                            updateCarouselNumber(carouselId, numberOfItems);
                        }
                    }
                });
                const observerConfig = { childList: true };
                observer.observe(parentElement, observerConfig);
                const prevButton = document.getElementById(carouselId).querySelector('.custom-carousel__action--previous');
                const nextButton = document.getElementById(carouselId).querySelector('.custom-carousel__action--next');
                updateCarouselNumber(carouselId, numberOfItems);
                if(prevButton != null && nextButton != null){
                    prevButton.addEventListener('click', function(event){
                        goToPrevSlide(event, carouselId, numberOfItems);
                    });
                    nextButton.addEventListener('click', function(event){
                        goToNextSlide(event, carouselId, numberOfItems);
                    });
                }

            });
    });
})();