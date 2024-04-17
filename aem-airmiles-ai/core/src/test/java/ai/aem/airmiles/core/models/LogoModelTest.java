package ai.aem.airmiles.core.models;

import ai.aem.airmiles.core.models.componentelements.CTAModel;
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
public class LogoModelTest {

    private LogoModel fixture;

    @Getter
    @ValueMapValue
    private String logoURL;

    @Getter
    @ValueMapValue
    private String logoImage;

    @Getter
    @ValueMapValue
    private String logoAltText;
    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(LogoModel.class);
        aemContext.create().resource("/content/logo", ImmutableMap.<String, Object>builder()
                .put("logoURL", "/content/airmiles.html")
                .put("logoImage", "/content/dam/asset1.jpg")
                .put("logoAltText", "alt text")
                .build());
        aemContext.currentResource("/content/logo");
        fixture = aemContext.request().adaptTo(LogoModel.class);

    }

    @Test
    void getLogoAltText() {
        assertEquals("alt text", fixture.getLogoAltText());
    }

    @Test
    void getLogoURL() {
        assertEquals("/content/airmiles.html", fixture.getLogoURL());
    }

    @Test
    void getLogoImage() {
        assertEquals("/content/dam/asset1.jpg", fixture.getLogoImage());
    }

}