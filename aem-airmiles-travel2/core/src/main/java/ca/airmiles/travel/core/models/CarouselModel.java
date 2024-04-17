package ca.airmiles.travel.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(
		adaptables = {Resource.class, SlingHttpServletRequest.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
		resourceType = "aem-airmiles-travel2/components/carousel"
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarouselModel {


	@Getter
	@Setter
	@ValueMapValue
	private String	dataTrackClick;

	@Setter
	@ValueMapValue
	private String	imgRight;

	@Getter
	@Setter
	@ChildResource
	private List<CarouselItemModel> carouselSlides;

	public boolean getImgRight() {
		return null != imgRight && imgRight.equals("true");
	}
}
