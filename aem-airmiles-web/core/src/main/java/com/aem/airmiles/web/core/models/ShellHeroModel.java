package com.aem.airmiles.web.core.models;

import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

/**
 * Model used to handle Shell Hero Component information.
 */
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class ShellHeroModel {

    /**
     * Path of the image to be used as background.
     */
    @Getter
    @ValueMapValue
    private String backgroundImageUrl;

    /**
     * Alternative text to be displayed on background image.
     */
    @Getter
    @ValueMapValue
    private String backgroundImageAltText;

    /**
     * Path of the image to be used as logo.
     */
    @Getter
    @ValueMapValue
    private String logoImageUrl;

    /**
     * Alternative text to be displayed on logo image.
     */
    @Getter
    @ValueMapValue
    private String logoAltText;

    /**
     * Color of the background.
     */
    @Getter
    @ValueMapValue
    private String backgroundColor;

    /**
     * Color to be used in texts.
     */
    @Getter
    @ValueMapValue
    private String textColor;

    /**
     * Text to be displayed in contest title.
     */
    @Getter
    @ValueMapValue
    private String contestTitle;


    /**
     * Text size for title.
     */       
    @ValueMapValue
    @Getter
    private String titleTextSize;


    /**
     * Text to be displayed in contest subtitle.
     */
    @Getter
    @ValueMapValue
    private String contestSubtitle;

    /**
     * Text size for subtitle.
     */

    @ValueMapValue
    @Getter
    private String subtitleTextSize;
    /**
     * Text to be displayed in contest description.
     */
    @Getter
    @ValueMapValue
    private String contestDescription;

    /**
     * Text to be displayed in submit button.
     */
    @Getter
    @ValueMapValue
    private String submitButtonText;

    /**
     * URL to hit in submit button.
     */
    @Getter
    @ValueMapValue
    private String submitButtonUrl;

    /**
     * Color to be used as background in button.
     */
    @Getter
    @ValueMapValue
    private String buttonBackgroundColor;

    /**
     * Color to be used as text color in button.
     */
    @Getter
    @ValueMapValue
    private String buttonTextColor;

    /**
     * Text to be displayed after the checkbox.
     */
    @Getter
    @ValueMapValue
    private String checkBoxText;

    /**
     * Validation output to be placed when checkbox needs to be marked.
     */
    @Getter
    @ValueMapValue
    private String checkboxValidationMessage;

    /**
     * Validation error to be displayed in submit button when there's any error in the user input.
     */
    @Getter
    @ValueMapValue
    private String submitErrorMessage;

    /**
     * Validation error to be displayed in form when there's any error in the user input.
     */
    @Getter
    @ValueMapValue
    private String formErrorMessage;
}
