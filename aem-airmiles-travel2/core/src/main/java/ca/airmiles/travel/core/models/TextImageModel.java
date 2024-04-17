package ca.airmiles.travel.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.api.resource.Resource;
import ca.airmiles.travel.core.utils.Utils;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class TextImageModel extends CTALink {

    @ValueMapValue
    @Getter
    @Setter
    private String dataTrackSection;

    @ValueMapValue
    @Getter
    @Setter
    private String title;

    @ValueMapValue
    @Getter
    @Setter
    private String titleTrademark;

    @ValueMapValue
    @Getter
    @Setter
    private String description;

    @ValueMapValue
    @Getter
    @Setter
    private String linkDisclaimerText;

    @ValueMapValue
    @Getter
    @Setter
    private String headline;

    @ValueMapValue
    @Getter
    @Setter
    private String fileReference;

    @ValueMapValue
    @Getter
    @Setter
    private String smallScreenFileReference;

    @ValueMapValue
    @Getter
    @Setter
    private String altText;

    @ValueMapValue
    @Setter
    private String url;

    @ValueMapValue
    @Getter
    @Setter
    private String imageTarget;

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackIDImg;

    @Getter
    @Setter
    @ValueMapValue
    private String dataClickIDImg;

    public String getUrl() {
        return Utils.getFormattedURL(url);
    }
}
