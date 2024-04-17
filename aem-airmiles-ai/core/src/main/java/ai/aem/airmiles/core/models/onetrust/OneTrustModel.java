package ai.aem.airmiles.core.models.onetrust;

import ai.aem.airmiles.core.models.onetrust.cacs.OneTrustCAC;
import ai.aem.airmiles.core.services.system.SystemResourceResolverService;
import com.day.cq.wcm.api.Page;
import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.settings.SlingSettingsService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isAnyBlank;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

/**
 * Sling model for OneTrust fields Business logic retrieval.
 *
 * @author pabpalac.
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = OPTIONAL)
public class OneTrustModel {

    /**
     * Used to log errors, warnings and information traces.
     */
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Current consumed page.
     */
    @ScriptVariable
    private Page currentPage;

    /**
     * System resource resolver service used to retrieve nodes with admin rights.
     */
    @OSGiService
    private SystemResourceResolverService system;

    /**
     * The Sling settings service, used to determine the current run mode.
     */
    @OSGiService
    private SlingSettingsService slingSettingsService;

    /**
     * OneTrust consent id used to consume CookieLaw Scripts.
     */
    @Getter
    private String consentId;

    /**
     * AutoBlock script from OneTrust's Cookie Law.
     */
    @Getter
    private String autoBlock;

    /**
     * SDK Stub script from OneTrust's Cookie Law.
     */
    @Getter
    private String sdkStub;

    /**
     * Inits Sling Model.
     */
    @PostConstruct
    protected void init() {
        // Set OneTrust values as "" as default.
        this.consentId = EMPTY;
        this.autoBlock = EMPTY;
        this.sdkStub = EMPTY;

        // Skip if the current page is null.
        if (null == currentPage) {
            log.error("Unable to retrieve current page.");
            return;
        }

        // Skip if the system service is null.
        if (null == system) {
            log.error("Unable to reference system user's resource resolver service.");
            return;
        }

        // Skip if the sling settings is null.
        if (null == slingSettingsService) {
            log.error("Unable to retrieve sling settings service.");
            return;
        }

        // Get run modes.
        final Set<String> runModes = slingSettingsService.getRunModes();

        // Skip when there are no run modes.
        if (runModes.isEmpty()) {
            log.error("No run modes detected.");
            return;
        }

        // Get resource resolver from system service.
        try (final ResourceResolver resolver = system.retrieveSystemUserResourceResolver()) {

            // Get current resource from current page path.
            final Resource resource = resolver.resolve(currentPage.getPath());

            // Retrieve inherited Analytics Context Aware Configurations
            final OneTrustCAC otCac = ofNullable(resource.adaptTo(ConfigurationBuilder.class))
                    .map(configurationBuilder -> configurationBuilder.as(OneTrustCAC.class))
                    .orElse(null);

            // Verify if the configuration is not null.
            if (null == otCac || isAnyBlank(otCac.consentId(), otCac.sdkStubScript(), otCac.autoBlockScript())) {
                log.warn("No CAConfig provided for OneTrust.");
                return;
            }

            // Retrieve OneTrust values from Configuration process.
            consentId = getConfig(otCac.consentId(), runModes);
            autoBlock = getConfig(otCac.autoBlockScript().replace("{{TOKEN}}", consentId), runModes);
            sdkStub = getConfig(otCac.sdkStubScript(), runModes);
            log.info("Using CAConfig as token: '{}', autoBlock: '{}' and sdkStubScript:'{}'",
                    this.consentId, this.autoBlock, this.sdkStub);
        } catch (final LoginException loginException) {
            log.error("Unable to retrieve system user resource resolver: {}", loginException.getMessage());
        }
    }


    /**
     * Retrieves a configuration value based on the specified configuration entry and run modes.
     *
     * @param configEntry The configuration entry for which the value is to be retrieved. Must not be null.
     * @param runModes    A set of run modes indicating the environment types. Must not be null.
     * @return The configuration value corresponding to the highest priority run mode found in the set.
     * If no matching run mode is found, an empty string is returned.
     */
    private @NotNull String getConfig(
            final @NotNull String configEntry,
            final @NotNull Set<String> runModes
    ) {
        // Declare environments.
        final String DEV = "dev";
        final String QA = "rde";
        final String STG = "stage";
        final String PROD = "prod";

        // Get the configuration map with each environment.
        final Map<String, String> configMap = getConfigMap(configEntry);

        // Return configMap value based on runmode.
        if (runModes.contains(DEV)) return configMap.get(DEV) == null ? EMPTY : configMap.get(DEV);
        if (runModes.contains(QA)) return configMap.get(QA) == null ? EMPTY : configMap.get(QA);
        if (runModes.contains(STG)) return configMap.get(STG) == null ? EMPTY : configMap.get(STG);
        if (runModes.contains(PROD)) return configMap.get(PROD) == null ? EMPTY : configMap.get(PROD);

        // If provided run-modes does not contain declared ones, the config should be null.
        return EMPTY;
    }

    /**
     * Extracts key-value pairs from a comma and colon separated config entry.
     *
     * @param configEntry Context Aware Configuration Entry.
     * @return map with configuration entries.
     */
    private @NotNull Map<String, String> getConfigMap(final @NotNull String configEntry) {

        // Create a new HashMap to store the key-value pairs.
        final Map<String, String> map = new HashMap<>();

        // Split the configuration entry string using commas as separators and iterate the result.
        for (final String entry : configEntry.split(",")) {

            // Split each entry into key and value using the '|' character as a separator.
            final String[] keyValue = entry.split("\\|");

            // Check if the key-value pair has both a key and a value, if they are found, add them to the map.
            if (keyValue.length == 2) map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

    /**
     * Checks whether script has provided configurations.
     *
     * @return {@code false} when configurations are empty, otherwise the model is considered as enabled.
     */
    public Boolean isEnabled() {
        return !isAnyBlank(consentId, autoBlock, sdkStub);
    }
}
