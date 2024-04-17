package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GetMoreMilesLinksModelTest {

    GetMoreMilesLinksModel model;

    @BeforeEach
    void init(){
        model = new GetMoreMilesLinksModel(){{
            setLinkText("GoogleCL");
            setLinkUrl("https://www.google.cl");
            setBody("body1");
            setTitle("Title1");
            setImagePath("/content/dam/we-retail-screens/usa.png");
            setLinkTarget("_blank");
            setDataClickID("clicked");
            setDataTrackID("id");
            setImagePath2("/content/dam/we-retail-screens/mx.png");
        }};
    }

    @Test
    void test_getMoreMilesLinks(){
        assertEquals("GoogleCL", model.getLinkText());
        assertEquals("https://www.google.cl", model.getLinkUrl());
        assertEquals("body1", model.getBody());
        assertEquals("Title1", model.getTitle());
        assertEquals("/content/dam/we-retail-screens/usa.png", model.getImagePath());
        assertEquals("/content/dam/we-retail-screens/mx.png", model.getImagePath2());
        assertEquals("_blank", model.getLinkTarget());
        assertEquals("clicked", model.getDataClickID());
        assertEquals("id", model.getDataTrackID());
    }
}
