package com.aem.airmiles.web.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.aem.airmiles.web.core.services.AppsFlyerKeyService;

@Model(adaptables = SlingHttpServletRequest.class)
public class AppsFlyerKeyModel {

    @OSGiService
    private AppsFlyerKeyService appsFlyerKeyService;

    public String getAppsFlyerKey() {
        return appsFlyerKeyService.getAppsFlyerKey();
    }
}
