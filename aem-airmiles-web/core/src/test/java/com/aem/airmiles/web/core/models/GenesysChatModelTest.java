package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.services.GenesysChatService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static junitx.util.PrivateAccessor.setField;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The GenesysChatModelTest class is used to test the GenesysChatModel class.
 *
 * @author pabpalac.
 */
 @ExtendWith(MockitoExtension.class)
class GenesysChatModelTest {

    /**
     * Values to be retrieved by the service.
     */
    private static final String
            UTF_8 = "utf-8",
            PLAIN_TEXT = "plain/text",
            C0002 = "C0002",
            FAKE_SCRIPT = "https://something.com/fake.js",
            ENVIRONMENT_ID = "456",
            DEPLOYMENT_ID = "SIERRA-117";

    /**
     * The GenesysChatModel class represents a model that interacts with the GenesysChatService to retrieve and provide
     * configuration values for the Genesys chat service.
     */
    private static GenesysChatModel model;

    /**
     * Simulates Genesys Chat service activation with the mocked configuration.
     */
    @BeforeAll
    static void buildModel() {
        final GenesysChatService svc = mock(GenesysChatService.class);

        when(svc.isEnabled()).thenReturn(true);
        when(svc.getCharset()).thenReturn(UTF_8);
        when(svc.getScriptType()).thenReturn(PLAIN_TEXT);
        when(svc.getConsentCategory()).thenReturn(C0002);
        when(svc.getScriptSrc()).thenReturn(FAKE_SCRIPT);
        when(svc.getEnvironmentId()).thenReturn(ENVIRONMENT_ID);
        when(svc.getDeploymentId()).thenReturn(DEPLOYMENT_ID);

        model = new GenesysChatModel();

        assertDoesNotThrow(() -> setField(model, "genesys", svc));
    }

    /**
     * Ensures that the service is enabled.
     */
    @Test
    void isEnabled() {
        assertTrue(model.isEnabled());
    }

    /**
     * Ensures that the charset is UTF-8.
     */
    @Test
    void getCharset() {
        assertEquals(UTF_8, model.getCharset());
    }

    /**
     * Ensures that the script type is JavaScript.
     */
    @Test
    void getScriptType() {
        assertEquals(PLAIN_TEXT, model.getScriptType());
    }

    /**
     * Ensures that OneTrust Cookie Category is C0002.
     */
    @Test
    void getConsentCategory() {
        assertEquals(C0002, model.getConsentCategory());
    }

    /**
     * Ensures that the script source is the provided one.
     */
    @Test
    void getScriptSrc() {
        assertEquals(FAKE_SCRIPT, model.getScriptSrc());
    }

    /**
     * Ensures that the environment id is the provided one.
     */
    @Test
    void getEnvironmentId() {
        assertEquals(ENVIRONMENT_ID, model.getEnvironmentId());
    }

    /**
     * Ensures that the deployment id is the provided one.
     */
    @Test
    void getDeploymentId() {
        assertEquals(DEPLOYMENT_ID, model.getDeploymentId());
    }
}