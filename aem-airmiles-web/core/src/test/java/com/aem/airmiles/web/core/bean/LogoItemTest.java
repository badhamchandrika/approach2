package com.aem.airmiles.web.core.bean;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class used to cover logo item.
 *
 * @author pabpalac.
 */
class LogoItemTest {

    /**
     * Test that model creation does not crash and make operations inside model creation.
     */
    @Test
    void modelValues() {

        // Ensure model does not crash.
        assertDoesNotThrow(() -> new LogoItem(){{

            // Check that logo URL is null.
            assertNull(getLogoUrl());

            // Set any text as alt text.
            setLogoAltText("altTxt");

            // Check that logo alt text is different from null.
            assertNotNull(getLogoAltText());

            // Ensure is hidden in mobile.
            assertFalse(isHideInMobile());

            // Ensure is hidden in tablet.
            assertFalse(isHideInTablet());
        }});
    }
}