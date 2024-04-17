package ca.airmiles.travel.core.models;

import org.apache.log4j.Logger;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static ca.airmiles.travel.core.constant.ContentConstant.ROOT_TRAVEL_PATH;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class CustomResource {
    static Logger log = Logger.getLogger(CustomResource.class.getName());

    @Inject
    private String resourcePath;
    @Self
    private SlingHttpServletRequest request;

    private ValueMap vp;



    @PostConstruct
    public void init() {
        Resource resource;
        ResourceResolver resolver = request.getResourceResolver();
        if (request.getAttribute("resourcePath") != null) {
            resource = resolver.getResource(request.getAttribute("resourcePath").toString());
            if (resource != null) {
                vp = resource.getValueMap();
            }
        }
    }

    public String getBreadcrumb(){
        if (!resourcePath.isEmpty())  return resourcePath.replace(ROOT_TRAVEL_PATH,"");
        return resourcePath;
    }
    public  String getTabImage(){
        return vp.getOrDefault("tabImage","").toString();
    }
    public  String getDataTrackID(){
        return vp.getOrDefault("dataTrackID","").toString();
    }
    public  String getDataClickID(){
        return vp.getOrDefault("dataClickID","").toString();
    }
    public  String getDataTrackType(){
        return vp.getOrDefault("dataTrackType","").toString();
    }

}