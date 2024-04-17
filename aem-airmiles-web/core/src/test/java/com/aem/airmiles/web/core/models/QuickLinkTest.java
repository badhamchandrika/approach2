package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class QuickLinkTest {
    QuickLink quickLink = new QuickLink();

    @Test
    void testQuickLinkFields() {
        quickLink.setLinkText("Browse Rewards");
        quickLink.setLinkDescription("Browse your rewards");
        quickLink.setLinkUrl("/content/aem-airmiles-web/ca/en");
        quickLink.setHideLink(false);
        quickLink.setNewTab(true);
        quickLink.setBgcolor("Citrine");
        quickLink.setIconPicker("am-icon2-browse-rewards");
        quickLink.setIconColor("icon-white-color");
        quickLink.setDataClickID("browse-rewards");
        quickLink.setDataTrackID("browse-rewards");
        quickLink.setDataTrackType("link");
        quickLink.setId("id");
        assertEquals("Browse Rewards", quickLink.getLinkText());
        assertEquals("Browse your rewards", quickLink.getLinkDescription());
        assertEquals("/content/aem-airmiles-web/ca/en", quickLink.getLinkUrl());
        assertEquals("am-icon2-browse-rewards", quickLink.getIconPicker());
        assertEquals("Citrine", quickLink.getBgcolor());
        assertFalse(quickLink.isHideLink());
        assertTrue(quickLink.isNewTab());
        assertEquals("browse-rewards", quickLink.getDataClickID());
        assertEquals("browse-rewards", quickLink.getDataTrackID());
        assertEquals("link", quickLink.getDataTrackType());
        assertEquals("id", quickLink.getId());
    }
}
