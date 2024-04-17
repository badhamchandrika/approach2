package com.aem.airmiles.web.core.services.impl;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentVariation;
import com.aem.airmiles.web.core.services.MindfulSurveyService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.osgi.service.metatype.annotations.AttributeType.BOOLEAN;
import static org.osgi.service.metatype.annotations.AttributeType.INTEGER;
import static org.osgi.service.metatype.annotations.AttributeType.STRING;

/**
 * The GenesysChatService class is responsible for configuring and providing the necessary settings for the Genesys
 * chat service. It retrieves the configuration values from the GenesysChatConfiguration class and initializes the chat
 * service accordingly.
 *
 * @author pabpalac.
 */
@Designate(ocd = MindfulSurveyServiceImpl.Config.class)
@Component(immediate = true, service = MindfulSurveyService.class)
public class MindfulSurveyServiceImpl implements MindfulSurveyService {

    /**
     * The enabled variable represents whether survey is enabled or not.
     */
    private Boolean enabled;

    /**
     * The charset/encoding used for the widget.
     */
    private String charset;

    /**
     * The scriptType variable represents the type of script used for the widget.
     * It determines the behavior and functionality of the widget.
     * The supported script types are:
     * <ol>
     *     <li>JavaScript: Represented by the value "application/javascript"</li>
     *     <li>Plain text: Represented by the value "text/plain"</li>
     * </ol>
     */
    private String scriptType;

    /**
     * Represents the consent category for the integration.<br>
     * The consent category is used to determine the category of consent for the widget.<br>
     * The supported consent categories are:
     * <ul>
     *     <li>C0001: Represented by the value "optanon-category-C0001"</li>
     *     <li>C0002: Represented by the value "optanon-category-C0002"</li>
     *     <li>C0003: Represented by the value "optanon-category-C0003"</li>
     *     <li>C0004: Represented by the value "optanon-category-C0004"</li>
     *     <li>C0005: Represented by the value "optanon-category-C0005"</li>
     *     <li>VID1: Represented by the value "optanon-category-C0003"</li>
     *     <li>None: Represented by an empty string</li>
     * </ul>
     *
     * @see <a href="https://my.onetrust.com/articles/en_US/Knowledge/UUID-8102e851-d860-d465-d8d6-b1d636d68eb9">
     * OneTrust documentation
     * </a>
     */
    private String consentCategory;

    /**
     * The script source code can be any valid JavaScript code or plain text.
     */
    private String scriptSrc;

    /**
     * Timeout represents the timeout value for a specific operation.
     */
    private Integer timeout;

    /**
     * Activates the Survey Service with the given configuration.
     *
     * @param config the configuration for the survey Service.
     */
    @Activate
    protected void activate(final @NotNull Config config) {
        this.enabled = config.enabled();
        this.charset = config.charset();
        this.scriptType = config.scriptType();
        this.consentCategory = config.consentCategory();
        this.scriptSrc = config.scriptSrc();
        this.timeout = config.timeout();
    }

    /**
     * Retrieves the value of the "enabled" configuration attribute.
     *
     * @return the value of the "enabled" attribute
     */
    @Override
    public Boolean isEnabled() {
        return enabled;
    }

    /**
     * Retrieves the value of the charset/encoding configuration attribute.
     *
     * @return the value of the charset/encoding attribute.
     */
    @Override
    public String getCharset() {
        return charset;
    }

    /**
     * Retrieves the value of the "scriptType" attribute from the widget.
     *
     * @return the value of the "scriptType" attribute.
     */
    @Override
    public String getScriptType() {
        return scriptType;
    }

    /**
     * Retrieves the value of the consent category.
     *
     * @return the value of the consent category
     */
    @Override
    public String getConsentCategory() {
        return consentCategory;
    }

    /**
     * Retrieves the value of the "scriptSrc" attribute from the class.
     *
     * @return the value of the "scriptSrc" attribute.
     */
    @Override
    public String getScriptSrc() {
        return scriptSrc;
    }

    /**
     * Retrieves the survey fragment from the Content Fragment specified by the given path.
     *
     * @param request the SlingHttpServletRequest object.
     * @param cfPath  the path of the Content Fragment.
     * @return the ContentFragment object representing the survey fragment, or null if the specified Content Fragment
     * does not exist.
     */
    @Override
    public @Nullable ContentFragment getSurveyFragment(
            final @NotNull SlingHttpServletRequest request,
            final @NotNull String cfPath
    ) {
        final ResourceResolver resourceResolver = request.getResourceResolver();
        final Resource cfResource = resourceResolver.getResource(cfPath);
        if (null == cfResource) return null;

        return cfResource.adaptTo(ContentFragment.class);
    }

    /**
     * Retrieves the content of a specific element variation from a given Content Fragment.
     *
     * @param cf        The Content Fragment from which to retrieve the element variation content.
     * @param name      The name of the element.
     * @param variation The variation of the element.
     * @return The content of the specified element variation as a String. Returns an empty string if the Content
     * Fragment, element, or variation is not found.
     */
    @Override
    public String getElementVariationContent(
            final @NotNull ContentFragment cf,
            final @NotNull String name,
            final @NotNull String variation
    ) {
        final ContentElement ce = cf.getElement(name);
        if (null == ce) return StringUtils.EMPTY;

        final ContentVariation cv = ce.getVariation(variation);
        if (null == cv) return StringUtils.EMPTY;

        return cv.getContent();
    }

    /**
     * Retrieves the value of the "timeout" configuration attribute.
     *
     * @return the value of the "timeout" attribute.
     */
    @Override
    public Integer getTimeout(){
        return timeout;
    }

    /**
     * Annotation class for Mindful survey configurations. It provides methods for getting various Mindful survey settings
     * such as enabled status, charset/encoding, script type, consent category, and script source.
     *
     * @author pabpalac.
     */
    @ObjectClassDefinition(
            name = "Mindful survey Configurations for Airmiles Web",
            description = "Provides the methods for getting Mindful survey settings."
    )
    public @interface Config {

        /**
         * Retrieves the current value of the "enabled" configuration attribute.
         *
         * @return true if the survey is enabled, false otherwise.
         */
        @AttributeDefinition(
                name = "Is mindful survey enabled?",
                description = "States whether survey is enabled or not.",
                type = BOOLEAN
        )
        boolean enabled() default false;

        /**
         * Retrieves the value of the charset/encoding configuration attribute.
         *
         * @return the value of the charset/encoding attribute.
         */
        @AttributeDefinition(
                name = "Charset/Encoding",
                description = "Select the proper charset/encoding for this widget.",
                options = {@Option(label = "UTF-8", value = "utf-8")}
        )
        String charset() default "utf-8";

        /**
         * Retrieves the value of the "scriptType" configuration attribute.
         *
         * @return the selected script type for the widget.
         */
        @AttributeDefinition(
                name = "Script type",
                description = "Select the proper script type for this widget.",
                options = {
                        @Option(label = "JavaScript", value = "text/javascript"),
                        @Option(label = "Plain text", value = "text/plain")
                }
        )
        String scriptType() default "text/javascript";

        /**
         * Retrieves the value of the consent category configuration attribute.
         *
         * @return the value of the consent category attribute.
         */
        @AttributeDefinition(
                name = "Consent category",
                description = "Select consent category:",
                options = {
                        @Option(label = "C0001", value = "optanon-category-C0001"),
                        @Option(label = "C0002", value = "optanon-category-C0002"),
                        @Option(label = "C0003", value = "optanon-category-C0003"),
                        @Option(label = "C0004", value = "optanon-category-C0004"),
                        @Option(label = "C0005", value = "optanon-category-C0005"),
                        @Option(label = "VID1", value = "optanon-category-C0003"),
                        @Option(label = "None", value = EMPTY),

                }
        )
        String consentCategory() default EMPTY;

        /**
         * Retrieves the value of the "scriptSrc" attribute from the class.
         *
         * @return the value of the "scriptSrc" attribute.
         */
        @AttributeDefinition(
                name = "Script source",
                description = "Provide the widget script source code.",
                type = STRING
        )
        String scriptSrc();

        /**
         * Retrieves the value of the "timeout" configuration attribute.
         *
         * @return the value of the "timeout" attribute.
         */
        @AttributeDefinition(
                name = "Timeout",
                description = "Provide the widget timeout.",
                type = INTEGER
        )
        int timeout();
    }
}