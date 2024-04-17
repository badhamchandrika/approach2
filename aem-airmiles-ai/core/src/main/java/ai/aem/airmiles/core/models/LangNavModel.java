package ai.aem.airmiles.core.models;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Model(
		adaptables = {Resource.class, SlingHttpServletRequest.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
		resourceType = "airmiles-ai/components/langnav"
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LangNavModel {		
    
    @Inject
    private ValueMap pageProperties;
	
	@Getter
	@ValueMapValue
	private String icon;
	
	@Getter
	private String displayText;
	
	@PostConstruct
    protected void init() {
		String lang = Optional.ofNullable(pageProperties)
                .map(p -> p.get("jcr:language"))
                .map(Object::toString)
                .orElse("");
		
		if(StringUtils.equals(lang, "en")) 
		{
			displayText = "ENG";
		}
        
        icon = "am-icon-globe";
        
    }
	
}
