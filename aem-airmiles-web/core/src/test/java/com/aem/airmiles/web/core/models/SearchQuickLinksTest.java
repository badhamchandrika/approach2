package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SearchQuickLinksTest {

    SearchQuickLinks searchQuickLinks = new  SearchQuickLinks();

    @BeforeEach
    void setUp() {
        searchQuickLinks.setText("Replacement Card");
        searchQuickLinks.setLinkTarget("_self");
        searchQuickLinks.setDataClickID("search");
        searchQuickLinks.setDataTrackID("replacement-card");
        searchQuickLinks.setCtaUrl("/content/airmiles/ca/en/replacement-card.html");

    }

    @Test
    void test_searchQuickLinks(){
        assertEquals("Replacement Card",searchQuickLinks.getText());
        assertEquals("_self",searchQuickLinks.getLinkTarget());
        assertEquals("search",searchQuickLinks.getDataClickID());
        assertEquals("replacement-card",searchQuickLinks.getDataTrackID());
        assertEquals("/content/airmiles/ca/en/replacement-card.html",searchQuickLinks.getCtaUrl());
    }
}
