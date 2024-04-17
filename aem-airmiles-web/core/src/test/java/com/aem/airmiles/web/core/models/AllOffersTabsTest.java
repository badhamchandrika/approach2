package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AllOffersTabsTest {

    AllOffersTabs allOffersTabs;

    @BeforeEach
    void init() throws ParseException {
        assertDoesNotThrow(() -> allOffersTabs = new AllOffersTabs() {{
            setTabName("Trending");
            setTabKey("trending");
            setTabStatus("both");
            setActivityType("mini-feed");
        }});
    }

    @Test
    void test_AllOffersRegion(){
        assertEquals("Trending",allOffersTabs.getTabName());
        assertEquals("trending",allOffersTabs.getTabKey());
        assertEquals("both",allOffersTabs.getTabStatus());
        assertEquals("mini-feed",allOffersTabs.getActivityType());
    }

}