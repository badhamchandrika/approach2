package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AllOffersTabs extends ComponentModel{

    @Getter
    @Setter
    @ValueMapValue
    private String tabName;

    @Getter
    @Setter
    @ValueMapValue
    private String tabKey;

    @Getter
    @Setter
    @ValueMapValue
    private String tabStatus;

    @ValueMapValue
    @Getter
    @Setter
    private boolean filterHide;

    @ValueMapValue
    @Getter
    @Setter
    private String activityType;

    @ValueMapValue
    @Getter
    @Setter
    private String pageViewAnalytics;
}
