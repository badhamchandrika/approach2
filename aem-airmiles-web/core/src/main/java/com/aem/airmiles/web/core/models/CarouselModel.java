package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

import static com.adobe.cq.export.json.ExporterConstants.SLING_MODEL_EXPORTER_NAME;
import static com.adobe.cq.export.json.ExporterConstants.SLING_MODEL_EXTENSION;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

/**
 * Sling model used to expose Carousel component information.
 *
 * @author mvelicheti.
 */
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, resourceType="aem-airmiles-web/components/airmiles-core/carousel", defaultInjectionStrategy = OPTIONAL)
@Exporter(name = SLING_MODEL_EXPORTER_NAME, extensions = SLING_MODEL_EXTENSION)
public class CarouselModel {

    /**
     * Component title.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String title;

    /**
     * List of {@link CarouselSlideModel}.
     */
    @Getter
    @Setter
    @ChildResource
    private List<CarouselSlideModel> carouselSlides;

    @Getter
    @Setter
    @ValueMapValue
    private String id;
}

