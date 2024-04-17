package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class HeroSectionModelTest {
    HeroSectionModel heroSectionModel = new HeroSectionModel();
    @BeforeEach
    void setUp() {
        heroSectionModel.setHeading("ONE.TWO.THREE");
        heroSectionModel.setImgPath("/content/dam/path/to/image");
        heroSectionModel.setDescription("Description");
        heroSectionModel.setDataTrackSection("hero-section");
        heroSectionModel.setBodyBgColour("blue");
        heroSectionModel.setHeadingTextColour("blue");
        heroSectionModel.setHeadingBgColour("blue");
        heroSectionModel.setBodyTextColour("blue");
        heroSectionModel.setFullBleed(true);
        heroSectionModel.setImgCardBgSource("/content/dam/path/to/img");
        heroSectionModel.setImgCardBgColour("black");

    }

    @Test
    void heroSection_getHeading() {
        assertEquals("ONE.TWO.THREE", heroSectionModel.getHeading());
    }
    @Test
    void heroSection_getImgPath() {
        assertEquals("/content/dam/path/to/image", heroSectionModel.getImgPath());
    }
    @Test
    void heroSection_getDescription() {
        assertEquals("Description",heroSectionModel.getDescription());
    }

    @Test
    void heroSection_getDataTrackSection() {
        assertEquals("hero-section",heroSectionModel.getDataTrackSection());
    }
    @Test
    void test_ctaDetails(){
        HeroSectionCTADetails heroSectionCTADetails = new HeroSectionCTADetails();
        List<HeroSectionCTADetails> ctaDetails = new ArrayList<>();
        ctaDetails.add(heroSectionCTADetails);
        heroSectionModel.setCtaDetails(ctaDetails);
        assertEquals(ctaDetails, heroSectionModel.getCtaDetails());
    }

    @Test
    void test_colors(){
        assertEquals("blue",heroSectionModel.getBodyBgColour());
        assertEquals("blue",heroSectionModel.getBodyTextColour());
        assertEquals("blue",heroSectionModel.getHeadingBgColour());
        assertEquals("blue",heroSectionModel.getHeadingTextColour());
        assertEquals("black",heroSectionModel.getImgCardBgColour());
        assertEquals("/content/dam/path/to/img",heroSectionModel.getImgCardBgSource());
        assertEquals("blue",heroSectionModel.getBodyBgColour());
        assertTrue(heroSectionModel.isFullBleed());
    }
}