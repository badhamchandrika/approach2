import { isAuthed } from "../../../dependencies/js/utils";
(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const _signInMod = document.querySelector('.alloffers-sign-in');
        if(!_signInMod )return;
        const _auth = isAuthed();
        const _downloadBtn = _signInMod.querySelector('.signin-download');
        const _downloadFrame = _signInMod.querySelector('#airmiles-app-download');
        const _replacementClassName = `.${_signInMod.dataset.compClass}`;
        const _replacedSignedComponent = document.querySelector(_replacementClassName);
        const _aHref = _downloadBtn.querySelector('a');
        _aHref.setAttribute('href','javascript:void(0)');

        const isEditMode = document.querySelector(".cq-placeholder");
        if (isEditMode) return;
        !_auth && _signInMod.classList.remove('d-none');
        if(_signInMod && _replacedSignedComponent){
            _auth ? _signInMod.remove() : _replacedSignedComponent.remove();
        }

        const _urls = {
            "androidURL": _signInMod.dataset.urlAndroid,
            "androidDeeplink": _signInMod.dataset.deeplinkAndroid,
            "iosURL": _signInMod.dataset.urlIos,
            "iosDeeplink": _signInMod.dataset.deeplinkIos,
        }
        function openURL(_deeplink,_url) {
            _downloadFrame.src = _deeplink;
            // window.location.href = _deeplink;
            setTimeout(function() {
                window.location.href = _url;
            }, 500);
        }
        const userAgent = navigator.userAgent || navigator.vendor || window.opera;
        _aHref.addEventListener('click',(()=>{
            if (/android/.test(userAgent.toLowerCase())) {
                console.log('ANDROID')
                openURL(_urls.androidDeeplink,_urls.androidURL)
            } else if (/iphone|ipad|ipod/.test(userAgent.toLowerCase()) && !window.MSStream) {
                console.log('IOS')
                openURL(_urls.iosDeeplink,_urls.iosURL)
            }
        }));
    });
})();