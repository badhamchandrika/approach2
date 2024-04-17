package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.Text;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;


@Model(
        adaptables = SlingHttpServletRequest.class,
        adapters = Text.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        resourceType = "aem-airmiles-web/components/footer/copyright"
)
@Exporter(name=ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CopyrightModel extends ComponentModel implements Text {
    @Self
    @Via(type = ResourceSuperType.class)
    Text textComponent;

    @Override
    public String getText(){
        return textComponent.getText();
    }

    @Override
    public boolean isRichText(){
        return textComponent.isRichText();
    }


}