package com.aem.airmiles.web.core.models.onetrust;

import com.aem.airmiles.web.core.models.onetrust.cacs.OneTrustCAC;
import com.aem.airmiles.web.core.services.system.SystemResourceResolverService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.Set;

import static java.util.Collections.singleton;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test class used to cover {@link OneTrustModel One Trust Business Logic} test cases.
 *
 * @author pabpalac.
 */
@ExtendWith(MockitoExtension.class)
class OneTrustModelTest {

    /**
     * Create a Set of strings to simulate runModes.
     */
    private final Set<String> runModes = singleton("dev");

    /**
     * Mock current page from Sling.
     */
    @Mock
    private Page currentPage;

    /**
     * Mock System user resource resolver service.
     */
    @Mock
    private SystemResourceResolverService system;

    /**
     * Mock Sling Settings service to simulate runModes retrieval.
     */
    @Mock
    private SlingSettingsService sling;

    /**
     * Mock resource resolver.
     */
    @Mock
    private ResourceResolver rr;

    /**
     * Mock Sling resource.
     */
    @Mock
    private Resource res;

    /**
     * Mock configuration builder to simulate CAC retrieval.
     */
    @Mock
    private ConfigurationBuilder cb;

    /**
     * OneTrust Context Aware Configuration.
     */
    @Mock
    private OneTrustCAC cac;

    /**
     * Inject mocked objects into OneTrust Model.
     */
    @InjectMocks
    private OneTrustModel ot = new OneTrustModel();

    /**
     * Init process should be taking OT Configurations as an empty string when current page object is missing.
     */
    @Test
    void initWithoutCurrentPage() {

        // Assert that OTModel does not crash.
        assertDoesNotThrow(() -> {

            // Create a new instance of OTModel (without necessary injections).
            new OneTrustModel() {{

                // Force model post construct process.
                init();

                // Assert that the values are empty.
                assertEquals(EMPTY, getConsentId());
                assertEquals(EMPTY, getSdkStub());
            }};
        });
    }

    /**
     * Test case for initializing OTModel without specifying run modes.
     */
    @Test
    void initWithoutRunModes() {

        // Assert that OTModel does not crash.
        assertDoesNotThrow(() -> {

            // Assert that the init method does not crash.
            assertDoesNotThrow(ot::init);

            // Assert that the values are empty.
            assertOneTrustPropertiesAreEmpty();
        });
    }

    /**
     * Test case for handling LoginException during initialization of OTModel.
     */
    @Test
    void initHandlingLoginException() {

        // Assert that OTModel does not crash.
        assertDoesNotThrow(() -> {

            // Make SlingSettingsService retrieve "author,dev".
            when(sling.getRunModes()).thenReturn(runModes);

            // Force service to crash with LoginException.
            when(system.retrieveSystemUserResourceResolver()).thenThrow(new LoginException());

            // Assert that the init method does not crash.
            assertDoesNotThrow(ot::init);

            // Assert that the values are empty.
            assertOneTrustPropertiesAreEmpty();
        });
    }

    /**
     * Validates that the init method executes successfully and asserts that OneTrust properties are empty when no CAC
     * is found.
     */
    @Test
    void noCACFound() {

        // Assert that OTModel does not crash.
        assertDoesNotThrow(() -> {

            // Make SlingSettingsService retrieve "author,dev".
            when(sling.getRunModes()).thenReturn(runModes);

            // Force service to retrieve mocked Resource Resolver.
            when(system.retrieveSystemUserResourceResolver()).thenReturn(rr);

            // Retrieve mocked resource from mocked resource resolver.
            when(rr.resolve(anyString())).thenReturn(res);

            // Return homepage from current page.
            when(currentPage.getPath()).thenReturn("/content/aem-airmiles-web/ca/en");

            // Return null CAC from configBuilder.
            when(cb.as(OneTrustCAC.class)).thenReturn(null);

            // Return configBuilder from Resource.
            when(res.adaptTo(ConfigurationBuilder.class)).thenReturn(cb);

            // Assert that the init method does not crash.
            assertDoesNotThrow(ot::init);

            // Assert that the values are empty.
            assertOneTrustPropertiesAreEmpty();
        });
    }

    /**
     * Tests OTModel initialization with CACs, ensuring successful execution and OneTrust activation.
     */
    @Test
    void initWithCACs() {

        // Assert that OTModel does not crash.
        assertDoesNotThrow(() -> {

            // Make SlingSettingsService retrieve "author,dev".
            when(sling.getRunModes()).thenReturn(runModes);

            // Force service to retrieve mocked Resource Resolver.
            when(system.retrieveSystemUserResourceResolver()).thenReturn(rr);

            // Retrieve mocked resource from mocked resource resolver.
            when(rr.resolve(anyString())).thenReturn(res);

            // Return homepage from current page.
            when(currentPage.getPath()).thenReturn("/content/aem-airmiles-web/ca/en");

            // Return null CAC from configBuilder.
            when(cb.as(OneTrustCAC.class)).thenReturn(cac);

            // Return configBuilder from Resource.
            when(res.adaptTo(ConfigurationBuilder.class)).thenReturn(cb);

            // Return OT values from CAC.
            when(cac.sdkStubScript()).thenReturn("dev|one-trust.com/sdk-stub.js");
            when(cac.consentId()).thenReturn("dev|some-4354-token98-test");


            // Assert that the init method does not crash.
            assertDoesNotThrow(ot::init);

            // Assert one-trust is enabled.
            assertTrue(ot.isEnabled());
        });
    }

    /**
     * Tests OneTrustModel's setConfig method. Validates method behavior for various input entries, using reflection
     * to access private methods.
     */
    @Test
    void setConfig() {
        assertDoesNotThrow(() -> {
            final Method getConfig = OneTrustModel.class.getDeclaredMethod("getConfig", String.class, Set.class);
            getConfig.setAccessible(true);
            for (final String entry : new String[]{"dev,a", "rde,b", "stage,c", "prod,d"}) {
                final String[] kv = entry.split(",");
                assertEquals(kv[1], getConfig.invoke(ot, "dev|a,rde|b,stage|c,prod|d", singleton(kv[0])));
            }
            assertEquals(EMPTY, getConfig.invoke(ot, "dev|dev", singleton("local")));
        });
    }


    /**
     * Asserts empty properties and disabled state for OneTrust configuration.
     */
    private void assertOneTrustPropertiesAreEmpty() {

        // Assert that the values are empty.
        assertEquals(EMPTY, ot.getConsentId());
        assertEquals(EMPTY, ot.getSdkStub());

        // Assert OT is disabled.
        assertFalse(ot.isEnabled());
    }
}