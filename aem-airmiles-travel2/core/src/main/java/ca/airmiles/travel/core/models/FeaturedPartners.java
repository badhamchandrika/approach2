package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.api.resource.Resource;
import java.util.List;


@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FeaturedPartners {

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
    @ChildResource
    private List<DetailsModel> details;

}
