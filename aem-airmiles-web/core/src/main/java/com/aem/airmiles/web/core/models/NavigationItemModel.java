package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NavigationItemModel {

    @ValueMapValue
    @Setter
    @Getter
    private String navTitle;

    @ValueMapValue
    @Setter
    private String navLink;

    @ValueMapValue
    @Setter
    @Getter
    private String linkTarget;

    @ValueMapValue
    @Setter
    @Getter
    private String dataTrackId;

    @ValueMapValue
    @Setter
    @Getter
    private String dataTrackClick;

    @ValueMapValue
    @Setter
    @Getter
    private String dataTrackType;

    @ValueMapValue
    @Setter
    @Getter
    private String ctaText;

    @ValueMapValue
    @Setter
    private String ctaUrl;

    @ValueMapValue
    @Setter
    @Getter
    private String targetCtaUrl;

    @ValueMapValue
    @Setter
    @Getter
    private String ctaDataTrackId;

    @ValueMapValue
    @Setter
    @Getter
    private String ctaDataTrackClick;

    @ValueMapValue
    @Setter
    @Getter
    private String ctaDataTrackType;

    @Getter
    @Setter
    @ChildResource
    List<ContentFragmentPaths> subSectionPaths;

    @Getter
    @Setter
    List<NavigationSectionBean> navSectionList;

    @ValueMapValue
    @Setter
    @Getter
    private String hideCtaInMob;

    public String getNavLink() {
        return Utils.getFormattedURL(navLink);
    }

    public String getCtaUrl() {
        return Utils.getFormattedURL(ctaUrl);
    }
}
