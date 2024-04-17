package com.aem.airmiles.web.core.models;

import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

import static com.adobe.cq.export.json.ExporterConstants.SLING_MODEL_EXPORTER_NAME;
import static com.adobe.cq.export.json.ExporterConstants.SLING_MODEL_EXTENSION;
import static com.aem.airmiles.web.core.models.Block5050Model.BLOCK_5050_RT;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;


/**
 * Model used to expose Block 50%/50% component properties.
 */
@Getter
@Exporter(name = SLING_MODEL_EXPORTER_NAME, extensions = SLING_MODEL_EXTENSION)
@Model(adaptables = Resource.class, resourceType = BLOCK_5050_RT, defaultInjectionStrategy = OPTIONAL)
public class Block5050Model {

    static final String BLOCK_5050_RT = "aem-airmiles-web/components/airmiles-core/block-50-50";

    @ValueMapValue
    private String preTitle;

    @ValueMapValue
    private String titleText;

    @ValueMapValue
    private String descriptionText;

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String imageAltText;

    @ValueMapValue
    private String imageTextId;

    @ValueMapValue
    private String imageAlign;

    @ChildResource
    private List<CTALink> ctas;

    @ValueMapValue
    private String trackName;

    @ValueMapValue
    private String trackCategory;
}