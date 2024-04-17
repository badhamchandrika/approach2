package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HeroBannerCTATest {
    HeroBannerCTA heroBannerCTA = new HeroBannerCTA();

    @BeforeEach
    void setUp() {
        heroBannerCTA.setLinkText("Join Now");
        heroBannerCTA.setLinkUrl("https://www.airmiles.ca/join_now.html");
        heroBannerCTA.setLinkType("button");
        heroBannerCTA.setLinkTarget("_blank");
        heroBannerCTA.setHexTextColor("#000000");
        heroBannerCTA.setHexBtnColor("#FFFFFF");
        heroBannerCTA.setDataTrackID("HeroBanner");
        heroBannerCTA.setDataClickID("join-now");
        heroBannerCTA.setDataTrackType("internal");
    }

    @Test
    void test_heroBannerCTA() {
        assertEquals("Join Now", heroBannerCTA.getLinkText());
        assertEquals("https://www.airmiles.ca/join_now.html", heroBannerCTA.getLinkUrl());
        assertEquals("button", heroBannerCTA.getLinkType());
        assertEquals("_blank", heroBannerCTA.getLinkTarget());
        assertEquals("#000000", heroBannerCTA.getHexTextColor());
        assertEquals("#FFFFFF", heroBannerCTA.getHexBtnColor());
        assertEquals("HeroBanner", heroBannerCTA.getDataTrackID());
        assertEquals("join-now", heroBannerCTA.getDataClickID());
        assertEquals("internal", heroBannerCTA.getDataTrackType());
    }

}
