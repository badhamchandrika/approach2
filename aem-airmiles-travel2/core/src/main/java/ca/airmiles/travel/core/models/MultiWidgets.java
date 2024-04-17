package ca.airmiles.travel.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import javax.inject.Named;
import java.util.List;

@Model(adaptables = Resource.class,resourceType = "aem-airmiles-travel2/components/multiwidgets",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class MultiWidgets {

    @Getter
    @ChildResource
    @Named("widgets")
    private List<Widget> widgets;

}

