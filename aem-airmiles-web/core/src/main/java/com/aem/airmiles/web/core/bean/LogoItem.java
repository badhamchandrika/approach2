package com.aem.airmiles.web.core.bean;

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
public class LogoItem {

    @ValueMapValue
    @Getter
    private String logoUrl;

    @ValueMapValue
    @Getter
    @Setter
    private String logoAltText;

    @ValueMapValue
    @Getter
    private boolean hideInTablet;

    @ValueMapValue
    @Getter
    private boolean hideInMobile;
}