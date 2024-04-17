package ca.airmiles.travel.core.models;

import com.day.cq.wcm.api.Page;
import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DynamicWidgetModel {

    @Getter
    @ChildResource
    private List<ExperienceTypeModel> dynamicWidgets;

    @Inject
    private Page currentPage;

    private static final String DESTINATIONS_PATH_EN = "/travel/destinations";
    private static final String DESTINATIONS_PATH_FR = "/voyages/destinations";
    private static final String TRAVEL_DETAIL_TEMPLATE_PATH = "/conf/aem-airmiles-travel2/settings/wcm/templates/travel-detail-page";

    @PostConstruct
    public void init() {
        if(null != dynamicWidgets && currentPage != null) {
            for (ExperienceTypeModel widget : dynamicWidgets) {
                if (currentPage.getPath().contains("/ca/fr/")) {
                    String experienceFragment = widget.getExperienceFragment();
                    experienceFragment = experienceFragment.replaceFirst("/ca/en/", "/ca/fr/");
                    widget.setExperienceFragment(experienceFragment);
                }
            }
        }
    }

    public boolean isDestinationsPage() {
        return isPageType(DESTINATIONS_PATH_EN, DESTINATIONS_PATH_FR);
    }

    private boolean isPageType(String pageTypeEN, String pageTypeFR) {
        if (currentPage != null) {
            return currentPage.getPath().contains(pageTypeEN) || currentPage.getPath().contains(pageTypeFR);
        }
        return false;
    }

    public boolean isTravelDetailsPageTemplate() {
        if (currentPage != null) {
            final String templatePath = currentPage.getTemplate().getPath();
            return TRAVEL_DETAIL_TEMPLATE_PATH.equals(templatePath);
        }
        return false;
    }
}