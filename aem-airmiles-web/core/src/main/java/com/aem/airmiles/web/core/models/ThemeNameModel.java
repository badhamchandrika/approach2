package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class)
public class ThemeNameModel {

    @Getter
    @Setter
    @Inject @Optional
    private String themeName;

    public String getThemeNameSite() {
        return "aem-airmiles-web-" + themeName + ".site";
    }

    public String getThemeNameDependency() {
        return "aem-airmiles-web-" + themeName + ".dependencies";
    }
}
