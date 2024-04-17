package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class UseMilesSectionTest extends TestUtil {

    @Test
    void getWayfinder(){
        aemctx.currentResource("/usemilesmodelcontent/usemiles");
        useMiles = aemctx.request().adaptTo(UseMilesSection.class);
        assertEquals("USE MILES", useMiles.getWayfinderText());
    }

    @Test
    void getHeader(){
        aemctx.currentResource("/usemilesmodelcontent/usemiles");
        useMiles = aemctx.request().adaptTo(UseMilesSection.class);
        assertEquals("WELCOME TO YOUR HAPPY PLACE", useMiles.getHeaderText());
    }

    @Test
    void getDataTrackSection(){
        aemctx.currentResource("/usemilesmodelcontent/usemiles");
        useMiles = aemctx.request().adaptTo(UseMilesSection.class);
        assertEquals("usemiles-section", useMiles.getDataTrackSection());
    }

    @Test
    void getTabList(){
        aemctx.currentResource("/usemilesmodelcontent/usemiles");
        useMiles = aemctx.request().adaptTo(UseMilesSection.class);
        assertEquals("tab1", useMiles.getTabsList().get(0).get("tabId"));
        assertEquals("CASH REWARDS", useMiles.getTabsList().get(0).get("tabTitle"));
        assertEquals("/content/dam/aem-airmiles-web/images/dollar_icon.png", useMiles.getTabsList().get(0).get("svgIcon"));
        assertEquals("tab1-track-id", useMiles.getTabsList().get(0).get("dataTrackID"));
        assertEquals("tab1-click-id", useMiles.getTabsList().get(0).get("dataClickID"));
    }
}
