import { getApiHost, apiURL, getDomBFFDomainUrl } from './api';

(() => {
    if (!document.getElementById("optin-page")) return;

    const optinButton = document.getElementById('optin-button');
    const landingPage = document.getElementById('landing-page')
    const loadingPage = document.getElementById('loading-page');
    const successPage = document.getElementById('success-page');
    const failurePage = document.getElementById('failure-page');
    const languageToggle = document.getElementById('language_toggle');
    const frenchFooterLanguageToggle = document.getElementsByClassName("Français")[0];
    const englishFooterLanguageToggle = document.getElementsByClassName("English")[0];

    const display = {
        none: "none",
        block: "block"
    }

    const setDisplay = (page, display) => {
        page.style.display = display
    }

    setDisplay(failurePage, display.none);
    setDisplay(successPage, display.none);
    setDisplay(loadingPage, display.none);
    setDisplay(landingPage, display.none);

    const queryStrings = window.location.search;
    const urlParams = new URLSearchParams(queryStrings);
    const sst = urlParams.get('sst');
    const version = urlParams.get('version');

    if (!sst) {
        setDisplay(failurePage, display.block);
        return;
    } else {
        setDisplay(landingPage, display.block);
        
        const checkVersion2 = () => {if(version === '2') return '&version=2'}
        
        if (languageToggle) languageToggle.href = `${languageToggle.href}?sst=${encodeURIComponent(sst)}${checkVersion2()}`;
        if (frenchFooterLanguageToggle) frenchFooterLanguageToggle.href = `${frenchFooterLanguageToggle.href}?sst=${encodeURIComponent(sst)}${checkVersion2()}`;
        if (englishFooterLanguageToggle) englishFooterLanguageToggle.href = `${englishFooterLanguageToggle.href}?sst=${encodeURIComponent(sst)}${checkVersion2()}`;
    }

    const profileOptin = async (e) => {
        e.preventDefault();
        scroll(0,0);

        setDisplay(landingPage, display.none);
        setDisplay(loadingPage, display.block);

        const reqString = version === '2' ? getDomBFFDomainUrl() + apiURL.bmoOptin : getDomBFFDomainUrl() + apiURL.profileOptin;

        try {
            const response = await fetch(reqString, {
                method: 'POST',
                headers: {
                    Authorization: version === '2' ? `Bearer ${sst}` : sst,
                }
            })

            if (response.ok) {
                setDisplay(loadingPage, display.none)
                setDisplay(successPage, display.block);
            } else if (!response.ok) {
                setDisplay(loadingPage, display.none);
                setDisplay(failurePage, display.block);
            }
        } catch {
            setDisplay(loadingPage, display.none);
            setDisplay(failurePage, display.block);
        }
    }

    optinButton.onclick = profileOptin;
})();