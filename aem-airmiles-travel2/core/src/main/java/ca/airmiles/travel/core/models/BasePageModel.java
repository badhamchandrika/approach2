package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.services.BaseConfigurationService;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import javax.inject.Inject;

@Model(adaptables = SlingHttpServletRequest.class)
public class BasePageModel {

    @Inject
    @Optional
    Page currentPage;

    int basePageRoot = 4;

    @OSGiService
    private BaseConfigurationService configurationService;

    public String getApiUrl() {
        return this.configurationService.getApiUrl();
    }

    public String getDomBFFUrl() {
        return this.configurationService.getDomBFFUrl();
    }

    public String getAAPageName() {
        int residual = (currentPage.getDepth()) - basePageRoot;
        Page tmpPage;
        StringBuilder aaPageName = new StringBuilder();
        if (residual > 0)
            for (int i = basePageRoot + 1 ; i < basePageRoot + residual; i++) {
                tmpPage = currentPage.getAbsoluteParent(i);
                aaPageName.append(":").append(tmpPage.getName());
            }

        return aaPageName.toString();
    }
}
