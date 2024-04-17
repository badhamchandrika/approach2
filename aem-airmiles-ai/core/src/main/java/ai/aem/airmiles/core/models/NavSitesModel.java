package ai.aem.airmiles.core.models;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import ai.aem.airmiles.core.models.componentelements.LinkModel;
import lombok.Getter;

@Model(
		adaptables = {Resource.class, SlingHttpServletRequest.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
		resourceType = "airmiles-ai/components/navsites"
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NavSitesModel {	
	
	@Getter
	@ChildResource
	private List<LinkModel> sites;	

	@Getter
    @ValueMapValue
    private String signInURL;	
	
	@Getter
    @ValueMapValue
    private String signInLabel;

	
}
