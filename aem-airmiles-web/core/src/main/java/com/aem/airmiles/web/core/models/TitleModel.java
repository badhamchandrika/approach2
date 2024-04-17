package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.Title;
import com.aem.airmiles.web.core.constants.ResourceTypes;
import com.day.cq.commons.jcr.JcrConstants;
import org.jetbrains.annotations.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.api.resource.Resource;
@Model(adaptables ={SlingHttpServletRequest.class, Resource.class},
		adapters = Title.class, 
		resourceType = ResourceTypes.TITLE_MODEL,
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class TitleModel extends ComponentModel implements Title {

    @ValueMapValue
    @Getter
    @Setter
    private String titleType;

    @ValueMapValue
    @Getter
    @Setter
    private String bgcolor;

    @ValueMapValue
    @Getter
    @Setter
    private String textAlign;

    @ValueMapValue(name = JcrConstants.JCR_TITLE)
    @Nullable
    private String title;

    @ValueMapValue
    private String type;
   @Override
    public String getText(){
        return title;
    }

    @Override
    public @Nullable String getType(){
       return type;
    }

}
