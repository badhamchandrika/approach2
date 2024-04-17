package ca.airmiles.travel.core.servlets;

import ca.airmiles.travel.core.constant.ContentConstant;
import ca.airmiles.travel.core.models.DropDownListData;
import com.adobe.cq.commerce.common.ValueMapDecorator;
import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.day.cq.commons.jcr.JcrConstants.NT_UNSTRUCTURED;

@Component(service = Servlet.class, immediate = true, property = {
        "sling.servlet.paths=" + ContentConstant.DEFAULT_VALUE_SERVLET_PATH,
        "sling.servlet.resourceTypes=" + ContentConstant.DEFAULT_VALUE_SERVLET_RESOURCE_TYPE,
        "sling.servlet.methods=" + HttpConstants.METHOD_GET
})
public class JsonDataWidgetsDefaultValues extends SlingSafeMethodsServlet {

    Gson gson;

    transient Resource pathResource;

    @Reference
    private ResourceResolverFactory resolverFactory;

    public JsonDataWidgetsDefaultValues() {
        this.gson = new Gson();
    }

    public JsonDataWidgetsDefaultValues(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) {
        try {
            resp.setContentType(ContentConstant.MIMETYPE_JSON);
            resp.setCharacterEncoding(ContentConstant.UTF8_STRING);

            pathResource = req.getResource();
            HashMap<String, Object> param = new HashMap<>();
            param.put(ResourceResolverFactory.SUBSERVICE, ContentConstant.TRAVEL_SERVICE_USER);
            ResourceResolver resolver = null;
            resolver = resolverFactory.getServiceResourceResolver(param);
            List<DropDownListData> lstData = getListFromJsonFile(resolver, ContentConstant.FLIGHT_JSON_DATA_URL);

            TypeToken<List<DropDownListData>> token = new TypeToken<List<DropDownListData>>() { };
            List<DropDownListData> optionList = gson.fromJson(gson.toJson(lstData), token.getType());
            List<Resource> optionResourceList = new ArrayList<>();

            for (DropDownListData opt : optionList) {
                ValueMap vm = getOptionValueMap(opt);
                optionResourceList
                        .add(new ValueMapResource(resolver, new ResourceMetadata(), NT_UNSTRUCTURED, vm));
            }

            DataSource ds = new SimpleDataSource(optionResourceList.iterator());
            req.setAttribute(DataSource.class.getName(), ds);

        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    private List<DropDownListData> getListFromJsonFile(ResourceResolver resolver, String filePath){
        JSONObject jsonObj;
        Resource resource =  resolver.getResource(filePath);
        JSONArray originsJsonArray;
        List<DropDownListData> formatedData = new ArrayList<>();

        //if the resource is null, we need to check the permissions for the user travel-service-user in the useradmin console to the path
        if(resource != null){
            try {
                Node jcnode = resource.adaptTo(Node.class);
                InputStream content = jcnode.getProperty(ContentConstant.JCR_DATA_STRING).getBinary().getStream();
                StringBuilder sb = new StringBuilder();
                String line = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(content, StandardCharsets.UTF_8));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                jsonObj = new JSONObject(sb.toString());

                originsJsonArray = jsonObj.getJSONObject(ContentConstant.PACKAGE_NODE_JSON).getJSONArray(ContentConstant.SOURCE_NODE_JSON);
                formatedData = jsonToDropDownListData(originsJsonArray);

            } catch (JSONException | IOException | RepositoryException e) {
                e.printStackTrace();
            }
        }
        return formatedData;
    }

    public List<DropDownListData> jsonToDropDownListData(JSONArray jsonArray) throws JSONException {

        jsonArray = sortJsonArray(jsonArray);

        List<DropDownListData> listDropDownListData = new ArrayList<>();
        DropDownListData dropDownListData;
        JSONObject tempData;

        for(int x = 0;x < jsonArray.length();x++) {
            tempData = jsonArray.getJSONObject(x);
            dropDownListData = new DropDownListData();
            dropDownListData.setText(tempData.getString(ContentConstant.FLIGHT_JSON_VALUE_TEXT) +
                                    ContentConstant.DEFAULT_VALUE_COMMA_SEPARATOR +
                                    tempData.getString(ContentConstant.DEFAULT_VALUE_COUNTRY_CODE) +
                                    ContentConstant.DEFAULT_VALUE_SPACE_SEPARATOR +
                                    ContentConstant.DEFAULT_VALUE_CODE_LEFT +
                                    tempData.getString(ContentConstant.FLIGHT_JSON_VALUE_VALUE) +
                                    ContentConstant.DEFAULT_VALUE_CODE_RIGHT );
            dropDownListData.setValue(tempData.getString(ContentConstant.FLIGHT_JSON_VALUE_VALUE));
            listDropDownListData.add(dropDownListData);
        }

        return listDropDownListData;
    }

    private ValueMap getOptionValueMap(DropDownListData opt) {
        ValueMap vm = new ValueMapDecorator(new HashMap<>());

        vm.put(ContentConstant.DEFAULT_VALUE_DROPDOWN_VALUE, opt.getValue());
        vm.put(ContentConstant.DEFAULT_VALUE_DROPDOWN_TEXT, opt.getText());
        if (opt.isSelected()) {
            vm.put(ContentConstant.DEFAULT_VALUE_DROPDOWN_SELECTED, true);
        }
        if (opt.isDisabled()) {
            vm.put(ContentConstant.DEFAULT_VALUE_DROPDOWN_DISABLED, true);
        }
        return vm;
    }

    public JSONArray sortJsonArray(JSONArray originalJsonArray) throws JSONException {
        JSONArray sortedJsonArray = new JSONArray();

        List<JSONObject> jsonValues = new ArrayList<>();
        for (int i = 0; i < originalJsonArray.length(); i++) {
            jsonValues.add(originalJsonArray.getJSONObject(i));
        }
        Collections.sort( jsonValues, (a, b) -> {
            String valA = "";
            String valB = "";
            try {
                valA = (String) a.get(ContentConstant.FLIGHT_JSON_VALUE_TEXT);
                valB = (String) b.get(ContentConstant.FLIGHT_JSON_VALUE_TEXT);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return valA.compareTo(valB); // ASC
            //if you want to change the sort order, simply use the following:
            //return -valA.compareTo(valB); // DESC
        });

        for (int i = 0; i < originalJsonArray.length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }

        return sortedJsonArray;
    }

}
