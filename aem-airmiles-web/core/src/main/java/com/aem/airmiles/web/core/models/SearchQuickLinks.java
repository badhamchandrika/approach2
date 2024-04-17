package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchQuickLinks extends ComponentModel {

    @Getter
    @Setter
    @ValueMapValue
    private String text;

    @Setter
    @ValueMapValue
    private String ctaUrl;

    public String getCtaUrl() {
        return Utils.getFormattedURL(ctaUrl);
    }
}
