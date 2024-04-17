let toastInitialized = false;
const toastTemplate = document.getElementById('toast-template');

export function showToast(toastID) {
    if(!toastTemplate)return;
    const toastComp = document.querySelector('#toast-template-info').content;
    const toastData = toastComp.querySelector(`#${toastID}`);
    const icon = toastData.dataset.customIcon ? toastData.dataset.customIcon : toastData.dataset.icon;
    const title = toastData.dataset.title;
    const message = toastData.dataset.message;
    const duration = toastData.dataset.duration;
    const type = toastData.dataset.type;
    const hasCloseBtn = toastData.hasAttribute('data-has-close');

    const clone = toastTemplate.content.querySelector('.amtoast-box').cloneNode(true);
    const styleType = type || toastTemplate.dataset.style;
    clone.classList.add(styleType);
    let _icons = icon || toastTemplate.dataset.icon;
    _icons = _icons.split(' ');
    const container = document.querySelector(".amtoast-container");
    if(_icons){
        for(const _ico of _icons){
            clone.querySelector('.amtoast-icon').classList.add(_ico);
        }
    }else{
        clone.querySelector('.amtoast-icon').remove();
    }

    const _closeBtn = clone.querySelector('.amtoast-close');
    function fadeToastOut(){
        setTimeout(() => {
            clone.classList.add('fadeout');
            setTimeout(() => {
                clone.remove();
            }, 500);
        }, duration * 1000);
    }
    _closeBtn.addEventListener('click',()=>{
        fadeToastOut();
    })
    if(!hasCloseBtn && duration > 0){
        _closeBtn.remove();
    }
    const _title = clone.querySelector('.amtoast-title');
    title ? (_title.innerText = title) : _title.remove();
    clone.querySelector('.amtoast-body').innerText = message;
    container.appendChild(clone);
    setTimeout(() => {
        clone.classList.add('show');
        if(duration > 0){fadeToastOut();}
    }, 10);
}

(() => {
    document.addEventListener("DOMContentLoaded", () => {
        if(!toastTemplate || toastInitialized)return;
        toastInitialized=true;
    });
})();