package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class MemberBannerModel {

    @Setter
    @Getter
    @ValueMapValue
    private String cashTitle;

    @Setter
    @Getter
    @ValueMapValue
    private String cashContent;

    @Setter
    @Getter
    @ValueMapValue
    private String dreamTitle;

    @Setter
    @Getter
    @ValueMapValue
    private String dreamContent;

    @Setter
    @Getter
    @ValueMapValue
    private String cashDataTrackID;

    @Setter
    @Getter
    @ValueMapValue
    private String dreamDataTrackID;

    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackClickID;

    @Setter
    @Getter
    @ValueMapValue
    private String hexBtnColor;

    @Setter
    @Getter
    @ValueMapValue
    private String hexTextColor;

    @Setter
    @Getter
    @ValueMapValue
    private String linkTarget;

    @Setter
    @Getter
    @ValueMapValue
    private String linkText;

    @Setter
    @Getter
    @ValueMapValue
    private String linkUrl;

    @Setter
    @Getter
    @ChildResource(name = "linkDetails")
    List<MemberBanner> linkDetails;
}
