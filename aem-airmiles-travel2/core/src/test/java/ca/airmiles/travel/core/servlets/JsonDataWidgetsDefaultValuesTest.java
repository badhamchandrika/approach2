package ca.airmiles.travel.core.servlets;

import acscommons.com.jcraft.jsch.Session;
import ca.airmiles.travel.core.constant.ContentConstant;
import ca.airmiles.travel.core.models.CustomResource;
import ca.airmiles.travel.core.models.DealsList;
import ca.airmiles.travel.core.models.DropDownListData;
import ca.airmiles.travel.core.models.FilterModel;
import com.google.gson.Gson;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.abdera.model.Text;
import org.apache.sling.api.resource.*;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.apache.sling.testing.resourceresolver.MockResourceResolverFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.ServletConfig;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class JsonDataWidgetsDefaultValuesTest {

    private AemContext ctx = new AemContext(ResourceResolverType.JCR_MOCK);

    private Map<String, String> configProps = new HashMap<String, String>();

    private Map<String, Object> parameterMap = new HashMap<String, Object>();

    @Mock
    private MockSlingHttpServletRequest req;

    @Mock
    private MockSlingHttpServletResponse res;

    @InjectMocks
    private JsonDataWidgetsDefaultValues jsonDataWidgetsDefaultValuesModel;

    private Resource resource;
    String jsonString = "[{\"Code\":\"AAL\",\"Name\":\"Estonia\",\"CountryCode\":\"DK\"},{\"Code\":\"AES\",\"Name\":\"Aalesund\",\"CountryCode\":\"NO\"},{\"Code\":\"AAR\",\"Name\":\"Belarus\",\"CountryCode\":\"DK\"},{\"Code\":\"MHH\",\"Name\":\"Yugia - Marsh Harbor\",\"CountryCode\":\"BS\"},{\"Code\":\"TCB\",\"Name\":\"Zurich - Treasure Cay\",\"CountryCode\":\"BS\"}]";
    String jsonPath = "/content/dam/aem-airmiles-travel2/trisept-jsons/flights/flights.json/jcr:content/renditions/original/jcr:content";

    @BeforeEach
    void setup() throws LoginException {
        Gson gson = mock(Gson.class);
        //jsonDataWidgetsDefaultValuesModel = new JsonDataWidgetsDefaultValues();
        //jsonDataWidgetsDefaultValuesModel = ctx.request().adaptTo(JsonDataWidgetsDefaultValues.class);
        ResourceResolver resourceResolver = mock(ResourceResolver.class);
        ResourceResolverFactory resolverFactory = mock(ResourceResolverFactory.class);
        List<DropDownListData> optionList = new ArrayList<>();
        DropDownListData dropDownListData = new DropDownListData();
        dropDownListData.setValue("option1");
        dropDownListData.setText("Option 1");
        optionList.add(dropDownListData);


        when(gson.toJson(anyCollection())).thenReturn("");
        when(gson.fromJson(anyString(),any(Type.class))).thenReturn(optionList);
        HashMap<String, Object> params = new HashMap<>();
        params.put(ResourceResolverFactory.SUBSERVICE, ContentConstant.TRAVEL_SERVICE_USER);
        //when(resolverFactory.getServiceResourceResolver(params)).thenReturn(resourceResolver);
        ctx.registerService(ResourceResolverFactory.class, new MockResourceResolverFactory());

        jsonDataWidgetsDefaultValuesModel = ctx.registerInjectActivateService(new JsonDataWidgetsDefaultValues(gson));

        ctx.request().setMethod("GET");
        req = ctx.request();
        res = ctx.response();
    }

    @Test
    void testJsonDataWidgetsDefaultValues_doGet() {
        // create a resource and set as currentResource
        ctx.build().resource("/content/myResource", "jcr:title", "My Title").commit();
        ctx.currentResource("/content/myResource");

        // create a page and set as currentPage
        ctx.create().page("/content/mysite/en", "/apps/myapp/template/homepage");
        ctx.currentPage("/content/mysite/en");
        ctx.addModelsForClasses(JsonDataWidgetsDefaultValues.class);

        jsonDataWidgetsDefaultValuesModel.doGet(req, res);
        //assertEquals("Travel", response.getOutputAsString());
    }

    @Test
    void testJsonToDropDownListData() throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        List<DropDownListData> lstDropDownListData = jsonDataWidgetsDefaultValuesModel.jsonToDropDownListData(jsonArray);
        assertEquals(5, lstDropDownListData.size());
    }

    @Test
    void testSortJsonArray() throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        JSONArray sortedJsonArray = jsonDataWidgetsDefaultValuesModel.sortJsonArray(jsonArray);
        assertEquals("Aalesund", sortedJsonArray.getJSONObject(0).getString("Name"));
    }
}