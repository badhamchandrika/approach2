(() => {
    const searchComp = document.querySelector(".search");
    if (!searchComp) {return;}

    const searchOverlay = document.querySelector(".search__input-overlay");
    const searchToggleBtn = document.querySelector(".search__toggle");
    const searchIcon = document.querySelector("#header-search-icon");
    const searchUnderline = document.querySelector(".search__toggle-underline");
    const searchBtn = document.querySelector(".search__button");
    const searchLabel = document.querySelector("#header-search-label");
    const searchClearBtn = document.querySelector(".search__desktop-clear-btn");
    const mobileSearchClearBtn = document.querySelector(".search__mobile-clear-btn");
    const searchLabelText = searchLabel.innerText;
    const searchCancelText = document.querySelector("#cancel-label").innerText;
    const searchInput = document.querySelector(".search__input");

    const mobileModal = searchComp.querySelector('.search__modal');
    const sButtonMobile = searchComp.querySelector('.search__button-mobile');
    const sCancelMobile = searchComp.querySelector('.search__button-mobile-cancel');
    const sInputMobile = searchComp.querySelector(".search__input-mobile");
    const sButton = searchComp.querySelector('.search__button');
    const sInput = searchComp.querySelector(".search__input");

    const searchAttr = searchComp.querySelector('.search__wrapper');
    if(!searchAttr) {return;}
    const searchUrl = searchAttr.getAttribute('data-searchresult-url');
    const searchLimt = searchAttr.getAttribute('data-pagination');
    let searchIsOpen = false;

    const openSearch = ()=>{
        searchLabel.classList.add('search__toggle--open');
        searchLabel.innerText = searchCancelText;
        searchIcon.classList.add('d-none');
        searchOverlay.classList.remove('d-none');
        document.activeElement.blur();
        searchInput.focus();
        searchUnderline.classList.add('search__toggle-underline--open');
    };

    searchInput.addEventListener('input', e => {
        if(e.target.value === ''){
            searchClearBtn.classList.add('d-none');
        } else {
            searchClearBtn.classList.remove('d-none');
        }
    });

    searchClearBtn.addEventListener('click', ()=>{
        searchInput.value = '';
        searchClearBtn.classList.add('d-none');
    });

    sInputMobile.addEventListener('input', e => {
        // console.log('rennung');
        if(e.target.value === ''){
            // console.log('rennung condition 1');
            mobileSearchClearBtn.classList.add('d-none');
        } else {
            // console.log('rennung condition 2');
            mobileSearchClearBtn.classList.remove('d-none');
        }
    });

    mobileSearchClearBtn.addEventListener('click', ()=>{
            sInputMobile.value = '';
            mobileSearchClearBtn.classList.add('d-none');
    });

    const closeSearch = ()=>{
        searchLabel.classList.remove('search__toggle--open');
        searchLabel.innerText = searchLabelText;
        searchIcon.classList.remove('d-none');
        searchOverlay.classList.add('d-none');
        searchUnderline.classList.remove('search__toggle-underline--open');
    };

    const toggleSearchOverlay = () => {
        searchIsOpen = !searchIsOpen ;

        if(searchIsOpen){
            openSearch();
        } else if (!searchIsOpen){
            closeSearch();
        }
    };

    const closeSearchOnBodyClick = (e) =>{
        const { target } = e;
        if(searchIsOpen && !target.classList.contains('_search')) {
            closeSearch();
            searchIsOpen = !searchIsOpen;
        }
    };

    const goToResultsPage = (src='')=>{
        const searchTerm = src === "mobile" ? sInputMobile.value : searchInput.value;
        if(!searchTerm) {return;}
        window.location.href= `${searchUrl}?q=${encodeURIComponent(searchTerm)}&page=1&items=${searchLimt}`;
    };

    searchToggleBtn.addEventListener('click', toggleSearchOverlay);
    document.body.addEventListener('click', closeSearchOnBodyClick);
    searchBtn.addEventListener('click', goToResultsPage);
    searchInput.addEventListener('keypress', e=>{
        if(e.key === 'Enter'){goToResultsPage();}
    });

    document.addEventListener('keydown', e=>{
        if(searchIsOpen && e.key === 'Escape') {
            closeSearch();
            searchIsOpen = !searchIsOpen;
        }
    });

    /* Mobile */

    function closeModal(){
        mobileModal.classList.add('d-none');
    }

    sButtonMobile.onclick = () => {
        mobileModal.classList.contains('d-none') ? mobileModal.classList.remove('d-none') : closeModal();
        sInputMobile.focus();
    };

    sCancelMobile.onclick = () => {
        closeModal();
    };

    sInput.addEventListener("keyup", function(event) {
        event.preventDefault();
        if (event.keyCode === 13) {
            sButton.click();
        }
    });

    sInputMobile.addEventListener("keyup", function(event) {
        event.preventDefault();
        if (event.keyCode === 13) {
            goToResultsPage('mobile');
        }
    });
    
})();
