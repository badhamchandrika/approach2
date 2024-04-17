package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.resource.Resource;

import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},resourceType="aem-airmiles-web/components/airmiles-core/signin",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class SignInModel{

    @Setter
    @Getter
    @ValueMapValue
    private String logoReference;

    @Setter
    @Getter
    @ValueMapValue
    private String loginMessage;

    @Setter
    @Getter
    @ValueMapValue
    private String imageAltText;

    @Setter
    @Getter
    @ValueMapValue
    private String androidURL;

    @Setter
    @Getter
    @ValueMapValue
    private String androidDeeplink;

    @Setter
    @Getter
    @ValueMapValue
    private String iosURL;

    @Setter
    @Getter
    @ValueMapValue
    private String iosDeeplink;

    @Setter
    @Getter
    @ValueMapValue
    private String replaceCompClass;

    @Setter
    @Getter
    @ChildResource
    private List<AllOffersCTA> loginButtons;
}
