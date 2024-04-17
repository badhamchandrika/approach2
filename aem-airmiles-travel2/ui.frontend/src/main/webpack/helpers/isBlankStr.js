import CONSTANTS from "../constants";
export const isBlankStr = (str) => CONSTANTS.REGEX_EMPTY_STR.test(str);
