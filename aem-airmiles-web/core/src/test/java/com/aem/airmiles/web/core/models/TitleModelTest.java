package com.aem.airmiles.web.core.models;

import com.adobe.cq.wcm.core.components.testing.MockExternalizerFactory;
import com.day.cq.commons.Externalizer;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TitleModelTest {

    String base = "/content/page";

    String base_page = base + "/jcr:content/root";

    public final AemContext aemctx = new AemContextBuilder()
            .beforeSetUp(context -> {
                context.registerService(Externalizer.class, MockExternalizerFactory.getExternalizerService());
            })
            .plugin(CORE_COMPONENTS)
            .build();

    @BeforeEach
    public void setUp() {
        aemctx.load().json(
                "/com.aem.airmiles.web.core/TitleComponentTestDefinition.json",
                "/apps/aem-airmiles-web/components/airmiles-core/common/title/v1/title");
        aemctx.load().json(
                "/com.aem.airmiles.web.core/TitleModelTest.json",
                base);
    }

    @Test
    void testValidTitle() {
        aemctx.currentResource(base_page + "/titleValid");
        TitleModel titleModel = aemctx.request().adaptTo(TitleModel.class);
        String expectedText = "Test Title Valid";
        String expectedLinkTitleAttribute = "bold";
        String expectedActive = "true";
        String expectedId = "testId";
        String expectedType = "h2";
        String expectedLinkURL = "/content/aem-airmiles-web/language-masters/en/search";
        String expectedLinkAccessibilityLabel = "testLabelId";
        String expectedTitleType = "wayfinder";
        assertNotNull(titleModel);
        assertEquals(expectedType, titleModel.getType());
        assertEquals(expectedText, titleModel.getText());
       assertEquals(expectedActive, titleModel.getActive());
        assertNull(titleModel.getData());
        assertNull(titleModel.getAppliedCssClasses());
        assertNotNull(titleModel.getExportedType());
        titleModel.isLinkDisabled();
        titleModel.setBgcolor("black");
        titleModel.setTextAlign("left");
        assertNotNull(titleModel.getBgcolor());
        assertNotNull(titleModel.getTextAlign());
        assertEquals(expectedTitleType, titleModel.getTitleType());
    }

    @Test
    void testNoLinkTitle() {
        aemctx.currentResource(base_page + "/titleNoLink");
        TitleModel titleModel = aemctx.request().adaptTo(TitleModel.class);
        assertNotNull(titleModel);
        assertNull(titleModel.getLink());
    }

    @Test
    void testNoAnalyticsNoValidTitle() {
        aemctx.currentResource(base_page + "/titleAnalyticsNoValid");
        assertNotNull(aemctx.request().adaptTo(TitleModel.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/titleNull", "/titleNoMasterFields", "/titleNoActive"})
    void titleCases(final String titleSource) {
        aemctx.currentResource(base_page + titleSource);
        final TitleModel titleModel = aemctx.request().adaptTo(TitleModel.class);
        assertNotNull(titleModel);
        assertNull(titleModel.getActive());
    }

    @Test
    void changeTitle(){
        final TitleModel model = new TitleModel(){{
            setTitleType("default");
        }};
        assertNotNull(model);
        assertEquals("default", model.getTitleType());

        model.setTitleType("custom");
        assertNotEquals("default", model.getTitleType());
    }
}
