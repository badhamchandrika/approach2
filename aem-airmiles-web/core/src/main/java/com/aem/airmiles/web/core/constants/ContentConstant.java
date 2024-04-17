package com.aem.airmiles.web.core.constants;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.NameConstants;

public class ContentConstant {

    /**
     * Private constructor used to hide the implicit one.
     *
     * @see <a href="https://rules.sonarsource.com/java/RSPEC-1118">java:S1118</a>
     */
    private ContentConstant() {
        // not used.
    }

    public static final String DOT_HTML = ".html";
    public static final String SLASH = "/";
    public static final String URL = "URL" ;
    public static final String UTF_8 = "UTF-8";
    public static final String APPLICATION_JSON = "application/json";
    public static final String TITLE = "Title";
    public static final String PATH = "path";
    public static final String CQ_PAGE = NameConstants.NT_PAGE ;
    public static final String TYPE = "type";
    public static final String FULLTEXT = "fulltext";
    public static final String FULLTEXT_RELPATH = "fulltext.relPath";
    public static final String LIMIT = "p.limit";

    public static final String OFFSET = "p.offset";
    public static final String LIMIT_VALUE = "-1";
    public static final String DOT = ".";
    public static final String SEARCH_MODEL_RESPATH = SLASH + "content/experience-fragments/aem-airmiles-web/ca/en/" +
            "site/header/master/jcr:content/root/search";
    public static final String DESCRIPTION = JcrConstants.JCR_DESCRIPTION;
    public static final String JSONKEY_SEARCHRESULT = "SearchResult";
    public static final String TOTAL = "total";

    //Hero Section Constants
    public static final String CTA_LINK = "ctalinks";
    public static final String BUTTON_LINK = "ctabuttons";
    public static final String CTA_LINK_TEXT = "ctaLinksText";
    public static final String BUTTON_LINK_TEXT = "ctaButtonsText";
    public static final String IMAGE = "image";
    public static final String HEADLINE = "headline";
    public static final String BODY = "body";

    //Analytics Constants
    public static final String DATA_TRACK_ID = "data-track-id";
    public static final String DATA_TRACK_CLICK = "data-track-click";
    public static final String SLASH_CONTENT_DAM = "/content/dam";
    public static final String ROOT_CONTAINER = "/jcr:content/root/container";

    // Date formats.
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MM_DD_YYYY = "MM-dd-yyyy";




}
