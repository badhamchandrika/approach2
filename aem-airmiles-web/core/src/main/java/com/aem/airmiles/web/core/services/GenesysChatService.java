package com.aem.airmiles.web.core.services;

/**
 * The GenesysChatService class is responsible for configuring and providing the necessary settings for the Genesys
 * chat service.
 *
 * @author pabpalac.
 */
public interface GenesysChatService {

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
     * Retrieves the value of the "scriptType" attribute from the GenesysChatService class.
     *
     * @return the value of the "scriptType" attribute.
     */
    String getScriptType();

    /**
     * Retrieves the value of the consent category from the GenesysChatService class.
     *
     * @return the value of the consent category
     */
    String getConsentCategory();

    /**
     * Retrieves the value of the script source attribute from the GenesysChatService class.
     *
     * @return the value of the "scriptSrc" attribute.
     */
    String getScriptSrc();

    /**
     * Retrieves the value of the "environmentId" attribute from the GenesysChatService class.
     *
     * @return the value of the "environmentId" attribute.
     */
    String getEnvironmentId();

    /**
     * Retrieves the value of the "deploymentId" attribute from the GenesysChatService class.
     *
     * @return the value of the "deploymentId" attribute.
     */
    String getDeploymentId();

}
