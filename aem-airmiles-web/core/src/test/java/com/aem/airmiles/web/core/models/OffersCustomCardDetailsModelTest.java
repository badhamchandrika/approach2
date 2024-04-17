package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OffersCustomCardDetailsModelTest {
    OffersCustomCardDetailsModel offersCustomCardDetailsModel = new OffersCustomCardDetailsModel();
    @BeforeEach
    void setUp() {
        offersCustomCardDetailsModel.setLogoPath("/content/dam/logo/logo.png");
        offersCustomCardDetailsModel.setCtaText("see more");
        offersCustomCardDetailsModel.setCtaUrl("/content/aem-airmiles-web/ca/en/offers/featured-offers/category-land-page.html");
        offersCustomCardDetailsModel.setCtaDataClickId("see more");
        offersCustomCardDetailsModel.setCtaDataTrackId("see more");
        offersCustomCardDetailsModel.setCtaDataTrackType("button");
        offersCustomCardDetailsModel.setDescription("We have more than 10 offers for you");

    }

    @Test
    void getCtaUrl() {
        assertEquals("/content/aem-airmiles-web/ca/en/offers/featured-offers/category-land-page.html",offersCustomCardDetailsModel.getCtaUrl());
    }

    @Test
    void getLogoPath() {
        assertEquals("/content/dam/logo/logo.png",offersCustomCardDetailsModel.getLogoPath());
    }

    @Test
    void getCtaText() {
        assertEquals("see more",offersCustomCardDetailsModel.getCtaText());
    }

    @Test
    void getDescription() {
        assertEquals("We have more than 10 offers for you",offersCustomCardDetailsModel.getDescription());
    }

    @Test
    void getCtaDataClickId() {
        assertEquals("see more",offersCustomCardDetailsModel.getCtaDataClickId());

    }

    @Test
    void getCtaDataTrackId() {
        assertEquals("see more",offersCustomCardDetailsModel.getCtaDataTrackId());
    }

    @Test
    void getCtaDataTrackType() {
        assertEquals("button",offersCustomCardDetailsModel.getCtaDataTrackType());
    }
}