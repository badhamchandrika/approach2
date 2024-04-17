package ca.airmiles.travel.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ComponentModelTest {

    private ComponentModel componentModel;

    public final AemContext aemctx= new AemContext();

    @BeforeEach
    void setUp(){
        aemctx.addModelsForClasses(ComponentModel.class);
        aemctx.load().json("/ca/airmiles/travel/core/models/ComponentModelTest.json", "/componentcontent");
    }

    @Test
    void testActiveTrue() {
        aemctx.currentResource("/componentcontent/testActiveTrue");
        componentModel = aemctx.request().adaptTo(ComponentModel.class);
        String expectedText = "true";
        assertNotNull(componentModel);
        assertEquals(expectedText, componentModel.getActive());
    }

    @Test
    void testActiveNull() {
        aemctx.currentResource("/componentcontent/testNullComponent");
        componentModel = aemctx.request().adaptTo(ComponentModel.class);
        assertNotNull(componentModel);
        assertNull(componentModel.getActive());
    }
    
    @Test
    void testAnalyticsListNull() {
        aemctx.currentResource("/componentcontent/testNullComponent");
        componentModel = aemctx.request().adaptTo(ComponentModel.class);
        assertNotNull(componentModel);
        assertNull(componentModel.getDataClickID());
    }
    
    @Test
    void testAnalyticsObjValid() {
        aemctx.currentResource("/componentcontent/testAnalyticsValid");
        componentModel = aemctx.request().adaptTo(ComponentModel.class);                
        String expectedOption = "test-ID";
        String expectedTrackingId = "test-clickID";
        assertNotNull(componentModel);
        assertEquals(expectedOption, componentModel.getDataTrackID());
        assertEquals(expectedTrackingId, componentModel.getDataClickID());
    }
}



