package com.aem.airmiles.web.core.models;

import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

/**
 * Model used to expose Quicksearch component properties.
 *
 * @author pabpalac.
 */
@Getter
@Model(adaptables = Resource.class, defaultInjectionStrategy = OPTIONAL)
public class QuicksearchModel {

    /**
     * Component's id.
     */
    @ValueMapValue
    private String id;

    /**
     * Search input custom placeholder.
     */
    @ValueMapValue
    private String placeholder;

    /**
     * Suggestive texts count.
     */
    @ValueMapValue
    private String suggestiveTexts;
}
