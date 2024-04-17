/* eslint-disable @typescript-eslint/no-empty-function */
import { createContext } from "react";
import CONSTANTS from "../../constants";

const defaultValue = {
  ...CONSTANTS.WIDGET_CONTEXT,
  bundlingProductsHeading: "",
  handleCheckboxProduct: () => {},
  isProductBundling: () => {},
  productCheckboxes: {},
};

export const BundlingContext = createContext(defaultValue);
