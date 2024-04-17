package com.aem.airmiles.web.core.models;

import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import javax.inject.Inject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CharityDetails {
    @Getter
    @Inject
    private String charityDescription;

    @Getter
    @Inject
    private String charityName;
}
