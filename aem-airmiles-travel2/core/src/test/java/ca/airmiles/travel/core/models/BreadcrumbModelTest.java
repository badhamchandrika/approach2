package ca.airmiles.travel.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class})
class BreadcrumbModelTest {

    private AemContext ctx=new AemContext();
    private Resource resource;
    BreadcrumbModel breadcrumbModel;
    String breadcrumbPath="/breadcrumb";

    @BeforeEach
    void setUp() {
        ctx.load().json("/ca/airmiles/travel/core/models/breadcrumb-custom.json",breadcrumbPath);
        resource=ctx.currentResource(breadcrumbPath);
        ctx.addModelsForClasses(BreadcrumbModel.class);
        breadcrumbModel=ctx.request().adaptTo(BreadcrumbModel.class);
    }

    @Test
    void getStartLevel() {
        assertEquals(true,breadcrumbModel.getDarkbg());
    }

    @Test
    void getDataTrackId() {
        assertEquals("001",breadcrumbModel.getDataTrackId());
    }

    @Test
    void getDarkbg() {
        assertEquals("3",breadcrumbModel.getStartLevel());
    }
}