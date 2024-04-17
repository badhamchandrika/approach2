package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.Date;
import java.util.List;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GlobalAlerts extends ComponentModel{

    @Setter
    @Getter
    @ValueMapValue
    private String globalAlertText;

    @Setter
    @Getter
    @ValueMapValue
    private String globalAlertName;

    @Setter
    @Getter
    @ValueMapValue
    private String globalAlertCategory;

    @Setter
    @Getter
    @ValueMapValue
    private String globalAlertTitle;

    @Setter
    @Getter
    @ValueMapValue
    private Date globalAlertStartTime;

    @Setter
    @Getter
    @ValueMapValue
    private Date globalAlertEndTime;

    @Setter
    @Getter
    @ValueMapValue
    private boolean globalAlertHasRole;

    @Setter
    @Getter
    @ValueMapValue
    private boolean globalAlertIsTrue;

    @Setter
    @Getter
    @ValueMapValue
    private String globalAlertType;

    @Setter
    @Getter
    @ChildResource
    private List<AlertsIndividualPages> alertPages;

}
