package ai.aem.airmiles.core.models.componentelements;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Model(
		adaptables = {SlingHttpServletRequest.class, Resource.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CTAModel {

	@Getter
	@ValueMapValue
	private String ctaText;
	
	@Getter
	@ValueMapValue
	private String ctaUrl;
	
	@Getter
	@ValueMapValue
	private String ctaIcon;
	
	@Getter
	@ValueMapValue
	private String ctaType;
	
	@Getter
	@ValueMapValue
	private String ctaTarget;
	
}
