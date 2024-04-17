package ca.airmiles.travel.core.models;

import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import static com.day.cq.commons.jcr.JcrConstants.JCR_CONTENT;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Widget {
    @ValueMapValue
    @Named("widget")
    private String path;

    @Getter
    @ValueMapValue
    @Named("tabName")
    private String tabName;

    @SlingObject
    private ResourceResolver resourceResolver;

    private boolean isVariation = false;

    @PostConstruct
    private void init() {
        if (null != path && !path.isEmpty()) {
            Resource resource = resourceResolver.getResource(path + "/" + JCR_CONTENT);
            if (resource != null) {
                isVariation = (Boolean) resource.getValueMap().getOrDefault("cq:xfMasterVariation", false);
            }
        }

    }

    /**
     * @return
     */
    public String getPath() {
        if (isVariation) {
            return path;
        }
        return "";
    }

}
