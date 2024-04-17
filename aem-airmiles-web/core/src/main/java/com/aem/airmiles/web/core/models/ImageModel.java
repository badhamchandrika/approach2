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
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ImageModel extends ComponentModel{

    @ValueMapValue
    @Getter
    @Setter
    private String imagePosition;

    @ValueMapValue
    @Getter
    @Setter
    private String imageScale;

    @ValueMapValue
    @Getter
    @Setter
    private boolean imageRounded;

}
