package ai.aem.airmiles.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Model(
		adaptables = SlingHttpServletRequest.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
		resourceType = "airmiles-ai/components/servletcall"
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServletCallModel {

	@ScriptVariable
    private Resource resource;
	
	@Getter
	@ValueMapValue
	private String method;
	
	@Getter
	@ValueMapValue
	private String url;
	
	@Getter
	@ValueMapValue
	private String enctype;
	
	@Getter
	@ValueMapValue
	private String formId;
	
	@Getter
	@ValueMapValue
	private String ajax;
}
