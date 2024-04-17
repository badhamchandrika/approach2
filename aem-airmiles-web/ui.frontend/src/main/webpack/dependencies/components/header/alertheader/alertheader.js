(() => {
    const alertHeader = document.querySelector(".alertheader");
    if(!alertHeader){return;}
    const alertContainer = alertHeader.querySelector(".alertheader-container");
    if(!alertContainer){return;}
    const alertBorders = alertContainer.querySelectorAll('.alertheader-border');
    let totalHeight = 0;
    let heightReset = 0;

    function removeHeight(){
        for (let i = alertContainer.classList.length - 1; i >= 0; i--) {
            const className = alertContainer.classList[i];
            if (className.startsWith('--am-base-height')) {
                alertContainer.classList.remove(className);
            }
        }
    }

    function resetAlertHeight(){
        // Remove existing height class.
        removeHeight();
        // Calculating total height of existing alert.
        const _alertBorders = alertContainer.querySelectorAll('.alertheader-border');
        totalHeight = 0;
        for(const border of _alertBorders){
            totalHeight += border.offsetHeight;
        }
        // Set height of alerts container.
        heightReset = "--am-base-height-"+Math.round(totalHeight/16)*2;
        alertContainer.classList.add(heightReset);
    }

    // Remove invalid Alerts
    for(const alert of alertBorders) {
        const currentTimestamp = new Date();
        const dateToStart = new Date(alert.dataset.startTime);
        const dateToEnd = new Date(alert.dataset.endTime);
        if(currentTimestamp < dateToStart || currentTimestamp > dateToEnd) {
            alert.remove();
        }
    }

    // Initial alerts setup
    for(const border of alertBorders){
        // Add onclick even for close button of each alert.
        const _closeBtn = border.querySelector('.alertheader-close');
        if(_closeBtn) {
            _closeBtn.onclick = () => {
                const _eid = _closeBtn.id.slice(6);
                document.getElementById(_eid).remove();
                sessionStorage.setItem(String(_eid), 'false');resetAlertHeight();
            };
        }
        // remove alert(s) been closed in session.
        const _val = sessionStorage.getItem(border.id);
        _val!=='false' ? sessionStorage.setItem(border.id, 'true') : document.getElementById(border.id).remove();
    }
    resetAlertHeight();

    const alertHeightObserver = new ResizeObserver(() => {
        resetAlertHeight();
    });
    alertHeightObserver.observe(alertHeader);

})();
