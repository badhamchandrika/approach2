package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({AemContextExtension.class})
class ContentBannerModelTest {

    public final AemContext context = new AemContext();

    private ContentBannerModel model;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(ComponentModel.class);
        context.addModelsForClasses(ContentBannerModel.class);
        context.addModelsForClasses(CTALink.class);
        context.load().json("/com.aem.airmiles.web.core/ContentBannerModelTest.json", "/content");
        context.currentResource("/content/contentbanner");
    }

    @Test
    void test_model() {
        model = context.request().adaptTo(ContentBannerModel.class);
        assertNotNull(model);
        assertEquals("Switch the way you sign in",model.getHeaderText());
        assertEquals("Now you can set up an email and password as a new, secure way of signing in to your AIR MILES® account.",model.getBodyText());
        assertEquals("https://sandbox-beta.airmiles.ca/ca/en/profile/convert.html", model.getLinkUrl());
        assertEquals("Set up secure sign-in by email", model.getMobileLinkText());
        assertEquals("https://sandbox-beta.airmiles.ca/ca/en/profile/convert.html", model.getMobileLinkUrl());
        assertEquals("_blank", model.getMobileLinkTarget());
        assertEquals("Set up now", model.getLinkText());
        assertEquals("Image Alt", model.getImageAlt());
        assertEquals("/content/dam/aem-airmiles-web/images/dollar_icon.png", model.getImageSrc());
    }
}
