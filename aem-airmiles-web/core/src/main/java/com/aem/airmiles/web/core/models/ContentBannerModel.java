package com.aem.airmiles.web.core.models;

import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ContentBannerModel extends CTALink {

    @ValueMapValue
    @Getter
    private String imageSrc;

    @Getter
    @ValueMapValue
    private String imageAlt;

    @Getter
    @ValueMapValue
    private String mobileLinkText;

    @Getter
    @ValueMapValue
    private String mobileLinkUrl;

    @Getter
    @ValueMapValue
    private String mobileLinkTarget;

    @ValueMapValue
    @Getter
    private String headerText;

    @Getter
    @ValueMapValue
    private String bodyText;

}
