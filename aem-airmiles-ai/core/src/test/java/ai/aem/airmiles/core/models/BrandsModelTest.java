package ai.aem.airmiles.core.models;

import ai.aem.airmiles.core.models.componentelements.BrandModel;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import lombok.Getter;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class BrandsModelTest {

    private BrandsModel fixture;

    @Getter
    @ValueMapValue
    private String wayfinderText;

    @Getter
    @ChildResource
    private List<BrandModel> brandsImages;
    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(BrandsModel.class);
        aemContext.load().fileVaultXml("/brands.xml", "/content/brands");
        aemContext.currentResource("/content/brands");
        fixture = aemContext.request().adaptTo(BrandsModel.class);
    }


    @Test
    void getWayfinderText() {
        assertEquals("Way Finder", fixture.getWayfinderText());
    }

    @Test
    void getBrandsImages() {
        assertNotNull(fixture.getBrandsImages());
    }
}