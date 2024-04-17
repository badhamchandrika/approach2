package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class CarouselSlideModel {
    /**
     * Experience Fragment path for carousel slide.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String slideXFPath;
    @Getter
    @Setter
    @ValueMapValue
    private String carouselSlideId;
}
