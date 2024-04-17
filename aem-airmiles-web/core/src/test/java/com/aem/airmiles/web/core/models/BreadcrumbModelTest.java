package com.aem.airmiles.web.core.models;

import com.day.cq.commons.Externalizer;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.adobe.cq.wcm.core.components.testing.MockExternalizerFactory.getExternalizerService;
import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class BreadcrumbModelTest {

    String base = "/content/page";

    String base_page = base + "/jcr:content/root";

    public final AemContext aemctx = new AemContextBuilder()
            .beforeSetUp(context -> context.registerService(Externalizer.class, getExternalizerService()))
            .plugin(CORE_COMPONENTS)
            .build();

    @BeforeEach
    public void setUp() {
        aemctx.load().json("/com.aem.airmiles.web.core/BreadcrumbComponentTestDefinition.json",
                "/apps/aem-airmiles-web/components/breadcrumb");
        aemctx.load().json("/com.aem.airmiles.web.core/BreadcrumbModelTest.json", base);
    }

    @Test
    void testValidBreadcrumb() {
        aemctx.currentResource(base_page + "/breadcrumbValid");
        BreadcrumbModel breadcrumbModel = aemctx.request().adaptTo(BreadcrumbModel.class);
        String expectedActive = "true";
        String expectedId = "testId";
        assertNotNull(breadcrumbModel);
        assertEquals(expectedActive, breadcrumbModel.getActive());
        assertEquals(expectedId, breadcrumbModel.getId());
        assertNull(breadcrumbModel.getData());
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "/breadcrumbNoActive",
            "/breadcrumbNoMasterFields",
            "/breadcrumbNull"
    })
    void breadcrumb_types(final String type) {
        aemctx.currentResource(base_page + type);
        BreadcrumbModel breadcrumbModel = aemctx.request().adaptTo(BreadcrumbModel.class);
        assertNotNull(breadcrumbModel);
        assertNull(breadcrumbModel.getActive());
    }

    @Test
    void testNoAnalyticsNoValidBreadcrumb() {
        aemctx.currentResource(base_page + "/breadcrumbAnalyticsNoValid");
        assertNotNull(aemctx.request().adaptTo(BreadcrumbModel.class));
    }
}