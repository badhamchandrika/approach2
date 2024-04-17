package com.aem.airmiles.web.core.models.impl;

import com.adobe.cq.wcm.core.components.models.Teaser;
import com.aem.airmiles.web.core.models.CustomTeaser;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class CustomTeaserImplTest {

    private static final String CONTENT_ROOT = "/content/teaser";
    private final AemContext aemContext = new AemContext();
    private CustomTeaser customTeaser = Mockito.spy(CustomTeaserImpl.class);

    @Mock
    private Teaser coreTeaser;

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(CustomTeaser.class);
        aemContext.load().json("/com.aem.airmiles.web.core/CustomTeaser.json", CONTENT_ROOT);
    }

    @Test
    void testCustomTeaser() {
        aemContext.currentResource(CONTENT_ROOT+"/teaser");
        customTeaser = aemContext.request().adaptTo(CustomTeaser.class);
        assertNotNull(customTeaser);
        assertEquals("/content/dam/aem-airmiles-web/images/dollar_icon.png", customTeaser.getMobileImageSrc());
        assertEquals("/content/dam/aem-airmiles-web/images/search.png", customTeaser.getTabletImageSrc());
        assertEquals("/content/dam/aem-airmiles-web/images/desktop-image.png", customTeaser.getDesktopImageSrc());
        assertEquals("alt-text", customTeaser.getImageAlt());
    }
    @Test
    void testCustomTeaserWithNoImages() {
        aemContext.currentResource(CONTENT_ROOT+"/emptyTeaser");
        customTeaser = aemContext.request().adaptTo(CustomTeaser.class);
        assertNotNull(customTeaser);
        assertNull(customTeaser.getDesktopImageSrc());
    }

    @Test
    void testCustomTeaserWithOnlyDesktopImage() {
        aemContext.currentResource(CONTENT_ROOT+"/desktopTeaser");
        customTeaser = aemContext.request().adaptTo(CustomTeaser.class);
        assertNotNull(customTeaser);
        assertEquals("/content/dam/aem-airmiles-web/images/desktop-image.png", customTeaser.getMobileImageSrc());
        assertEquals("/content/dam/aem-airmiles-web/images/desktop-image.png", customTeaser.getTabletImageSrc());
    }

    @Test
    void testCoreTeaser() throws NoSuchFieldException {
        customTeaser = Mockito.spy(CustomTeaserImpl.class);
        PrivateAccessor.setField(customTeaser, "teaser", coreTeaser);
        when(coreTeaser.getTitle()).thenReturn("Teaser Title");
        assertEquals("Teaser Title", customTeaser.getTitle());
    }
}
