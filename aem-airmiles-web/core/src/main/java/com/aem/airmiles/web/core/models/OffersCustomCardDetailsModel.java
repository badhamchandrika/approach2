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
public class OffersCustomCardDetailsModel {

    @Getter
    @Setter
    @ValueMapValue
    private String logoPath;

    @Getter
    @Setter
    @ValueMapValue
    private String ctaText;


    @Setter
    @ValueMapValue
    private String ctaUrl;

    @Getter
    @Setter
    @ValueMapValue
    private String description;

    @Getter
    @Setter
    @ValueMapValue
    private String ctaDataClickId;

    @Getter
    @Setter
    @ValueMapValue
    private String ctaDataTrackId;

    @Getter
    @Setter
    @ValueMapValue
    private String ctaDataTrackType;

    public String getCtaUrl() {
        return Utils.getFormattedURL(ctaUrl);
    }

}
