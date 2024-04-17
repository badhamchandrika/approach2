package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuickLink {
    @Getter
    @Setter
    @ValueMapValue
    private String iconPicker;

    @Getter
    @Setter
    @ValueMapValue
    private String iconColor;

    @Getter
    @Setter
    @ValueMapValue
    private String linkText;

    @Getter
    @Setter
    @ValueMapValue
    private String id;

    @Getter
    @Setter
    @ValueMapValue
    private String bgcolor;

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

    @Getter
    @Setter
    @ValueMapValue
    private String linkDescription;

    @Getter
    @Setter
    @ValueMapValue
    private String linkUrl;

    @Getter
    @Setter
    @ValueMapValue
    private boolean newTab;

    @Getter
    @Setter
    @ValueMapValue
    private boolean hideLink;
}
