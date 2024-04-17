package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GetMoreMilesLinksModel extends CTALink{

    @Getter
    @Setter
    @ValueMapValue
    private String title;

    @Getter
    @Setter
    @ValueMapValue
    private String body;

    @Getter
    @Setter
    @ValueMapValue
    private String imagePath;

    @Getter
    @Setter
    @ValueMapValue
    private String imagePath2;

}
