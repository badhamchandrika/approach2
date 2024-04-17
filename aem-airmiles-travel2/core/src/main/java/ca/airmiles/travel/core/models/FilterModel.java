package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.pojo.ChildFilterPojo;
import ca.airmiles.travel.core.pojo.FilterPojo;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.resource.Resource;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,resourceType = DealsList.RESOURCE_TYPE)
@Exporter(name= ExporterConstants.SLING_MODEL_EXPORTER_NAME,extensions = ExporterConstants.SLING_MODEL_EXTENSION,selector = "filters")
public class FilterModel {
    @ValueMapValue
    String[] tagField;
    @SlingObject
    ResourceResolver resourceResolver;
    List<FilterPojo> filters;
    @Self(injectionStrategy = InjectionStrategy.OPTIONAL)
    private SlingHttpServletRequest request;

    @PostConstruct
    protected  void init(){
        filters=new ArrayList<>();
        TagManager tagManager=resourceResolver.adaptTo(TagManager.class);
        Locale pageLocale = null;
        String compNodePath = request.getRequestPathInfo().getResourcePath();
        String pagePath = StringUtils.substringBefore(compNodePath, "jcr:content");
        PageManager pageMgr = request.getResourceResolver().adaptTo(PageManager.class);
        Page page = pageMgr.getContainingPage(pagePath);
        if (page != null) pageLocale =  page.getLanguage(false);

        for (String tagString : tagField){
            Tag parentTag=tagManager.resolve(tagString);
            if (parentTag!=null){
                FilterPojo filterPojo;
                Iterator<Tag> childTagIterator=parentTag.listChildren();
                filterPojo=new FilterPojo();
                filterPojo.setTagTitle(pageLocale != null ? parentTag.getTitle(pageLocale) : parentTag.getTitle());
                filterPojo.setTagId(parentTag.getTagID());
                List<ChildFilterPojo> childFilters=new ArrayList<>();
                while(childTagIterator.hasNext()){
                    Tag childTag=childTagIterator.next();
                    ChildFilterPojo childFilterPojo=new ChildFilterPojo();
                    childFilterPojo.setTagTitle(pageLocale != null ? childTag.getTitle(pageLocale) : childTag.getTitle());
                    childFilterPojo.setTagId(childTag.getTagID());
                    childFilters.add(childFilterPojo);
                }
                filterPojo.setTags(childFilters);
                filters.add(filterPojo);
            }
        }
    }

    public List<FilterPojo> getFilters() {
        return filters;
    }
}
