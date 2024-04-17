package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class CarouselModelTest {

    CarouselModel carouselModel = new CarouselModel();
    @Test
    void modelValues() {
        carouselModel.setTitle("Latest Updates");
        carouselModel.setId("carousel");
        CarouselSlideModel carousel = new CarouselSlideModel();
        carousel.setSlideXFPath("/content/experience-fragments/ca/en/carousel");
        List<CarouselSlideModel> carouselSlides = new ArrayList<>();
        carouselModel.setCarouselSlides(carouselSlides);

        assertEquals("Latest Updates", carouselModel.getTitle());
        assertEquals("carousel", carouselModel.getId());
        assertEquals(carouselSlides, carouselModel.getCarouselSlides());

    }

}
