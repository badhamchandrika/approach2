package ai.aem.airmiles.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import lombok.Getter;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class ContentBlockModelTest {

    private ContentBlockModel fixture;

    @Getter
    @ValueMapValue
    private String headerText;

    @Getter
    @ValueMapValue
    private String bodyText;

    @Getter
    @ValueMapValue
    private String image;

    @Getter
    @ValueMapValue
    private String imageAltText;

    @Getter
    @ValueMapValue
    private String backgroundColor;

    @Getter
    @ValueMapValue
    private String invertPlacement;

    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(ContentBlockModel.class);
        aemContext.load().fileVaultXml("/contentBlock.xml", "/content/contentBlock");
        aemContext.currentResource("/content/contentBlock");
        fixture = aemContext.request().adaptTo(ContentBlockModel.class);
    }


    @Test
    void getHeaderText() {
        assertEquals("Header", fixture.getHeaderText());
    }

    @Test
    void getBodyText() {
        assertEquals("Body", fixture.getBodyText());
    }

    @Test
    void getImage() {
        assertEquals("Image", fixture.getImage());
    }

    @Test
    void getImageAltText() {
        assertEquals("Image Alt", fixture.getImageAltText());
    }

    @Test
    void getBackgroundColor() {
        assertEquals("Background", fixture.getBackgroundColor());
    }

    @Test
    void getInvertPlacement() {
        assertEquals("Invert Placement", fixture.getInvertPlacement());
    }
}

