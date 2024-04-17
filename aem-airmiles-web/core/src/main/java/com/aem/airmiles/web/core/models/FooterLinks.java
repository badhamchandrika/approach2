package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(adaptables =  Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterLinks extends ComponentModel{

    @Getter
    @Setter
    @ValueMapValue
    private String imagePath;

    @Getter
    @Setter
    @ValueMapValue
    private String altText;

    @Setter
    @ValueMapValue
    private String link;

    public String getLink() {
        return Utils.getFormattedURL(link);
    }
}
