package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.resource.Resource;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Links {

    @Getter
    @Setter
    @ValueMapValue
    private String title;

    @Setter
    @ValueMapValue
    private String link;

    @Getter
    @Setter
    @ValueMapValue
    private String linkTarget;

    @Getter
    @Setter
    @ValueMapValue
    @Optional
    private String icon;

    @Getter
    @Setter
    @ValueMapValue
    @Optional
    private String description;

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackId;

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackClick;

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackType;

    public String getLink() {
        return Utils.getFormattedURL(link);
    }
}
