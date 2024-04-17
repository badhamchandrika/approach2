package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CTALinkTest {

    CTALink ctaLink = new CTALink();
    
    @BeforeEach
    void init() throws ParseException {
        ctaLink.setLinkType("framebutton");
        ctaLink.setLinkUrl("https://www.airmiles.ca");
        ctaLink.setModalUniqueID("id-xyz123");
        ctaLink.setLinkTarget("_self");
        ctaLink.setLinkText("Custom Link");
        ctaLink.setFontSize("cta-md");
        ctaLink.setLinkColor("brand-blue");
        ctaLink.setHexTextColor("#AAFFAA");
        ctaLink.setHexBtnColor("#31569E");
        ctaLink.setDataTrackID("modal-xyz123");
        ctaLink.setDataClickID("dashboard-modal");
        ctaLink.setDataTrackType("internal");
        ctaLink.setId("uniqueId");
        ctaLink.setIconOnRight(true);
        ctaLink.setIconClassGroup("am-icon2");
        ctaLink.setIconClassName("am-icon-external-link");
        ctaLink.setIconColor("#FF00FF");
    }

    @Test
    void test_ctaLink(){
        assertEquals("framebutton",ctaLink.getLinkType());
        assertEquals("https://www.airmiles.ca",ctaLink.getLinkUrl());
        assertEquals("id-xyz123",ctaLink.getModalUniqueID());
        assertEquals("_self",ctaLink.getLinkTarget());
        assertEquals("Custom Link",ctaLink.getLinkText());
        assertEquals("cta-md",ctaLink.getFontSize());
        assertEquals("brand-blue",ctaLink.getLinkColor());
        assertEquals("#AAFFAA",ctaLink.getHexTextColor());
        assertEquals("#31569E",ctaLink.getHexBtnColor());
        assertEquals("modal-xyz123",ctaLink.getDataTrackID());
        assertEquals("dashboard-modal",ctaLink.getDataClickID());
        assertEquals("internal",ctaLink.getDataTrackType());
        assertEquals("uniqueId", ctaLink.getId());
        assertTrue(ctaLink.isIconOnRight());
        assertEquals("am-icon2",ctaLink.getIconClassGroup());
        assertEquals("am-icon-external-link", ctaLink.getIconClassName());
        assertEquals("#FF00FF", ctaLink.getIconColor());
    }

}
