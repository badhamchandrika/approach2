import {getCookie, setWebCookie} from "./utils";
(() => {
    const _modals = document.querySelectorAll('.modal_js_pageload');
    if (!_modals) {  return; }
    for (const _modal of _modals) {
        const _uid = _modal.id;
        const _modalCookie = 'modal_' + _uid;
        // console.log("getModalCookie(_modalCookie)::", getCookie(_modalCookie));
        if (_modal.dataset.runOnce !== 'true') {
            setWebCookie(_modalCookie, 'false', 1);
        }
        if (getCookie(_modalCookie) !== 'true') {
            window.addEventListener('DOMContentLoaded', () => {
                const _modalId = '#' + _uid;
                const _modalJS = new bootstrap.Modal(_modalId);
                _modalJS.show();
            });
            if (_modal.dataset.runOnce === 'true') {
                setWebCookie(_modalCookie, 'true', 30);
            }
        }
    }
})();