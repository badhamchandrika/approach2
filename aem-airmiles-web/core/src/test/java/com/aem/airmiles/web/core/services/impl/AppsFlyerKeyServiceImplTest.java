package com.aem.airmiles.web.core.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppsFlyerKeyServiceImplTest {

    @Mock
    AppsFlyerKeyServiceImpl.Config cfg;

    @Test
    void getAppsFlyerKey() {
        when(cfg.appsFlyerKey()).thenReturn(EMPTY);
        assertDoesNotThrow(()->
            new AppsFlyerKeyServiceImpl(){{
                active(cfg);
                assertEquals(EMPTY, getAppsFlyerKey());
            }});
    }
}