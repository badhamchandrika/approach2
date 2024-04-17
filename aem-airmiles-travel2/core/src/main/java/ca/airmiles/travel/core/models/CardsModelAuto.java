package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.utils.Utils;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.i18n.I18n;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.apache.sling.settings.SlingSettingsService;

import javax.annotation.PostConstruct;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

import static ca.airmiles.travel.core.constant.ContentConstant.ROOT_TRAVEL_PATH;

@Model(adaptables = SlingHttpServletRequest.class, resourceType = CardsModelAuto.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION, selector = "auto")
public class CardsModelAuto {
    public static final String RESOURCE_TYPE = "aem-airmiles-travel2/components/card";

    @ChildResource
    @Getter
    @Setter
    @Default(values="")
    private String rootPath;

    @ValueMapValue
    @Getter
    @Setter
    private String variant;

    @ValueMapValue(name="cq:tags")
    @Getter
    @Setter
    @Default(values="")
    private String tag;

    @SlingObject
    @Setter
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

    }

    private void setCards()  {
        PageManager pageManager = resolver.adaptTo(PageManager.class);
        currentPage = pageManager.getContainingPage(currentResource);
        Locale pageLocale = currentPage.getLanguage(true);
        ResourceBundle bundle = request.getResourceBundle(pageLocale);
        I18n i18n = new I18n(bundle);
        if (!tag.isEmpty()) {
            Iterator<Resource> hits = searchForPagesWithTags(tag, pageLocale);
            while (hits.hasNext()) {
                Resource n = hits.next();
                Page childrenPage = null;
                childrenPage = pageManager.getPage(n.getPath());
                CardDetail cd = Utils.getCardDetail(childrenPage.getProperties(), i18n);
                if (cd.getUrl().isEmpty()) {
                    cd.setUrl(childrenPage.getPath().replace(ROOT_TRAVEL_PATH, "").concat(".html"));
                }
                list.add(cd);
            }
        }
    }

        private Iterator<Resource> searchForPagesWithTags(String tag, Locale pageLocale) {
        String language = pageLocale.getLanguage().toLowerCase().contains("fr") ? "/fr/voyages/aubaines" : "/en/travel/";
        QueryBuilder queryBuilder = resolver.adaptTo(QueryBuilder.class);
        Map<String, String> map = new HashMap<>();
        if (rootPath.isEmpty()) {
            map.put("path", ROOT_TRAVEL_PATH + language);
        }else{
            map.put("path", rootPath);
        }
        map.put("type", "cq:Page");
        map.put("orderby", "@jcr:content/cq:lastModified");
        map.put("tagid.property", "jcr:content/cq:tags");
        map.put("tagid", tag);
        PredicateGroup searchGroup = PredicateGroup.create(map);
        Session session = resolver.adaptTo(Session.class);
        Query query = queryBuilder.createQuery(searchGroup, session);
        query.setHitsPerPage(0);
        SearchResult searchResult = query.getResult();
        return searchResult.getResources();
    }


    public List<CardDetail> getDeals()  {
        setCards();
        return Collections.unmodifiableList(this.list);
    }

    public String getCurrentPage() {
        PageManager pageManager = resolver.adaptTo(PageManager.class);
        currentPage = pageManager.getContainingPage(currentResource);
        return Utils.getExternalizedPath(resolver, currentPage.getPath(), slingSettingsService.getRunModes()).concat(".html");
    }
    public boolean isAuthor(){
        return slingSettingsService.getRunModes().contains("author");
    }

}
