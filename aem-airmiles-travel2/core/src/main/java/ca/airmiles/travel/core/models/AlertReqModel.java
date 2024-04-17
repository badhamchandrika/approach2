package ca.airmiles.travel.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class AlertReqModel extends CTALink {

    @Getter
    @Setter
    @ValueMapValue
    private String title;

    @Getter
    @Setter
    @ValueMapValue
    private String description;

    @Getter
    @Setter
    @ValueMapValue
    private String datatrackclick;

    @Getter
    @Setter
    @ValueMapValue
    private String datatrackid;

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackSection;

}
