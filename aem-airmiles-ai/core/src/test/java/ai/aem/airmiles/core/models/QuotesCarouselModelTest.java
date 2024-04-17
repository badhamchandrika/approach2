package ai.aem.airmiles.core.models;

import com.adobe.cq.wcm.core.components.models.Carousel;
import com.google.common.collect.ImmutableMap;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import lombok.experimental.Delegate;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class QuotesCarouselModelTest{

    private QuotesCarouselModel fixture;

    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();

    @Self
    @Via(type = ResourceSuperType.class)
    @Delegate(types = Carousel.class)
    private Carousel delegate;

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(QuotesCarouselModel.class);
        aemContext.addModelsForClasses(Carousel.class);
        aemContext.create().resource("/content/quotecar", ImmutableMap.<String, Object>builder()
                .put("sling:resourceType", "airmiles-ai/components/quotescarousel")
                .build());
        aemContext.currentResource("/content/quotecar");
        fixture = aemContext.request().adaptTo(QuotesCarouselModel.class);
    }

    @Test
    void getDisplayText() {
        assertNull(delegate);
    }

}