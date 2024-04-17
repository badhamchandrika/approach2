package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.services.GenesysChatService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.jetbrains.annotations.NotNull;


/**
 * The GenesysChatModel class represents a model that interacts with the GenesysChatService to retrieve and provide
 * configuration values for the Genesys chat service.
 *
 * @author pabpalac.
 */
@Model(adaptables = Resource.class)
public class GenesysChatModel {

	/**
	 * The genesys variable represents the GenesysChatService instance used for configuring and providing the necessary
     * settings for the Genesys chat service.
	 */
    @OSGiService
    private @NotNull GenesysChatService genesys;

    /**
     * Retrieves the value of the "enabled" configuration attribute.
     *
     * @return The value of the "enabled" attribute.
     */
    public Boolean isEnabled() {
        return genesys.isEnabled();
    }

    /**
     * Retrieves the value of the charset/encoding configuration attribute.
     *
     * @return The value of the charset/encoding attribute.
     */
    public String getCharset() {
        return genesys.getCharset();
    }

    /**
     * Retrieves the value of the "scriptType" attribute from the GenesysChatService class.
     *
     * @return The value of the "scriptType" attribute.
     */
    public String getScriptType() {
        return genesys.getScriptType();
    }

    /**
     * Retrieves the value of the consent category from the GenesysChatService class.
     *
     * @return The value of the consent category.
     */
    public String getConsentCategory() {
        return genesys.getConsentCategory();
    }

    /**
     * Retrieves the value of the script source attribute from the GenesysChatService class.
     *
     * @return The value of the "scriptSrc" attribute.
     */
    public String getScriptSrc() {
        return genesys.getScriptSrc();
    }

    /**
     * Retrieves the value of the "environmentId" attribute from the GenesysChatService class.
     *
     * @return The value of the "environmentId" attribute.
     */
    public String getEnvironmentId() {
        return genesys.getEnvironmentId();
    }

    /**
     * Retrieves the value of the "deploymentId" attribute from the GenesysChatService class.
     *
     * @return The value of the "deploymentId" attribute.
     */
    public String getDeploymentId() {
        return genesys.getDeploymentId();
    }
}