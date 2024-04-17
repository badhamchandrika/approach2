package com.aem.airmiles.web.core.models;

import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class UseMilesSection extends ComponentModel {

    private static final Logger LOG = LoggerFactory.getLogger(UseMilesSection.class);

    @ValueMapValue
    @Getter
    private String wayfinderText;

    @ValueMapValue
    @Getter
    private String headerText;

    @ValueMapValue
    @Getter
    private String dataTrackSection;

    @ChildResource(name = "tabs")
    Resource tabsList;

    public List<Map<String, String>> getTabsList() {
        List<Map<String, String>> tabsDataMap = new ArrayList<>();
        if (tabsList != null) {
            Iterable<Resource> tabsChildren = tabsList.getChildren();
            for (Resource tab : tabsChildren) {
                Map<String, String> tabDataMap = new HashMap<>();

                String svgIcon = tab.getValueMap().get("svgIcon", String.class);
                tabDataMap.put("svgIcon", svgIcon);

                String tabTitle = tab.getValueMap().get("tabTitle", String.class);
                tabDataMap.put("tabTitle", tabTitle);

                String tabId = tab.getValueMap().get("tabId", String.class);
                tabDataMap.put("tabId", tabId);

                String dataTrackID = tab.getValueMap().get("dataTrackID", String.class);
                tabDataMap.put("dataTrackID", dataTrackID);

                String dataClickID = tab.getValueMap().get("dataClickID", String.class);
                tabDataMap.put("dataClickID", dataClickID);

                String dataTrackType = tab.getValueMap().get("dataTrackType", String.class);
                tabDataMap.put("dataTrackType", dataTrackType);

                tabsDataMap.add(tabDataMap);
            }

        }
        LOG.info("\n SIZE {} ", tabsDataMap.size());
        return tabsDataMap;
    }

}
