package ai.aem.airmiles.core.models.componentelements;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
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
public class DatacardModel{		
	
	@Getter
	@ValueMapValue
	private String headerText;
	
	@Getter
	@ValueMapValue
	private String midText;
	
	@Getter
	@ValueMapValue
	private String icon;
	
	@Getter
	@ValueMapValue
	private String image;
	
	@Getter
	@ValueMapValue
	private String imageAltText;
	
	@Getter
	@ChildResource
	private CTAModel cta;
	
}
