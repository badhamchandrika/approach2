package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.bean.AppItem;
import com.aem.airmiles.web.core.utils.Utils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class GetAppModel {
    private static final Logger LOG = LoggerFactory.getLogger(GetAppModel.class);

    @ChildResource(name = "appslist")
    Resource appsList;
    
    public List<AppItem> getAppsList() {
        List<AppItem> appDetails= new ArrayList<>();
        if(appsList!=null){
            Iterable<Resource> appChildren = appsList.getChildren();
            for (Resource app : appChildren) {
                AppItem appItem = new AppItem();
                String appImage = app.getValueMap().get("image", String.class);
                appItem.setImage(appImage);
                String appAlt = app.getValueMap().get("alt", String.class);
                appItem.setAlt(appAlt);
                String appLink = app.getValueMap().get("link", String.class);
                appItem.setLink(Utils.getFormattedURL(appLink));
                String appLinkTarget = app.getValueMap().get("linkTarget", String.class);
                appItem.setLinkTarget(appLinkTarget);
                String dataTrackID = app.getValueMap().get("dataTrackID",String.class);
                appItem.setDataTrackID(dataTrackID);
                String dataClickID = app.getValueMap().get("dataClickID",String.class);
                appItem.setDataClickID(dataClickID);
                String dataTrackType = app.getValueMap().get("dataTrackType",String.class);
                appItem.setDataTrackType(dataTrackType);
                appDetails.add(appItem);
            }
        }
        LOG.info("\n SIZE {} ",appDetails.size());
        return appDetails;
    }

}