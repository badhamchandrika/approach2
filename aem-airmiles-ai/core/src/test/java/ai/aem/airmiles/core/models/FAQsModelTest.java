package ai.aem.airmiles.core.models;

import ai.aem.airmiles.core.models.componentelements.QuestionAnswerModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import ai.aem.airmiles.core.models.componentelements.QuestionAnswerModel;

import java.util.List;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class FAQsModelTest {

    private FAQsModel fixture;

    @Getter
    @ChildResource
    List<QuestionAnswerModel> questionsAnswers;

    @Getter
    @ValueMapValue
    private String moreInfoText;
   public final AemContext aemContext = new AemContextBuilder()
           .plugin(CORE_COMPONENTS)
           .plugin(CACONFIG)
           .<AemContext>afterSetUp(aemContext -> {}).build();



    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(FAQsModel.class);
        aemContext.load().fileVaultXml("/faqs.xml", "/content/faqs");
        aemContext.currentResource("/content/faqs");
        fixture = aemContext.request().adaptTo(FAQsModel.class);
    }


    @Test
    void getMoreInfoText() {
        assertEquals("Info Text 2", fixture.getMoreInfoText());
    }

    @Test
    void getQuestionsAnswers() {
        assertNotNull(fixture.getQuestionsAnswers());
    }
}