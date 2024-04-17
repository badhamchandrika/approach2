package ca.airmiles.travel.core.models;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = {Resource.class,SlingHttpServletRequest.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BreadcrumbModel  {


	@ValueMapValue
	private String startLevel;

	@ValueMapValue
	private String dataTrackId;

    @ValueMapValue
	private Boolean darkbg;

	public String getStartLevel() {
		return startLevel;
	}

	public String getDataTrackId() {
		return dataTrackId;
	}

	public Boolean getDarkbg() {
		if(darkbg != null){
			return darkbg;
		}
		return null;
	}

}
