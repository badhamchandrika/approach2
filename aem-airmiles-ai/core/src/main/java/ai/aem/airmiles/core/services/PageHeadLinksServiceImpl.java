package ai.aem.airmiles.core.services;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;


@Designate(ocd = PageHeadLinksServiceImpl.Config.class)
@Component(
    immediate = true,
    service = PageHeadLinksService.class,
	property = {
			Constants.SERVICE_DESCRIPTION +"=Page Head Links Utils Airmiles AI Service" }
)
public class PageHeadLinksServiceImpl implements PageHeadLinksService {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(PageHeadLinksServiceImpl.class);		
	
	@ObjectClassDefinition(name = "Analytics Service Configuration", description = "Service to get the Analytics URL")
	public static @interface Config {
	  /**
	   * Property to store the Analytics URL
	   *
	   * @return String analyticsURL
	   */
	  @AttributeDefinition(
	      name = "Analytics-URL",
	      description = "Analytics URL depending on the env."
	  )
	  String analyticsURL();

	}
	
	private String analyticsURL;
	
	@Activate
	protected void activate(final Config config) {
		analyticsURL = config.analyticsURL();		
	}

	@Override
	public String getAnalyticsURL() {		
		return analyticsURL;
	}

	@Override
	public String getDefaultHref(Page page, ResourceResolver resolver, Externalizer externalizer) {
		String lang = Optional.ofNullable(page.getProperties())
		        .map(p -> p.get("jcr:language"))
		        .map(Object::toString)
		        .orElse("");
		String currentPageUrl = externalizer.publishLink(resolver, page.getPath()) + ".html";				
		
		return currentPageUrl.replace("/" + lang + "/", "/");
	}
}
