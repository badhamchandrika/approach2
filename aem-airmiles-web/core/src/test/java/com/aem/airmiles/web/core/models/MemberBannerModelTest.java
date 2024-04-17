package com.aem.airmiles.web.core.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MemberBannerModelTest {

    MemberBannerModel memberBannerModel = new MemberBannerModel();
    @BeforeEach
    void init() throws ParseException {
        memberBannerModel.setCashTitle("Your Cash Account");
        memberBannerModel.setCashContent("Use your Cash Miles towards travel, merchandise, entertainment and more! Choose from over 1,200 Rewards.");
        memberBannerModel.setDreamTitle("Your Dream Account");
        memberBannerModel.setDreamContent("Use your Dream Miles towards travel, merchandise, entertainment and more! Choose from over 1,200 Rewards.");
        memberBannerModel.setCashDataTrackID("cash-rewards");
        memberBannerModel.setDreamDataTrackID("dream-rewards");
        memberBannerModel.setDataTrackClickID("MemberBannerReward-Cash-Dream-ID");
        memberBannerModel.setHexBtnColor("#119977");
        memberBannerModel.setHexTextColor("#fefefe");
        memberBannerModel.setLinkTarget("_self");
        memberBannerModel.setLinkText("Set Balance Preferences");
        memberBannerModel.setLinkUrl("http://www.airmiles.ca/rewards");
    }

    @Test
    void test_MemberBanner(){
        assertEquals("Your Cash Account",memberBannerModel.getCashTitle());
        assertEquals("Use your Cash Miles towards travel, merchandise, entertainment and more! Choose from over 1,200 Rewards.",memberBannerModel.getCashContent());
        assertEquals("Your Dream Account",memberBannerModel.getDreamTitle());
        assertEquals("Use your Dream Miles towards travel, merchandise, entertainment and more! Choose from over 1,200 Rewards.",memberBannerModel.getDreamContent());
        assertEquals("cash-rewards",memberBannerModel.getCashDataTrackID());
        assertEquals("dream-rewards",memberBannerModel.getDreamDataTrackID());
        assertEquals("MemberBannerReward-Cash-Dream-ID",memberBannerModel.getDataTrackClickID());
        assertEquals("#119977",memberBannerModel.getHexBtnColor());
        assertEquals("#fefefe",memberBannerModel.getHexTextColor());
        assertEquals("_self",memberBannerModel.getLinkTarget());
        assertEquals("Set Balance Preferences",memberBannerModel.getLinkText());
        assertEquals("http://www.airmiles.ca/rewards",memberBannerModel.getLinkUrl());
    }

    @Test
    void test_LinkDetails(){
        List<MemberBanner> linkDetails = new ArrayList<>();
        MemberBanner memberBanner = new MemberBanner();
        linkDetails.add(memberBanner);
        memberBannerModel.setLinkDetails(linkDetails);
        assertEquals(linkDetails,memberBannerModel.getLinkDetails());
    }
}