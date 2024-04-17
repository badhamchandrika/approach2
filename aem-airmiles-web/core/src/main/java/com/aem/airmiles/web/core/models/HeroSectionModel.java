package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
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

@Model(adaptables = { Resource.class,
        SlingHttpServletRequest.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class HeroSectionModel {

    @Setter
    @Getter
    @ValueMapValue
    private String heading;

    @Setter
    @Getter
    @ValueMapValue
    private String description;

    @Setter
    @Getter
    @ValueMapValue
    private String imgPath;

    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackSection;

    @Setter
    @Getter
    @ValueMapValue
    private String headingTextColour;

    @Setter
    @Getter
    @ValueMapValue
    private String headingBgColour;

    @Setter
    @Getter
    @ValueMapValue
    private String bodyTextColour;

    @Setter
    @Getter
    @ValueMapValue
    private String bodyBgColour;

    @Setter
    @Getter
    @ValueMapValue
    private String imgCardBgSource;

    @Setter
    @Getter
    @ValueMapValue
    private String imgCardBgColour;

    @Setter
    @Getter
    @ValueMapValue
    private boolean fullBleed;


    @Setter
    @Getter
    @ChildResource
    private List<HeroSectionCTADetails> ctaDetails;
}

