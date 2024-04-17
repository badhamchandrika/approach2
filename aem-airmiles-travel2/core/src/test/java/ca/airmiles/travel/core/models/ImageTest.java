package ca.airmiles.travel.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.factory.ModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 
 * @author sponnapati, @fcervantes
 *
 */
@ExtendWith(AemContextExtension.class)
class ImageTest {
	private final AemContext ctx = new AemContext();
	private Resource resource;
	
	private final String resourcePath = "/content";	
	
	Image image;
	
	/** The resource resolver. */
	@SlingObject
	private ResourceResolver resourceResolver;
	
	@BeforeEach
	public void setUp() {
		ctx.load().json("/ca/airmiles/travel/core/models/image.json", resourcePath);
		resource = ctx.resourceResolver().getResource("/content");
		ctx.registerService(Resource.class,resource);
		image = ctx.getService(ModelFactory.class).createModel(resource, Image.class);
	}

	
	
	@Test
	void testImageStringStringStringBooleanBooleanResourceResolver() {
		image = new Image("/content/dam/airmiles/ca/en/get-miles/lens-3.webp","/content/dam/airmiles/ca/en/get-miles/lens-3.png","lens-3",true,true,resourceResolver);
	}

	@Test
	void testGetWebpSrc() {
		assertEquals("/content/dam/airmiles/ca/en/get-miles/lens-3.webp",image.getWebpSrc());
	}

	@Test
	void testGetAltSrc() {
		assertEquals("/content/dam/airmiles/ca/en/get-miles/lens-3.png",image.getAltSrc());
	}

	@Test
	void testGetType() {
		assertEquals("testType",image.getType());
	}

	@Test
	void testGetImageAlt() {
		assertEquals("lens-3",image.getImageAlt());
	}

	@Test
	void testGetSrc() {
		assertNotNull(image.getSrc());
	}

}
