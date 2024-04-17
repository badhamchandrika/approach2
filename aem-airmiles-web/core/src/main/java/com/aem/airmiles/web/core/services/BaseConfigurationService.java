package com.aem.airmiles.web.core.services;

/**
 * The BaseConfigurationsService declares the api retrieval methods that will be implemented.
 */
public interface BaseConfigurationService {

    /**
     * Configured Generic API URL.
     *
     * @return API URL.
     */
    String getApiUrl();

    /**
     * Configured DOM BFF API URL.
     *
     * @return DOM BFF API.
     */
    String getDomBFFUrl();

    /**
     * Configured DOM BFF API URL for Offers
     * @return
     */
    String getDomBFFOffersUrl();
}
