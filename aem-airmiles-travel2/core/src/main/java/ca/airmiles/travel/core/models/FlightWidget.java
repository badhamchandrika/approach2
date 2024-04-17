package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.constant.ContentConstant;
import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.map.HashedMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Model(adaptables = Resource.class,resourceType = "aem-airmiles-travel2/components/flightwidget", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class FlightWidget {

    @Getter
    @Setter
    @ValueMapValue
    private String cabinHeading;

    @Getter
    @Setter
    @ValueMapValue
    private String tripTypeHeading;

    @Getter
    @Setter
    @ValueMapValue
    private String passengerHeading;

    @Getter
    @Setter
    @ValueMapValue
    @JsonIgnore
    private String adult;
    @Getter
    @Setter
    @ValueMapValue
    @JsonIgnore
    private String child;
    @Getter
    @Setter
    @ValueMapValue
    @JsonIgnore
    private String infant;

    @Getter
    @Setter
    @ChildResource
    private List<TypeList> tripTypeList;

    @Getter
    @Setter
    private Map<String,String> passengerList;

    @Getter
    @Setter
    @ChildResource
    private List<TypeList> cabinList;

    @Getter
    @Setter
    @ValueMapValue
    private String warningMessage;

    @Getter
    @Setter
    @ValueMapValue
    private String infantWarningMsg;

    @Getter
    @Setter
    @ValueMapValue
    private String maxWarningMsg;

    @Getter
    @Setter
    @ValueMapValue
    private String buttonText;

    @Getter
    @Setter
    @ValueMapValue
    private String locationSearchText;

    @Getter
    @Setter
    @ValueMapValue
    private String advSearchText;

    @Getter
    @Setter
    @ValueMapValue
    private String linkText;

    @Getter
    @Setter
    @ValueMapValue
    private String linkUrl;

    @Getter
    @Setter
    @ValueMapValue
    private String fromLocationText;

    @Getter
    @Setter
    @ValueMapValue
    private String defaultDestinationValue;

    @Getter
    @Setter
    @ValueMapValue
    private String toLocationText;

    @Getter
    @Setter
    @ValueMapValue
    private String departureDateText;

    @Getter
    @Setter
    @ValueMapValue
    private String returnDateText;

    @Getter
    @Setter
    @ValueMapValue
    private String searchSuggestionText;

    @Getter
    @Setter
    @ValueMapValue
    private String updateButtonText;

    @Getter
    @Setter
    @ValueMapValue
    private String fromInstructionalText;

    @Getter
    @Setter
    @ValueMapValue
    private String toInstructionalText;

    @PostConstruct
    void init(){
        passengerList = new HashedMap();
        passengerList.put(ContentConstant.ADULT, adult);
        passengerList.put(ContentConstant.CHILD, child);
        passengerList.put(ContentConstant.INFANT, infant);
    }

}
