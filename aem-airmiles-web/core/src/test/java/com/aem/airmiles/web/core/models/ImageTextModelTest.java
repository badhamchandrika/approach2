package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class ImageTextModelTest {


    @Test
    void modelValues() {
        final ImageTextModel model = new ImageTextModel(){{
            setTitleText("Dashboard");
            setDescriptionText("Welcome to Airmiles");
            setFileReference("/content/dam/images/abc.jpg");
            setTrackName("carousel-banner");
            setTrackCategory("carousel");
            setLogoPath("/content/dam/airmiles/logo.jpg");
            setImageTextId("1234");
            setImageAltText("alt text");
            setIsCarouselItem("true");
            setImageAlign("top");
            setTitleHeading("h1");
            setTitleType("h1-small");
        }};
        assertEquals("Dashboard", model.getTitleText());
        assertEquals("Welcome to Airmiles", model.getDescriptionText());
        assertEquals("/content/dam/images/abc.jpg", model.getFileReference());
        assertEquals("carousel-banner", model.getTrackName());
        assertEquals("carousel", model.getTrackCategory());
        assertEquals("/content/dam/airmiles/logo.jpg", model.getLogoPath());
        assertEquals("1234",model.getImageTextId());
        assertEquals("alt text", model.getImageAltText());
        assertEquals("true", model.getIsCarouselItem());
        assertEquals("top", model.getImageAlign());
        assertEquals("h1", model.getTitleHeading());
        assertEquals("h1-small", model.getTitleType());
        assertNull(model.getCta());
    }

}