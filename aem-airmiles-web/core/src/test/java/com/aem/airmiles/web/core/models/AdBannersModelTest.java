package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AdBannersModelTest {

    @Test
    void test_AdBanners() {
        // Init model.
        final AdBannersModel adBannerModel = new AdBannersModel(){{
            setEnableGoogleAdSense(true);
            setAdSlotEnabled(true);
            setAdID("div-gpt-ad-1684176883688-0");
            setAdDetails(Collections.singletonList(new AdBanners()));
            setId("some-id");
            setRemoveStyles(true);
        }};

        // Assertions,
        assertNotNull(adBannerModel);
        assertTrue(adBannerModel.isEnableGoogleAdSense());
        assertTrue(adBannerModel.isAdSlotEnabled());
        assertEquals("div-gpt-ad-1684176883688-0", adBannerModel.getAdID());
        assertNotNull(adBannerModel.getAdDetails());
        assertNotNull(adBannerModel.getId());
        assertTrue(adBannerModel.isRemoveStyles());
    }
}
