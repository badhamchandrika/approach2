package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CTALinkTest {

    CTALink ctaLink = new CTALink();

    @BeforeEach
    void setup(){
        ctaLink.setLinkText("Help");
        ctaLink.setLinkType("button");
        ctaLink.setHexLinkBarColor("#fff");
        ctaLink.setLinkUrl("https://airmiles.ca");
        ctaLink.setLinkTarget("_self");
        ctaLink.setHexBtnColor("x095f");
        ctaLink.setHexTextColor("x0h5h");
        ctaLink.setLinkMobileType("mobile");
        ctaLink.setDataTrackID("123");
        ctaLink.setDataClickID("1234");
    }

    @Test
    void test_hero() {
        assertEquals("Help", ctaLink.getLinkText());
        assertEquals("button", ctaLink.getLinkType());
        assertEquals("#fff", ctaLink.getHexLinkBarColor());
        assertEquals("https://airmiles.ca", ctaLink.getLinkUrl());
        assertEquals("_self", ctaLink.getLinkTarget());
        assertEquals("x095f", ctaLink.getHexBtnColor());
        assertEquals("x0h5h", ctaLink.getHexTextColor());
        assertEquals("mobile", ctaLink.getLinkMobileType());
        assertEquals("123", ctaLink.getDataTrackID());
        assertEquals("1234", ctaLink.getDataClickID());
    }
}
