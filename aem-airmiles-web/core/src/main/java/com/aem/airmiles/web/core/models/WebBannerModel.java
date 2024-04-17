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

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},resourceType="aem-airmiles-web/components/airmiles-core/webbanner", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name= ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions= ExporterConstants.SLING_MODEL_EXTENSION)
public class WebBannerModel {

    @Setter
    @Getter
    @ValueMapValue
    private String imageLargeScreen;

    @Setter
    @Getter
    @ValueMapValue
    private String imageMediumScreen;

    @Setter
    @Getter
    @ValueMapValue
    private String imageSmallScreen;

    @Setter
    @Getter
    @ValueMapValue
    private String imageAlt;

    @Setter
    @Getter
    @ValueMapValue
    private String adUrl;

    @Setter
    @Getter
    @ValueMapValue
    private String adUrlTarget;

    @Setter
    @Getter
    @ValueMapValue
    private String linkAriaLabel;

    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackId;

    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackClick;

    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackType;

    @Setter
    @Getter
    @ValueMapValue
    private String dataBannerName;

    @Setter
    @Getter
    @ValueMapValue
    private String dataBannerCategory;

    @Setter
    @Getter
    @ValueMapValue
    private String id;

}
