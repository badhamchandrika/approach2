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
public class DropDownListData {

    @Getter
    @Setter
    @ValueMapValue
    private String value;

    @Getter
    @Setter
    @ValueMapValue
    private String text;

    @Getter
    @Setter
    private boolean selected;

    @Getter
    @Setter
    private  boolean disabled;
}
