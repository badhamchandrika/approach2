package com.aem.airmiles.web.core.services.impl;

import com.aem.airmiles.web.core.services.AppsFlyerKeyService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Component(service = AppsFlyerKeyService.class)
@Designate(ocd = AppsFlyerKeyServiceImpl.Config.class)
public class AppsFlyerKeyServiceImpl implements AppsFlyerKeyService {

    @ObjectClassDefinition(name = "AppsFlyer Key", description = "This service is used to expost the AppsFlyer API key to the Sling service.")
    public @interface Config {
        @AttributeDefinition(name = "AppsFlyer Key", description = "AppsFlyer Key for the mobile banner for downloading the Airmiles App.", type = AttributeType.STRING)
        String appsFlyerKey() default EMPTY;
    }

    private String appsFlyerKey;

    @Activate
    protected void active(final Config config) {
        appsFlyerKey = config.appsFlyerKey();
    }

    @Override
    public String getAppsFlyerKey() {
        return appsFlyerKey;
    }
}
