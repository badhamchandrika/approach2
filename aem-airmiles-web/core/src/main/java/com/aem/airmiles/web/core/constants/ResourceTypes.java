package com.aem.airmiles.web.core.constants;

public class ResourceTypes {



    /**
     * Private constructor used to hide the implicit one.
     *
     * @see <a href="https://rules.sonarsource.com/java/RSPEC-1118">java:S1118</a>
     */
    private ResourceTypes() {
        // not used.
    }

    public static final String SEARCH_MODEL = "aem-airmiles-web/components/search";
    public static final String CTA_BUTTON_MODEL = "aem-airmiles-web/components/cta-button";
    public static final String FEATURED_OFFERS = "aem-airmiles-web/components/alloffers/offers";
    public static final String OFFERS_FILTER_LIST= "aem-airmiles-web/components/alloffers/offersfilterlist";
    public static final String TITLE_MODEL = "aem-airmiles-web/components/airmiles-core/common/title/v1/title";

    public static final String CONTAINER_MODEL = "aem-airmiles-web/components/container";
    public static final String COLUMN_MODEL = "aem-airmiles-web/components/columns";
    public static final String SLING_SERVLET = "sling/servlet/default";
    public static final String PROPERTY_CQMODEL = "cq:model";
    public static final String JCR_CONTENT_DATA = "/jcr:content/data";
    public static final String OFFERS_CAROUSEL_MODEL = "/conf/aem-airmiles-web/offers/settings/dam/cfm/models/offers-carousel";
    public static final String CLO_HERO_MODEL = "/conf/aem-airmiles-web/offers/settings/dam/cfm/models/clo-hero-section";
    public static final String CLO_QUERY_PARAM_MODEL = "/conf/aem-airmiles-web/offers/settings/dam/cfm/models/clo-query-param";


}
