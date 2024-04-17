package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.services.RunModeService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

/**
 * The RunModeModel class represents a Sling Model that provides the current run mode environment.
 *
 * @author pabpalac.
 */
@Model(adaptables = SlingHttpServletRequest.class)
public class RunModeModel {

    /**
     * The runModes variable represents a service that provides methods to retrieve the current run mode environment.
     */
    @OSGiService
    private RunModeService runModes;

    /**
     * Returns the current run mode environment.
     *
     * @return the current run mode environment as a String.
     */
    public String getEnvironment() {
        return runModes.getEnvironment();
    }
}
