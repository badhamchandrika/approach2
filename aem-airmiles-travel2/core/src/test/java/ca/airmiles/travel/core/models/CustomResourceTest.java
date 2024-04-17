package ca.airmiles.travel.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class CustomResourceTest {
    private AemContext ctx = new AemContext();
    CustomResource model;

    @BeforeEach
    void setup(){
        ctx.load().json("/ca/airmiles/travel/core/models/item.json","/item");
        ctx.currentResource("/item");
        ctx.request().setAttribute("resourcePath","/item");
        model = ctx.request().adaptTo(CustomResource.class);
    }

    @Test
    void test(){
        assertEquals("/item", model.getBreadcrumb());
        assertEquals("about", model.getTabImage());
        assertEquals("dataTrackID", model.getDataTrackID());
        assertEquals("dataClickID", model.getDataClickID());
        assertEquals("dataTrackType", model.getDataTrackType());
    }
}
