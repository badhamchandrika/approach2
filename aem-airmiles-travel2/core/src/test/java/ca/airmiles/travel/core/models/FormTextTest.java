package ca.airmiles.travel.core.models;

import com.adobe.cq.wcm.core.components.models.form.Text;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.models.factory.ModelFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class FormTextTest {

    private final AemContext context = new AemContextBuilder(ResourceResolverType.JCR_MOCK).plugin(CORE_COMPONENTS).build();

    private FormText formText;

    @BeforeEach
    void setUp(final AemContext context) {
        context.load().json("/ca/airmiles/travel/core/models/FormTextTest.json", "/content/text");
        context.build().resource("/apps/aem-airmiles-travel2/components/form/text", "sling:resourceSuperType", "core/wcm/components/form/text/v2/text").commit();
        context.currentResource("/content/text");
    }

    @Test
    void test_icon() {
        assertNotNull(context.currentResource());
        formText = context.request().adaptTo(FormText.class);
        assertNotNull(formText);
        assertEquals("location-icon", formText.getIcon());
    }
}