import {isAuthed, getMemdeets, setWebCookie} from "../../../js/utils";
import {apiURL, getApiHost, useFetch} from "../../../js/api";

(() => {
    const memdeetsTemp = {
        "cardNumber":"",
        "memberId":"",
        "firstName":"",
        "tier":"",
        "cashBalance":"",
        "dreamBalance":"",
        "region":"",
        "languagePreference":"",
        "postalCode":""
    }
    async function generateMemdeetCookie() {
        try {
            const data = await useFetch(getApiHost()+apiURL.memberBanner, false);
            memdeetsTemp['cardNumber'] = data['cardNumber'];
            memdeetsTemp['memberId'] = data['memberId'];
            memdeetsTemp['firstName'] = data['firstName'];
            memdeetsTemp['tier'] = data['tier'];
            memdeetsTemp['cashBalance'] = data['cashBalance'];
            memdeetsTemp['dreamBalance'] = data['dreamBalance'];
            memdeetsTemp['region'] = data['region'];
            memdeetsTemp['languagePreference'] = data['languagePreference'];
            memdeetsTemp['postalCode'] = data['postalCode'];

            const memdeetsObjStr = JSON.stringify(memdeetsTemp);
            const encodedString = encodeURIComponent(memdeetsObjStr);
            setWebCookie('memdeets', encodedString, 7);
        } catch (error) {
            console.error('Error:', error);
        }
    }

    if (isAuthed() && !getMemdeets()) {
        generateMemdeetCookie();
    }
})();
