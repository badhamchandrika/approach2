package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FooterSocialModelTest {

    FooterSocialModel footerSocialModel = new FooterSocialModel();

    @Test
    void test_heading(){
        footerSocialModel.setHeading("Join our community");
        assertEquals("Join our community",footerSocialModel.getHeading());
    }

    @Test
    void test_socialLinks(){
        FooterLinks footerLinks = new FooterLinks();
        List<FooterLinks> socialLinks = new ArrayList<>();
        socialLinks.add(footerLinks);
        footerSocialModel.setSocialLinks(socialLinks);
        assertEquals(socialLinks,footerSocialModel.getSocialLinks());
    }
}
