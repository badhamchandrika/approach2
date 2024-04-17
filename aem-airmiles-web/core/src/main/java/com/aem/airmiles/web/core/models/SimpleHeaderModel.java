package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.resource.Resource;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},resourceType="aem-airmiles-web/components/airmiles-core/simple-header",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class SimpleHeaderModel {
    @Setter
    @Getter
    @ValueMapValue
    private String airmilesLogoPath;

    @Setter
    @Getter
    @ValueMapValue
    private String logoUrl;

    @Setter
    @Getter
    @ValueMapValue
    private String airmilesLogoAltText;

    @ValueMapValue
    @Setter
    @Getter
    private String dataTrackID;

    @ValueMapValue
    @Setter
    @Getter
    private String dataClickID;

    @Setter
    @Getter
    @ValueMapValue
    private String cancelText;

    @Setter
    @Getter
    @ValueMapValue
    private String cancelLinkUrl;

    @ValueMapValue
    @Setter
    @Getter
    private String cancelDataTrackID;

    @ValueMapValue
    @Setter
    @Getter
    private String cancelDataClickID;
}
