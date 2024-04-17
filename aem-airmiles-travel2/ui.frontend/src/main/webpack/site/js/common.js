import Cookies from 'universal-cookie';

export const isBrowser = () => typeof window !== 'undefined';

export const getLocale = () => {
    const availableLocales = {
        'en-CA': 'en-CA',
        'en': 'en-CA',
        'fr-CA': 'fr-CA',
        'fr': 'fr-CA',
    };
    if (isBrowser()) {
        const locale = document
            .getElementsByTagName('html')[0]
            .getAttribute('lang');
        return availableLocales[locale] || 'en-CA';
    }
    return 'en-CA';
};

export const currentLang = () => {
    // uncomment for local dev since the cms might not update the <html lang="en">
    // to <html lang="fr-CA"> when switching languages
    // return window.location.href.indexOf('/fr/') > -1 ? 'fr' : 'en';
    return getLocale() === 'fr-CA' || getLocale() === 'fr' ? 'fr' : 'en';
};

export const generateRedirectURL = (() => {
    // Check for cross-origin errors. When we're hosting the header in an iframe
    // on a different airmiles subdomain, we need to set matching document.domain

    if (isBrowser()) {
        const homePageURL = `${location.origin}/${currentLang()}.html`;
        // Add any additional keys which you want to black list
        const englishBlackListKeys = ['sign-out', 'error'];
        // Add same keys here but in french to support french URLs
        const frenchBlackListKeys = ['erreurs', 'deconnexion'];
        const blackListKeys = [...englishBlackListKeys, ...frenchBlackListKeys];

        const pageRedirectLogic = location.href;

        const shouldRedirectToHomePage = !!location.pathname
            .split('/')
            .filter(
                string =>
                    blackListKeys.filter(blackKey => string.includes(blackKey)).length
            ).length;
        return shouldRedirectToHomePage ? homePageURL : pageRedirectLogic;
    }

    return '';
})();

const errorPages = {
    500: '500.html',
};
export const generateErrorRedirectURL = (errorCode = 500) => {
    const lang = currentLang();
    const errorString = lang === `en` ? `errors` : `erreurs`;
    return `${location.origin}/${lang}/${errorString}/${errorPages[errorCode]}`;
};
// Setting Redirect Cookie Here
const cookies = new Cookies();
cookies.set("returnTo", generateRedirectURL, {
    path: '/',
    domain: '.airmiles.ca',
});

// Setting Redirect Error Cookie
cookies.set("returnToErr", generateErrorRedirectURL(500), {
    path: '/',
    domain: '.airmiles.ca',
});
