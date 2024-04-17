package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MemberBanner extends ComponentModel{

    @Setter
    @Getter
    @ValueMapValue
    private String type;

    @Setter
    @Getter
    @ValueMapValue
    private String title;

    @Setter
    @Getter
    @ValueMapValue
    private String id;

    @Setter
    @Getter
    @ValueMapValue
    private String weburl;

    @Setter
    @Getter
    @ValueMapValue
    private String clickact;

    @Setter
    @Getter
    @ValueMapValue
    private String dataClickID;

    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackID;

    @ValueMapValue
    @Setter
    @Getter
    private String dataTrackType;

}
