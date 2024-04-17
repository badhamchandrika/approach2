package ca.airmiles.travel.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = Resource.class, resourceType = StaysWidget.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class StaysWidget {

    public static final String RESOURCE_TYPE = "aem-airmiles-travel2/components/stayswidget";

    @Getter
    @ValueMapValue
    private String roomsTypeHeading;

    @Getter
    @ChildResource
    private List<TypeList> roomsTypeList;

    @Getter
    @ValueMapValue
    private String toLocationText;

    @Getter
    @ValueMapValue
    private String checkinLabel;

    @Getter
    @ValueMapValue
    private String checkinText;

    @Getter
    @ValueMapValue
    private String checkoutLabel;

    @Getter
    @ValueMapValue
    private String checkoutText;

    @Getter
    @ValueMapValue
    private String advSearchText;

    @Getter
    @ValueMapValue
    private String buttonText;

}
