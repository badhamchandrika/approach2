package com.aem.airmiles.web.core.models;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class used to cover {@link ShellHeroModel Shell Hero Model} cases.
 *
 * @author pabpalac.
 */
class ShellHeroModelTest {

    /**
     * Initializes the model with null values, verifies that the model is not null and has its values set as null.
     */
    @Test
    void newShellHeroModel_shouldShouldNotCrash_andShouldHasAllGettersAsNull() {

        // Check that new shell hero model instantiation does not crash.
        assertDoesNotThrow(() -> new ShellHeroModel() {{

            // Assert that background image is null.
            assertNull(getBackgroundImageUrl());

            // Assert that background image alternative text is null.
            assertNull(getBackgroundImageAltText());

            // Assert that logo image URL is null.
            assertNull(getLogoImageUrl());

            // Assert that logo alternative text is null.
            assertNull(getLogoAltText());

            // Assert that background color is null.
            assertNull(getBackgroundColor());

            // Assert that contest title is null.
            assertNull(getContestTitle());

           // Assert that contest title text size is null
            assertNull(getTitleTextSize());
            
            // Assert that text color is null.
            assertNull(getTextColor());

            // Assert that contest subtitle is null.
            assertNull(getContestSubtitle());

            // Assert that contest subtitle text size is null.
            assertNull(getSubtitleTextSize());
            
            // Assert that contest description is null.
            assertNull(getContestDescription());

            // Assert that submit button text is null.
            assertNull(getSubmitButtonText());

            // Assert that button text color is null.
            assertNull(getButtonTextColor());

            // Assert that button background color is null.
            assertNull(getButtonBackgroundColor());

            // Assert that checkbox text is null.
            assertNull(getCheckBoxText());

            // Assert that checkbox validation message is null.
            assertNull(getCheckboxValidationMessage());

            // Assert that submit error message is null.
            assertNull(getSubmitErrorMessage());

            // Assert that form error message is null.
            assertNull(getFormErrorMessage());

            // Assert that submit button URL is null.
            assertNull(getSubmitButtonUrl());
        }});
    }
}