package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.constant.ContentConstant;
import com.day.cq.dam.api.Asset;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class VideoModelTest {

    AemContext context = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
    private VideoModel model;

    @BeforeEach
    void setUp() {
        SlingHttpServletRequest request = context.request();
        context.load().json("/ca/airmiles/travel/core/models/videoasset.json","/content/dam/video");
        context.load().json("/ca/airmiles/travel/core/models/videoComponent.json","/content/videocomponent");
        context.currentResource("/content/videocomponent");
        this.model = request.adaptTo(VideoModel.class);
    }


    @Test
    void testMimeType() {
        assertNotNull(this.model);
        assertEquals("video/mp4", model.getMimeType());
        assertEquals("local", model.getSource());
        assertNull(model.getYoutubeSource());
        assertEquals("/content/dam/video", model.getLocalSource());
    }

}