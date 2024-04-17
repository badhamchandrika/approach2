package com.aem.airmiles.web.core.services.impl;

import com.aem.airmiles.web.core.services.GenesysChatService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The GenesysChatServiceTest class is responsible for testing the functionality of the GenesysChatService class.
 * It uses Mockito to mock the GenesysChatConfiguration class and initialize the GenesysChatService with the mocked
 * configuration.
 *
 * @author pabpalac.
 */
@ExtendWith(MockitoExtension.class)
class GenesysChatServiceImplTest {

    /**
     * Values to be inserted and retrieved by the service.
     */
    private static final String
            UTF_8 = "utf-8",
            APPLICATION_JAVASCRIPT = "application/javascript",
            C0001 = "C0001",
            FAKE_SCRIPT = "https://something.com/fake.js",
            ENVIRONMENT_ID = "123",
            DEPLOYMENT_ID = "117";

    /**
     * The GenesysChatService class is responsible for configuring and providing the necessary settings for the Genesys
     * chat service.
     */
    private static GenesysChatService svc;

    /**
     * Simulates Genesys Chat service activation with the mocked configuration.
     */
    @BeforeAll
    static void activate() {
        final GenesysChatServiceImpl.Config config = mock(GenesysChatServiceImpl.Config.class);

        when(config.enabled()).thenReturn(true);
        when(config.charset()).thenReturn(UTF_8);
        when(config.scriptType()).thenReturn(APPLICATION_JAVASCRIPT);
        when(config.consentCategory()).thenReturn(C0001);
        when(config.scriptSrc()).thenReturn(FAKE_SCRIPT);
        when(config.environmentId()).thenReturn(ENVIRONMENT_ID);
        when(config.deploymentId()).thenReturn(DEPLOYMENT_ID);

        svc = new GenesysChatServiceImpl();
        assertDoesNotThrow(() -> {
            final Method mtd = svc.getClass().getDeclaredMethod("activate", GenesysChatServiceImpl.Config.class);
            mtd.setAccessible(true);
            mtd.invoke(svc, config);
        });
    }

    /**
     * Ensures that the service is enabled.
     */
    @Test
    void isEnabled() {
        assertTrue(svc.isEnabled());
    }

    /**
     * Ensures that the charset is UTF-8.
     */
    @Test
    void getCharset() {
        assertEquals(UTF_8, svc.getCharset());
    }

    /**
     * Ensures that the script type is JavaScript.
     */
    @Test
    void getScriptType() {
        assertEquals(APPLICATION_JAVASCRIPT, svc.getScriptType());
    }

    /**
     * Ensures that OneTrust Cookie Category is C0001.
     */
    @Test
    void getConsentCategory() {
        assertEquals(C0001, svc.getConsentCategory());
    }

    /**
     * Ensures that the script source is the provided one.
     */
    @Test
    void getScriptSrc() {
        assertEquals(FAKE_SCRIPT, svc.getScriptSrc());
    }

    /**
     * Ensures that the environment id is the provided one.
     */
    @Test
    void getEnvironmentId() {
        assertEquals(ENVIRONMENT_ID, svc.getEnvironmentId());
    }

    /**
     * Ensures that the deployment id is the provided one.
     */
    @Test
    void getDeploymentId() {
        assertEquals(DEPLOYMENT_ID, svc.getDeploymentId());
    }
}