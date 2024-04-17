package com.aem.airmiles.web.core.services;

public interface AppsFlyerKeyService {
    /**
     * @return appsFlyerKey that is configured in the com.aem.airmiles.web.core.services.impl.AppsFlyerKeyServiceImpl.cfg.json within it's run mode configuration folder.
     */
    String getAppsFlyerKey();
}
