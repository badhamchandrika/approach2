package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class OptinModel {

    @Getter
    @Setter
    @ValueMapValue
    private String icon;

    @Getter
    @Setter
    @ValueMapValue
    private String title;

    @Getter
    @Setter
    @ValueMapValue
    private String description;

    @Getter
    @Setter
    @ValueMapValue
    private String linkUrl;

    @Getter
    @Setter
    @ValueMapValue
    private String linkText;

    @Getter
    @Setter
    @ValueMapValue
    private String linkType;

    @Getter
    @Setter
    @ValueMapValue
    private String linkTarget;

    @Getter
    @Setter
    @ValueMapValue
    private String dataClickID;

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackID;

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackType;
}
