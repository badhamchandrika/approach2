package ca.airmiles.travel.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Model(
		adaptables = {Resource.class, SlingHttpServletRequest.class},
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CarouselItemModel {


	@ChildResource(name = "slideImage", injectionStrategy = InjectionStrategy.OPTIONAL)
	private Image slideImage;

	@ChildResource(name = "png", injectionStrategy = InjectionStrategy.OPTIONAL)
	private Image png;

	@ValueMapValue
	private String url;

	@ValueMapValue
	private String bodyText;

	@ValueMapValue
	private String headerText;
	@ValueMapValue
	private String startDate;
	@ValueMapValue
	private String endDate;

	@ValueMapValue
	private String headerColor;

	@ValueMapValue
	private String bgColor;

	@ValueMapValue
	private String imageLink;

	@ValueMapValue
	private String dataTrackId;

	@ValueMapValue
	private String description;

	@ChildResource(name = "actions", injectionStrategy = InjectionStrategy.OPTIONAL)
	private Resource actions;


	@JsonProperty("slideImage")
	public Image getSlideImage() {

		return slideImage;
	}

	@JsonProperty("png")
	public Image getPng() {

		return png;
	}

	@JsonProperty("bodyText")
	public String getBodyText() {

		return bodyText;

	}

	@JsonProperty("headerText")
	public String getHeaderText() {

		return headerText;

	}

	@JsonProperty("imageLink")
	public String getImageLink() {

		return imageLink;

	}

	@JsonProperty("headerColor")
	public String getHeaderColor() {

		return headerColor;

	}

	@JsonProperty("bgColor")
	public String getBgColor() {

		return bgColor;

	}

	@JsonProperty("dataTrackId")
	public String getDataTrackId() {
		return dataTrackId;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("startDate")
	public String getStartDate() {
		return startDate;
	}

	@JsonProperty("endDate")
	public String getEndDate() {
		return endDate;
	}
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public List<CTALink> getActions() {
		List<CTALink> actionsList = new ArrayList<>();
		if (actions == null)
			return actionsList;
		Iterator<Resource> configuredCTA = actions.listChildren();
		while (configuredCTA.hasNext()) {
			Resource rscta = configuredCTA.next();
			CTALink cta = rscta.adaptTo(CTALink.class);
			actionsList.add(cta);
		}

		return actionsList;
	}
}
