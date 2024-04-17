package ai.aem.airmiles.core.models.componentelements;

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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class DataCardModelTest {
    private DatacardModel fixture;

    @Getter
    @ValueMapValue
    private String headerText;

    @Getter
    @ValueMapValue
    private String midText;

    @Getter
    @ValueMapValue
    private String icon;

    @Getter
    @ValueMapValue
    private String image;

    @Getter
    @ValueMapValue
    private String imageAltText;

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
        aemContext.addModelsForClasses(DatacardModel.class);
        aemContext.create().resource("/content/dataCard", ImmutableMap.<String, Object>builder()
                .put("headerText", "Header")
                .put("midText", "Mid Text")
                .put("icon", "/content/dam/asset1.jpg")
                .put("image", "/content/dam/asset1.jpg")
                .put("imageAltText", "Image Alt")
                .put("cta", ImmutableMap.<String, Object>builder()
                        .put("sling:resourceType", "airmiles-ai/components/cta")
                        .put("ctaText", "cta text")
                )
                .build());

        aemContext.currentResource("/content/dataCard");
        fixture = aemContext.request().adaptTo(DatacardModel.class);

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
    void getImage() {
        assertEquals("/content/dam/asset1.jpg", fixture.getImage());
    }

    @Test
    void getImageAltText() {
        assertEquals("Image Alt", fixture.getImageAltText());
    }

    @Test
    void getIcon() {
        assertEquals("/content/dam/asset1.jpg", fixture.getIcon());
    }


    @Test
    void getCta() {
        assertNotNull( fixture.getCta());
    }
}

