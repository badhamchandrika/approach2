package com.aem.airmiles.web.core.services;

import com.adobe.cq.dam.cfm.ContentFragment;
import org.apache.sling.api.SlingHttpServletRequest;

/**
 * The MindfulSurveyService class represents a service that provides configuration and retrieval methods
 * for a mindful survey widget.
 *
 * @author pabpalac.
 */
public interface MindfulSurveyService {

    /**
     * Retrieves the value of the "enabled" configuration attribute.
     *
     * @return the value of the "enabled" attribute
     */
    Boolean isEnabled();

    /**
     * Retrieves the value of the charset/encoding configuration attribute.
     *
     * @return the value of the charset/encoding attribute.
     */
    String getCharset();

    /**
     * Retrieves the value of the "scriptType" attribute from the widget.
     *
     * @return the value of the "scriptType" attribute.
     */
    String getScriptType();

    /**
     * Retrieves the value of the consent category.
     *
     * @return the value of the consent category
     */
    String getConsentCategory();

    /**
     * Retrieves the value of the script source attribute.
     *
     * @return the value of the "scriptSrc" attribute.
     */
    String getScriptSrc();

    /**
     * Retrieves the survey fragment from the Content Fragment specified by the given path.
     *
     * @param request the SlingHttpServletRequest object.
     * @param cfPath  the path of the Content Fragment.
     * @return the ContentFragment object representing the survey fragment.
     */
    ContentFragment getSurveyFragment(SlingHttpServletRequest request, String cfPath);

    /**
     * Retrieves the content of a specific element variation from a given Content Fragment.
     *
     * @param cf        The Content Fragment from which to retrieve the element variation content.
     * @param name      The name of the element.
     * @param variation The variation of the element.
     * @return The content of the specified element variation as a String.
     */
    String getElementVariationContent(ContentFragment cf, String name, String variation);

    /**
     * Retrieves the timeout value from the mindful survey widget.
     *
     * @return The timeout value as a String.
     */
    Integer getTimeout();
}
