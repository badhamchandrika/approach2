package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.bean.TopNavItem;
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
public class FooterNav{
    private static final Logger LOG = LoggerFactory.getLogger(FooterNav.class);
    @ChildResource(name = "sitedetailswithmap")
    Resource siteDetailsWithMap;

    public List<TopNavItem> getSiteDetails() {
        List<TopNavItem> siteDetailsMap=new ArrayList<>();
        if(siteDetailsWithMap!=null){
            Iterable<Resource> siteChildren = siteDetailsWithMap.getChildren();
            for (Resource site : siteChildren) {
                TopNavItem navItem = new TopNavItem();
                String siteName = site.getValueMap().get("sitename", String.class);
                navItem.setSiteName(siteName);
                String webUrl = site.getValueMap().get("weburl", String.class);
                navItem.setWebUrl(Utils.getFormattedURL(webUrl));
                String urlTarget = site.getValueMap().get("target", String.class);
                navItem.setUrlTarget(urlTarget);
                String dataClickID = site.getValueMap().get("dataClickID",String.class);
                navItem.setDataClickID(dataClickID);
                String dataTrackType = site.getValueMap().get("dataTrackType",String.class);
                navItem.setDataTrackType(dataTrackType);
                String dataTrackID = site.getValueMap().get("dataTrackID",String.class);
                navItem.setDataTrackID(dataTrackID);
                siteDetailsMap.add(navItem);
            }
        }
        LOG.info("\n SIZE {} ",siteDetailsMap.size());
        return siteDetailsMap;
    }
}