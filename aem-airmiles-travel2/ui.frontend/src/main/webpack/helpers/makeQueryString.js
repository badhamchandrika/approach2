import formurlencoded from "form-urlencoded";
import { getLocale } from "../site/js/common";

/**
 * Accepts all query params accepted by Trisept and turns them a url-encoded string
 *
 * @param {string[]} gsOrigin
 * @param {string[]} gsDestination
 * @param {string[]} gsDepartureDate
 * @param {string} gsReturnDate
 * @param {room[]} rooms
 * @returns {string}
 */

const makeQueryString = ({
  gsOrigin,
  gsDestination,
  gsDepartureDate,
  gsReturnDate,
  gsNumberOfTravelers,
  gsAgesObject,
  gsSourceCode,
  gsvacationtype,
  foCabinPreference,
  gsPromotionCode
}) => {
  /* 
        This object will be turned into the query string. If you need to 
    add a parameter to the request, put it in this object. The key will 
    become the parameter's name, and the value will be the parameter's 
    value. 
    */
  const queryObj = {
    currentCulture: getLocale(),
    gsVendor: "LAM",
    gsdateformat: "d",
    ...(gsOrigin && { gsOrigin: gsOrigin.join("|") }),
    ...(gsDestination && { gsDestination: gsDestination.join("|") }),
    ...(gsDepartureDate && { gsDepartureDate: gsDepartureDate.join("|") }),
    ...(gsReturnDate && gsReturnDate.length!==0 && { gsReturnDate: gsReturnDate.join("|") }),
    ...(gsNumberOfTravelers && { gsNumberOfTravelers: gsNumberOfTravelers.join("|") }),
    ...(foCabinPreference && { foCabinPreference }),
    ...(gsAgesObject && { ...gsAgesObject }),
    ...(gsPromotionCode && { gsPromotionCode }),
    gsSourceCode,
    gsvacationtype,
  };

  return formurlencoded(queryObj);
};

export default makeQueryString;
