package ca.airmiles.travel.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Sling exporter model for Image element used in site. Should be adapted from
 * resource where image's webpSrc and/or altSrc properties are stored for
 * injecting these properties. Properties exported are webpSrc
 * (<code>String</code>), altSrc (<code>String</code>) and
 * imageAlt(<code>String</code>).
 * 
 * @author everma, fcervantes
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Image {

	private final Logger logger = LoggerFactory.getLogger(Image.class);
	@ValueMapValue
	private String webpSrc;

	@ValueMapValue
	private String altSrc;

	@ValueMapValue
	private String imageAlt;

	private boolean webpSrcExternal;
	private boolean altSrcExternal;

	@ValueMapValue
	private String type;
	

	@PostConstruct
	protected void init() {
		if (StringUtils.isNotBlank(webpSrc) && !webpSrc.startsWith("/content"))
			webpSrcExternal = true;
		if (StringUtils.isNotBlank(altSrc) && !altSrc.startsWith("/content"))
			altSrcExternal = true;
	}

	@Inject
	ResourceResolver resolver;

	public Image() {
		super();
	}

	public Image(String webpSrc, String altSrc, String imageAlt, boolean webpSrcExternal, boolean altSrcExternal,
                 ResourceResolver resolver) {
		this.webpSrc = webpSrc;
		this.altSrc = altSrc;
		this.imageAlt = imageAlt;
		this.webpSrcExternal = webpSrcExternal;
		this.altSrcExternal = altSrcExternal;
		this.resolver = resolver;
	}

	@JsonIgnore
	public String getWebpSrc() {
		return this.webpSrc;
	}

	@JsonIgnore
	public String getAltSrc() {
		return this.altSrc;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	@JsonProperty("alt")
	public String getImageAlt() {
		// // return the authored image alternate text if injected
		if (StringUtils.isNotBlank(this.imageAlt)) {
			return this.imageAlt;
		}
		return  StringUtils.EMPTY;
	}


	/**
	 * @return the src
	 */
	@JsonProperty("sources")
	public List<String> getSrc() {

		List<String> imageSources = new ArrayList<String>();
		if (StringUtils.isNotBlank(this.webpSrc)) {
			imageSources.add(this.webpSrc);
		}
		if (StringUtils.isNotBlank(this.altSrc)) {
			imageSources.add(this.altSrc);
		}
		return imageSources;
	}

}
