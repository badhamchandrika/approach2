package com.aem.airmiles.web.core.services.system.impl;

import com.aem.airmiles.web.core.services.system.SystemResourceResolverService;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains test cases for the {@link SystemResourceResolverServiceImpl} class.
 */
@ExtendWith(AemContextExtension.class)
class SystemResourceResolverServiceImplTest {

    /**
     * Tests the behavior of the {@link SystemResourceResolverService#retrieveSystemUserResourceResolver()} method.
     *
     * @param context The AemContext instance provided by the test framework.
     */
    @Test
    void retrieveSystemUserResourceResolver(final @NotNull AemContext context) {

        // Create an instance of SystemResourceResolverServiceImpl for testing.
        final SystemResourceResolverService system = new SystemResourceResolverServiceImpl();

        // Register the service within AemContext.
        context.registerService(SystemResourceResolverService.class, system);

        // Activate the service and inject dependencies.
        context.registerInjectActivateService(system);

        // Ensure that retrieving the system user resource resolver does not throw exceptions and is live.
        assertDoesNotThrow(() -> assertTrue(system.retrieveSystemUserResourceResolver().isLive()));
    }
}