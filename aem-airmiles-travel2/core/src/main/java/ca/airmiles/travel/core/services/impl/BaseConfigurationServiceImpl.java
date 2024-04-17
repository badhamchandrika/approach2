package ca.airmiles.travel.core.services.impl;

import ca.airmiles.travel.core.services.BaseConfigurationService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = BaseConfigurationService.class)
@Designate(ocd = BaseConfigurationServiceImpl.Configuration.class)
public class BaseConfigurationServiceImpl implements BaseConfigurationService {

    private Configuration config;

    @Activate
    @Modified
    protected void activate(Configuration config) {
        this.config = config;
    }


    @Override
    public String getApiUrl() {
        return config.apiUrl();
    }

    @Override
    public String getDomBFFUrl() { return config.domBFFUrl(); }

    @ObjectClassDefinition(name = "Airmiles: Base Configuration Details")
    public @interface Configuration {

        @AttributeDefinition(name = "API Url", description = "provide api url ", type = AttributeType.STRING)
        String apiUrl() default "http://localhost:3000";

        @AttributeDefinition(name = "DOM BFF Url", description = "Provide DOM BFF API URL.", type = AttributeType.STRING)
        String domBFFUrl() default "http://localhost:3000";
    }
}
