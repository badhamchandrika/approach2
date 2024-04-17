package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.services.BaseConfigurationService;
import com.aem.airmiles.web.core.services.RunModeService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

/**
 * Sling model used to expose endpoints from OSGi service.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class BasePageModel {

    /**
     * Configuration service containing all endpoints.
     */
    @OSGiService
    private BaseConfigurationService configurationService;

    /**
     * Service used to determine run modes.
     */
    @OSGiService
    private RunModeService runModes;

    /**
     * Configured generic API URL.
     *
     * @return API URL.
     */
    public String getApiUrl() {
        return this.configurationService.getApiUrl();
    }

    /**
     * Configured DOM BFF API URL.
     *
     * @return DOM BFF API URL.
     */
    public String getDomBFFUrl() {
        return this.configurationService.getDomBFFUrl();
    }

    /**
     * Retrieves the configured DOM BFF API URL for Offers.
     *
     * @return The DOM BFF API URL for Offers.
     */
    public String getDomBFFOffersUrl() {
        return this.configurationService.getDomBFFOffersUrl();
    }

    /**
     * Determines whether is productive or non-productive environment based on run mode.
     *
     * @return {@code true} if PRODUCTION and {@code false} when non-production.
     */
    public boolean isProduction() {
        return getEnvironment().equals("prod");
    }

    /**
     * Retrieves the environment value from the RunModeService class.
     *
     * @return the environment value.
     */
    public String getEnvironment() {
        return runModes.getEnvironment();
    }
}