package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.utils.Utils;
import com.day.cq.wcm.api.Page;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class LinkModel {

    @Setter
    @Getter
    @ValueMapValue
    private String linkUrl;

    @ValueMapValue
    @Setter
    @Getter
    private String dataTrackID;

    @ValueMapValue
    @Setter
    @Getter
    private String dataClickID;

    @ValueMapValue
    @Setter
    @Getter
    private String dataTrackType;

    @ValueMapValue
    @Getter
    @Setter
    private String linkText;

    @ValueMapValue
    @Getter
    @Setter
    private String linkTarget;

    @ValueMapValue
    @Getter
    @Setter
    private String icon;

    String currentPage;

    public String getLinkUrl() {
        return Utils.getFormattedURL(linkUrl);
    }

    public void setCurrentPage(String currentPage){
        this.currentPage = currentPage;
    }
    public boolean isActive(){
        if (currentPage != null){
            String pageName = currentPage.substring(currentPage.lastIndexOf("/"));
            return currentPage.equals(linkUrl) || linkUrl.substring(linkUrl.lastIndexOf("/")).contains(pageName);
        }
        return false;
    }
}
