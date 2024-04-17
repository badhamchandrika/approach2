package com.aem.airmiles.web.core.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static com.aem.airmiles.web.core.services.impl.BaseConfigurationServiceImpl.Configuration;
import static org.mockito.Mockito.when;

/**
 * Test class used to cover {@link BaseConfigurationServiceImpl BaseConfigsService}.
 *
 * @author pabpalac.
 */
@ExtendWith(MockitoExtension.class)
class BaseConfigurationServiceImplTest {

    /**
     * Test service properties without OSGi config.
     */
    @Test
    void serviceWithoutConfigs() {
        assertDoesNotThrow(()->{
            final BaseConfigurationServiceImpl base = new BaseConfigurationServiceImpl();
            base.activate(null);
            assertEquals(StringUtils.EMPTY, base.getApiUrl());
            assertEquals(StringUtils.EMPTY, base.getDomBFFUrl());
        });
    }

    /**
     * Test service properties with OSGi config.
     */
    @Test
    void serviceWithConfigs() {
        assertDoesNotThrow(()->{
            final Configuration config = mock(Configuration.class);
            when(config.apiUrl()).thenReturn(StringUtils.EMPTY);
            when(config.domBFFUrl()).thenReturn(StringUtils.EMPTY);
            final BaseConfigurationServiceImpl base = new BaseConfigurationServiceImpl();
            base.activate(config);
            assertEquals(StringUtils.EMPTY, base.getApiUrl());
            assertEquals(StringUtils.EMPTY, base.getDomBFFUrl());
        });
    }
}