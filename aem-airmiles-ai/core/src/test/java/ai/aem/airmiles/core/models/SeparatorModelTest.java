package ai.aem.airmiles.core.models;

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
public class SeparatorModelTest {

    private SeparatorModel fixture;

    @Getter
    @ValueMapValue
    private String text;

    @Getter
    @ValueMapValue
    private String color;

    @Getter
    @ValueMapValue
    private String icon;


    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(SeparatorModel.class);
        aemContext.create().resource("/content/quotecard", ImmutableMap.<String, Object>builder()
                .put("text", "Text")
                .put("color", "#000")
                .put("icon", "/content/dam/asset1.jpg")
                .build());
        aemContext.currentResource("/content/quotecard");
        fixture = aemContext.request().adaptTo(SeparatorModel.class);
    }


    @Test
    void getText() {
        assertEquals("Text", fixture.getText());
    }

    @Test
    void getIcon() {
        assertEquals("/content/dam/asset1.jpg", fixture.getIcon());
    }

    @Test
    void getColor() {
        assertEquals("#000", fixture.getColor());
    }
}


