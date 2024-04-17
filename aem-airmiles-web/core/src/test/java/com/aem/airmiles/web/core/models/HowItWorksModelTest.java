package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class used to cover {@link HowItWorksModel}.
 *
 * @author pabpalac.
 */
@ExtendWith(MockitoExtension.class)
class HowItWorksModelTest {

    /**
     * Sling model.
     */
    HowItWorksModel model;

    /**
     * Test fields.
     */
    final String title = "LOREM", subtitle = "IPSUM", ext = ".png", img = "/content/dam/lam/img" + ext, alt = "asset", uniqueID="1a2b3c4d5e";

    /**
     * To be executed before each test.
     */
    @BeforeEach
    void setUp() {
        // Instantiate new model with provided values.
        model = new HowItWorksModel() {{

            // Set title.
            setTitle(title);

            // Set subtitle.
            setSubtitle(subtitle);

            // Set step 1 text.
            setStep1text(title + 1);

            // Set step 1 subtext.
            setStep1subtext(subtitle + 1);

            // Set step 2 text.
            setStep2text(title + 2);

            // Set step 2 subtext.
            setStep2subtext(subtitle + 2);

            // Set step 2 image.
            setStep2image(img.replace(ext, 2 + ext));

            // Set step 2 image alt.
            setStep2imageAltText(alt + 2);

            // Set step 3 text.
            setStep3text(title + 3);

            // Set step 3 subtext.
            setStep3subtext(subtitle + 3);

            // Set step 3 image.
            setStep3image(img.replace(ext, 3 + ext));

            // Set step 3 image alt.
            setStep3imageAltText(alt + 3);

            // Set uniqueID.
            setUniqueID(uniqueID);
        }};
    }

    /**
     * Test retrieval of title property.
     */
    @Test
    void getTitle_shouldGetProvidedTitle() {
        // Check that model does not crash and the title is the provided one.
        assertDoesNotThrow(() -> assertEquals(title, model.getTitle()));
    }

    /**
     * Test retrieval of subtitle property.
     */
    @Test
    void getSubtitle_shouldGetProvidedSubtitle() {
        // Check that model does not crash and the subtitle is the provided one.
        assertDoesNotThrow(() -> assertEquals(subtitle, model.getSubtitle()));
    }

    /**
     * Test retrieval of step 1 text.
     */
    @Test
    void getStep1Text_shouldGetProvidedText() {
        // Check that model does not crash and the text is the provided one.
        assertDoesNotThrow(() -> assertEquals(title + 1, model.getStep1text()));
    }

    /**
     * Test retrieval of step 1 subtext.
     */
    @Test
    void getStep1Subtext_shouldGetProvidedSubtext() {
        // Check that model does not crash and the subtext is the provided one.
        assertDoesNotThrow(() -> assertEquals(subtitle + 1, model.getStep1subtext()));
    }

    /**
     * Test retrieval of step 2 text.
     */
    @Test
    void getStep2Text_shouldGetProvidedText() {
        // Check that model does not crash and the text is the provided one.
        assertDoesNotThrow(() -> assertEquals(title + 2, model.getStep2text()));
    }

    /**
     * Test retrieval of step 2 sub text.
     */
    @Test
    void getStep2Subtext_shouldGetProvidedSubtext() {
        // Check that model does not crash and the subtext is the provided one.
        assertDoesNotThrow(() -> assertEquals(subtitle + 2, model.getStep2subtext()));
    }

    /**
     * Test retrieval of step 2 image.
     */
    @Test
    void getStep2Image_shouldGetProvidedImage() {
        // Check that model does not crash and the image is the provided one.
        assertDoesNotThrow(() -> assertEquals(img.replace(ext, 2 + ext), model.getStep2image()));
    }

    /**
     * Test retrieval of step 2 image alt text.
     */
    @Test
    void getStep2ImageAlt_shouldGetProvidedImageAlt() {
        // Check that model does not crash and the image alt is the provided one.
        assertDoesNotThrow(() -> assertEquals(alt + 2, model.getStep2imageAltText()));
    }

    /**
     * Test retrieval of step 3 text.
     */
    @Test
    void getStep3Text_shouldGetProvidedText() {
        // Check that model does not crash and the text is the provided one.
        assertDoesNotThrow(() -> assertEquals(title + 3, model.getStep3text()));
    }

    /**
     * Test retrieval of step 3 subtext.
     */
    @Test
    void getStep3Subtext_shouldGetProvidedSubtext() {
        // Check that model does not crash and the subtext is the provided one.
        assertDoesNotThrow(() -> assertEquals(subtitle + 3, model.getStep3subtext()));
    }

    /**
     * Test retrieval of step 3 image.
     */
    @Test
    void getStep3Image_shouldGetProvidedImage() {
        // Check that model does not crash and the image is the provided one.
        assertDoesNotThrow(() -> assertEquals(img.replace(ext, 3 + ext), model.getStep3image()));
    }

    /**
     * Test retrieval of step 3 image alt text.
     */
    @Test
    void getStep3ImageAlt_shouldGetProvidedImageAlt() {
        // Check that model does not crash and the image alt is the provided one.
        assertDoesNotThrow(() -> assertEquals(alt + 3, model.getStep3imageAltText()));
    }

    /**
     * Test retrieval of uniqueID property.
     */
    @Test
    void getUinqueID_shouldGetUniqueID() {
        // Check that model does not crash and the title is the provided one.
        assertDoesNotThrow(() -> assertEquals(uniqueID, model.getUniqueID()));
    }
}