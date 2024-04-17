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
public class QuestionAnswerModelTest {
    private  QuestionAnswerModel fixture;

    @Getter
    @ValueMapValue
    private String question;

    @Getter
    @ValueMapValue
    private String answer;



    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();


    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses( QuestionAnswerModel.class);
        aemContext.create().resource("/content/questionanswer", ImmutableMap.<String, Object>builder()
                .put("question", "question 1")
                .put("answer", "answer 1")
                .build());

        aemContext.currentResource("/content/questionanswer");
        fixture = aemContext.request().adaptTo( QuestionAnswerModel.class);

    }

    @Test
    void getCtaText() {
        assertEquals("question 1", fixture.getQuestion());
    }

    @Test
    void getCtaIcon() {
        assertEquals("answer 1", fixture.getAnswer());
    }



}