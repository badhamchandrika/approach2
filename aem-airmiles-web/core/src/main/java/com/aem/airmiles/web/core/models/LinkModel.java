package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.resource.Resource;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class LinkModel {
    @ValueMapValue
    @Getter
    @Setter
    private String text;    
    
    @ValueMapValue
    @Setter
    private String url;
    
    @ValueMapValue
    @Getter
    @Setter
    private String target;

    @ValueMapValue
    @Setter
    private boolean authenticated;

    public boolean isAuthenticated(){
        return authenticated;
    }

    public String getUrl() {
        return Utils.getFormattedURL(url);
    }
}