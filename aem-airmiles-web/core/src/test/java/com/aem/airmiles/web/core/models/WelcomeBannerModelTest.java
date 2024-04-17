package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WelcomeBannerModelTest {

    WelcomeBannerModel welcomeBannerModel;

    @BeforeEach
    void setUp() {
        welcomeBannerModel = new WelcomeBannerModel();
    }

    @Test
    void testWelcomeBannerFields() {
        welcomeBannerModel.setGoodMorningText("Good Morning");
        welcomeBannerModel.setGoodAfternoonText("Good Afternoon");
        welcomeBannerModel.setGoodEveningText("Good Evening");
        welcomeBannerModel.setCashMilesText("Cash Miles");
        welcomeBannerModel.setDreamMilesText("Dream Miles");
        welcomeBannerModel.setBlueCollectorText("Blue Collector");
        welcomeBannerModel.setGoldCollectorText("Gold Collector");
        welcomeBannerModel.setOnyxCollectorText("Onyx Collector");
        welcomeBannerModel.setMilesAllocationText("When earning Miles, you’ve allocated:");
        welcomeBannerModel.setCashPercentageText("to Cash Miles");
        welcomeBannerModel.setDreamPercentageText("to Dream Miles");
        welcomeBannerModel.setBlueTierColor("none");
        welcomeBannerModel.setGoldTierColor("#785C09");
        welcomeBannerModel.setOnyxTierColor("#000000");
        welcomeBannerModel.setSetBalancePreferenceCTATxt("Set Balance Preferences");
        welcomeBannerModel.setSetBalancePreferenceCTAUrl("/balance-preference");
        welcomeBannerModel.setBalancePreferenceUrlTarget("_blank");
        welcomeBannerModel.setBalDataClickID("bal_click_id");
        welcomeBannerModel.setBalDataTrackID("bal_track_id");
        welcomeBannerModel.setBalDataTrackType("Internal");
        welcomeBannerModel.setCta2LinkType("button");
        welcomeBannerModel.setCta2HexBtnColor("#fff");
        welcomeBannerModel.setCta2HexTextColor("#000");
        welcomeBannerModel.setCta2LnkColor("Blue");
        welcomeBannerModel.setMockJson("false");
        assertEquals("Good Afternoon", welcomeBannerModel.getGoodAfternoonText());
        assertEquals("Good Morning", welcomeBannerModel.getGoodMorningText());
        assertEquals("Good Evening", welcomeBannerModel.getGoodEveningText());
        assertEquals("Cash Miles", welcomeBannerModel.getCashMilesText());
        assertEquals("Dream Miles", welcomeBannerModel.getDreamMilesText());
        assertEquals("Blue Collector", welcomeBannerModel.getBlueCollectorText());
        assertEquals("Gold Collector", welcomeBannerModel.getGoldCollectorText());
        assertEquals("Onyx Collector", welcomeBannerModel.getOnyxCollectorText());
        assertEquals("Set Balance Preferences", welcomeBannerModel.getSetBalancePreferenceCTATxt());
        assertEquals("/balance-preference", welcomeBannerModel.getSetBalancePreferenceCTAUrl());
        assertEquals("_blank", welcomeBannerModel.getBalancePreferenceUrlTarget());
        assertEquals("bal_click_id", welcomeBannerModel.getBalDataClickID());
        assertEquals("bal_track_id", welcomeBannerModel.getBalDataTrackID());
        assertEquals("Internal", welcomeBannerModel.getBalDataTrackType());
        assertEquals("button", welcomeBannerModel.getCta2LinkType());
        assertEquals("#fff", welcomeBannerModel.getCta2HexBtnColor());
        assertEquals("#000", welcomeBannerModel.getCta2HexTextColor());
        assertEquals("Blue", welcomeBannerModel.getCta2LnkColor());
        assertEquals("When earning Miles, you’ve allocated:", welcomeBannerModel.getMilesAllocationText());
        assertEquals("to Cash Miles", welcomeBannerModel.getCashPercentageText());
        assertEquals("to Dream Miles", welcomeBannerModel.getDreamPercentageText());
        assertEquals("none", welcomeBannerModel.getBlueTierColor());
        assertEquals("#785C09", welcomeBannerModel.getGoldTierColor());
        assertEquals("#000000", welcomeBannerModel.getOnyxTierColor());
        assertEquals("false", welcomeBannerModel.getMockJson());
    }

    @Test
    void getId_shouldGetGivenId(){
        welcomeBannerModel.setId("cmp-id");
        assertEquals("cmp-id", welcomeBannerModel.getId());

    }
}
