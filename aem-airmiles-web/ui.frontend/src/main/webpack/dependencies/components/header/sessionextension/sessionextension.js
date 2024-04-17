import {isAuthed,getCookie,setSessionFutureDate,sessionSignOut,setWebCookie} from "../../../js/utils";
(() => {
    // Temporary disabled for cookie fix
    if(isAuthed && 0){
        const el = document.querySelector('.count-js');
        const sessionExt = document.querySelector('.sessionextension');
        const sessionExtCanvas = document.querySelector('.sessionextension-canvas');
        const sessionExtStay = document.querySelector('.sessionextension-modal__stay');
        const sessionExtExit = document.querySelector('.sessionextension-modal__exit');
        const exitUrl = sessionExtCanvas.dataset.signouturl;
        const extSec = parseInt(sessionExtCanvas.dataset.extensionsec);
        let timestamp = '';
        function getNewSessionDate(){
            const dateString = decodeURIComponent(getCookie("g2gExpiry"));
            timestamp = Date.parse(dateString.replace(/\+/g, ' '));
        }
        getNewSessionDate();

        sessionExtStay.addEventListener('click',()=>{
            sessionExt.style.display = 'none';
            setWebCookie("g2gExpiry", setSessionFutureDate(extSec), 1);
            getNewSessionDate();
        })

        sessionExtExit.addEventListener('click',()=>{
            sessionSignOut(exitUrl);
        })

        let now = Date.now();
        let counter = 300;
        if(getCookie("g2gExpiry")){
            const extensionCountdown = setInterval(function(){
                now = Date.now();
                counter = Math.round((timestamp - now)/1000);
                const minutes = Math.floor(counter / 60);
                const seconds = counter % 60;
                const secondsString = seconds.toString().padStart(2, '0');
                // console.log(minutes + ":" + secondsString);
                el.innerHTML = minutes + ":" + secondsString;
                if (counter <= 0) {
                    el.innerHTML = "0";
                    clearInterval(extensionCountdown);
                    // temporary comment out
                    // sessionSignOut(exitUrl);
                }else if(counter < 30){
                    sessionExt.style.display = 'block';
                }
            }, 1000);
        }
    }
})();
