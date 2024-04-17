package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.utils.Utils;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.apache.sling.settings.SlingSettingsService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;

import static ca.airmiles.travel.core.constant.ContentConstant.ROOT_TRAVEL_PATH;

@Model(adaptables = SlingHttpServletRequest.class, resourceType = DealsList.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class DealsList {
    public static final String RESOURCE_TYPE = "aem-airmiles-travel2/components/dealslist";

    @ChildResource
    @Getter
    @Setter
    private List<ChildPage> rootPages;

    @ValueMapValue
    @Getter
    @Setter
    private String variant;

    @SlingObject
    private ResourceResolver resolver;

    private List<CardDetail> list;

    @Self
    SlingHttpServletRequest request;

    @SlingObject
    private Resource currentResource;
    Page currentPage;

    @OSGiService
    SlingSettingsService slingSettingsService;


    @PostConstruct
    protected void init() {
        this.list = new ArrayList<>();
        PageManager pageManager = resolver.adaptTo(PageManager.class);
        currentPage = pageManager.getContainingPage(currentResource);
        Locale pageLocale = currentPage.getLanguage(true);
        ResourceBundle bundle = request.getResourceBundle(pageLocale);
        I18n i18n = new I18n(bundle);

        Page newPage = null;
        if (rootPages != null && !rootPages.isEmpty()) {
            for (ChildPage child : rootPages) {
                newPage = pageManager.getPage(child.getPagePath());
                if (newPage != null) {
                    Iterator<Page> pageIterator = newPage.listChildren();
                    addToList(pageIterator, i18n);
                }
            }
        }
    }

    public void addToList(Iterator<Page> pageIterator, I18n i18n) {
        while (pageIterator.hasNext()) {
            Page childrenPage = pageIterator.next();
            CardDetail cd = Utils.getCardDetail(childrenPage.getProperties(), i18n);
            if (cd.getUrl().isEmpty()) {
                cd.setUrl(childrenPage.getPath().replace(ROOT_TRAVEL_PATH, "").concat(".html"));
            }
            list.add(cd);
        }
    }

    public List<CardDetail> getDeals() {
        return Collections.unmodifiableList(this.list);
    }

    public String getCurrentPage() {
        return Utils.getExternalizedPath(resolver, currentPage.getPath(), slingSettingsService.getRunModes()).concat(".html");
    }
}
