package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

/**
 * Sling model used to retrieve card configurations.
 *
 * @author pabpalac.
 */
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class HighlightCardModel extends CTALink {

    /**
     * Card title.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String title;

    /**
     * Card description.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String description;

    /**
     * Image for the card.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String cardImage;

    /**
     * Alternate Text for image.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String imageAltText;

    /**
     * Flag to display image as icon.
     */
    @Getter
    @Setter
    @ValueMapValue
    private Boolean isIcon;
}

