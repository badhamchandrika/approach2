(() => {
    const _um = document.querySelector('.usemiles');
    if(!_um){return;}
    const tabs = _um.querySelectorAll(".usemiles__tab-item");
    const tabContents = _um.querySelectorAll(".usemiles__tab-content-innercontainer");
    const underline = _um.querySelector('.usemiles__underline');
    const _umContainer = _um.querySelector('.usemiles__container');
    const _umContainers = _umContainer.querySelectorAll('.usemiles__tab-content-innercontainer');
    function setUnderline(){
        const tab = _um.querySelector('.active-tab');
        underline.style.left = tab.offsetLeft+"px";
        underline.style.width = tab.offsetWidth+"px";
    }
    function mouseOverUnderline(_l, _w){
        underline.style.left = _l+"px";
        underline.style.width = _w+"px";
    }
    function switchClass(e) {
        for (let i = 0; i < tabs.length; i++) {
            tabs[i].classList.remove("active-tab");
            tabContents[i].classList.remove("show-container");
        }
        const selectedTab = e.target;
        selectedTab.classList.add("active-tab");
        const selectedTabID = e.target.id;
        const idTabContent = selectedTabID.replace("tab_", "tabcontent_");
        const currentTabContent= document.getElementById(idTabContent);
        currentTabContent.classList.add("show-container");
        setUnderline();
    }
    for (let i = 0; i < tabs.length; i++) {
        const tab = tabs[i];
        tab.addEventListener("click", switchClass);
        tab.onmouseover = () => {mouseOverUnderline(tab.offsetLeft, tab.offsetWidth);};
        tab.onmouseout = () => {setUnderline();};
        if(i === 0){
            tab.classList.add("active-tab");
            // Set the underline at 1st time for 1st tab
            setUnderline();
        }
    }

    // Reset layout to match legacy layout
    for (const container of _umContainers){
        const _img = container.querySelector('.text-image__col-image');
        const _txt = container.querySelector('.text-image__col-text');
        _img.classList.remove('col-md-6');
        _img.classList.add('col-lg-6');
        _txt.classList.remove('text-image__col-text');
        _txt.classList.remove('col-md-6');
        _txt.classList.add('col-lg-6');
        _txt.classList.remove('text-md-start');
        _txt.classList.add('text-lg-start');
    }

    const underlineObserver = new ResizeObserver(() => {
        setUnderline();
    });
    underlineObserver.observe(_um);

})();
