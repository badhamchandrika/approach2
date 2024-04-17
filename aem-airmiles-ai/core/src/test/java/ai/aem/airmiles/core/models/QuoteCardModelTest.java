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
public class QuoteCardModelTest {

    private QuoteCardModel fixture;

    @Getter
    @ValueMapValue
    private String headerText;

    @Getter
    @ValueMapValue
    private String midText;

    @Getter
    @ValueMapValue
    private String image;

    @Getter
    @ValueMapValue
    private String collabName;

    @Getter
    @ValueMapValue
    private String collabJob;

    @Getter
    @ValueMapValue
    private String collabSection;

    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(QuoteCardModel.class);
        aemContext.create().resource("/content/quotecard", ImmutableMap.<String, Object>builder()
                .put("headerText", "Header Text")
                .put("midText", "Mid Text")
                .put("image", "/content/dam/asset1.jpg")
                .put("collabName", "Collab")
                .put("collabJob", "Collab Job")
                .put("collabSection", "Collab Section")
                .build());
        aemContext.currentResource("/content/quotecard");
        fixture = aemContext.request().adaptTo(QuoteCardModel.class);
    }


    @Test
    void getHeaderText() {
        assertEquals("Header Text", fixture.getHeaderText());
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
    void getCollabJob() {
        assertEquals("Collab Job", fixture.getCollabJob());
    }

    @Test
    void getCollabName() {
        assertEquals("Collab", fixture.getCollabName());
    }

    @Test
    void getCollabSection() {
        assertEquals("Collab Section", fixture.getCollabSection());
    }
}

