package ai.aem.airmiles.core.services.system.impl;

import ai.aem.airmiles.core.services.system.SystemResourceResolverService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;
import static org.apache.sling.api.resource.ResourceResolverFactory.SUBSERVICE;

/**
 * Implementation of the {@link SystemResourceResolverService} interface, used to retrieve system user resource resolver
 * with system user privileges in order to perform operations within the OSGi and Sling ecosystems.
 *
 * @author pabpalac.
 */
@Component(service = SystemResourceResolverService.class, immediate = true)
public class SystemResourceResolverServiceImpl implements SystemResourceResolverService {

    /**
     * Reference to the ResourceResolverFactory service for retrieving system users.
     */
    @Reference
    private ResourceResolverFactory resFact;

    /**
     * Initializes the service when activated in the OSGi environment.
     */
    @Activate
    protected void activate() {
        // Activates the service.
    }

    /**
     * Retrieves a ResourceResolver instance associated with the system user.
     *
     * @return A ResourceResolver instance representing the system user's access privileges.
     * @throws LoginException If an error occurs while attempting to log in the system user.
     */
    @Override
    public ResourceResolver retrieveSystemUserResourceResolver() throws LoginException {

        // Converts a 2D array of strings into a Map using the first element as key and second one as value.
        return resFact.getServiceResourceResolver(stream(new String[][]{{SUBSERVICE, "airmilesServiceUser"}})
                .collect(toMap(kv -> kv[0], kv -> kv[1])));
    }
}
