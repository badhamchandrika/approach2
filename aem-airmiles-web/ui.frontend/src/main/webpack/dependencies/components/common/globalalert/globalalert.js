(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const _globalAlerts = document.querySelectorAll('.globalalert-container');
        function globalAlertDisplay(_globalAlerts){
            for(const _globalAlert of _globalAlerts){
                const observer = new MutationObserver(function(mutationsList) {
                    for (const mutation of mutationsList) {
                        if (mutation.type === 'childList' && (mutation.addedNodes.length > 0 || mutation.removedNodes.length > 0)) {
                            globalAlertDisplay(_globalAlerts);
                        }
                    }
                });
                const displaySlide = _globalAlert.querySelector('.globalalert-display');
                const observerConfig = { childList: true };
                observer.observe(displaySlide, observerConfig);
                //remove invalid date alert
                const _allAlerts = _globalAlert.querySelectorAll('.globalalert-display__slide');
                for(const _ga of _allAlerts) {
                    const currentTimestamp = new Date();
                    const dateToStart = new Date(_ga.dataset.startTime);
                    const dateToEnd = new Date(_ga.dataset.endTime);
                    if (currentTimestamp < dateToStart || currentTimestamp > dateToEnd) {
                        _ga.remove();
                    }
                }
                const _allAlertsInternal = _globalAlert.querySelectorAll('.int-view');
                for(const _ga of _allAlertsInternal) {
                    const currentTimestamp = new Date();
                    const dateToStart = new Date(_ga.dataset.startTime);
                    const dateToEnd = new Date(_ga.dataset.endTime);
                    if (currentTimestamp < dateToStart || currentTimestamp > dateToEnd) {
                        _ga.classList.add('deactivate');
                    }
                }

                const _display = _globalAlert.querySelector('.globalalert-display');
                const _alerts = _globalAlert.querySelectorAll('.globalalert-display__slide:not(.d-none)');
                const _controller = _globalAlert.querySelector('.globalalert-controller');
                if(_allAlerts.length < 1){
                    _controller.classList.add('d-none');
                }
                //If there are no alerts, remove the white space
                if(_allAlerts.length == 0){
                    _display.style.height = 0+'px';
                    _globalAlert.classList.remove('bsp-container');
                }
                else {
                    if(!_globalAlert.classList.contains('bsp-container')){
                        _globalAlert.classList.add('bsp-container');
                    }
                }
                const _duration = parseInt(_display.dataset.duration);
                const _pageNum = _globalAlert.querySelector('.globalalert-pagination__numbers');

                let _index = 0;
                let _intervalId;

                const _play = _globalAlert.querySelector('.globalalert-player__play');
                const _pause = _globalAlert.querySelector('.globalalert-player__pause');
                const _prev = _globalAlert.querySelector('.globalalert-pagination__prev');
                const _next = _globalAlert.querySelector('.globalalert-pagination__next');

                function showSlide(_idx) {
                    _alerts.forEach(_slide => _slide.classList.remove('active'));
                    if(_alerts[_idx]){
                        _alerts[_idx].classList.add('active');
                        const _tempHt = _alerts[_idx].clientHeight < 40 ? _alerts[_idx].clientHeight - 14 : _alerts[_idx].clientHeight;
                        _display.style.height = _tempHt+'px';
                        _pageNum.textContent = _alerts.length > 1 ? _idx+1 + " / " + _alerts.length : '';
                    }
                }

                function nextSlide() {
                    _index++;
                    if (_index >= _alerts.length) {
                        _index = 0;
                    }
                    showSlide(_index);
                }

                function prevSlide() {
                    _index--;
                    if (_index < 0) {
                        _index = _alerts.length - 1;
                    }
                    showSlide(_index);
                }

                function playAlertSlides() {
                    _pause.classList.remove('d-none');
                    _play.classList.add('d-none');
                    _intervalId = setInterval(nextSlide, _duration * 1000);
                }

                function pauseAlertSlides() {
                    _play.classList.remove('d-none');
                    _pause.classList.add('d-none');
                    clearInterval(_intervalId);
                }

                // Show the alert slide initially
                if(_alerts.length > 0){ showSlide(_index); }
                _controller.classList.toggle('d-none', _alerts.length <= 1);
                if(_alerts.length > 1){ playAlertSlides(); }

                // Add event listeners to buttons
                _play.addEventListener('click', playAlertSlides);
                _pause.addEventListener('click', pauseAlertSlides);

                _prev.addEventListener('click', prevSlide);
                _next.addEventListener('click', nextSlide);

                // Update layout when resize
                const globalAlertObserver = new ResizeObserver(() => {
                    showSlide(_index);
                });
                globalAlertObserver.observe(_display);
            }
        }
        if(_globalAlerts){
            globalAlertDisplay(_globalAlerts);
        }
    });
})();
