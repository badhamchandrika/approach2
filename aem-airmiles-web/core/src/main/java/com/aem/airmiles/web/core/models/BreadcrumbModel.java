package com.aem.airmiles.web.core.models;

import com.adobe.cq.wcm.core.components.models.Breadcrumb;
import lombok.experimental.Delegate;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;

@Model(adaptables = SlingHttpServletRequest.class, 
		adapters = Breadcrumb.class, 
		resourceType = "aem-airmiles-web/components/breadcrumb",
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class BreadcrumbModel extends ComponentModel implements Breadcrumb {

    @Self @Via(type = ResourceSuperType.class)
    @Delegate(types = Breadcrumb.class)
    private Breadcrumb delegate;

}
