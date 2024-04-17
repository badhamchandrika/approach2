package com.aem.airmiles.web.core.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class OffersCarouselDetailsModelTest {
    OffersCarouselDetailsModel offersCarouselDetailsModel = new OffersCarouselDetailsModel();

    @Before
    public void setup(){
        offersCarouselDetailsModel.setCarouselTitle("Card Linked Offers");
        offersCarouselDetailsModel.setCarouselDescription("Card Linked Description");
        offersCarouselDetailsModel.setCarouselCta("see all offers");
        offersCarouselDetailsModel.setCarouselCtaUrl("/content/aem-airmiles-web/ca/en/offers/clo.html");
        offersCarouselDetailsModel.setCloCtaDataTrackId("clo-track-id");
        offersCarouselDetailsModel.setCloCtaDataClickId("clo-click-id");
        offersCarouselDetailsModel.setCloCtaDataTrackType("clo-track-type");
        offersCarouselDetailsModel.setNumberOfOffersInCarousel(10);
        offersCarouselDetailsModel.setScrollNumberOfOffers(4);
        offersCarouselDetailsModel.setOffersCustomCardDetails("/content/dam/aem-airmiles-web/offers-clo/offers-last-card");
        offersCarouselDetailsModel.setCloQueryParamDetails("/content/dam/aem-airmiles-web/offers-clo/offers-query-param");
    }
    @Test
    public void testDetails(){
        assertNotNull(offersCarouselDetailsModel);
        assertEquals("Card Linked Offers",offersCarouselDetailsModel.getCarouselTitle());
        assertEquals("Card Linked Description", offersCarouselDetailsModel.getCarouselDescription());
        assertEquals("see all offers", offersCarouselDetailsModel.getCarouselCta());
        assertEquals("/content/aem-airmiles-web/ca/en/offers/clo.html", offersCarouselDetailsModel.getCarouselCtaUrl());
        assertEquals("clo-track-id", offersCarouselDetailsModel.getCloCtaDataTrackId());
        assertEquals("clo-track-type", offersCarouselDetailsModel.getCloCtaDataTrackType());
        assertEquals("clo-click-id",offersCarouselDetailsModel.getCloCtaDataClickId());
        assertEquals(10,offersCarouselDetailsModel.getNumberOfOffersInCarousel());
        assertEquals(4,offersCarouselDetailsModel.getScrollNumberOfOffers());
        assertEquals("/content/dam/aem-airmiles-web/offers-clo/offers-last-card",offersCarouselDetailsModel.getOffersCustomCardDetails());
        assertEquals("/content/dam/aem-airmiles-web/offers-clo/offers-query-param",offersCarouselDetailsModel.getCloQueryParamDetails());



    }

}
