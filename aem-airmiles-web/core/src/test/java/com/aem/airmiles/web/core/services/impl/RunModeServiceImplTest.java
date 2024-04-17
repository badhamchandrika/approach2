package com.aem.airmiles.web.core.services.impl;

import com.aem.airmiles.web.core.services.RunModeService;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;

import static junitx.util.PrivateAccessor.setField;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The RunModeServiceImplTest class is a unit test class for the RunModeServiceImpl class.
 * It tests the functionality of retrieving the current run mode environment.
 *
 * @author pabpalac.
 */
@ExtendWith(MockitoExtension.class)
class RunModeServiceImplTest {

    /**
     * The svc variable is an instance of the RunModeService interface.
     */
    private static RunModeService svc;

    /**
     * Environment runModes available.
     */
    private static final String PROD = "prod", STAGE = "stage", DEV = "dev";

    /**
     * This method activates the RunModeService by initializing the OSGi service.
     */
    @BeforeAll
    static void activate() {
        svc = new RunModeServiceImpl();
        final SlingSettingsService settings = mock(SlingSettingsService.class);
        when(settings.getRunModes())
                .thenReturn(Collections.singleton("prod"))
                .thenReturn(Collections.singleton("stage"))
                .thenReturn(Collections.singleton("rde"))
                .thenReturn(Collections.singleton("dev"))
                .thenReturn(new HashSet<>());
        assertDoesNotThrow(() -> {
            final Method mtd = svc.getClass().getDeclaredMethod("activate");
            setField(svc, "settings", settings);
            mtd.setAccessible(true);
            mtd.invoke(svc);
        });
    }

    /**
     * Checks that the environment values are correct.
     */
    @Test
    void getEnvironments() {
        assertEquals(PROD, svc.getEnvironment());
        assertEquals(STAGE, svc.getEnvironment());
        assertEquals("qa", svc.getEnvironment());
        assertEquals(DEV, svc.getEnvironment());
        assertEquals("local", svc.getEnvironment());
    }
}