package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.api.resource.Resource;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DetailsModel extends LinkModel {

    @Getter
    @Setter
    @ValueMapValue
    private String imagePath;

    @Getter
    @Setter
    @ValueMapValue
    private String altText;

    @Getter
    @Setter
    @ValueMapValue
    private String text;

}
