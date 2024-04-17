package com.aem.airmiles.web.core.bean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class used to cover {@link TopNavItem TopNavItem class}.
 *
 * @author pabpalac.
 */
class TopNavItemTest {

    /**
     * Model to be tested.
     */
    private static TopNavItem model;

    /**
     * To be executed before all the tests.
     */
    @BeforeAll
    static void setUp() {
        // Create a new instance of the model and fill it with data.
        model = new TopNavItem(){{
           setSiteName("Airmiles");
           setWebUrl("airmiles.ca");
           setUrlTarget("airmiles.ca/en.html");
           setUid("1234");
           setClickAction("action");
           setDataTrackID("1234");
           setDataClickID("1234");
           setDataTrackType("1234");
        }};
    }

    /**
     * Checks that the site name is not null.
     */
    @Test
    void siteName_shouldNotBeNull() {
        assertNotNull(model.getSiteName());
    }

    /**
     * Checks that the web url is not null.
     */
    @Test
    void webUrl_shouldNotBeNull() {
        assertNotNull(model.getWebUrl());
    }

    /**
     * Checks that the url target is not null.
     */
    @Test
    void urlTarget_shouldNotBeNull() {
        assertNotNull(model.getUrlTarget());
    }

    /**
     * Checks that the uid is not null.
     */
    @Test
    void uid_shouldNotBeNull() {
        assertNotNull(model.getUid());
    }

    /**
     * Checks that the click action is not null.
     */
    @Test
    void clickAction_shouldNotBeNull() {
        assertNotNull(model.getClickAction());
    }

    /**
     * Checks that the data track id is not null.
     */
    @Test
    void dataTrackId_shouldNotBeNull() {
        assertNotNull(model.getDataTrackID());
    }

    /**
     * Checks that the data click id is not null.
     */
    @Test
    void dataClickId_shouldNotBeNull() {
        assertNotNull(model.getDataClickID());
    }

    /**
     * Checks that the data track type is not null.
     */
    @Test
    void dataTrackType_shouldNotBeNull() {
        assertNotNull(model.getDataTrackType());
    }
}