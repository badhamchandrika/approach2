package com.aem.airmiles.web.core.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ImageModelTest {

    ImageModel image = new ImageModel();

    @BeforeEach
    void init() throws ParseException {
        image.setImagePosition("d-flex align-items-end justify-content-end");
        image.setImageScale("w-100");
        image.setImageRounded(true);
    }

    @Test
    void test_Image(){
        assertEquals("d-flex align-items-end justify-content-end",image.getImagePosition());
        assertEquals("w-100",image.getImageScale());
        assertTrue(image.isImageRounded());
    }

}
