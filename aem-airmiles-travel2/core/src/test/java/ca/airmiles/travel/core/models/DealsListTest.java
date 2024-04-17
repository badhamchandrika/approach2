package ca.airmiles.travel.core.models;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class DealsListTest {

    AemContext context = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);


    private DealsList model;

    @BeforeEach
    void setUp() {
        context.load().json("/ca/airmiles/travel/core/models/DealsListPages.json", "/content/aem-airmiles-travel2/ca/en/travel/deals");
        context.load().json("/ca/airmiles/travel/core/models/DealsListTest.json", "/content/aem-airmiles-travel2/ca/en/travel/deals/jcr:content/dealslist");

        Resource pageResource = context.resourceResolver().getResource("/content/aem-airmiles-travel2/ca/en/travel/deals");
        Page page = pageResource.adaptTo(Page.class);
        context.currentPage(page);

        context.currentResource("/content/aem-airmiles-travel2/ca/en/travel/deals/jcr:content/dealslist");
        context.request().setPathInfo("/content/aem-airmiles-travel2/ca/en/travel/deals");
        context.request().setLocale(new Locale("en"));
        this.model = context.request().adaptTo(DealsList.class);
    }


    @Test
    void testDeals() {
        assertNotNull(this.model);
        assertEquals("/content/aem-airmiles-travel2/ca/en/travel/deals", model.getRootPages().get(0).getPagePath());
        List<CardDetail> list = model.getDeals();
        assertNotNull(list);
        assertEquals(6, list.size());
        CardDetail detail = list.get(0);
        assertEquals("Title", detail.getCardTitle());
        assertEquals("Card Description", detail.getCardDescription());
        assertEquals("/content/dam/aem-airmiles-travel2/images/deals/Screen Shot 2022-11-18 at 15.41.31.png", detail.getCardImagePath());
        assertEquals("https://www.airmiles.com", detail.getUrl());
        assertEquals("we-retail:gender/women", detail.getTags()[0]);
        assertFalse(detail.isHideInListing());
    }

}