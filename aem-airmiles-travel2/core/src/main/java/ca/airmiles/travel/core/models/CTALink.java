package ca.airmiles.travel.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.api.resource.Resource;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class CTALink extends LinkModel {

    @ValueMapValue
    @Getter
    @Setter
    private String btnStyle;

    @ValueMapValue
    @Getter
    @Setter
    private String hexTextColor;

    @ValueMapValue
    @Getter
    @Setter
    private String hexLinkBarColor;

    @ValueMapValue
    @Getter
    @Setter
    private String hexBtnColor;

    @ValueMapValue
    @Getter
    @Setter
    private String linkTarget;

    @ValueMapValue
    @Setter
    @Getter
    private String linkType;

    @ValueMapValue
    @Setter
    @Getter
    private String linkMobileType;
    
    @ValueMapValue
    @Setter
    @Getter
    private String ctaBreakpoint;

}
