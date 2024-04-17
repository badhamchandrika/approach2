package ca.airmiles.travel.core.models;

import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class CardModelAutoTest {

    AemContext context = new AemContext();


    private CardsModelAuto model;
    @Mock
    ResourceResolver resolver;

    @BeforeEach
    void setUp() throws RepositoryException {
        context.addModelsForClasses(CardsModelAuto.class);
        context.registerService(Session.class);
        context.load().json("/ca/airmiles/travel/core/models/DealsListPages.json", "/content/aem-airmiles-travel2/ca/en/travel/deals");
        context.load().json("/ca/airmiles/travel/core/models/DealsListPages.json", "/page");
        context.load().json("/ca/airmiles/travel/core/models/cardsAuto.json", "/page/jcr:content/cards");
        context.load().json("/ca/airmiles/travel/core/models/dealPage.json", "/deal-page");

        Resource pageResource = context.resourceResolver().getResource("/content/aem-airmiles-travel2/ca/en/travel/deals");
        Page page = pageResource.adaptTo(Page.class);
        context.currentPage(page);
        context.currentResource("/page/jcr:content/cards");
        context.request().setPathInfo("/deals");
        context.request().setLocale(new Locale("en"));
        Resource r = context.resourceResolver().resolve("/deal-page");
        Page dealPage = r.adaptTo(Page.class);
        Resource[] resources = {r};
        Iterator<Resource> it = Arrays.stream(resources).iterator();
        PageManager pageManager = mock(PageManager.class);
        QueryBuilder queryBuilder = mock(QueryBuilder.class);
        Session session = mock(Session.class);
        Query query = mock(Query.class);
        SearchResult searchResult = mock(SearchResult.class);
        when(pageManager.getContainingPage(context.currentResource())).thenReturn(page);
        when(pageManager.getPage(any())).thenReturn(dealPage);
        when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        when(resolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
        when(resolver.adaptTo(Session.class)).thenReturn(session);
        when(queryBuilder.createQuery(any(),any())).thenReturn(query);
        when(query.getResult()).thenReturn(searchResult);
        when(searchResult.getResources()).thenReturn(it);
        this.model = context.request().adaptTo(CardsModelAuto.class);
        this.model.setResolver(resolver);

    }


    @Test
    void testDeals() {
        assertNotNull(this.model);
        List<CardDetail> list = model.getDeals();
        assertNotNull(list);
        assertEquals(1, list.size());
        CardDetail detail = list.get(0);
        assertEquals("travel2:products/flight", detail.getTags()[0]);
        assertFalse(detail.isHideInListing());
        context.runMode("author");
        this.model.setVariant("deals");
        assertEquals("deals",  this.model.getVariant());
        assertEquals(true,  this.model.isAuthor());
    }


}