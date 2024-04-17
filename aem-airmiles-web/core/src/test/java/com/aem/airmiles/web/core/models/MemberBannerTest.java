package com.aem.airmiles.web.core.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MemberBannerTest {

    MemberBanner memberBanner = new MemberBanner();

    @BeforeEach
    void init() throws ParseException {
        memberBanner.setType("billboard");
        memberBanner.setTitle("AD Banner Test");
        memberBanner.setWeburl("http://www.airmiles.ca/offers");
        memberBanner.setClickact("_blank");
        memberBanner.setDataClickID("offers");
        memberBanner.setDataTrackID("nav-header");
        memberBanner.setDataTrackType("internal");
    }

    @Test
    void test_AdBanners(){
        assertEquals("billboard",memberBanner.getType());
        assertEquals("AD Banner Test",memberBanner.getTitle());
        assertEquals("http://www.airmiles.ca/offers",memberBanner.getWeburl());
        assertEquals("_blank",memberBanner.getClickact());
        assertEquals("offers",memberBanner.getDataClickID());
        assertEquals("nav-header",memberBanner.getDataTrackID());
        assertEquals("internal",memberBanner.getDataTrackType());
    }
}