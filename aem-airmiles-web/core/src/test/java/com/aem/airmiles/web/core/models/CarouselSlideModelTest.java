package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CarouselSlideModelTest {

    CarouselSlideModel slideModel = new CarouselSlideModel();
    @Test
    void testCarouselSlideFields() {
        slideModel.setSlideXFPath("/content/experience-fragments/ca/en/carousel");
        slideModel.setCarouselSlideId("slide1");
        assertEquals("/content/experience-fragments/ca/en/carousel", slideModel.getSlideXFPath());
        assertEquals("slide1", slideModel.getCarouselSlideId());
    }
}
