package ai.aem.airmiles.core.models;

import ai.aem.airmiles.core.models.componentelements.CTAModel;
import ai.aem.airmiles.core.models.componentelements.LinkModel;
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

import java.util.List;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class HeroModelTest {

    private HeroModel fixture;

    @Getter
    @ValueMapValue
    private String headerText;

    @Getter
    @ValueMapValue
    private String midText;

    @Getter
    @ValueMapValue
    private String backgroundImage;

    @Getter
    @ValueMapValue
    private String backgroundImageAltText;

    @Getter
    @ValueMapValue
    private String productImage;

    @Getter
    @ValueMapValue
    private String productImageAltText;

    @Getter
    @ValueMapValue
    private String alignment;

    @Getter
    @ChildResource
    private CTAModel cta;
    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(HeroModel.class);
        aemContext.create().resource("/content/hero", ImmutableMap.<String, Object>builder()
                .put("headerText", "Header")
                .put("midText", "Mid Text")
                .put("backgroundImage", "background Image")
                .put("backgroundImageAltText", "Image Alt")
                .put("productImage", "Product Image")
                .put("productImageAltText", "Product Alt Text")
                .put("alignment", "alignment")
                .put("cta", ImmutableMap.<String, Object>builder()
                        .put("sling:resourceType", "airmiles-ai/components/cta")
                        .put("ctaText", "cta text")
                )
                .build());

        aemContext.currentResource("/content/hero");
        fixture = aemContext.request().adaptTo(HeroModel.class);

    }

    @Test
    void getHeaderText() {
        assertEquals("Header", fixture.getHeaderText());
    }

    @Test
    void getMidText() {
        assertEquals("Mid Text", fixture.getMidText());
    }

    @Test
    void getBackgroundImage() {
        assertEquals("background Image", fixture.getBackgroundImage());
    }

    @Test
    void getBackgroundImageAltText() {
        assertEquals("Image Alt", fixture.getBackgroundImageAltText());
    }

    @Test
    void getProductImage() {
        assertEquals("Product Image", fixture.getProductImage());
    }

    @Test
    void getProductImageAltText() {
        assertEquals("Product Alt Text", fixture.getProductImageAltText());
    }

    @Test
    void getAlignment() {
        assertEquals("alignment", fixture.getAlignment());
    }

    @Test
    void getCta() {
        assertNotNull( fixture.getCta());
    }
}