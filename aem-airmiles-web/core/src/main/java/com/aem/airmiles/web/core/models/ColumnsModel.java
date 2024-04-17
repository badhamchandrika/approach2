package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ColumnsModel extends ComponentModel{

    @ValueMapValue
    @Getter
    @Setter
    private String columnnumber;

    @ValueMapValue
    @Getter
    @Setter
    private boolean fullwidth;

    @ValueMapValue
    @Getter
    @Setter
    private boolean verticalCenter;

    @ValueMapValue
    @Getter
    @Setter
    private boolean horizontalCenter;

    @ValueMapValue
    @Getter
    @Setter
    private String backgroundcolor;

}
