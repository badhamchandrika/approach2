package ai.aem.airmiles.core.services.system;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * A service interface for retrieving a ResourceResolver associated with the system user. This interface provides a
 * method to obtain a ResourceResolver instance that represents the system user's privileges for accessing resources.
 */
public interface SystemResourceResolverService {

    /**
     * Retrieves a ResourceResolver instance associated with the system user.
     *
     * @return A ResourceResolver instance representing the system user's access privileges.
     * @throws LoginException If an error occurs while attempting to log in as system user.
     */
    ResourceResolver retrieveSystemUserResourceResolver() throws LoginException;

}