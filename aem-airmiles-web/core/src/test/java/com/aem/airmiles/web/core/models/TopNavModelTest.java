package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TopNavModelTest extends TestUtil {

    //For Site Details Tab:
    @ParameterizedTest
    @CsvSource(value = {
            "/topnavmodelcontent/topnav,airmiles.ca,https://www.airmiles.ca/en.html",
            "/topnavmodelcontent/invalid_websitename_valid_websiteurl,airmiles34546456dd," +
                    "https://www.airmiles.ca/en.html",
            "/topnavmodelcontent/null_websitename_valid_websiteurl,,https://www.airmiles.ca/en.html",
            "/topnavmodelcontent/valid_websitename_null_websiteurl,airmiles.ca,",
            "/topnavmodelcontent/null_websitename_websiteurl,,"
    })
    void websiteNames_cases(final String resPath, final String siteName, final String webUrl){
        aemctx.currentResource(resPath);
        topNav = aemctx.request().adaptTo(TopNav.class);
        assertNotNull(topNav);
        assertEquals(siteName, topNav.getSiteDetails().get(0).getSiteName());
        assertEquals(webUrl, topNav.getSiteDetails().get(0).getWebUrl());
    }

    @Test
    void Add_New_WebsiteName_and_WebsiteURL() {
        aemctx.currentResource("/topnavmodelcontent/with_new_websitename_websiteurl");
        topNav = aemctx.request().adaptTo(TopNav.class);
        assertNotNull(topNav);
        assertEquals(2, topNav.getSiteDetails().size());
        assertEquals("airmiles.ca", topNav.getSiteDetails().get(0).getSiteName());
        assertEquals("https://www.airmiles.ca/en.html", topNav.getSiteDetails().get(0).getWebUrl());
        assertEquals("top_nav", topNav.getSiteDetails().get(0).getDataTrackID());
        assertEquals("airmiles_ca", topNav.getSiteDetails().get(0).getDataClickID());
        assertEquals("internal", topNav.getSiteDetails().get(0).getDataTrackType());
        assertEquals("airmilesshops.ca", topNav.getSiteDetails().get(1).getSiteName());
        assertEquals("https://www.airmilesshops.ca/en.html", topNav.getSiteDetails().get(1).getWebUrl());
        assertEquals("airmilesshops_ca", topNav.getSiteDetails().get(1).getDataClickID());

    }

    @Test
    void empty_SiteDetails_ProfileTools() {
        aemctx.currentResource("/topnavmodelcontent/with_empty_sitedetails_profiletools_tabs");
        topNav = aemctx.request().adaptTo(TopNav.class);
        assertNotNull(topNav);
        assertEquals(0, topNav.getSiteDetails().size());
        assertEquals(0, topNav.getProfileToolsDetails().size());
    }

    @Test
    void testgetProfileToolsDetails_InvalidData() {
        aemctx.currentResource("/topnavmodelcontent/invalid_profiletooltitle_websiteurl");
        topNav = aemctx.request().adaptTo(TopNav.class);
        assertNotNull(topNav);
        assertEquals("12323423", topNav.getProfileToolsDetails().get(0).getSiteName());
    }

    @Test
    void ValidProfileToolTitle_InvalidWebsiteURL() {
        aemctx.currentResource("/topnavmodelcontent/valid_profiletooltitle_invalid_websiteurl");
        topNav = aemctx.request().adaptTo(TopNav.class);
        assertNotNull(topNav);
        assertEquals("English", topNav.getProfileToolsDetails().get(0).getSiteName());
    }

    //For Profile Tools Tab:
    @ParameterizedTest
    @CsvSource(value = {
            "/topnavmodelcontent/topnav,English,https://www.airmiles.ca/en.html",
            "/topnavmodelcontent/invalid_profiletooltitle_valid_websiteurl,English@#$%,https://www.airmiles.ca/en.html",
            "/topnavmodelcontent/null_profiletooltitle_valid_websiteurl,,https://www.airmiles.ca/en.html",
            "/topnavmodelcontent/valid_profiletooltitle_null_websiteurl,English,",
            "/topnavmodelcontent/null_profiletooltitle_websiteurl,,"

    })
    void topNavLangCases(final String resPath, final String lang, final String url) {
        aemctx.currentResource(resPath);
        topNav = aemctx.request().adaptTo(TopNav.class);
        assertNotNull(topNav);
        assertEquals(lang, topNav.getProfileToolsDetails().get(0).getSiteName());
        assertEquals(url, topNav.getProfileToolsDetails().get(0).getWebUrl());
    }

    @Test
    void Add_New_ProfileToolTitle_and_WebsiteURL() {
        aemctx.currentResource("/topnavmodelcontent/with_new_profiletooltitle_websiteurl");
        topNav = aemctx.request().adaptTo(TopNav.class);
        assertNotNull(topNav);
        assertEquals(4, topNav.getProfileToolsDetails().size());
        assertEquals("English", topNav.getProfileToolsDetails().get(0).getSiteName());
        assertEquals("https://www.airmiles.ca/en.html", topNav.getProfileToolsDetails().get(0).getWebUrl());
        assertEquals("Français", topNav.getProfileToolsDetails().get(1).getSiteName());
        assertEquals("https://www.airmiles.ca/fr.html", topNav.getProfileToolsDetails().get(1).getWebUrl());
        assertEquals("Sign in", topNav.getProfileToolsDetails().get(2).getSiteName());
        assertEquals("https://oauth.airmiles.ca/login", topNav.getProfileToolsDetails().get(2).getWebUrl());
        assertEquals("Join now", topNav.getProfileToolsDetails().get(3).getSiteName());
        assertEquals("https://www.airmiles.ca/en/join.html", topNav.getProfileToolsDetails().get(3).getWebUrl());
    }
}
