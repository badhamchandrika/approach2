package ca.airmiles.travel.core.models;

import com.adobe.acs.commons.genericlists.GenericList;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.log4j.Logger;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import ca.airmiles.travel.core.constant.ContentConstant;
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class CustomList{
    private static final String ROOTPATH = ContentConstant.SLASH +"etc/acs-commons/lists/airmiles/common-configurations/";
    static Logger log = Logger.getLogger(CustomList.class.getName());

    @Inject
    private String listName;
    @Self
    private SlingHttpServletRequest request;
    private  HashMap<String, String> listValues;

    @PostConstruct
    public void init() {
        listValues =  new LinkedHashMap<>();
        setList();

    }

    public Map<String, String> getList(){
        return listValues;
    }

    private void setList() {
        PageManager pageManager = request.getResourceResolver().adaptTo(PageManager.class);
        listValues = new LinkedHashMap<>();
        try {
            Page listPage = pageManager.getPage( ROOTPATH + request.getAttribute("listname"));
            GenericList genericList = listPage.adaptTo(GenericList.class);
            List<GenericList.Item> list = genericList.getItems();
            for(GenericList.Item item : list) {
                listValues.put(item.getTitle(), item.getValue());
            }
        } catch (Exception e) {
            log.error("Error in :" + e);
        }

    }
}