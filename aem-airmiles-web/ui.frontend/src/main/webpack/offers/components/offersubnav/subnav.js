function initBreadCrumbs(_breadcrumb){
    const mobileArrow = _breadcrumb.querySelector('.am-icon.am-icon-functional-arrow-left');
    mobileArrow && mobileArrow.remove();
    const ltArrows = _breadcrumb.querySelectorAll('.am-icon.am-icon-functional-arrow-right');
    for (const ltArrow of ltArrows){
        ltArrow.classList = [];
        ltArrow.classList.add('offers-subnav__divider');
        ltArrow.textContent = "";
    }
    const _breadcrumbCont = _breadcrumb.querySelector('.cmp-breadcrumb.bsp-container');
    _breadcrumbCont.classList.remove('bsp-container');

}
(() => {
    document.addEventListener("DOMContentLoaded", () => {
        const subNav = document.querySelector('.offers-subnav');
        if(!subNav)return;
        subNav.classList.remove('d-none');
        const _breadcrumb = subNav.querySelector('.breadcrumb');
        _breadcrumb && initBreadCrumbs(_breadcrumb);
    });
})();
