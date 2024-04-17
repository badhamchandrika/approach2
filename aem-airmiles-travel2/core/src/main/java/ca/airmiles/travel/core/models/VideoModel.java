package ca.airmiles.travel.core.models;


import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.dam.api.Asset;
import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class VideoModel {

    private static final String YOUTUBE = "youtube";
    @Getter
    @ValueMapValue
    private String source;
    @Getter
    @ValueMapValue
    private String youtubeSource;
    @Getter
    @ChildResource
    private String localSource;
    @Getter
    @ChildResource
    private Boolean isCenteredVideo;

    @Inject
    SlingHttpServletRequest request;

    private String mimeType = "";
    @PostConstruct
    void init() {
        ResourceResolver resolver = request.getResourceResolver();
       if (source == null || source.isEmpty()){
           source = YOUTUBE;
       }
       if( source.equals("local") && !localSource.isEmpty()){
           Asset asset = resolver.getResource(localSource).adaptTo(Asset.class);
           if (asset != null){
               mimeType = asset.getMimeType();
           }
       }
    }

    public String getMimeType(){
        return mimeType;
    }

}
