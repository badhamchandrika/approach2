package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = Resource.class,resourceType="aem-airmiles-web/components/airmiles-core/globalalert",defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class GlobalAlertModel {

    @Setter
    @Getter
    @ValueMapValue
    private Integer duration;

    @Setter
    @Getter
    @ValueMapValue
    private String uniqueId;

    @Setter
    @Getter
    @ValueMapValue
    private String id;

    @Setter
    @Getter
    @ValueMapValue
    private boolean active;

    @Setter
    @Getter
    @ValueMapValue
    private boolean isInjectable;

    @Setter
    @Getter
    @ChildResource(name = "globalAlertDetails")
    List<GlobalAlerts> globalAlertDetails;
}
