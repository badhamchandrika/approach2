package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import com.aem.airmiles.web.core.constants.ResourceTypes;
import com.aem.airmiles.web.core.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.api.resource.Resource;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        resourceType = ResourceTypes.CTA_BUTTON_MODEL,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CTALink {

    @ValueMapValue
    @Getter
    @Setter
    private String linkText;

    @ValueMapValue
    @Getter
    @Setter
    private String id;

    @ValueMapValue
    @Getter
    @Setter
    private String linkColor;

    @ValueMapValue
    @Getter
    @Setter
    private String hexTextColor;

    @ValueMapValue
    @Getter
    @Setter
    private String hexBtnColor;

    @ValueMapValue
    @Getter
    @Setter
    private String fontSize;
    
    @ValueMapValue
    @Setter
    private String linkUrl;
    
    @ValueMapValue
    @Getter
    @Setter
    private String linkTarget;

    @ValueMapValue
    @Setter
    @Getter
    private String linkType;

    @ValueMapValue
    @Setter
    @Getter
    private String dataTrackID;

    @ValueMapValue
    @Setter
    @Getter
    private String dataClickID;

    @ValueMapValue
    @Setter
    @Getter
    private String dataTrackType;

    @ValueMapValue
    @Setter
    @Getter
    private String modalUniqueID;

    @ValueMapValue
    @Setter
    @Getter
    private boolean iconOnRight;

    @ValueMapValue
    @Setter
    @Getter
    private String iconClassGroup;

    @ValueMapValue
    @Setter
    @Getter
    private String iconClassName;

    @ValueMapValue
    @Setter
    @Getter
    private String iconColor;

    public String getLinkUrl() {
        return Utils.getFormattedURL(linkUrl);
    }

}
