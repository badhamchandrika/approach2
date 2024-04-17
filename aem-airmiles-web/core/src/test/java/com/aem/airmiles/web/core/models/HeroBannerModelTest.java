package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HeroBannerModelTest {
    HeroBannerModel heroBannerModel = new HeroBannerModel();
    @BeforeEach
    void setUp() {
        heroBannerModel.setTitleText("Hero Banner Title");
        heroBannerModel.setDescriptionText("Description goes here");
        heroBannerModel.setFileReference("/content/dam/path/to/image/test.jpg");
        heroBannerModel.setImageAlt("hero image ALT text");
    }

    @Test
    void heroBanner_getTitleText() {
        assertEquals("Hero Banner Title", heroBannerModel.getTitleText());
    }
    @Test
    void heroBanner_getFileReference() {
        assertEquals("/content/dam/path/to/image/test.jpg", heroBannerModel.getFileReference());
    }
    @Test
    void heroBanner_getDescriptionText() {
        assertEquals("Description goes here",heroBannerModel.getDescriptionText());
    }

    @Test
    void heroBanner_getImageAlt() {
        assertEquals("hero image ALT text",heroBannerModel.getImageAlt());
    }
    @Test
    void test_ctaButtons(){
        HeroBannerCTA heroBannerCTA = new HeroBannerCTA();
        List<HeroBannerCTA> ctaButtons = new ArrayList<>();
        ctaButtons.add(heroBannerCTA);
        heroBannerModel.setCtaButtons(ctaButtons);
        assertEquals(ctaButtons, heroBannerModel.getCtaButtons());
    }
}