package com.aem.airmiles.web.core.servlets;

import com.aem.airmiles.web.core.bean.ErrorBean;
import com.aem.airmiles.web.core.constants.ContentConstant;
import com.aem.airmiles.web.core.constants.ErrorConstants;
import com.aem.airmiles.web.core.constants.ResourceTypes;
import com.aem.airmiles.web.core.models.CTALink;
import com.aem.airmiles.web.core.models.FeaturedOffers;
import com.aem.airmiles.web.core.models.TitleModel;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.Query;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.day.cq.search.PredicateGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component(service = {Servlet.class})
@SlingServletResourceTypes(
        resourceTypes =ResourceTypes.SLING_SERVLET,
        methods = HttpConstants.METHOD_GET,
        selectors = "generic-data",
        extensions = "json")
public class OffersContentExporter extends SlingSafeMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(OffersContentExporter.class);
    @Reference
    private QueryBuilder queryBuilder;
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setContentType(ContentConstant.APPLICATION_JSON);
        response.addHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        response.setCharacterEncoding(ContentConstant.UTF_8);
        String[] selectors = request.getRequestPathInfo().getSelectors();

        if(StringUtils.isNotEmpty(selectors[1]) && selectors[1].equalsIgnoreCase("offers")){
            String resourcePath = request.getRequestPathInfo().getResourcePath();
            ResourceResolver resourceResolver = request.getResourceResolver();
            Resource resource = resourceResolver.getResource(resourcePath + ContentConstant.ROOT_CONTAINER);
            ErrorBean errorBean=null;
            PrintWriter out = response.getWriter();
            try {
                if (null != resource) {
                    Session session = resourceResolver.adaptTo(Session.class);
                    Map<String, String> map = prepareMap(resourcePath);
                    JSONArray jsonArray = getResults(map, session, resourceResolver);
                    if (jsonArray.length() > 0) {
                        out.write(jsonArray.toString());
                    } else {
                        errorBean = new ErrorBean(ErrorConstants.ERROR_OFFER_NODATA, ErrorConstants.ERROR_OFFER_NODATA_MSG);
                    }
                }else{
                    errorBean = new ErrorBean(ErrorConstants.ERROR_OFFER_CODE, ErrorConstants.ERROR_OFFER_MSG);
                }

                if(null!=errorBean) {
                    ObjectMapper objectMapper = getObjectMapper();
                    out.write(objectMapper.writeValueAsString(errorBean));
                }
                out.close();
            } catch (RepositoryException e) {
                LOG.error("RepositoryException occurred in OffersContentExporter:"+e.getMessage(),e);
            } catch (JSONException e) {
                LOG.error("JSONException occurred in OffersContentExporter::"+e.getMessage(),e);
            }

        }



    }

    private Map<String, String> prepareMap(String resourcePath) {
        Map<String, String> map = new HashMap<>();
        map.put("path", resourcePath);
        map.put("type", "nt:unstructured");
        map.put("property", "sling:resourceType");
        map.put("property.1_value", ResourceTypes.TITLE_MODEL);
        map.put("property.2_value", ResourceTypes.CTA_BUTTON_MODEL);
        map.put("property.3_value", ResourceTypes.FEATURED_OFFERS);
        map.put("p.limit", "-1");
        return map;

    }

    private JSONArray  getResults(Map<String, String> map, Session session, ResourceResolver resourceResolver) throws RepositoryException, JsonProcessingException, JSONException {
        Query query = queryBuilder.createQuery(PredicateGroup.create(map), session);
        SearchResult result = query.getResult();
        long totalMatches = result.getTotalMatches();
        JSONArray jsonArray = new JSONArray();
        if(totalMatches>0) {
            for (Hit hit : result.getHits()) {
                String path = hit.getPath();
                Resource resource = resourceResolver.getResource(path);
                if(null!=resource)
                    getComponentData(resource.getResourceType(), resource, jsonArray);
            }
        }
        return jsonArray;

    }


    private void getComponentData(String resourceType, Resource res, JSONArray jsonArray) throws JsonProcessingException, JSONException {
        ObjectMapper objectMapper = getObjectMapper();
        if(resourceType.equals(ResourceTypes.TITLE_MODEL)) {
            TitleModel titleModel = res.adaptTo(TitleModel.class);
            jsonArray.put(new JSONObject(objectMapper.writeValueAsString(titleModel)));
        }
        else if (resourceType.equals(ResourceTypes.CTA_BUTTON_MODEL)) {
            CTALink ctaLink = res.adaptTo(CTALink.class);
            jsonArray.put(new JSONObject(objectMapper.writeValueAsString(ctaLink)));
        }
        else if (resourceType.equals(ResourceTypes.FEATURED_OFFERS)) {
            FeaturedOffers featuredOffers = res.adaptTo(FeaturedOffers.class);
            jsonArray.put(new JSONObject(objectMapper.writeValueAsString(featuredOffers)));
        }
    }

    @NotNull
    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }
}


