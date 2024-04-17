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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class TabSolutionBlockModelTest {
    private  TabSolutionBlockModel fixture;

    @Getter
    @ValueMapValue
    private String title;

    @Getter
    @ValueMapValue
    private String body;

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
        aemContext.addModelsForClasses( TabSolutionBlockModel.class);
        aemContext.create().resource("/content/tabsolution", ImmutableMap.<String, Object>builder()
                .put("title", "Title")
                .put("body", "Body")
                .build());

        aemContext.currentResource("/content/tabsolution");
        fixture = aemContext.request().adaptTo( TabSolutionBlockModel.class);

    }

    @Test
    void getTitle() {
        assertEquals("Title", fixture.getTitle());
    }

    @Test
    void getBody() {
        assertEquals("Body", fixture.getBody());
    }

    @Test
    void getCta() {
        assertNull(fixture.getCta());
    }



}