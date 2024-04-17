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
public class ComponentModel {

    @ValueMapValue
    @Setter
    @Getter
    private String active;

    @Getter
    @Setter
    @ValueMapValue
    private String linkTarget;

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackID;

    @Getter
    @Setter
    @ValueMapValue
    private String dataClickID;

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackType;
}