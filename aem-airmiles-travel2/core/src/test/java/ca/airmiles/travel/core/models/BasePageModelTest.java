package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.services.BaseConfigurationService;
import ca.airmiles.travel.core.services.impl.BaseConfigurationServiceImpl;
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
class BasePageModelTest {

    private AemContext context = new AemContext();

    private BasePageModel model;


    @BeforeEach
    void setUp() {
        BaseConfigurationService configurationService = new BaseConfigurationServiceImpl();
        Map<String,Object> map = new HashMap<>();
        map.put("apiUrl", "https://int-services.api.airmiles.ca");
        map.put("domBFFUrl", "https://int-bff.api.airmiles.ca/dombff-contents");
        context.load().json("/ca/airmiles/travel/core/models/DealsListPages.json", "/content/aem-airmiles-travel2/ca/en/travel/deals");
        context.currentPage("/content/aem-airmiles-travel2/ca/en/travel/deals");
        context.registerInjectActivateService(configurationService, map);
        model = context.request().adaptTo(BasePageModel.class);

    }

    @Test
    void getApiUrl() {
        assertNotNull(model);
        assertEquals("https://int-services.api.airmiles.ca",model.getApiUrl());
    }

    @Test
    void getDomBffApiUrl() {
        assertEquals("https://int-bff.api.airmiles.ca/dombff-contents", model.getDomBFFUrl());
    }

    @Test
    void getAAPageNameTest() {
        assertNotNull(model);
        assertEquals(":deals",model.getAAPageName());
    }
}