package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        resourceType="aem-airmiles-web/components/airmiles-core/image-text",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name= ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions= ExporterConstants.SLING_MODEL_EXTENSION)
public class ImageTextModel {

    @Getter
    @Setter
    @ValueMapValue
    private String titleText;

    @Getter
    @Setter
    @ValueMapValue
    private String titleHeading;

    @Getter
    @Setter
    @ValueMapValue
    private String titleType;

    @Getter
    @Setter
    @ValueMapValue
    private String descriptionText;

    @Getter
    @Setter
    @ValueMapValue
    private String isCarouselItem;

    @Getter
    @Setter
    @ValueMapValue
    private String logoPath;

    @Getter
    @Setter
    @ValueMapValue
    private String imageAltText;

    @Getter
    @Setter
    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    @Getter
    @Setter
    private String trackName;

    @ValueMapValue
    @Getter
    @Setter
    private String trackCategory;

    @Getter
    @Setter
    @ValueMapValue
    private String imageTextId;

    @Getter
    @Setter
    @ValueMapValue
    private String imageAlign;

    @Self
    @Getter
    private CTALink cta;
}
