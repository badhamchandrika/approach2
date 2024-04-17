package ai.aem.airmiles.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.annotation.JsonInclude;

import ai.aem.airmiles.core.services.PageHeadLinksService;

@Model(
		adaptables = SlingHttpServletRequest.class,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageUtilsModel {

	@OSGiService
	private Externalizer externalizer;
	
	@Inject
	private PageHeadLinksService pageHeadService;
	
	@ScriptVariable
	private Page currentPage;
	
	@ScriptVariable
	private ResourceResolver resolver;
	
	private String analyticsURL;
	private String defaultHref;
	
	@PostConstruct
	void init () {
		this.analyticsURL = pageHeadService.getAnalyticsURL();
		this.defaultHref = pageHeadService.getDefaultHref(currentPage, resolver, externalizer);		
	}

	public String getAnalyticsURL() {
		return analyticsURL;
	}

	public String getDefaultHref() {
		return defaultHref;
	}		
}
