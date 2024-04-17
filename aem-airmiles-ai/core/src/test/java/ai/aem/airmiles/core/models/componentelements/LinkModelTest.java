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
public class LinkModelTest {
    private  LinkModel fixture;


    @Getter
    @ValueMapValue
    private String linkText;

    @Getter
    @ValueMapValue
    private String linkURL;


    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses( LinkModel.class);
        aemContext.create().resource("/content/link", ImmutableMap.<String, Object>builder()
                .put("linkText", "Text")
                .put("linkURL", "/content/airmiles.html")
                .build());

        aemContext.currentResource("/content/link");
        fixture = aemContext.request().adaptTo( LinkModel.class);

    }

    @Test
    void getCtaText() {
        assertEquals("Text", fixture.getLinkText());
    }

    @Test
    void getCtaIcon() {
        assertEquals("/content/airmiles.html", fixture.getLinkURL());
    }



}