package com.aem.airmiles.web.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)

public class OffersQueryParamModel {


    @Getter
    @Setter
    @ValueMapValue
    private String sortOptions;

    @Getter
    @Setter
    @ValueMapValue
    private List<String> massOffer;

    @Getter
    @Setter
    @ValueMapValue
    private List<String> programType;

    @Getter
    @Setter
    @ValueMapValue
    private List<String>  partnerId;

    @Getter
    @Setter
    @ValueMapValue
    private String  promotionId;

    @Getter
    @Setter
    @ValueMapValue
    private List<String> categoryId;
    @Getter
    @Setter
    @ValueMapValue
    private List<String>  subcategoryId;

}
