package ai.aem.airmiles.core.models;

import ai.aem.airmiles.core.models.componentelements.LinkModel;
import com.google.common.collect.ImmutableMap;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import lombok.Getter;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class FooterLinksModelTest {

    private FooterLinksModel fixture;

    @Getter
    @ChildResource
    private List<LinkModel> sites;
    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(FooterLinksModel.class);
        //  aemContext.load().fileVaultXml("/faqs.xml", "/content/footerLinksModel");
        aemContext.create().resource("/content/footerLinks", ImmutableMap.<String, Object>builder()
                .put("sites",
                        ImmutableMap.<String, Object>builder()
                                .put("item0", ImmutableMap.<String, Object>builder()
                                        .put("linkText", "link")
                                        .put("linkURL", "URL"))
                                .put("item1",ImmutableMap.<String, Object>builder()
                                        .put("linkText", "link")
                                        .put("linkURL", "URL"))
                )
                .build());

        aemContext.currentResource("/content/footerLinks");
        fixture = aemContext.request().adaptTo(FooterLinksModel.class);

    }

    @Test
    void getMoreInfoText() {
        assertEquals(0, fixture.getSites().size());
    }

}