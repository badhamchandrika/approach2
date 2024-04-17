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
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.Map;

@Model(adaptables = Resource.class,resourceType = "aem-airmiles-travel2/components/activitieswidget",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ActivitiesWidget {

    @Getter
    @Setter
    @ValueMapValue
    private String personsTitle;

    @Getter
    @Setter
    @ValueMapValue
    private String goingToPlaceholder;

    @Getter
    @Setter
    @ValueMapValue
    private String departurePlaceholder;

    @Getter
    @Setter
    @ValueMapValue
    private String advanceSearchText;

    @Getter
    @Setter
    @ValueMapValue
    private String urlAdvanceSearch;

    @Getter
    @Setter
    @ValueMapValue
    private String searchButtonText;

    @Getter
    @Setter
    @ValueMapValue
    private String passengerHeadingTxt;

    @Getter
    @Setter
    @ValueMapValue
    @JsonIgnore
    private String adultTxt;

    @Getter
    @Setter
    @ValueMapValue
    @JsonIgnore
    private String childTxt;

    @Getter
    @Setter
    @ValueMapValue
    @JsonIgnore
    private String infantTxt;

    @Getter
    @Setter
    private Map<String,String> passengerList;

    @PostConstruct
    void init(){
        passengerList = new HashedMap();
        passengerList.put(ContentConstant.ADULT, adultTxt);
        passengerList.put(ContentConstant.CHILD, childTxt);
        passengerList.put(ContentConstant.INFANT, infantTxt);
    }
}
