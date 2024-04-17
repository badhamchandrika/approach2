package ai.aem.airmiles.core.services;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.commons.Externalizer;
import com.day.cq.wcm.api.Page;

public interface PageHeadLinksService {

	public String getAnalyticsURL();
	
	public String getDefaultHref(Page page, ResourceResolver resolver, Externalizer externalizer);
	
}
