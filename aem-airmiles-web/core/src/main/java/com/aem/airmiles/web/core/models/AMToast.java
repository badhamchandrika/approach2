package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AMToast extends ComponentModel{

    @Setter
    @Getter
    @ValueMapValue
    private String toastUID;

    @Setter
    @Getter
    @ValueMapValue
    private String toastTitle;

    @Setter
    @Getter
    @ValueMapValue
    private String toastMessage;

    @Setter
    @Getter
    @ValueMapValue
    private String icon;

    @Setter
    @Getter
    @ValueMapValue
    private String customIcon;

    @Setter
    @Getter
    @ValueMapValue
    private String style;

    @Setter
    @Getter
    @ValueMapValue
    private Integer duration;

    @Setter
    @Getter
    @ValueMapValue
    private boolean hasCloseButton;

}
