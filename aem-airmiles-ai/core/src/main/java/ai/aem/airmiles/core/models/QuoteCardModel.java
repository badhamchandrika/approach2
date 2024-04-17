package ai.aem.airmiles.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Model(
		adaptables = SlingHttpServletRequest.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
		resourceType = "airmiles-ai/components/quote"
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuoteCardModel{
	
	@Getter
	@ValueMapValue
	private String headerText;

	@Getter
	@ValueMapValue
	private String midText;

	@Getter
	@ValueMapValue
	private String image;

	@Getter
	@ValueMapValue
	private String collabName;

	@Getter
	@ValueMapValue
	private String collabJob;

	@Getter
	@ValueMapValue
	private String collabSection;

}
