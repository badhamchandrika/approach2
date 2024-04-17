package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class used to cover {@link WebBannerModel}.
 *
 * @author mathavelicheti.
 */
@ExtendWith(MockitoExtension.class)
class WebBannerModelTest {

    /**
     * Sling model.
     */
    WebBannerModel model;

    /**
     * Test fields.
     */
    final String imageLargeScreen = "/content/dam/folder1/img.png", imageMediumScreen = "/content/dam/folder1/img.png",
            imageSmallScreen = "/content/dam/folder1/img.png", imageAlt = "Alt text",
            adUrl = "/content/airmiles/en.html", adUrlTarget = "_self", linkAriaLabel = "banner-label",
            dataTrackId = "track-id", dataTrackClick = "track-click", dataTrackType = "track-type",
            dataBannerName = "banner-name", dataBannerCategory = "banner-category", id = "test-id";

    /**
     * To be executed before each test.
     */
    @BeforeEach
    void setUp() {
        // Instantiate new model.
        model = new WebBannerModel() {{
            setAdUrl(adUrl);
            setId(id);
            setAdUrlTarget(adUrlTarget);
            setDataBannerName(dataBannerName);
            setDataBannerCategory(dataBannerCategory);
            setDataTrackClick(dataTrackClick);
            setDataTrackId(dataTrackId);
            setDataTrackType(dataTrackType);
            setImageAlt(imageAlt);
            setImageLargeScreen(imageLargeScreen);
            setImageMediumScreen(imageMediumScreen);
            setImageSmallScreen(imageSmallScreen);
            setLinkAriaLabel(linkAriaLabel);
        }};
    }

    /**
     * Test retrieval of title property.
     */
    @Test
    void getAdUrl_shouldGetProvidedAdUrl() {
        assertDoesNotThrow(() -> assertEquals(adUrl, model.getAdUrl()));
    }

    @Test
    void getId_shouldGetProvidedId() {
        assertDoesNotThrow(() -> assertEquals(id, model.getId()));
    }

    @Test
    void getAdUrlTarget_shouldGetProvidedAdUrlTarget() {
        assertDoesNotThrow(() -> assertEquals(adUrlTarget, model.getAdUrlTarget()));
    }

    @Test
    void getDataBannerName_shouldGetProvidedDataBannerName() {
        assertDoesNotThrow(() -> assertEquals(dataBannerName, model.getDataBannerName()));
    }

    @Test
    void getDataBannerCategory_shouldGetProvidedDataBannerCategory() {
        assertDoesNotThrow(() -> assertEquals(dataBannerCategory, model.getDataBannerCategory()));
    }

    @Test
    void getDataTrackClick_shouldGetProvidedDataTrackClick() {
        assertDoesNotThrow(() -> assertEquals(dataTrackClick, model.getDataTrackClick()));
    }

    @Test
    void getDataTrackType_shouldGetProvidedDataTrackType() {
        assertDoesNotThrow(() -> assertEquals(dataTrackType, model.getDataTrackType()));
    }

    @Test
    void getDataTrackId_shouldGetProvidedDataTrackId() {
        assertDoesNotThrow(() -> assertEquals(dataTrackId, model.getDataTrackId()));
    }

    @Test
    void getImageAlt_shouldGetProvidedImageAlt() {
        assertDoesNotThrow(() -> assertEquals(imageAlt, model.getImageAlt()));
    }

    @Test
    void getImageLargeScreen_shouldGetProvidedImageLargeScreen() {
        assertDoesNotThrow(() -> assertEquals(imageLargeScreen, model.getImageLargeScreen()));
    }

    @Test
    void getImageMediumScreen_shouldGetProvidedImageMediumScreen() {
        assertDoesNotThrow(() -> assertEquals(imageMediumScreen, model.getImageMediumScreen()));
    }

    @Test
    void getImageSmallScreen_shouldGetProvidedImageSmallScreen() {
        assertDoesNotThrow(() -> assertEquals(imageSmallScreen, model.getImageSmallScreen()));
    }

    @Test
    void getLinkAriaLabel_shouldGetLinkAriaLabel() {
        assertDoesNotThrow(() -> assertEquals(linkAriaLabel, model.getLinkAriaLabel()));
    }
}
