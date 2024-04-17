package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class used to cover {@link HighlightCardModel}.
 *
 * @author pabpalac.
 */
@ExtendWith(MockitoExtension.class)
class HighlightCardModelTest {

    /**
     * Sling model.
     */
    HighlightCardModel model;

    /**
     * Test fields.
     */
    final String title = "LOREM", description = "IPSUM", img = "/content/dam/folder1/img.png", altText = "Alt text";
    final Boolean  isIcon = true;

    /**
     * To be executed before each test.
     */
    @BeforeEach
    void setUp() {
        // Instantiate new model.
        model = new HighlightCardModel() {{

            // Set card title.
            setTitle(title);

            // Set card description.
            setDescription(description);

            // Set card image.
            setCardImage(img);

            //set alt text
            setImageAltText(altText);

            //Set icon as true
            setIsIcon(true);
        }};
    }

    /**
     * Test retrieval of title property.
     */
    @Test
    void getTitle_shouldGetProvidedTitle() {
        assertDoesNotThrow(() -> assertEquals(title, model.getTitle()));
    }

    /**
     * Test retrieval of description property.
     */
    @Test
    void getDescription_shouldGetProvidedDescription() {
        assertDoesNotThrow(() -> assertEquals(description, model.getDescription()));
    }

    /**
     * Test retrieval of image property.
     */
    @Test
    void getCardImage_shouldGetProvidedImg() {
        assertDoesNotThrow(() -> assertEquals(img, model.getCardImage()));
    }

    @Test
    void getCardImageAltText_shouldGetProvidedImg() {
        assertDoesNotThrow(() -> assertEquals(altText, model.getImageAltText()));
    }

    @Test
    void isIcon_shouldCheckIfImageIsIcon() {
        assertDoesNotThrow(() -> assertEquals(isIcon, model.getIsIcon()));
    }

}
