package com.aem.airmiles.web.core.models;

import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling model to export data list.
 */
@Getter
@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DataList extends ComponentModel{

    /**
     * Identifier for the list item.
     */
    @ValueMapValue
    private String itemID;

    /**
     * Value of the list item.
     */
    @ValueMapValue
    private String dataListItem;
}
