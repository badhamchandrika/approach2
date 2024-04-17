package com.aem.airmiles.web.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TransactionDetailModel {
    @Getter
    @Inject
    private String activityType;

    @Getter
    @Inject
    private String channelType;

    @Getter
    @Inject
    private String redemptionStatus;
    @Getter
    @Inject
    private String channelLabel;
    @Getter
    @Inject
    private String logoSrc;
    @Getter
    @Inject
    private String logoAltText;
    @Getter
    @Inject
    @JsonProperty("charityDetails")
    @ChildResource(name = "charitySection")
    List<CharityDetails> charitySection;


}
