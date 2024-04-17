package com.aem.airmiles.web.core.services.impl;

import com.aem.airmiles.web.core.services.RunModeService;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Set;

/**
 * The RunModeServiceImpl class is an implementation of the RunModeService interface.
 * It provides methods to retrieve the current run mode environment.
 *
 * @author pabpalac.
 */
@Component(immediate = true, service = RunModeService.class)
public class RunModeServiceImpl implements RunModeService {

    /**
     * References to sling setting service to import runmode variables.
     */
    @Reference
    private SlingSettingsService settings;

    /**
     * This method is called when the component is activated.
     * It initializes the OSGi service.
     */
    @Activate
    protected void activate() {
        // Inits OSGi service.
    }

    /**
     * Retrieves the environment value from the SlingSettingsService.
     *
     * @return the environment value.
     */
    @Override
    public String getEnvironment() {
        final String DEV = "dev";
        final String STG = "stage";
        final String PROD = "prod";

        final Set<String> runModes = settings.getRunModes();

        if (runModes.contains(PROD))
            return PROD;
        if (runModes.contains(DEV))
            return DEV;
        if (runModes.contains("rde"))
            return "qa";
        if (runModes.contains(STG))
            return STG;
        return "local";
    }
}
