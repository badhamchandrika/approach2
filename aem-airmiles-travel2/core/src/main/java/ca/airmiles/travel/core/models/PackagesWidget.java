package ca.airmiles.travel.core.models;

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

@Model(adaptables = Resource.class,resourceType = {"aem-airmiles-travel2/components/packageswidget","aem-airmiles-travel2/components/bundlingwidget"},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PackagesWidget extends FlightWidget {

    @Getter
    @Setter
    @ValueMapValue
    private String buildATripText;

    @Getter
    @Setter
    @ValueMapValue
    private String allInclusiveText;

    @Getter
    @Setter
    @ValueMapValue
    private String flightsCheckboxText;

    @Getter
    @Setter
    @ValueMapValue
    private String hotelsCheckboxText;

    @Getter
    @Setter
    @ValueMapValue
    private String carsCheckboxText;

    @Getter
    @Setter
    @ValueMapValue
    private String roomsTypeHeadingTxt;

    @Getter
    @Setter
    @ChildResource
    private List<TypeList> roomsTypeLst;

    @Getter
    @Setter
    @ValueMapValue
    private String renterAgeTitleText;

}
