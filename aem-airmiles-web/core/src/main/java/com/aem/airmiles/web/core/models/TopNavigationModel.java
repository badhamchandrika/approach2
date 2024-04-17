package com.aem.airmiles.web.core.models;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.aem.airmiles.web.core.utils.Utils.getElements;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class TopNavigationModel {

    private static final Logger log = LoggerFactory.getLogger(TopNavigationModel.class);

    @Setter
    @Getter
    @ChildResource(name = "topNavDetails")
    private List<NavigationItemModel> navItem;

    @Getter
    @Setter
    List<NavigationSectionBean> navSectionList;

    @SlingObject
    @Getter
    @Setter
    private ResourceResolver resourceResolver;

    @PostConstruct
    public void init() {
        getNavigationDetails();

    }

    protected void getNavigationDetails() {
        if (null != navItem) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            for (NavigationItemModel navigationItemModel : navItem) {
                navSectionList = new ArrayList<>();
                List<ContentFragmentPaths> listOfContentFragmentPaths = navigationItemModel.getSubSectionPaths();
                if (null != listOfContentFragmentPaths) {
                    for (ContentFragmentPaths contentFragmentPaths : listOfContentFragmentPaths) {
                        String cfPath = contentFragmentPaths.getSubSectionPaths();
                        if (null != cfPath) {
                            final Resource cfRes = resourceResolver.getResource(cfPath);
                            processContentFragmentResource(cfRes, objectMapper);
                        }
                    }

                }
                navigationItemModel.setNavSectionList(navSectionList);
                log.info("navSectionList size>>>>{}", navigationItemModel.getNavSectionList().size());
            }
        }
    }

    private void processContentFragmentResource(final Resource resource, final ObjectMapper objectMapper) {
        if (null != resource) {
            final ContentFragment contentFragment = resource.adaptTo(ContentFragment.class);
            if (null != contentFragment) {
                navSectionList.add(objectMapper
                        .convertValue(getElements(contentFragment), NavigationSectionBean.class));
            }
        }
    }
}
