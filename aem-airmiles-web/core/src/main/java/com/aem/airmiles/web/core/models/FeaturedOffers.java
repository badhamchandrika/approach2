package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import com.aem.airmiles.web.core.constants.ResourceTypes;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},resourceType= ResourceTypes.FEATURED_OFFERS,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FeaturedOffers{

    @Getter
    @Setter
    @ChildResource
    private List<AllOffersTabs> featuredOffersTabs;
    @Getter
    @Setter
    @ValueMapValue
    private String sortFilterText;
    @Getter
    @Setter
    @ValueMapValue
    private String displayOffersList;
    @Getter
    @Setter
    @ValueMapValue
    private String menuHide;
    @Getter
    @Setter
    @ValueMapValue
    private String sortByText;
    @Getter
    @Setter
    @ValueMapValue
    private String filterByText;
    @Getter
    @Setter
    @ValueMapValue
    private String filterTypesTitle;
    @Getter
    @Setter
    @ValueMapValue
    private String filterPartnersTitle;
    @Getter
    @Setter
    @ValueMapValue
    private String filterCategoryTitle;
    @Getter
    @Setter
    @ValueMapValue
    private String filterSubCategoryTitle;
    @Getter
    @Setter
    @ValueMapValue
    private String regionText;

    @Getter
    @Setter
    @ValueMapValue
    private String featuredOffersTitle;
    @Getter
    @Setter
    @ValueMapValue
    private String noResultTitle;
    @Getter
    @Setter
    @ValueMapValue
    private String noResultDescription;
    @Getter
    @Setter
    @ValueMapValue
    private Integer pagination;
    @Getter
    @Setter
    @ValueMapValue
    private Integer trendingLimit;
    @Getter
    @Setter
    @ValueMapValue
    private String resultShowingText;
    @Getter
    @Setter
    @ValueMapValue
    private String resultOffersText;
    @Getter
    @Setter
    @ChildResource
    private List<AllOffersCTA> filterButtons;
    @Getter
    @Setter
    @ValueMapValue
    private String backText;
    @Getter
    @Setter
    @ValueMapValue
    private String clearAllText;
    @Getter
    @Setter
    @ValueMapValue
    private String collapseSortText;
    @Getter
    @Setter
    @ValueMapValue
    private String expandSortText;
    @Getter
    @Setter
    @ValueMapValue
    private String filterSortText;
    @Getter
    @Setter
    @ValueMapValue
    private String backToOfferText;
    @Getter
    @Setter
    @ValueMapValue
    private String termsTitle;
    @Getter
    @Setter
    @ValueMapValue
    private String seeAllPartnersTitle;
    @Getter
    @Setter
    @ValueMapValue
    private String fullDetailText;
    @Getter
    @Setter
    @ValueMapValue
    private String miniFeedTitle;

    @Getter
    @Setter
    @ValueMapValue
    private String noResultWhenApiFails;

    @Getter
    @Setter
    @ValueMapValue
    private String messageAbort;

    @Getter
    @Setter
    @ValueMapValue
    private String messageNoOffersForYou;

    @Getter
    @Setter
    @ValueMapValue
    private String messageNoSavedOffers;

    @Getter
    @Setter
    @ValueMapValue
    private String backToOffersDataTrackID;
    @Getter
    @Setter
    @ValueMapValue
    private String backToOffersDataClickID;
    @Getter
    @Setter
    @ValueMapValue
    private String backToOffersDataTrackType;

    @Getter
    @Setter
    @ValueMapValue
    private String backTextDataTrackID;

    @Getter
    @Setter
    @ValueMapValue
    private String backTextDataClickID;
    @Getter
    @Setter
    @ValueMapValue
    private String backTextDataTrackType;

    @Getter
    @Setter
    @ValueMapValue
    private String closeIconDataClickID;

    @Getter
    @Setter
    @ValueMapValue
    private String closeIconDataTrackID;
    @Getter
    @Setter
    @ValueMapValue
    private String closeIconDataTrackType;

    @Getter
    @Setter
    @ValueMapValue
    private String offerEndsText;

    @Getter
    @Setter
    @ValueMapValue
    private String offerStartText;

    @Getter
    @Setter
    @ValueMapValue
    private String offersCardTrackID;

    @Getter
    @Setter
    @ValueMapValue
    private String offersCardClickID;

    @Getter
    @Setter
    @ValueMapValue
    private String offersCardTrackType;

    @Getter
    @Setter
    @ValueMapValue
    private String ctaDetailCLOText;

    @Getter
    @Setter
    @ValueMapValue
    private String ctaDetailCLOUrl;

    @Getter
    @Setter
    @ValueMapValue
    private String ctaDetailTrackID;

    @Getter
    @Setter
    @ValueMapValue
    private String ctaDetailClickID;

    @Getter
    @Setter
    @ValueMapValue
    private String ctaDetailTrackType;

    @Getter
    @Setter
    @ValueMapValue
    private Boolean ctaDetailTarget;

    @Getter
    @Setter
    @ValueMapValue
    private String optInDetailTrackID;

    @Getter
    @Setter
    @ValueMapValue
    private String optInDetailClickID;

    @Getter
    @Setter
    @ValueMapValue
    private String optInDetailTrackType;

    @Getter
    @Setter
    @ValueMapValue
    private Boolean optInDetailTarget;

    @Getter
    @Setter
    @ChildResource
    List<OffersFilterListItem> sortByOptionsList;

    @Getter
    @Setter
    @ChildResource
    List<OffersFilterListItem> mechanismList;

    @Getter
    @Setter
    @ChildResource
    List<OffersFilterListItem> offersTypeList;

}
