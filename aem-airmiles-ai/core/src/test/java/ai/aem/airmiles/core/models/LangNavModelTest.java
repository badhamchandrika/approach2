package ai.aem.airmiles.core.models;

import ai.aem.airmiles.core.models.componentelements.CTAModel;
import com.google.common.collect.ImmutableMap;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import lombok.Getter;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class LangNavModelTest {

    private LangNavModel fixture;

    @Inject
    private ValueMap pageProperties;

    @Getter
    @ValueMapValue
    private String icon;

    @Getter
    private String displayText;
    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(LangNavModel.class);
        aemContext.create().resource("/content/lang-nav", ImmutableMap.<String, Object>builder()
                .put("sling:resourceType", "airmiles-ai/components/langnav")
                .put("displayText", "ENG")
                .build());

        aemContext.currentResource("/content/lang-nav");
        fixture = aemContext.request().adaptTo(LangNavModel.class);

    }

    @Test
    void getDisplayText() {
        assertNull( fixture.getDisplayText());
    }


    @Test
    void getIcon() {
        assertEquals("am-icon-globe", fixture.getIcon());
    }

}