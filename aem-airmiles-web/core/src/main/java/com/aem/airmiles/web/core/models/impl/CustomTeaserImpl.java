package com.aem.airmiles.web.core.models.impl;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.wcm.core.components.models.Teaser;
import com.aem.airmiles.web.core.models.CustomTeaser;
import lombok.Getter;
import lombok.experimental.Delegate;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import javax.annotation.PostConstruct;

@Model(adaptables = SlingHttpServletRequest.class, adapters = {CustomTeaser.class, ComponentExporter.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,resourceType = CustomTeaserImpl.RESOURCE_TYPE)
public class CustomTeaserImpl implements CustomTeaser {

    public static final String RESOURCE_TYPE = "aem-airmiles-web/components/airmiles-core/teaser";

    @Self
    @Via(type = ResourceSuperType.class)
    @Delegate
    private Teaser teaser;

    @ValueMapValue
    @Optional
    private String mobileImage;

    @ValueMapValue
    private String desktopImage;

    @ValueMapValue
    @Optional
    private String tabletImage;

    @ValueMapValue
    private String imageAlt;

    @ValueMapValue
    @Getter
    private String dataTrackID;

    @ValueMapValue
    @Getter
    private String dataClickID;

    @ValueMapValue
    @Getter
    private String dataTrackType;

    @PostConstruct
    protected void init() {
        if (StringUtils.isNotBlank(this.desktopImage)) {
            if (StringUtils.isBlank(mobileImage)) {
                this.mobileImage = this.desktopImage;
            }
            if (StringUtils.isBlank(tabletImage)) {
                this.tabletImage = this.desktopImage;
            }
        }
    }

    @Override
    public String getMobileImageSrc() {
        return this.mobileImage;
    }

    @Override
    public String getTabletImageSrc() {
        return this.tabletImage;
    }

    @Override
    public String getDesktopImageSrc() {
        return this.desktopImage;
    }

    @Override
    public String getImageAlt() {
        return this.imageAlt;
    }
}
