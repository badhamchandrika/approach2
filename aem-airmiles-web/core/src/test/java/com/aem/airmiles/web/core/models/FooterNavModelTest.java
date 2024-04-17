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
class FooterNavModelTest extends TestUtil {

    //For Site Details Tab:
    @ParameterizedTest
    @CsvSource(value = {
            "/topnavmodelcontent/topnav,airmiles.ca,https://www.airmiles.ca/en.html",
            "/topnavmodelcontent/invalid_websitename_valid_websiteurl,airmiles34546456dd,https://www.airmiles.ca" +
                    "/en.html",
            "/topnavmodelcontent/null_websitename_valid_websiteurl,,https://www.airmiles.ca/en.html",
            "/topnavmodelcontent/valid_websitename_null_websiteurl,airmiles.ca,",
            "/topnavmodelcontent/null_websitename_websiteurl,,"
    })
    void websiteNames(final String resPath, final String name, final String url){
        aemctx.currentResource(resPath);
        footerNav = aemctx.request().adaptTo(FooterNav.class);
        assertNotNull(footerNav);
        assertEquals(name, footerNav.getSiteDetails().get(0).getSiteName());
        assertEquals(url, footerNav.getSiteDetails().get(0).getWebUrl());
    }

    @Test
    void Add_New_WebsiteName_and_WebsiteURL() {
        aemctx.currentResource("/topnavmodelcontent/with_new_websitename_websiteurl");
        footerNav = aemctx.request().adaptTo(FooterNav.class);
        assertNotNull(footerNav);
        assertEquals(2, footerNav.getSiteDetails().size());
        assertEquals("airmiles.ca", footerNav.getSiteDetails().get(0).getSiteName());
        assertEquals("https://www.airmiles.ca/en.html", footerNav.getSiteDetails().get(0).getWebUrl());
        assertEquals("airmilesshops.ca", footerNav.getSiteDetails().get(1).getSiteName());
        assertEquals("https://www.airmilesshops.ca/en.html", footerNav.getSiteDetails().get(1).getWebUrl());
    }

    @Test
    void empty_SiteDetails_ProfileTools() {
        aemctx.currentResource("/topnavmodelcontent/with_empty_sitedetails_profiletools_tabs");
        footerNav = aemctx.request().adaptTo(FooterNav.class);
        assertNotNull(footerNav);
        assertEquals(0, footerNav.getSiteDetails().size());
    }
}
