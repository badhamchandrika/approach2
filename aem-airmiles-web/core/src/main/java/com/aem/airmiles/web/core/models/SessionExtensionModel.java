package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class SessionExtensionModel {

    @ValueMapValue
    @Getter
    @Setter
    private String title;

    @ValueMapValue
    @Getter
    @Setter
    private String iconType;

    @ValueMapValue
    @Getter
    @Setter
    private String bodyText;

    @ValueMapValue
    @Getter
    @Setter
    private String modalEnable;

    @ValueMapValue
    @Getter
    @Setter
    private String extensionSec;

    @ValueMapValue
    @Getter
    @Setter
    private String stayLabel;

    @ValueMapValue
    @Getter
    @Setter
    private String dataTrackID;

    @ValueMapValue
    @Getter
    @Setter
    private String dataClickID;

    @ValueMapValue
    @Getter
    @Setter
    private String dataTrackType;

    @ValueMapValue
    @Getter
    @Setter
    private String uidStay;

    @ValueMapValue
    @Getter
    @Setter
    private String exitLabel;

    @ValueMapValue
    @Getter
    @Setter
    private String signOutUrl;

    @ValueMapValue
    @Getter
    @Setter
    private String dataTrackIDExit;

    @ValueMapValue
    @Getter
    @Setter
    private String dataClickIDExit;

    @ValueMapValue
    @Getter
    @Setter
    private String dataTrackTypeExit;

    @ValueMapValue
    @Getter
    @Setter
    private String uidExit;

}
