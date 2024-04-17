package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class QuickLinkModelTest {
    QuickLinkModel quickLinkModel = new QuickLinkModel();

    @Test
    void testQuickLinkModelFields(){
        quickLinkModel.setQuickLinkTitle("Quick Links");
        assertEquals("Quick Links", quickLinkModel.getQuickLinkTitle());
        quickLinkModel.setQuickLinkSubTitle("Quick Links subtitle");
        assertEquals("Quick Links subtitle", quickLinkModel.getQuickLinkSubTitle());
        quickLinkModel.setExpandTitle("More self-serve tools");
        assertEquals("More self-serve tools", quickLinkModel.getExpandTitle());
        quickLinkModel.setCollapseTitle("Fewer self-serve tools");
        assertEquals("Fewer self-serve tools", quickLinkModel.getCollapseTitle());
        QuickLink quickLink = new QuickLink();
        quickLink.setLinkText("Browse Rewards");
        quickLink.setLinkDescription("Browse your rewards");
        quickLink.setLinkUrl("/content/aem-airmiles-web/ca/en");
        quickLink.setHideLink(false);
        quickLink.setNewTab(true);
        quickLink.setBgcolor("Citrine");
        quickLink.setIconPicker("am-icon2-browse-rewards");
        quickLink.setIconColor("icon-white-color");
        quickLinkModel.setId("cmp-id");
        List<QuickLink> quickLinks = new ArrayList<>();
        quickLinks.add(quickLink);
        quickLinkModel.setLinkDetails(quickLinks);
        assertEquals(quickLinks, quickLinkModel.getLinkDetails());
        assertEquals("cmp-id", quickLinkModel.getId());
    }
}
