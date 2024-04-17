
export const envElements = {
    mainContainer : document.querySelector('.alloffers-container'),
    lang: document.querySelector('.alloffers-container') ?
        document.querySelector('.alloffers-container').getAttribute('data-lang') : 'en-CA',
    body : document.querySelector('body'),
    filterBtn : document.querySelector('.alloffers-tabs__apart__sort'),
    tagContainer : document.querySelector('.alloffers-tag__container'),
    tagClearBtn: document.querySelector('.alloffers-tag__container') ?
        document.querySelector('.alloffers-tag__container').querySelector('.clear') : '',
    showcase : document.querySelector('.alloffers-offers__showcase'),
    quickFilterTabs : document.querySelectorAll('.alloffers-tabs__slides__tab'),
    filterPanel : document.querySelector('.alloffers-offers__filter-panel'),
    filterBox : document.querySelector('.alloffers-offers__filter-box'),
    filterViewResultBtn : document.querySelector('.alloffers-filters__buttons__viewresult'),
    filterResetBtn : document.querySelector('.alloffers-filters__buttons__reset'),
    closeBtnPanel : document.querySelector('.alloffers-offers__close-btn'),
    tabletFilterBG : document.querySelector('.alloffers-offers__filter-bg'),
    panelToggle : document.querySelector('.alloffers-panel__toggle'),
    cardTemplate : document.querySelector('#all-offers-template'),
    barcodeTemplate : document.querySelector('#alloffers-barcode'),
    loaderCardTemplate : document.querySelector('#all-offers-loader-template'),
    offersShowing : document.querySelector('.alloffers-showing'),
    offersNum : document.querySelector('.alloffers-showing__numbers'),
    typeGroup : document.querySelector('.filter-by__types'),
    typesInputs : document.querySelectorAll('.types-input'),
    accordionButtons : document.querySelectorAll('.alloffers-filters__accordion-btn'),
    filterBackButtons : document.querySelectorAll('.alloffers-filters__slides__backbutton'),
    resultContainer : document.querySelector('.alloffers-offers__result-container'),
    noOffers : document.querySelector('.alloffers-offers__no-offers-description'),
    apiFails : document.querySelector('.alloffers-offers__no-result-apifails-description'),
    noOffersForYou : document.querySelector('.alloffers-offers__no-result-no-offers-for-you'),
    noSavedOffers : document.querySelector('.alloffers-offers__no-result-no-saved-offers'),
    customError : document.querySelector('.alloffers-offers__no-result-custom'),
    tabTemplate : document.querySelector('#filter-tab'),
    totalPartnersNum : document.querySelector('.alloffers-filters__partners__total'),
    totalTypesNum : document.querySelector('.alloffers-filters__types__total'),
    totalCategoriesNum : document.querySelector('.alloffers-filters__categories__total'),
    totalFiltersNum : document.querySelector('.alloffers-filters__total')
};

export const modalElements = {
    detailTemplate: document.querySelector('.alloffers-detail'),
    detailTermsBody: document.querySelector('.alloffers-detail__terms'),
    detailedSaveBtn: document.querySelector('.alloffers-detail__saved'),
    detailBackBtn: document.querySelector('.alloffers-detail__backbutton'),
    detailSlide: document.querySelector('.alloffers-detail__slides'),
}