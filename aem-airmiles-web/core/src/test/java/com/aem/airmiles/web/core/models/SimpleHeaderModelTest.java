package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SimpleHeaderModelTest {

    SimpleHeaderModel headerModel = new SimpleHeaderModel();

    @BeforeEach
    void setUp() {
        headerModel.setAirmilesLogoPath("/content/dam/airmiles/logo.png");
        headerModel.setCancelText("Cancel");
        headerModel.setLogoUrl("/en.html");
        headerModel.setDataClickID("airmiles-logo");
        headerModel.setDataTrackID("header");
        headerModel.setAirmilesLogoAltText("AirMiles");
        headerModel.setCancelLinkUrl("/en.html");
        headerModel.setCancelDataClickID("Cancel");
        headerModel.setCancelDataTrackID("header");
    }

    @Test
    void test_headerDetails() {

        assertEquals("/content/dam/airmiles/logo.png", headerModel.getAirmilesLogoPath());
        assertEquals("Cancel", headerModel.getCancelText());
        assertEquals("/en.html", headerModel.getCancelLinkUrl());
        assertEquals("AirMiles", headerModel.getAirmilesLogoAltText());
        assertEquals("/en.html", headerModel.getLogoUrl());
        assertEquals("airmiles-logo", headerModel.getDataClickID());
        assertEquals("header", headerModel.getDataTrackID());
        assertEquals("Cancel", headerModel.getCancelDataClickID());
        assertEquals("header", headerModel.getCancelDataTrackID());
    }
}
