package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class LinksModelTest {
    public Links links;

    @BeforeEach
    public void setUp() {
        links = new Links();
    }

    @Test
    void getLinkModel_url() {
        links.setLink("https://www.google.com");
        assertEquals("https://www.google.com", links.getLink());
    }

    @Test
    void getLinkModel_title() {
        links.setTitle("Useful Information");
        assertEquals("Useful Information", links.getTitle());
    }

    @Test
    void getLinkModel_linkTarget() {
        links.setLinkTarget("_self");
        assertEquals("_self", links.getLinkTarget());
    }
    @Test
    void getLinkModel_icon() {
        links.setIcon("travel");
        assertEquals("travel", links.getIcon());
    }

    @Test
    void getLinkModel_dataTrackId() {
        links.setDataTrackId("Data Track Id");
        assertEquals("Data Track Id", links.getDataTrackId());
    }
    @Test
    void getLinkModel_dataTrackClick() {
        links.setDataTrackClick("Data Track Click");
        assertEquals("Data Track Click", links.getDataTrackClick());
    }
    @Test
    void getLinkModel_dataTrackType() {
        links.setDataTrackType("Data Track Type");
        assertEquals("Data Track Type", links.getDataTrackType());
    }
}
