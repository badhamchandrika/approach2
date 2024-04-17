package ca.airmiles.travel.core.models;

import com.adobe.cq.wcm.core.components.models.form.Text;
import lombok.Getter;
import lombok.experimental.Delegate;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.via.ResourceSuperType;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        adapters = Text.class,
        resourceType = "aem-airmiles-travel2/components/form/text",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class FormText implements Text {

    @Self
    @Via(type = ResourceSuperType.class)
    @Delegate
    private Text text;

    @ValueMapValue
    @Getter
    private String icon;

}
