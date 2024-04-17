package com.aem.airmiles.web.core.models;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.aem.airmiles.web.core.services.MindfulSurveyService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.designer.Style;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.Locale;

import static junitx.util.PrivateAccessor.setField;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class used to cover MindfulSurveyModel class. It tests the functionality of the methods in the
 * MindfulSurveyModel class by mocking the GenesysChatService class and setting up the necessary configuration values.
 *
 * @author pabpalac.
 */
@ExtendWith(MockitoExtension.class)
class MindfulSurveyModelTest {

    /**
     * Values to be retrieved by the service.
     */
    private static final String
            UTF_8 = "utf-8",
            PLAIN_TEXT = "plain/text",
            C0002 = "C0002",
            FAKE_SCRIPT = "https://something.com/fake.js",
            SURVEY_CF = "/content/dam/aem-airmiles-web/cf/mindful.zip",
            SURVEY_LANDING = "https://something.com/survey",
            TRIGGER_TYPE = "text",
            TRIGGER_TEXT = "Take survey?",
            TEXT_COLOR = "blue",
            BG_COLOR = "white",
            PROMPT_TXT = "Please take the survey",
            BTN_TXT = "OK",
            NO_TXT = "NOPE",
            EN = "en";

    /**
     * Represents the timeout value for the mindful survey widget.
     * The value is specified in milliseconds.
     */
    private static final int TIMEOUT = 500;

    /**
     * The MindfulSurveyModel class represents a model class that provides methods to retrieve configuration attributes
     * related to mindful survey widget.
     */
    private static MindfulSurveyModel model;

    /**
     * Simulates Genesys Chat service activation with the mocked configuration.
     */
    @BeforeAll
    static void buildModel() {
        final MindfulSurveyService svc = mock(MindfulSurveyService.class);
        final Page page = mock(Page.class);
        final Style style = mock(Style.class);
        final ContentFragment cf = mock(ContentFragment.class);
        when(svc.isEnabled()).thenReturn(true);
        when(svc.getCharset()).thenReturn(UTF_8);
        when(svc.getScriptType()).thenReturn(PLAIN_TEXT);
        when(svc.getConsentCategory()).thenReturn(C0002);
        when(svc.getScriptSrc()).thenReturn(FAKE_SCRIPT);
        when(svc.getSurveyFragment(any(), any())).thenReturn(cf);
        when(svc.getTimeout()).thenReturn(TIMEOUT);

        when(page.getLanguage()).thenReturn(new Locale(EN));

        when(style.get("surveyCf", String.class)).thenReturn(SURVEY_CF);

        when(svc.getElementVariationContent(any(), eq("surveyUrl"), any(String.class)))
                .thenReturn(SURVEY_LANDING);
        when(svc.getElementVariationContent(any(), eq("triggerType"), any(String.class)))
                .thenReturn(TRIGGER_TYPE);
        when(svc.getElementVariationContent(any(), eq("triggerText"), any(String.class)))
                .thenReturn(TRIGGER_TEXT);
        when(svc.getElementVariationContent(any(), eq("surveyPromptTextColor"), any(String.class)))
                .thenReturn(TEXT_COLOR);
        when(svc.getElementVariationContent(any(), eq("surveyPromptBgColor"), any(String.class)))
                .thenReturn(BG_COLOR);
        when(svc.getElementVariationContent(any(), eq("surveyPromptText"), any(String.class)))
                .thenReturn(PROMPT_TXT);
        when(svc.getElementVariationContent(any(), eq("surveyButtonText"), any(String.class)))
                .thenReturn(BTN_TXT);
        when(svc.getElementVariationContent(any(), eq("surveyRejectionText"), any(String.class)))
                .thenReturn(NO_TXT);

        model = new MindfulSurveyModel();

        assertDoesNotThrow(() -> {
            final Method mtd = model.getClass().getDeclaredMethod("init");
            mtd.setAccessible(true);
            setField(model, "mindful", svc);
            setField(model, "currentPage", page);
            setField(model, "currentStyle", style);
            mtd.invoke(model);
        });
    }

    /**
     * Ensures that the service is enabled.
     */
    @Test
    void isEnabled() {
        assertTrue(model.isEnabled());
    }

    /**
     * Ensures that the charset is UTF-8.
     */
    @Test
    void getCharset() {
        assertEquals(UTF_8, model.getCharset());
    }

    /**
     * Ensures that the script type is JavaScript.
     */
    @Test
    void getScriptType() {
        assertEquals(PLAIN_TEXT, model.getScriptType());
    }

    /**
     * Ensures that OneTrust Cookie Category is C0002.
     */
    @Test
    void getConsentCategory() {
        assertEquals(C0002, model.getConsentCategory());
    }

    /**
     * Ensures that the script source is the provided one.
     */
    @Test
    void getScriptSrc() {
        assertEquals(FAKE_SCRIPT, model.getScriptSrc());
    }

    /**
     * Ensures that the survey landing is the provided one.
     */
    @Test
    void getSurveyLanding() {
        assertEquals(SURVEY_LANDING, model.getSurveyLanding());
    }

    /**
     * Ensures that the trigger type is the provided one.
     */
    @Test
    void getTriggerType() {
        assertEquals(TRIGGER_TYPE, model.getTriggerType());
    }

    /**
     * Ensures that the trigger text is the provided one.
     */
    @Test
    void getTriggerTxt() {
        assertEquals(TRIGGER_TEXT, model.getTriggerTxt());
    }

    /**
     * Ensures that the text color is the provided one.
     */
    @Test
    void getTxtColor() {
        assertEquals(TEXT_COLOR, model.getSurveyPromptTextColor());
    }

    /**
     * Ensures that the background color is the provided one.
     */
    @Test
    void getBgColor() {
        assertEquals(BG_COLOR, model.getSurveyPromptBgColor());
    }

    /**
     * Ensures that the prompt text is the provided one.
     */
    @Test
    void getPromptTxt() {
        assertEquals(PROMPT_TXT, model.getSurveyPromptTxt());
    }

    /**
     * Ensures that the button text is the provided one.
     */
    @Test
    void getBtnTxt() {
        assertEquals(BTN_TXT, model.getSurveyPromptBtnTxt());
    }

    /**
     * Ensures that the rejection text is the provided one.
     */
    @Test
    void getRejectionTxt() {
        assertEquals(NO_TXT, model.getSurveyPromptRejectionTxt());
    }

    /**
     * Ensures that the locale is built properly.
     */
    @Test
    void getLocale() {
        assertEquals("en-US", model.getLocale());
    }

    /**
     * Ensures the timeout is the provided one.
     */
    @Test
    void getTimeout() {
        assertEquals(TIMEOUT, model.getTimeout());
    }
}