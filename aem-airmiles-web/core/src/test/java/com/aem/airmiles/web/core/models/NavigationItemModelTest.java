package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class NavigationItemModelTest {

    NavigationItemModel navigationItemModel = new NavigationItemModel();

    @BeforeEach
    void setUp() {

        navigationItemModel.setNavTitle("Offers");
        navigationItemModel.setNavLink("airmiles.ca");
        navigationItemModel.setDataTrackId("Offers Id");
        navigationItemModel.setDataTrackClick("Offers Clicked");
        navigationItemModel.setDataTrackType("button");
        navigationItemModel.setCtaText("See all Offers");
        navigationItemModel.setCtaUrl("airmiles.ca/offer");
        navigationItemModel.setCtaUrl("airmiles.ca/offer");
        navigationItemModel.setTargetCtaUrl("airmiles.ca/offer");
        navigationItemModel.setLinkTarget("_blank");
        navigationItemModel.setCtaDataTrackId("CTA track Id");
        navigationItemModel.setCtaDataTrackClick("CTA track Click");
        navigationItemModel.setCtaDataTrackType("internal");
        navigationItemModel.setHideCtaInMob("True");
    }

    @Test
    void getNavItem_navTitle() {

        assertEquals("Offers", navigationItemModel.getNavTitle());
        assertEquals("airmiles.ca", navigationItemModel.getNavLink());
        assertEquals("Offers Id", navigationItemModel.getDataTrackId());
        assertEquals("Offers Clicked", navigationItemModel.getDataTrackClick());
        assertEquals("button", navigationItemModel.getDataTrackType());
        assertEquals("See all Offers", navigationItemModel.getCtaText());
        assertEquals("airmiles.ca/offer", navigationItemModel.getCtaUrl());
        assertEquals("airmiles.ca/offer", navigationItemModel.getTargetCtaUrl());
        assertEquals("_blank",navigationItemModel.getLinkTarget());
        assertEquals("CTA track Id",navigationItemModel.getCtaDataTrackId());
        assertEquals("CTA track Click",navigationItemModel.getCtaDataTrackClick());
        assertEquals("internal",navigationItemModel.getCtaDataTrackType());
        assertEquals("True",navigationItemModel.getHideCtaInMob());
    }

    @Test
    void test_navSectionList(){
        NavigationSectionBean navigationSectionBean = new NavigationSectionBean();
        List<NavigationSectionBean> navigationSectionBeanList = new ArrayList<>();
        navigationSectionBeanList.add(navigationSectionBean);
        navigationItemModel.setNavSectionList(navigationSectionBeanList);
        assertEquals(navigationSectionBeanList, navigationItemModel.getNavSectionList());
    }

    @Test
    void test_subSectionPaths(){
        ContentFragmentPaths contentFragmentPaths = new ContentFragmentPaths();
        List<ContentFragmentPaths> contentFragmentPathsList = new ArrayList<>();
        contentFragmentPathsList.add(contentFragmentPaths);
        navigationItemModel.setSubSectionPaths(contentFragmentPathsList);
        assertEquals(contentFragmentPathsList, navigationItemModel.getSubSectionPaths());
    }

}