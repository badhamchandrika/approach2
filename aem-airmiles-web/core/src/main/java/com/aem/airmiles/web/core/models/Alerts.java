package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Alerts extends ComponentModel{

    @Setter
    @Getter
    @ValueMapValue
    private String alertText;

    @Setter
    @Getter
    @ValueMapValue
    private String dataTrackSection;

    @Setter
    @Getter
    @ValueMapValue
    private Date startTime;

    @Setter
    @Getter
    @ValueMapValue
    private Date endTime;

    @Setter
    @Getter
    private boolean showAlert;

    @Setter
    @Getter
    @ValueMapValue
    private boolean displayClose;

    @Setter
    @Getter
    @ValueMapValue
    private boolean hasRoleAlert;

    @Setter
    @Getter
    @ValueMapValue
    private boolean isGlobalAlert;

    @Setter
    @Getter
    @ValueMapValue
    private String alertType;

    @Setter
    @Getter
    @ChildResource
    private List<AlertsIndividualPages> alertPages;

    @Getter
    @Setter
    private boolean displayAlert = true;

    @PostConstruct
    void init(){
        Date date = new Date();
        if(date.before(startTime) || date.after(endTime)){
            displayAlert = false;
        }
    }
}
