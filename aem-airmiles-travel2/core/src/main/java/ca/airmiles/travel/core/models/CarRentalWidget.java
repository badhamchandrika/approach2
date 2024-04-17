package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.constant.ContentConstant;
import com.adobe.cq.export.json.ExporterConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class,resourceType = "aem-airmiles-travel2/components/carrentalwidget",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CarRentalWidget {

    @Getter
    @Setter
    @ValueMapValue
    private String renterAgeTitle;

    @Getter
    @Setter
    private List<String> renderAgeList;

    @Getter
    @Setter
    @ValueMapValue
    private String fromLocationText;

    @Getter
    @Setter
    @ValueMapValue
    private String toLocationText;

    @Getter
    @Setter
    @ValueMapValue
    private String buttonText;

    @Getter
    @Setter
    @ValueMapValue
    private String advSearchText;

    @Getter
    @Setter
    @ValueMapValue
    private String urlAdvSearch;

    @Getter
    @Setter
    @ValueMapValue
    private String pickUpDateText;

    @Getter
    @Setter
    @ValueMapValue
    private String dropOffDateText;

    @Getter
    @Setter
    @ValueMapValue
    private String pickUpTimeText;

    @Getter
    @Setter
    @ValueMapValue
    private String dropOffTimeText;

    @PostConstruct
    void init(){
        renderAgeList = new ArrayList<>();
        renderAgeList.add(ContentConstant.AGE1);
        renderAgeList.add(ContentConstant.AGE2);
        renderAgeList.add(ContentConstant.AGE3);
        renderAgeList.add(ContentConstant.AGE4);
    }
}
