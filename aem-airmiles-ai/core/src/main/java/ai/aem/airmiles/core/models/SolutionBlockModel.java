package ai.aem.airmiles.core.models;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import ai.aem.airmiles.core.models.componentelements.TabSolutionBlockModel;
import lombok.Getter;

@Model(
		adaptables = SlingHttpServletRequest.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
		resourceType = "airmiles-ai/components/solutionblock"
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SolutionBlockModel {

	@Getter
	@ChildResource
	private List<TabSolutionBlockModel> tabs;
	
	@Getter
	@ValueMapValue
	private String image;
	
	@Getter
	@ValueMapValue
	private String imageAltText;
	
	@Getter
	@ValueMapValue
	private String imageDesc;
	
}
