package com.aem.airmiles.web.core.services.impl;

import com.aem.airmiles.web.core.services.GenesysChatService;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.osgi.service.metatype.annotations.AttributeType.BOOLEAN;
import static org.osgi.service.metatype.annotations.AttributeType.STRING;

/**
 * The GenesysChatService class is responsible for configuring and providing the necessary settings for the Genesys
 * chat service. It retrieves the configuration values from the GenesysChatConfiguration class and initializes the chat
 * service accordingly.
 *
 * @author pabpalac.
 */
@Designate(ocd = GenesysChatServiceImpl.Config.class)
@Component(immediate = true, service = GenesysChatService.class)
public class GenesysChatServiceImpl implements GenesysChatService{

    /**
     * The enabled variable represents whether Genesys chat is enabled or not.
     */
    private Boolean enabled;

    /**
     * The charset/encoding used for the widget.
     */
    private String charset;

    /**
     * The scriptType variable represents the type of script used for the Genesys widget.
     * It determines the behavior and functionality of the widget.
     * The supported script types are:
     * <ol>
     *     <li>JavaScript: Represented by the value "application/javascript"</li>
     *     <li>Plain text: Represented by the value "text/plain"</li>
     * </ol>
     * This variable is part of the GenesysChatService class and is used to configure
     * the Genesys chat settings.
     *
     * @see GenesysChatService
     * @see Config
     */
    private String scriptType;

    /**
     * Represents the consent category for Genesys Chat.<br>
     * The consent category is used to determine the category of consent for the Genesys chat widget.<br>
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
     *          OneTrust documentation
     *      </a>
     */
    private String consentCategory;

    /**
     * The script source code can be any valid JavaScript code or plain text.
     */
    private String scriptSrc;

    /**
     * The environmentId variable represents the Genesys environment identifier string.
     * It is used to identify the specific Genesys environment that the chat widget is configured for.
     */
    private String environmentId;

    /**
     * Represents the deployment ID for Genesys Chat configuration.
     * It is a string value used to identify the Genesys deployment.
     */
    private String deploymentId;

    /**
     * Activates the Genesys Chat Service with the given configuration.
     *
     * @param config the configuration for the Genesys Chat Service
     */
    @Activate
    protected void activate(final @NotNull Config config) {
        this.enabled = config.enabled();
        this.charset = config.charset();
        this.scriptType = config.scriptType();
        this.consentCategory = config.consentCategory();
        this.scriptSrc = config.scriptSrc();
        this.environmentId = config.environmentId();
        this.deploymentId = config.deploymentId();
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
     * Retrieves the value of the "scriptType" attribute from the GenesysChatService class.
     *
     * @return the value of the "scriptType" attribute.
     */
    @Override
    public String getScriptType() {
        return scriptType;
    }

    /**
     * Retrieves the value of the consent category from the GenesysChatService class.
     *
     * @return the value of the consent category
     */
    @Override
    public String getConsentCategory() {
        return consentCategory;
    }

    /**
     * Retrieves the value of the script source attribute from the GenesysChatService class.
     *
     * @return the value of the "scriptSrc" attribute.
     */
    @Override
    public String getScriptSrc() {
        return scriptSrc;
    }

    /**
     * Retrieves the value of the "environmentId" attribute from the GenesysChatService class.
     *
     * @return the value of the "environmentId" attribute.
     */
    @Override
    public String getEnvironmentId() {
        return environmentId;
    }

    /**
     * Retrieves the value of the "deploymentId" attribute from the GenesysChatService class.
     *
     * @return the value of the "deploymentId" attribute.
     */
    @Override
    public String getDeploymentId() {
        return deploymentId;
    }

    /**
     * This annotation class provides the configuration options for Genesys Chat.
     * <br>
     * It includes attributes for enabling chat, setting the encoding/charset,
     * selecting the script type, defining the consent category, providing the
     * script source, environment id, and deployment id.
     *
     * @author pabpalac.
     */
    @ObjectClassDefinition(
            name = "Genesys Chat Configurations for Airmiles Web",
            description = "Provides the methods for getting Genesys Chat settings."
    )
    public @interface Config {

        /**
         * Retrieves the value of the "enabled" configuration attribute.
         *
         * @return the value of the "enabled" attribute.
         */
        @AttributeDefinition(
                name = "Is chat enabled?",
                description = "States whether genesys chat is enabled or not.",
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
         * Retrieves the value of the "scriptSrc" attribute from the GenesysChatConfiguration class.
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
         * Retrieves the value of the environment identifier string.
         *
         * @return the value of the environment identifier string.
         */
        @AttributeDefinition(
                name = "Environment id.",
                description = "Provide Genesys environment identifier string.",
                type = STRING
        )
        String environmentId();

        /**
         * Retrieves the value of the "deploymentId" attribute from the GenesysChatConfiguration class.
         *
         * @return the value of the "deploymentId" attribute.
         */
        @AttributeDefinition(
                name = "Deployment id.",
                description = "Provide Genesys deployment identifier string.",
                type = STRING
        )
        String deploymentId();
    }
}