package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AMModal extends ComponentModel{

    @Setter
    @Getter
    @ValueMapValue
    private String modalTitle;

    @Setter
    @Getter
    @ValueMapValue
    private String uniqueID;

    @Setter
    @Getter
    @ValueMapValue
    private String typePicker;

    @Setter
    @Getter
    @ValueMapValue
    private String sizePicker;

    @Setter
    @Getter
    @ValueMapValue
    private String buttonText;

    @Setter
    @Getter
    @ValueMapValue
    private String linkType;

    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackID;

    @Setter
    @Getter
    @ValueMapValue
    private String dataClickID;

    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackType;

    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackIDCloseBtn;

    @Setter
    @Getter
    @ValueMapValue
    private String dataClickIDCloseBtn;

    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackTypeCloseBtn;

    @Setter
    @Getter
    @ValueMapValue
    private boolean once;

}
