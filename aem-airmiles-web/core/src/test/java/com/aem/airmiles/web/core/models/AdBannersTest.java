package com.aem.airmiles.web.core.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AdBannersTest {

    AdBanners adBanners = new AdBanners();

    @BeforeEach
    void init() throws ParseException {
        adBanners.setAdSlotAdPath("campaign/banner/type");
        adBanners.setAdSlotAdID("Banner-test-id-01234567");
        adBanners.setAdSlotAdSize("920,300");
    }

    @Test
    void test_AdBanners(){
        assertEquals("campaign/banner/type",adBanners.getAdSlotAdPath());
        assertEquals("Banner-test-id-01234567",adBanners.getAdSlotAdID());
        assertEquals("920,300",adBanners.getAdSlotAdSize());
    }

}
