package com.aem.airmiles.web.core.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FooterLinksTest {

    FooterLinks footerLinks = new FooterLinks();

    @BeforeEach
    void init(){
        footerLinks.setImagePath("path/to/image");
        footerLinks.setLink("/content/airmiles/ca/en/contact-us.html");
        footerLinks.setLinkTarget("_self");
        footerLinks.setAltText("Contact Us");
        footerLinks.setDataClickID("footer");
        footerLinks.setDataTrackID("contact-us");
        footerLinks.setDataTrackType("internal");
    }

    @Test
    void test_footerLinks(){
        assertEquals("path/to/image",footerLinks.getImagePath());
        assertEquals("/content/airmiles/ca/en/contact-us.html",footerLinks.getLink());
        assertEquals("_self",footerLinks.getLinkTarget());
        assertEquals("Contact Us",footerLinks.getAltText());
        assertEquals("footer",footerLinks.getDataClickID());
        assertEquals("contact-us",footerLinks.getDataTrackID());
        assertEquals("internal",footerLinks.getDataTrackType());
    }
}
