package ai.aem.airmiles.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.Carousel;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.experimental.Delegate;

@Model(
		adaptables = SlingHttpServletRequest.class, 
		adapters = Carousel.class, 
		resourceType = "airmiles-ai/components/quotescarousel",
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuotesCarouselModel implements Carousel{

	@Self 
	@Via(type = ResourceSuperType.class)
    @Delegate(types = Carousel.class)
    private Carousel delegate;
	
}
