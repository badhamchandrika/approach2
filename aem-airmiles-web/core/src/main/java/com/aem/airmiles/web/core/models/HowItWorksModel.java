package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import static com.adobe.cq.export.json.ExporterConstants.SLING_MODEL_EXPORTER_NAME;
import static com.adobe.cq.export.json.ExporterConstants.SLING_MODEL_EXTENSION;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

/**
 * Sling model used to expose 'How It Works?' component.
 *
 * @author pabpalac.
 */
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
@Exporter(name = SLING_MODEL_EXPORTER_NAME, extensions = SLING_MODEL_EXTENSION)
public class HowItWorksModel {

    /**
     * Component title.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String title;

    /**
     * Component subtitle.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String subtitle;

    /**
     * Component step 1 text.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String step1text;

    /**
     * Component step 1 subtext.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String step1subtext;

    /**
     * Component step 2 text.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String step2text;

    /**
     * Component step 2 subtext.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String step2subtext;

    /**
     * Component step 2 image.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String step2image;

    /**
     * Component step 2 image.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String step2imageAltText;

    /**
     * Component step 3 text.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String step3text;

    /**
     * Component step 3 subtext.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String step3subtext;

    /**
     * Component step 3 image.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String step3image;

    /**
     * Component step 3 image.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String step3imageAltText;

    /**
     * Component uniqueID for personalization.
     */
    @Getter
    @Setter
    @ValueMapValue
    private String uniqueID;
}