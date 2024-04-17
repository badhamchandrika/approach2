package com.aem.airmiles.web.core.services.impl;

import com.aem.airmiles.web.core.services.BaseConfigurationService;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import static org.osgi.service.metatype.annotations.AttributeType.STRING;

/**
 * Base configurations service used to expose internal APIs.
 */
@Component(service = BaseConfigurationService.class)
@Designate(ocd = BaseConfigurationServiceImpl.Configuration.class)
public class BaseConfigurationServiceImpl implements BaseConfigurationService {

    /**
     * Default API URL for local instances.
     */
    static final String LOCAL_API = "http://localhost:3000";

    /**
     * Generic API URL.
     */
    private String apiUrl;

    /**
     * API URL used for DOM BFF.
     */
    private String domBFFUrl;

    /**
     * API URL used for Offers DOM BFF.
     */
    private String domBFFOffersUrl;
    /**
     * Initializes service in OSGi. If the configuration does not exist, the APIs will be EMPTY.
     *
     * @param config OSGi configurations.
     */
    @Activate
    @Modified
    protected void activate(final Configuration config) {
        if (null != config) {
            this.apiUrl = config.apiUrl();
            this.domBFFUrl = config.domBFFUrl();
            this.domBFFOffersUrl = config.domBFFOffersUrl();
            return;
        }
        this.apiUrl = StringUtils.EMPTY;
        this.domBFFUrl = StringUtils.EMPTY;
        this.domBFFOffersUrl = StringUtils.EMPTY;
    }

    /**
     * Exposes generic API URL.
     *
     * @return API.
     */
    @Override
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * Exposes DOM BFF API URL.
     *
     * @return API.
     */
    @Override
    public String getDomBFFUrl() {
        return domBFFUrl;
    }

    @Override
    public  String getDomBFFOffersUrl(){return domBFFOffersUrl;}
    /**
     * Object Class Definition used to configure OSGi entries.
     */
    @ObjectClassDefinition(name = "Airmiles: Base Configuration Details")
    public @interface Configuration {

        /**
         * Configured Generic API URL.
         *
         * @return API URL.
         */
        @AttributeDefinition(name = "API Url", description = "Provide API URL.", type = STRING)
        String apiUrl() default LOCAL_API;

        /**
         * Configured DOM BFF API URL.
         *
         * @return DOM BFF API.
         */
        @AttributeDefinition(name = "DOM BFF Url", description = "Provide DOM BFF API URL.", type = STRING)
        String domBFFUrl() default LOCAL_API;

        @AttributeDefinition(name = "DOM BFF Offers Url", description = "Provide Offers DOM BFF API URL.", type = STRING)
        String domBFFOffersUrl() default LOCAL_API;
    }
}
