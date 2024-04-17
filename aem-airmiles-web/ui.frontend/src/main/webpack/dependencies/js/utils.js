/**
 *
 * @param {String} tag
 * @param {Array<String>} classNames
 * @param {String} innerText
 * @param {HTMLElement} parent
 * @returns
 */

export const createElement = (
  tag,
  classNames,
  parent,
  attributes,
  innerText
) => {
  const el = document.createElement(tag);
  if(classNames){
    classNames.forEach((className) => {
      el.classList.add(className);
    });
  }

  for (const key in attributes) {
    el.setAttribute(key, attributes[key]);
  }

  innerText && (el.innerText = innerText);

  if(parent){
    parent.appendChild(el);
  }
  return el;
};

export const getCookie = (cookieName) => {
  const cookieVal = document.cookie.match(new RegExp('(^| )' + cookieName + '=([^;]+)'));
  return cookieVal ? cookieVal[2] : null;
};

export const isBrowser = () => typeof window !== 'undefined';

export const getLocale = () => {
  if (isBrowser()) {
    const locale = document
        .getElementsByTagName('html')[0]
        .getAttribute('lang');
    return locale !== 'fr'? 'en-CA' : 'fr-CA';
  }
  return 'en-CA';
};

export const isAuthed = ()=> {
  return !(getCookie('g2g') === 'false' || !getCookie('g2g') || getCookie('g2g') === 'null');
};

export const getMemdeets = ()=> {
  return JSON.parse(decodeURIComponent(getCookie("memdeets")));
};

export const setSessionFutureDate = (ext = 1200) => {
  const timestamp = Date.now();
  const futureTimestamp = timestamp + (ext * 1000);
  const date = new Date(futureTimestamp);
  const weekdays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
  const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
  const weekday = weekdays[date.getUTCDay()];
  const month = months[date.getUTCMonth()];
  const day = date.getUTCDate();
  const year = date.getUTCFullYear();
  const hours = date.getUTCHours();
  const minutes = date.getUTCMinutes();
  const seconds = date.getUTCSeconds();
  return weekday+"+"+month+"+"+day+"+"+hours+"%3A"+minutes+"%3A"+seconds+"+GMT+"+year;
}

export const eraseWebCookie = (cName) => {
  document.cookie = cName +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

export const sessionSignOut = (_url) => {
  eraseWebCookie("memdeets");
  eraseWebCookie("g2g");
  eraseWebCookie("g2gExpiry");
  window.location.replace(_url);
}

export const setWebCookie = (name,value,days)=> {
  let expires = "";
  if (days) {
    const date = new Date();
    date.setTime(date.getTime() + (days*24*60*60*1000));
    expires = "; expires=" + date.toUTCString();
  }
  document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}

export const shortDate = (_date, _lang) => {
  const rDate = new Date(_date);
  const options = {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  };
  const _locale = _lang !== 'E'? 'fr-CA' : 'en-CA';
  return rDate.toLocaleDateString(_locale, options);
}

export const getBodyAttr = (attr) =>{
  return document.querySelector('body').dataset[attr];
}

export const removeCloseClassNames = (_el) =>{
  const classNames = _el.className.split(' ');
  const filteredClassNames = classNames.filter(className => !className.startsWith('am-icon2-'));
  return filteredClassNames.join(' ');
}

export const detectDigiGhost = (profile) => {
  if (Object.keys(profile).length === 0) {
    return;
  }
  const digighostSchemeCodePrefix = 'DIGIGK';
  const digighostPersonalizationPage = {
    en: '/content/airmiles/ca/en/join/qr/profile-details.html',
    fr: '/content/airmiles/ca/fr/adherer/qr/renseignements-profil.html',
  };

  const locale = getLocale() === 'en-CA' ? 'en' : 'fr';

  const emailExists =
    profile.contactDetails &&
    profile.contactDetails.email !== null &&
    profile.contactDetails.email !== undefined;

  const hasDigighostScheme =
    profile.scheme && profile.scheme.startsWith(digighostSchemeCodePrefix);

  const hasDigighostEnrollmentScheme =
    profile.enrollmentScheme &&
    profile.enrollmentScheme.startsWith(digighostSchemeCodePrefix);

  const hasDigighostSchemeCode =
    hasDigighostScheme || hasDigighostEnrollmentScheme;

  const isDigighostCollector =
    profile.ghost === true && emailExists && hasDigighostSchemeCode;

  const personalizationPage =
    digighostPersonalizationPage[locale];

  const personalizationPageSegments = {
    en: 'join/qr/profile-details',
    fr: 'adherer/qr/renseignements-profil',
  };

  const isPersonalizationPage = window.location.href.includes(
    personalizationPageSegments[locale]
  );

  const shouldRedirectToPersonlizationPage =
    isDigighostCollector && !isPersonalizationPage;

  if (shouldRedirectToPersonlizationPage) 
    window.location.href = personalizationPage;
}

export function toCaptial(str) {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

export function shuffleArray(array, num) {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
  return array.slice(0, num);
}

export function sortByKeyVal(a, b, key) {
  if (!a.hasOwnProperty(key) || !b.hasOwnProperty(key)) {
    return 0;
  }
  return a[key] - b[key];
}

export function checkHttpUrl(_url) {
  let _testURL;
  try {
    _testURL = new URL(_url);
  } catch (error) {
    return false;
  }
  return _testURL.protocol === "http:" || _testURL.protocol === "https:";
}

export function htmlDecode(_input) {
  const _doc = new DOMParser().parseFromString(_input, "text/html");
  return _doc.documentElement.textContent;
}

export function sanitizeHTML(input) {
  const entityMap = {
    '&': '&amp;',
    '<': '&lt;',
    '>': '&gt;',
    '"': '&quot;',
    "'": '&#39;'
  };

  return String(input).replace(/[&<>"'/]/g, function (s) {
    return entityMap[s];
  });
}

export function sanitizeJSON(json) {
  // Iterate through each property in the JSON object
  for (const key in json) {
    if (json.hasOwnProperty(key)) {
      // Check the type of the property
      if (typeof json[key] === 'string') {
        // Sanitize string values
        json[key] = sanitizeHTML(json[key]);
      } else if (Array.isArray(json[key])) {
        // If it's an array, sanitize each element
        json[key] = json[key].map(item => typeof item === 'string' ? sanitizeHTML(item) : item);
      } else if (typeof json[key] === 'object') {
        // If it's an object, recursively sanitize its properties
        json[key] = sanitizeJSON(json[key]);
      }
    }
  }
  return json;
}

export function offerInfo(_jsonData, _count, _offerType=''){
  const _data = JSON.parse(_jsonData);
  let _saved = '0';
  if(_data['states']) {
    const saveItem = _data['states'] ? _data['states'].find(state => state.name.toUpperCase() === "SAVE") : null;
    _saved = saveItem['value'].toUpperCase() === "SAVED" ? '1' : '0';
  }
  const storedPayloadString = sessionStorage.getItem('am_OSCP');
  const _region = storedPayloadString ? JSON.parse(storedPayloadString)['filters']['region'] : getCookie('province');

  return {
    "SKU": _data.id,
    "merchVars":{
      "activityName":_data['activityName'] || '',
      "experienceName":_data['experienceName'] || '',
      "offerAward":_data['awardType'] || '',
      "offerBonusMiles":_data['tiers'][0]['awardValue'] || '',
      "offerID":_data.id,
      "offerMechanism":_data['mechanisms'][0]['mechanismType'] || '',
      "offerName":_data['awardShort'] || '',
      "offerPartnerName":_data['partnerLabel'] || '',
      "offerPromotionLabels":_data['promotionLabel'] || '',
      "offerRegion":_region,
      "offerTargeting":_data['massOffer'] || '',
      "offerType":_offerType,
      "partnerID":_data['partnerId'] || '',
      "partnerPlacement":_data['partnerPlacement'] || '',
      "productPlacement":_count,
      "productSaved":_saved,
      "productType":'offers'
    },
    "name":_data['awardShort'] || '',
    "priceTotal":_data['priceTotal'] || '',
    "quantity":_data['quantity'] || '',
  };
}