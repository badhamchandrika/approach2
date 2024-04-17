package ai.aem.airmiles.core.models.componentelements;

import com.google.common.collect.ImmutableMap;
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
public class CTAModelTest {
    private CTAModel fixture;

    @Getter
    @ValueMapValue
    private String ctaText;

    @Getter
    @ValueMapValue
    private String ctaUrl;

    @Getter
    @ValueMapValue
    private String ctaIcon;

    @Getter
    @ValueMapValue
    private String ctaType;

    @Getter
    @ValueMapValue
    private String ctaTarget;


    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(CTAModel.class);
        aemContext.create().resource("/content/cta", ImmutableMap.<String, Object>builder()
                .put("ctaIcon", "/content/dam/asset1.jpg")
                .put("ctaText", "Text")
                .put("ctaUrl", "/content/airmiles.html")
                .put("ctaType", "button")
                .put("ctaTarget", "_SELF")
                .build());

        aemContext.currentResource("/content/cta");
        fixture = aemContext.request().adaptTo(CTAModel.class);

    }

    @Test
    void getCtaText() {
        assertEquals("Text", fixture.getCtaText());
    }

    @Test
    void getCtaIcon() {
        assertEquals("/content/dam/asset1.jpg", fixture.getCtaIcon());
    }
    @Test
    void getCtaTarget() {
        assertEquals("_SELF", fixture.getCtaTarget());
    }
    @Test
    void getCtaUrl() {
        assertEquals("/content/airmiles.html", fixture.getCtaUrl());
    }
    @Test
    void getCtaType() {
        assertEquals("button", fixture.getCtaType());
    }


}