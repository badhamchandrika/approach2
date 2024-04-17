package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.constants.ResourceTypes;
import com.aem.airmiles.web.core.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = { Resource.class,
        SlingHttpServletRequest.class },resourceType = ResourceTypes.SEARCH_MODEL, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchModel extends ComponentModel{

    @ValueMapValue
    @Getter
    @Setter
    private String searchLabel;

    @ValueMapValue
    @Getter
    @Setter
    private String cancelLabel;

    @ValueMapValue
    @Getter
    @Setter
    private String searchIcon;

    @ValueMapValue
    @Getter
    @Setter
    private String pagination;

    @ValueMapValue
    @Getter
    @Setter
    private String searchPlaceholderText;

    @ValueMapValue
    @Getter
    @Setter
    private String searchButtonTrackID;

    @ValueMapValue
    @Getter
    @Setter
    private String searchButtonClickID;

    @ValueMapValue
    @Getter
    @Setter
    private String searchButtonTrackType;

    @ValueMapValue
    @Getter
    @Setter
    private String searchInputTrackID;

    @ValueMapValue
    @Getter
    @Setter
    private String searchInputTrackType;

    @ValueMapValue
    @Getter
    @Setter
    private String searchInputClickID;

    @ValueMapValue
    @Getter
    @Setter
    private String searchSubmitTrackID;

    @ValueMapValue
    @Getter
    @Setter
    private String searchSubmitTrackType;

    @ValueMapValue
    @Getter
    @Setter
    private String searchSubmitClickID;

    @ValueMapValue
    @Getter
    @Setter
    private String searchCancelTrackID;

    @ValueMapValue
    @Getter
    @Setter
    private String searchCancelTrackType;

    @ValueMapValue
    @Getter
    @Setter
    private String searchCancelClickID;

    @ValueMapValue
    @Setter
    private String searchResultPage;

    public String getSearchResultPage() {
        return Utils.getFormattedURL(searchResultPage);
    }

    @Getter
    @Setter
    @ChildResource
    private List<SearchQuickLinks> searchQuickLinks;


}