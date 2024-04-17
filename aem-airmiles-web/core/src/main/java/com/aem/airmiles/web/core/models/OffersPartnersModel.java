package com.aem.airmiles.web.core.models;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.resource.Resource;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class OffersPartnersModel {

    @Getter
    @Setter
    @ValueMapValue
    private Boolean standalone;

    @Getter
    @Setter
    @ValueMapValue
    private Integer suggestPartnerNum;

}
