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
public class TopNav{
    private static final Logger LOG = LoggerFactory.getLogger(TopNav.class);

    @ChildResource(name = "sitedetailswithmap")
    Resource sitedetailswithmap;

    @ChildResource(name = "profiletoolsdetails")
    Resource profiletoolsdetails;
    
    public List<TopNavItem> getSiteDetails() {
        List<TopNavItem> siteDetails=new ArrayList<>();
        if(sitedetailswithmap!=null){
            Iterable<Resource> siteChildren = sitedetailswithmap.getChildren();
            for (Resource site : siteChildren) {
                TopNavItem navItem = new TopNavItem();
                String siteName = site.getValueMap().get("sitename",String.class);
                navItem.setSiteName(siteName);
                String webUrl = site.getValueMap().get("weburl",String.class);
                navItem.setWebUrl(Utils.getFormattedURL(webUrl));
                String uid = site.getValueMap().get("uid",String.class);
                navItem.setUid(uid);
                String clickAction = site.getValueMap().get("clickaction",String.class);
                navItem.setClickAction(clickAction);
                String dataTrackID = site.getValueMap().get("dataTrackID",String.class);
                navItem.setDataTrackID(dataTrackID);
                String dataClickID = site.getValueMap().get("dataClickID",String.class);
                navItem.setDataClickID(dataClickID);
                String dataTrackType = site.getValueMap().get("dataTrackType",String.class);
                navItem.setDataTrackType(dataTrackType);
                siteDetails.add(navItem);
            }
        }
        LOG.info("\n SIZE {} ",siteDetails.size());
        return siteDetails;
    }


    public List<TopNavItem> getProfileToolsDetails() {
        List<TopNavItem> profileTools=new ArrayList<>();
        if(profiletoolsdetails!=null){
            Iterable<Resource> profileToolsChildren = profiletoolsdetails.getChildren();
            for (Resource profile : profileToolsChildren) {
                TopNavItem navItem = new TopNavItem();
                String name = profile.getValueMap().get("name",String.class);
                navItem.setSiteName(name);

                String webUrl = profile.getValueMap().get("weburl",String.class);
                navItem.setWebUrl(Utils.getFormattedURL(webUrl));

                String uid = profile.getValueMap().get("uid",String.class);
                navItem.setUid(uid);

                String clickAction = profile.getValueMap().get("clickact",String.class);
                navItem.setClickAction(clickAction);

                String dataTrackID = profile.getValueMap().get("dataTrackID",String.class);
                navItem.setDataTrackID(dataTrackID);

                String dataClickID = profile.getValueMap().get("dataClickID",String.class);
                navItem.setDataClickID(dataClickID);

                String dataTrackType = profile.getValueMap().get("dataTrackType",String.class);
                navItem.setDataTrackType(dataTrackType);

                profileTools.add(navItem);
            }
        }
        LOG.info("\n SIZE {} ",profileTools.size());
        return profileTools;
    }
}