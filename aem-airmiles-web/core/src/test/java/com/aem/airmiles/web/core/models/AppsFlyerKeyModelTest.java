package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.services.AppsFlyerKeyService;
import com.aem.airmiles.web.core.services.impl.AppsFlyerKeyServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class AppsFlyerKeyModelTest {

    private AemContext context = new AemContext();

    private AppsFlyerKeyModel model;


    @BeforeEach
    void setUp() {
        AppsFlyerKeyService appsFlyerKeyService = new AppsFlyerKeyServiceImpl();
        Map<String,Object> map = new HashMap<>();
        map.put("appsFlyerKey", "8366f32d-825a-4186-9062-dd25a5ec4336");
        context.registerInjectActivateService(appsFlyerKeyService, map);
        model = context.request().adaptTo(AppsFlyerKeyModel.class);
    }

    @Test
    void getApiUrl() {
        assertNotNull(model);
        assertEquals("8366f32d-825a-4186-9062-dd25a5ec4336",model.getAppsFlyerKey());
    }
}
