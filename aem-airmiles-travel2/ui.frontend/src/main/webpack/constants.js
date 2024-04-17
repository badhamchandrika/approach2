/* eslint-disable @typescript-eslint/no-empty-function */
let amPmHours = [];

for (let i = 0; i < 24; i++) {
  let hour = i % 12 === 0 ? 12 : i % 12;
  amPmHours.push(
    hour + ":00 " + `${i < 12 ? "AM" : "PM"}` + `${i === 12 ? " (noon)" : ""}`
  );
}

export const CONSTANTS = {
  URL: {
    ADVANCED_SEARCH_PROD: "https://bookings.airmiles.ca/search/default.aspx?",
    ADVANCED_SEARCH: "https://bookings.beta.airmiles.ca/search/default.aspx?",
    OAUTH_TOKEN: "https://oauth-int.airmiles.ca/oauth/token",
    SEARCH_PROD: "https://bookings.airmiles.ca/search/externalformpost.aspx?",
    SEARCH: "https://bookings.beta.airmiles.ca/search/externalformpost.aspx?",
    CONTACT: "https://www.airmiles.ca/en/get-help.html#contact-us",
    CONTACT_FR: "https://www.airmiles.ca/fr/aide.html#contact-us",
  },
  JSONS_BASE_PATH: "/content/dam/aem-airmiles-travel2/trisept-jsons",
  CLIENT_ID: "BxlRBXvvX5FftdowmBrbdcdtYMgoRR5a",
  CLIENT_SECRET:
    "ZBf0vAdQLsGKZ0QR-rXNlwpz6a19Xds-IZ5ZWzabISI9-nuK4VjJLCqZheWKXxvJ",
  GRANT_TYPE: "client_credentials",
  AUDIENCE: "airmiles-int-m2m-segment",
  SCOPE: "segment:read",
  AIRLINE_AGE_RULES_URL: "#",
  CASH: "cash",
  MILES: "Miles",
  REFERER: "same-origin",
  REGEX_EMPTY_STR: /^\s*$/,
  CABIN_TYPE_ECONOMY: "Y",
  TRIP_TYPE_MULTY: "multi",
  TRIP_TYPE_ONEWAY: "oneway",
  TRIP_TYPE_ROUND: "round",
  FORMPOST_TRIP_TYPE_ONEWAY: "OW",
  FORMPOST_TRIP_TYPE_ROUND: "RT",
  ENVIRONMENT_QA: "int",
  ENVIRONMENT_DEV: "dev",
  ENVIRONMENT_STAGE: "uat",
  ENVIRONMENT_PROD: "prod",
  ENVIRONMENT_NOENV: "noenv",
  MIN_CHILD_AGE_FLIGHTS: 2,
  MIN_CHILD_AGE_NO_FLIGHTS: 0,
  MAX_CHILD_AGE_FLIGHTS: 11,
  MAX_CHILD_AGE_NO_FLIGHTS: 11,
  DEFAULT_CLUSTER: "BASE",
  REGEX_ALPHANUMERIC_MAX20: /^[a-zA-Z0-9\s]{0,20}$/,
  PROMO_CODE_ID: "PromoCodeId",

  // breakpoints for media-queries
  BREAKPOINTS: {
    XS: 0,
    SM: 576,
    MD: 768,
    LG: 992,
    XL: 1280,
    XXL: 1400,
  },
  // breakpoints for container-queries
  CONTAINER_QUERIES: {
    CONTAINER: {
      sm: {
        maxWidth: 703.98,
      },
      md: {
        minWidth: 704,
        maxWidth: 1223.98,
      },
      xl: {
        minWidth: 1224,
      },
    },
  },
  CARD_VARIANTS: {
    DEAL: "deal",
    DESTINATION: "destination",
    PARTNER: "partner",
  },
  TIMES_ARR: amPmHours,
  TIMES_DROP_DEFAULT_VALUE: 12,
  WIDGET: {
    FLIGHTS: "flights",
    STAYS: "stays",
    PACKAGES: "packages",
    ACTIVITIES: "activities",
    CRUISES: "cruises",
    RENTALS: "rentals",
    BUNDLING: "bundling",
  },
  WIDGET_CONTEXT: {
    classNameObject: {},
    errorFields: [],
    gsPromotionCode: "",
    hasWidgetUpdates: false,
    isSmallWidget: false,
    add_promo_code: "",
    promo_code_error: "",
    promo_code: "",
    runmodes: "",
    setErrorFields: () => {},
    setGsPromotionCode: () => {},
  },

  COLORS: {
    STEEL_900: "#1C2D3F",
    SAPPHIRE_900: "#1F68DA",
  },

  DEALS: {
    DATE_ADDED: "dateAdded",
    EXPIRY_DATE_OBJ: "expiryDateObj",
  },
};

export default CONSTANTS;
