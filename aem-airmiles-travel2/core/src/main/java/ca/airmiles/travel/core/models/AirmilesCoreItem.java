package ca.airmiles.travel.core.models;

import com.adobe.cq.wcm.core.components.commons.editor.dialog.childreneditor.Item;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

public class AirmilesCoreItem extends Item{

    @Setter
    protected String tabImage;

    @Setter
    @Getter
    private String dataTrackID;

    @Setter
    @Getter
    private String dataClickID;

    @Setter
    @Getter
    private String dataTrackType;

    public AirmilesCoreItem(SlingHttpServletRequest request, Resource resource) {
        super(request, resource);
        this.setTabImage(resource.getValueMap().getOrDefault("tabImage","").toString());
        this.setDataTrackType(resource.getValueMap().getOrDefault("dataTrackType","").toString());
        this.setDataTrackID(resource.getValueMap().getOrDefault("dataTrackID","").toString());
        this.setDataClickID(resource.getValueMap().getOrDefault("dataClickID","").toString());
    }

    public String getTabImage() {
        return tabImage;
    }

}
