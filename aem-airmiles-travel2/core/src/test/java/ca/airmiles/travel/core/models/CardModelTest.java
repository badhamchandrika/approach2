package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.constant.ContentConstant;
import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.settings.SlingSettingsService;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class CardModelTest {

    AemContext context = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    ResourceResolver resourceResolver = mock(ResourceResolver.class);
    SlingHttpServletRequest request = context.request();
    String pageUrl = "Path/to/page";
    CardModel cardModel;


    @Mock
    private SlingSettingsService slingSettingsService;

    Set<String> mockRunModes = new TreeSet<String>();

    @BeforeEach
    public void before() {
        context.runMode("author");
        context.registerService(SlingSettingsService.class, slingSettingsService);
        context.create().page("/content/mysite/en", "/apps/myapp/template/homepage");
        context.currentPage("/content/mysite/en");
        context.load().json("/ca/airmiles/travel/core/models/cardModel.json","/item");
        context.load().json("/ca/airmiles/travel/core/models/cardModelDetail.json","/item/details");
        context.currentResource("/item");

    }
    @Test
    void test_init() throws ParseException {
        cardModel = request.adaptTo(CardModel.class);
        Resource resource1 = mock(Resource.class);
        Page page = mock(Page.class);
        when(page.getLanguage(false)).thenReturn(new Locale("en"));
        List<Resource> resourceList = new ArrayList<>();
        cardModel.setDetails(resourceList);
        resourceList.add(resource1);
        ValueMap valueMap = mock(ValueMap.class);
        when(resource1.getValueMap()).thenReturn(valueMap);
        when(valueMap.get(ContentConstant.PAGE_URL, String.class)).thenReturn(pageUrl);
        cardModel.setResourceResolver(resourceResolver);
        cardModel.setRequest(request);
        Resource pageResource = mock(Resource.class);
        when(resourceResolver.getResource(pageUrl+ ContentConstant.JCR_CONTENT)).thenReturn(pageResource);
        ValueMap pageValueMap = mock(ValueMap.class);
        when(pageResource.getValueMap()).thenReturn(pageValueMap);
        cardModel.setCurrentPage(page);
//        when(pageResource.adaptTo(Page.class)).thenReturn(page);
        cardModel.setTitle("Main title");
        cardModel.setVariant("deals");

        CardDetail cardDetail = request.adaptTo(CardDetail.class);
        pageValueMap.put(ContentConstant.CARD_TITLE, "Card Title");
        pageValueMap.put(ContentConstant.CARD_DESTINATION_COUNTRY, "England");
        pageValueMap.put(ContentConstant.CARD_DESCRIPTION, "Card Description");
        pageValueMap.put(ContentConstant.CARD_TAGICON, "Card Tag Icon");
        pageValueMap.put(ContentConstant.LINK_TARGET, "Card Link Target");
        pageValueMap.put(ContentConstant.CARD_IMAGEPATH, "Card Image Path");
        pageValueMap.put(ContentConstant.DATE_TEXT, "Date Text");
        pageValueMap.put(ContentConstant.ICON, "car_icon");
        pageValueMap.put(ContentConstant.URL, "URL");
        pageValueMap.put(ContentConstant.DATATRACKID, "dataTrackID");
        pageValueMap.put(ContentConstant.DATATRACKCLICK, "dataClickID");
        pageValueMap.put(ContentConstant.DATATRACKTYPE, "dataTrackType");
        assertEquals(true, cardModel.isAuthor());
        assertEquals("Main title", cardModel.getTitle());
        assertEquals("deals", cardModel.getVariant());
        assertEquals(resourceList, cardModel.getDetails());
        assertEquals(resourceResolver, cardModel.getResourceResolver());
        List<CardDetail> cardDetailList = new ArrayList<>();
        cardDetailList.add(cardDetail);
        cardModel.setCardDetailList(cardDetailList);
        assertEquals(cardDetailList, cardModel.getCardDetailList());
        cardModel.init();
    }

}