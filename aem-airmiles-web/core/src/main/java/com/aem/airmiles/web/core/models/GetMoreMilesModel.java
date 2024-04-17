package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = { Resource.class,
        SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GetMoreMilesModel {


    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackSection;

    @ValueMapValue
    @Getter
    private String wayFinderText;

    @ValueMapValue
    @Getter
    private String mainTitleText;

    @ValueMapValue
    @Getter
    private String mainBodyText;

    @ValueMapValue
    @Getter
    private String backgroundImagePath;
    @Getter
    @Setter
    @ChildResource(name = "subCategories")
    List<GetMoreMilesLinksModel> subCategories;

}
