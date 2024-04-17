package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.services.BaseConfigurationService;
import com.aem.airmiles.web.core.services.RunModeService;
import com.aem.airmiles.web.core.services.impl.BaseConfigurationServiceImpl;
import com.aem.airmiles.web.core.services.impl.RunModeServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class used to cover {@link BasePageModel model}.
 */
@ExtendWith(AemContextExtension.class)
class BasePageModelTest {

    /**
     * Model to be tested.
     */
    private static BasePageModel model;

    /**
     * Initializes the model with AemContext Service and mocked configs.
     *
     * @param context AemContextExtension entity.
     */
    @BeforeAll
    static void setUp(final @NotNull AemContext context) {
        final BaseConfigurationService configurationService = new BaseConfigurationServiceImpl();
        final RunModeService runModeService = new RunModeServiceImpl();
        final Map<String, Object> map = new HashMap<>();
        context.runMode("prod");
        map.put("apiUrl", "https://int-services.api.airmiles.ca");
        map.put("domBFFUrl", "https://int-bff.api.airmiles.ca/dombff-contents");
        map.put("domBFFOffersUrl", "https://int-bff.api.airmiles.ca/dombff-offers");
        context.registerInjectActivateService(configurationService, map);
        context.registerInjectActivateService(runModeService);
        model = context.request().adaptTo(BasePageModel.class);
    }

    /**
     * Asserts that the model is not null and API Url is the configured one.
     */
    @Test
    void getApiUrl() {
        assertNotNull(model);
        assertEquals("https://int-services.api.airmiles.ca", model.getApiUrl());
    }

    /**
     * Asserts that the model is not null and DOM BFF API Url is the configured one.
     */
    @Test
    void getDomBFFUrl() {
        assertNotNull(model);
        assertEquals("https://int-bff.api.airmiles.ca/dombff-contents", model.getDomBFFUrl());
    }

    /**
     * Asserts that the model is not null and OFFERS DOM BFF API Url is the configured one.
     */
    @Test
    void getDomBFFOffersUrl() {
        assertNotNull(model);
        assertEquals("https://int-bff.api.airmiles.ca/dombff-offers", model.getDomBFFOffersUrl());
    }

    /**
     * Checks whether the current environment is production.
     */
    @Test
    void shouldBeProduction() {
        assertTrue(model.isProduction());
        assertEquals("prod", model.getEnvironment());
    }
}