import {isAuthed, createElement, getBodyAttr} from "../../../js/utils";

(() => {

    const navMobileMenu = document.querySelector('.headnav__mobile-menu');
    const navMenu = document.querySelectorAll('.headnav__item');
    const navBar = document.querySelector('.headnav__underbar');

    const mBanners = document.querySelectorAll('.memberbanner-item');
    const mbSpace = document.querySelector('.headnav__mobile-mbSpacing');
    if(!navMenu || !navMobileMenu) {return;}
    // Hamburger button handler
    const hamburgerMenu = document.querySelector('.headnav__icon-menu');
    const hamburgerIcon = document.querySelector('.hamburger');
    const cancelIcon = document.querySelector('.close-modal');

    let initialed = false;

    if(!hamburgerMenu || !hamburgerIcon || !cancelIcon) {
      return;
    }
    hamburgerMenu.onclick = () => {
      if(navMobileMenu.classList.contains('d-none')){
        navMobileMenu.classList.remove('d-none');
        navMobileMenu.classList.add('d-flex');
        hamburgerIcon.classList.add('d-none');
        cancelIcon.classList.remove('d-none');
        document.documentElement.style.overflow = 'hidden';
      }else{
        navMobileMenu.classList.add('d-none');
        navMobileMenu.classList.remove('d-flex');
        hamburgerIcon.classList.remove('d-none');
        cancelIcon.classList.add('d-none');
        document.documentElement.style.overflow = 'auto';
      }
    };
    // Mobile menu handler
    function setAttributes(el, attrs) {
      for(const key in attrs) {
        el.setAttribute(key, attrs[key]);
      }
    }
    function copyAttributes(_src, _tar){
      const _tlk = _src.getAttribute('href');
      const _tgt = _src.getAttribute('target');
      const _tid = _src.getAttribute('data-track-id');
      const _tck = _src.getAttribute('data-track-click');
      const _ttp = _src.getAttribute('data-track-type');
      setAttributes(_tar, {
        "href": _tlk ? _tlk : '',
        "target": _tgt ? _tgt : '',
        "data-track-id": _tid ? _tid : '',
        "data-track-click": _tck ? _tck : '',
        "data-track-type": _ttp ? _ttp : '',
      });
    }
    const topNav = document.querySelector('.topnav.section');
    const navSites = topNav.querySelectorAll('.topnav__branding');
    const navLinks = document.querySelectorAll('.topnav__rightnav');
    const joinNow = document.getElementById('join_now');
    const signIn = document.getElementById('sign_in');

    function checkExists(_linkName, _type){
        let linkExists = false;
        if(_type==='links'){
            const _jsLinks = navMobileMenu.querySelector('.js_url_links').querySelectorAll('a');
            for (const element of _jsLinks) {
                if (element.textContent.trim() === _linkName.trim()) {
                    linkExists = true;
                    break;
                }
            }
        }else{
            const _jsSites = navMobileMenu.querySelector('.js_url_sites').querySelector('.headnav__bottomnav-wrapper-others').querySelectorAll('a');
            for (const element of _jsSites) {
                if (element.textContent.trim() === _linkName.trim()) {
                    linkExists = true;
                    break;
                }
            }
        }
        return linkExists;
    }

    function mobileNavInitial(){
        // Render Join now links in mobile
        if(joinNow){
            const _join = navMobileMenu.querySelector('.js_joinnow');
            copyAttributes(joinNow, _join);
            _join.innerHTML = joinNow.innerHTML;
        }
        // Render Join now links in mobile
        if(signIn){
            const _signIn = navMobileMenu.querySelector('.js_signin');
            copyAttributes(signIn, _signIn);
            _signIn.innerHTML = signIn.innerHTML;
        }

        // Render top nav links in mobile
        if(navSites && navLinks){
            const _link = navMobileMenu.querySelector('.js_url_links');
            const _site = navMobileMenu.querySelector('.js_url_sites');

            // Copy attributes from links on 'top-right' in desktop to mobile
            for(const navLink of navLinks){
                if(!isAuthed() && navLink.id === 'sign_out') {
                    // Don't copy sign out to mobile when not authenticated
                }else{
                    if(!checkExists(navLink.textContent,'links')){
                        const _li = createElement('li', '', _link,'','');
                        const _el = createElement('a', '', _li, {},navLink.innerHTML);
                        copyAttributes(navLink, _el);
                    }
                }
            }
            // Copy attributes from links on 'top-left' in desktop to mobile
            for(const navSite of navSites){
                const _other = navMobileMenu.querySelector('.headnav__bottomnav-wrapper-others');
                switch (navSite.id) {
                    // Static ID 'airmiles' from cq_dialog for airmiles.ca
                    case 'airmiles':
                        const _a = navMobileMenu.querySelector('.headnav__bottomnav-wrapper-airmiles').querySelector('a');
                        copyAttributes(navSite, _a);
                        break;
                    // Static ID 'shops' from cq_dialog for airmilesshops.ca
                    case 'shops':
                        const _s = navMobileMenu.querySelector('.headnav__bottomnav-wrapper-shops').querySelector('a');
                        copyAttributes(navSite, _s);
                        break;
                    default:
                        // Other links created here. Currently, none.
                        if(!checkExists(navSite.textContent,'sites')) {
                            const _div = createElement('div', ['headnav__bottomnav-mobile'], _site, '', '');
                            const navSiteClone = navSite.cloneNode(true);
                            _div.appendChild(navSiteClone);
                            _other.appendChild(_div);
                        }
                }
            }
        }
        initialed = true;
    }
    if(!initialed){
        mobileNavInitial();
    }

    function closeAllMemberBanner(){
        for(const _banner of mBanners){
            _banner.classList.remove('mb-active');
        }
        if(!mbSpace){ return;}else{mbSpace.style.height = 0;}
    }

    // desktop menu
    function setHover(_item, _stat){
      if(_stat){
        _item.classList.add('headnav__item__hover','buffer');
        navBar.style.left = _item.offsetLeft+"px";
        navBar.style.width = _item.offsetWidth+"px";
        setTimeout(function (){_item.classList.remove('buffer');}, 10);
      }else{
        _item.classList.remove('headnav__item__hover');
        navBar.style.width = 0+"px";
      }
      closeAllMemberBanner();
    }
    for(const navItem of navMenu) {
      const _arrow = navItem.querySelector('.headnav__item-arrow');
      navItem.onmouseover = () => {
        setHover(navItem,true);
      };
      navItem.onmouseout = () => {
        setHover(navItem,false);
      };
      if(_arrow){
        _arrow.onclick = () =>{
          const _parent = _arrow.parentElement;
          if(!_parent.classList.contains('buffer')){
            _parent.classList.contains('headnav__item__hover')? setHover(_parent,false) : setHover(_parent,true);
          }
        };
      }
    }

    const navLinkObserver = new ResizeObserver(() => {
      const viewWidth = document.documentElement.clientWidth;
      if(viewWidth >= 768 && navMobileMenu.classList.contains('d-flex')){
        navMobileMenu.classList.add('d-none');
        navMobileMenu.classList.remove('d-flex');
        hamburgerIcon.classList.remove('d-none');
        cancelIcon.classList.add('d-none');
        document.documentElement.style.overflow = 'auto';
      }
    });
    navLinkObserver.observe(navMobileMenu);

})();
