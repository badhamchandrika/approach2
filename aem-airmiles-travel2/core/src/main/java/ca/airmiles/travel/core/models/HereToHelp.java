package ca.airmiles.travel.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.api.resource.Resource;
import java.util.List;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)public class HereToHelp extends CTALink {

    @Getter
    @Setter
    @ValueMapValue
    private String displayType;

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
    @ChildResource
    private List<DetailsModel> details;

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackID;

    @Setter
    @Getter
    @ValueMapValue
    private String dataClickID;

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackType;

}
