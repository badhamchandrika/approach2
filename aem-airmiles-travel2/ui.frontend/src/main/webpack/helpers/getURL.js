import CONSTANTS from "../constants";
import { currentLang, getLocale } from "../site/js/common";
import { getEnvironment } from "./getEnvironment";

export const getSearchUrl = (environment) =>
  getEnvironment(environment) === CONSTANTS.ENVIRONMENT_PROD
    ? CONSTANTS.URL.SEARCH_PROD
    : CONSTANTS.URL.SEARCH;

export const getAdvancedSearchUrl = (environment) => {
  const language = `language=${getLocale()}&`;

  return getEnvironment(environment) === CONSTANTS.ENVIRONMENT_PROD
    ? CONSTANTS.URL.ADVANCED_SEARCH_PROD + language
    : CONSTANTS.URL.ADVANCED_SEARCH + language;
};

export const getContactUrl = () =>
  currentLang() === "en" ? CONSTANTS.URL.CONTACT : CONSTANTS.URL.CONTACT_FR;
