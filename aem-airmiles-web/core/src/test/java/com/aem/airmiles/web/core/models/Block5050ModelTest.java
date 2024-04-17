package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.lang.Boolean.TRUE;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Class used to cover {@link  Block5050Model Block 50%/50% model} test cases.
 */
class Block5050ModelTest {

    /**
     * Test model with simulating provided values.
     */
    @Test
    void modelValues() {

        // Declare strings to test.
        final String IMG_ALIGN = "top";
        final String IMG_TXT_ID = "1234";
        final String TRACK_CAT = "block5050";
        final String IMG_ALT_TXT = "alt text";
        final String PRE_TITLE = "Become a collector";
        final String TRACK_NAME = "block5050-homepage";
        final String TITLE_TXT = "Dont miss out on rewards";
        final String IMG_FR = "/content/dam/images/image.jpg";
        final String DESC_TEXT = "So may rewards, even more ways to get them.";

        // Assert this process doesn't throw any Reflection Exception.
        assertDoesNotThrow(() -> {

            // Create a new instance of Block5050Model initializing its values.
            new Block5050Model() {{

                // Use reflection to insert model values.
                writeField(this, "preTitle", PRE_TITLE, TRUE);
                writeField(this, "titleText", TITLE_TXT, TRUE);
                writeField(this, "imageAlign", IMG_ALIGN, TRUE);
                writeField(this, "trackName", TRACK_NAME, TRUE);
                writeField(this, "imageTextId", IMG_TXT_ID, TRUE);
                writeField(this, "ctas", new ArrayList<>(), TRUE);
                writeField(this, "trackCategory", TRACK_CAT, TRUE);
                writeField(this, "imageAltText", IMG_ALT_TXT, TRUE);
                writeField(this, "descriptionText", DESC_TEXT, TRUE);
                writeField(this, "fileReference", IMG_FR, TRUE);

                // Assertions.
                assertNotNull(getCtas());
                assertEquals(PRE_TITLE, getPreTitle());
                assertEquals(TITLE_TXT, getTitleText());
                assertEquals(IMG_ALIGN, getImageAlign());
                assertEquals(TRACK_NAME, getTrackName());
                assertEquals(IMG_TXT_ID, getImageTextId());
                assertEquals(TRACK_CAT, getTrackCategory());
                assertEquals(IMG_ALT_TXT, getImageAltText());
                assertEquals(DESC_TEXT, getDescriptionText());
                assertEquals(IMG_FR, getFileReference());
            }};
        });
    }
}