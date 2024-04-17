package com.aem.airmiles.web.core.models;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.aem.airmiles.web.core.services.MindfulSurveyService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.designer.Style;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.jetbrains.annotations.NotNull;

import javax.annotation.PostConstruct;

/**
 * The MindfulSurveyModel class represents a model class that provides methods to retrieve configuration attributes
 * related to mindful survey widget.
 *
 * @author pabpalac.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class MindfulSurveyModel {

    @Self
    private SlingHttpServletRequest request;

    /**
     * Page object representing the current page in the application.
     */
    @ScriptVariable
    private @NotNull Page currentPage;

    /**
     * Represents the current style object used for the MindfulSurveyModel.
     */
    @ScriptVariable
    private @NotNull Style currentStyle;

    /**
     * The mindful variable represents a service that provides configuration and retrieval methods for  mindful survey
     * widget.
     */
    @OSGiService
    private @NotNull MindfulSurveyService mindful;

    /**
     * Represents a private variable that holds a ContentFragment object.
     */
    private ContentFragment cf;

    /**
     * The lang variable represents the locale obtained from current page.
     */
    private String lang;

    /**
     * Initializes the MindfulSurveyModel object by setting the language and survey fragment.
     */
    @PostConstruct
    protected void init() {
        lang = currentPage.getLanguage().getLanguage();
        cf = mindful.getSurveyFragment(request, (String) currentStyle.getOrDefault("surveyCf", StringUtils.EMPTY));
    }

    /**
     * Retrieves the value of the "enabled" configuration attribute.
     *
     * @return The value of the "enabled" attribute.
     */
    public Boolean isEnabled() {
        return mindful.isEnabled();
    }

    /**
     * Retrieves the value of the charset/encoding configuration attribute.
     *
     * @return The value of the charset/encoding attribute.
     */
    public String getCharset() {
        return mindful.getCharset();
    }

    /**
     * Retrieves the value of the "scriptType" attribute.
     *
     * @return The value of the "scriptType" attribute.
     */
    public String getScriptType() {
        return mindful.getScriptType();
    }

    /**
     * Retrieves the value of the consent category.
     *
     * @return The value of the consent category.
     */
    public String getConsentCategory() {
        return mindful.getConsentCategory();
    }

    /**
     * Retrieves the value of the script source attribute.
     *
     * @return The value of the "scriptSrc" attribute.
     */
    public String getScriptSrc() {
        return mindful.getScriptSrc();
    }

    /**
     * Retrieves the landing URL for the survey.
     *
     * @return The URL as a String.
     */
    public String getSurveyLanding() {
        return mindful.getElementVariationContent(cf, "surveyUrl", lang);
    }

    /**
     * Retrieves the trigger type from the current style.
     *
     * @return the trigger type as a string
     */
    public String getTriggerType() {
        return mindful.getElementVariationContent(cf, "triggerType", lang);
    }

    /**
     * Retrieves the value of the "triggerTxt" attribute from the current style.
     *
     * @return The value of the "triggerTxt" attribute.
     */
    public String getTriggerTxt() {
        return mindful.getElementVariationContent(cf, "triggerText", lang);
    }

    /**
     * Retrieves the value of the "surveyPromptTextColor" configuration attribute.
     *
     * @return The value of the "surveyPromptTextColor" attribute.
     */
    public String getSurveyPromptTextColor() {
        return mindful.getElementVariationContent(cf, "surveyPromptTextColor", lang);
    }

    /**
     * Retrieves the value of the "surveyPromptBgColor" configuration attribute.
     *
     * @return The value of the "surveyPromptBgColor" attribute.
     */
    public String getSurveyPromptBgColor() {
        return mindful.getElementVariationContent(cf, "surveyPromptBgColor", lang);
    }

    /**
     * Retrieves the value of the "surveyPromptTxt" configuration attribute from the current style.
     *
     * @return the value of the "surveyPromptTxt" attribute.
     */
    public String getSurveyPromptTxt() {
        return mindful.getElementVariationContent(cf, "surveyPromptText", lang);
    }

    /**
     * Retrieves the value of the "surveyPromptBtnTxt" configuration attribute from the current style.
     *
     * @return the value of the "surveyPromptBtnTxt" attribute.
     */
    public String getSurveyPromptBtnTxt() {
        return mindful.getElementVariationContent(cf, "surveyButtonText", lang);
    }

    /**
     * Retrieves the value of the "surveyPromptRejectionTxt" configuration attribute.
     *
     * @return The value of the "surveyPromptRejectionTxt" attribute.
     */
    public String getSurveyPromptRejectionTxt() {
        return mindful.getElementVariationContent(cf, "surveyRejectionText", lang);
    }

    /**
     * Retrieves the locale based on the language of the current page.
     * If the language is "en", it returns "en-US". Otherwise, it returns "fr-CA".
     *
     * @return The locale string representing the language and country code.
     */
    public String getLocale() {
        return lang.equals("en") ? "en-US" : "fr-CA";
    }

    /**
     * Retrieves the timeout value from the mindful survey widget.
     *
     * @return The timeout value as an Integer.
     */
    public Integer getTimeout() {
        return mindful.getTimeout();
    }
}