import { isWithinRange, isAfter } from "date-fns";

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

  parent.appendChild(el);
  return el;
};

export const getCookie = (cookieName) => {
  const name = cookieName + "=";
  const cookieDecoded = decodeURIComponent(document.cookie);
  const cookieArr = cookieDecoded.split('; ');
  let res;
  cookieArr.forEach(val => {
    if (val.indexOf(name) === 0) {res = val.substring(name.length);}
  });
  return res;
};

/**
 * Takes a start and an end date, converts them to the client's time 
 * zone, and returns true if the client's current time is between 
 * the start and end dates
 * 
 * @param {String} startDate A date string in any format that can be used with the Date constructor
 * @param {String} endDate A date string in any format that can be used with the Date constructor
 * @returns {Boolean}
 */

export const isCurrentDateInLocalizedRange = (startDate, endDate) => {
    
  let startDateObj = new Date(startDate)
  let endDateObj = new Date(endDate)

  if(isAfter(startDateObj, endDateObj)) {
    startDateObj = new Date(endDate)
    endDateObj = new Date(startDate)
  }
  
  const clientTimezone = Intl.DateTimeFormat().resolvedOptions().timeZone
  
  const localizedStartDate = new Date(startDateObj.toLocaleString("en-US", {
    timeZone: clientTimezone
  }))
  const localizedEndDate = new Date(endDateObj.toLocaleString("en-US", {
    timeZone: clientTimezone
  }))
  
  return isWithinRange(new Date(), localizedStartDate, localizedEndDate)
}

/**
 * Takes a date, converts it to the client's time zone, 
 * and returns true if the client's current time is 
 * before the date supplied
 * 
 * @param {String} date A date string in any format that can be used with the Date constructor
 * @returns {Boolean} True if the date has passed, false if it hasn't
 */

export const hasLocalizedDatePassed = (date) => {
    
  const dateObj = new Date(date)
  
  const clientTimezone = Intl.DateTimeFormat().resolvedOptions().timeZone
  
  const localizedDate = new Date(dateObj.toLocaleString("en-US", {
    timeZone: clientTimezone
  }))

  return isAfter(new Date(), localizedDate) 
}