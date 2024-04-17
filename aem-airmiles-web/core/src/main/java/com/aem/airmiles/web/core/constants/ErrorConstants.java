package com.aem.airmiles.web.core.constants;

public class ErrorConstants {




    /**
     * Private constructor used to hide the implicit one.
     *
     * @see <a href="https://rules.sonarsource.com/java/RSPEC-1118">java:S1118</a>
     */
    private ErrorConstants() {
        // not used.
    }

    public static final String ERROR_SEARCH_CODE = "001";
    public static final String ERROR_SEARCH_MSG = "NO Match Found";
    public static final String ERROR_SEARCHMODEL_CODE = "002" ;
    public static final String ERROR_SEARCHMODEL_MSG = "Please check search model resource";
    public static final String ERROR_OFFER_CODE = "003" ;
    public static final String ERROR_OFFER_MSG = "Please check resource";
    public static final String ERROR_OFFER_NODATA = "004";
    public static final String ERROR_OFFER_NODATA_MSG = "No Data Found";

}
