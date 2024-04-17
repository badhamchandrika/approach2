package ca.airmiles.travel.core.models;

import com.day.cq.wcm.api.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class LinkModelTest {

    LinkModel ctaLink = new LinkModel();

    @BeforeEach
    void setup(){
        ctaLink.setLinkUrl("/content/aem-airmiles-travel2/ca/en");
        ctaLink.setDataTrackID("data-track-id");
        ctaLink.setDataClickID("data-click-id");
        ctaLink.setDataTrackType("data-track-type");
        ctaLink.setLinkText("Flights");
        ctaLink.setLinkTarget("_blank");
        ctaLink.setIcon("fa fa-plane");
        ctaLink.setCurrentPage("/content/aem-airmiles-travel2/ca/en");
    }

    @Test
    void test_Link() {
        assertEquals("/content/aem-airmiles-travel2/ca/en.html", ctaLink.getLinkUrl());
        assertEquals("data-track-id", ctaLink.getDataTrackID());
        assertEquals("data-click-id", ctaLink.getDataClickID());
        assertEquals("data-track-type", ctaLink.getDataTrackType());
        assertEquals("Flights", ctaLink.getLinkText());
        assertEquals("_blank", ctaLink.getLinkTarget());
        assertEquals("fa fa-plane", ctaLink.getIcon());
        assertEquals(ctaLink.isActive(),true);

    }
}
