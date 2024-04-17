package ca.airmiles.travel.core.models;

import com.day.cq.wcm.api.Page;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.osgi.resource.Resource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@Model( adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LinksBarModel {
    @Inject
    @Source("script-bindings")
    Page currentPage;
    @Getter
    @Setter
    @ChildResource
    private List<LinkModel> links;

    @PostConstruct
    private void setActive(){
        if (null !=links){
            for (LinkModel link: links){
                link.setCurrentPage(currentPage.getPath());
            }
        }
    }

}
