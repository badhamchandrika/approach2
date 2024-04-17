package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class LogoModelTest extends TestUtil {

    @Test
    void testGetLogoUrl() {
        aemctx.currentResource("/logomodelcontent/logo");
        logo = aemctx.request().adaptTo(LogoModel.class);
        assertNotNull(logo);
        assertEquals("/content/aem-airmiles-web/language-masters/en.html", logo.getLogoUrl());
    }

    @Test
    void testWithoutlogo() {
        aemctx.currentResource("/logomodelcontent/without-logo");
        logo = aemctx.request().adaptTo(LogoModel.class);
        assertNotNull(logo);
        assertNull(logo.getLogo());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "/logomodelcontent/logo,/content/dam/aem-airmiles-web/asset.jpg",
            "/logomodelcontent/logo-with-png_file,/content/dam/aem-airmiles-web/asset.png",
            "/logomodelcontent/logo-with-pdf_file,/content/dam/wknd/en/magazine/western-australia/WA_camping_Adobe.pdf",
            "/logomodelcontent/logo-with-svg_file,/content/dam/core-components-examples/library/adobe-logo.svg",
            "/logomodelcontent/logo-with-gif_file,/content/dam/aem-airmiles-web/asset.gif",
            "/logomodelcontent/without-logourl,"
    })
    void getLogo(final String currRes, final String assetPath){
        aemctx.currentResource(currRes);
        logo = aemctx.request().adaptTo(LogoModel.class);
        assertNotNull(logo);
        assertEquals(assetPath != null ? assetPath : "/content/dam/aem-airmiles-web/asset.jpg", logo.getLogo());
    }

    @Test
    void testLogo_LogoUrl_IsEmpty() {
        aemctx.currentResource("/logomodelcontent/logo-logourl-is-empty");
        logo = aemctx.request().adaptTo(LogoModel.class);
        assertNotNull(logo);
        assertNull(logo.getLogo());

    }

    @Test
    void verifyLogo_LogoUrl_with_int() {
        aemctx.currentResource("/logomodelcontent/verify-logo-logourl-with-int");
        logo = aemctx.request().adaptTo(LogoModel.class);
        assertNotNull(logo);
        assertEquals("12412", logo.getLogo());
    }

    @Test
    void testLogoWith_MP4() {
        aemctx.currentResource("/logomodelcontent/logo-with-mp4");
        logo = aemctx.request().adaptTo(LogoModel.class);
        assertNotNull(logo);
        assertEquals("/content/dam/aem-airmiles-web/asset.mp4", logo.getLogo());
    }
}