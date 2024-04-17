package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class OffersCarouselDetailsModel {

    @Getter
    @Setter
    @ValueMapValue
    private String carouselTitle;

    @Getter
    @Setter
    @ValueMapValue
    private String carouselDescription;

    @Getter
    @Setter
    @ValueMapValue
    private String carouselCta;

    @Setter
    @ValueMapValue
    private String carouselCtaUrl;

    @Getter
    @Setter
    @ValueMapValue
    private String cloCtaDataClickId;

    @Getter
    @Setter
    @ValueMapValue
    private String cloCtaDataTrackId;

    @Getter
    @Setter
    @ValueMapValue
    private String cloCtaDataTrackType;

    @Getter
    @Setter
    @ValueMapValue
    private Integer numberOfOffersInCarousel;

    @Getter
    @Setter
    @ValueMapValue
    private Integer scrollNumberOfOffers;

    @Getter
    @Setter
    @ValueMapValue
    private String cloQueryParamDetails;

    @Getter
    @Setter
    @ValueMapValue
    private String offersCustomCardDetails;

    public String getCarouselCtaUrl() {
        return Utils.getFormattedURL(carouselCtaUrl);
    }

}
