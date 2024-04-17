package ai.aem.airmiles.core.models.componentelements;

import ai.aem.airmiles.core.models.HeroModel;
import com.google.common.collect.ImmutableMap;
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

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class BrandModelTest {

    private BrandModel fixture;
    @Getter
    @ValueMapValue
    private String brandImage;

    @Getter
    @ValueMapValue
    private String brandAltText;
    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(BrandModel.class);
        aemContext.create().resource("/content/brand", ImmutableMap.<String, Object>builder()
                .put("brandImage", "/content/dam/asset1.jpg")
                .put("brandAltText", "Alt Text")
                .build());

        aemContext.currentResource("/content/brand");
        fixture = aemContext.request().adaptTo(BrandModel.class);

    }

    @Test
    void getHeaderText() {
        assertEquals("/content/dam/asset1.jpg", fixture.getBrandImage());
    }

    @Test
    void getMidText() {
        assertEquals("Alt Text", fixture.getBrandAltText());
    }

}