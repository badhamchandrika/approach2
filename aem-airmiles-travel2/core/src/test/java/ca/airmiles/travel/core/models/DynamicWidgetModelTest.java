package ca.airmiles.travel.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class DynamicWidgetModelTest {
    private AemContext aemContext = new AemContext();
    private DynamicWidgetModel dynamicWidget;

    @BeforeEach
    public void setUp() {
        aemContext.addModelsForClasses(DynamicWidgetModel.class);
        aemContext.load().json("/ca/airmiles/travel/core/models/travel-pages.json", "/content/aem-airmiles-travel2/ca/en/travel");
    }

    @Test
    public void testIsDestinationsPage() {
        aemContext.currentPage("/content/aem-airmiles-travel2/ca/en/travel/destinations");
        dynamicWidget = aemContext.request().adaptTo(DynamicWidgetModel.class);
        assertTrue(dynamicWidget.isDestinationsPage());
    }

    @Test
    public void testIsNotDestinationsPage() {
        aemContext.currentPage("/content/aem-airmiles-travel2/ca/en/travel/some-test");
        dynamicWidget = aemContext.request().adaptTo(DynamicWidgetModel.class);
        assertFalse(dynamicWidget.isDestinationsPage());
    }
}
