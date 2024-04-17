package ai.aem.airmiles.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import lombok.Getter;

@Model(
		adaptables = {SlingHttpServletRequest.class,Resource.class}, 
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
		resourceType = "airmiles-ai/components/logo"
)
public class LogoModel{

	@Getter
    @ValueMapValue
    private String logoURL;

	@Getter
    @ValueMapValue
    private String logoImage;
	
	@Getter
    @ValueMapValue
    private String logoAltText;
}
