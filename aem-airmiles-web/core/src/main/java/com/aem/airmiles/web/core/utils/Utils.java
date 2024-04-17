package com.aem.airmiles.web.core.utils;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.aem.airmiles.web.core.constants.ContentConstant;
import com.aem.airmiles.web.core.models.Links;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Utils class to reuse methods within Airmiles Web.
 */
public class Utils {

    /**
     * Private constructor used to hide the implicit one.
     *
     * @see <a href="https://rules.sonarsource.com/java/RSPEC-1118">java:S1118</a>
     */
    private Utils() {
        // not used.
    }

    /**
     * @param contentFragment This method takes Content Fragment object and return CF elements.
     * @return Map of Content Fragment Elements
     */
    public static Map<String, Object> getElements(ContentFragment contentFragment) {
        Iterator<ContentElement> elements = contentFragment.getElements();
        Map<String, Object> elementMap = new HashMap<>();
        while (elements.hasNext()) {
            ContentElement nextElement = elements.next();
            String elementName = nextElement.getName();
            Object elementValue = nextElement.getValue().getValue();
            elementMap.put(elementName, elementValue);
        }
        return elementMap;
    }

    /**
     * @param multiFieldName This takes string[] property as parameter
     * @return and return List of Links Object
     * @throws JsonProcessingException this exception is thrown when mapping array of string to pojo
     */
    public static List<Links> getLinks(String[] multiFieldName) throws JsonProcessingException {
        List<Links> items = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        if (null != multiFieldName) {
            items = objectMapper.readValue(Arrays.toString(multiFieldName),
                    TypeFactory.defaultInstance().constructCollectionType(List.class, Links.class));
        }
        return items;
    }

    /**
     * This method return url ending with .html if it is internal link
     *
     * @param url This is Content URL
     * @return Formatted URL
     */
    public static String getFormattedURL(String url) {
        if (StringUtils.isNotBlank(url) && url.startsWith(ContentConstant.SLASH)
                && !url.startsWith(ContentConstant.SLASH_CONTENT_DAM) && !url.contains(ContentConstant.DOT_HTML)) {
            return url + ContentConstant.DOT_HTML;
        } else {
            return url;
        }
    }

    /**
     * This method extracts the request body as a map of key-value pairs from the given SlingHttpServletRequest object.
     *
     * @param rq The SlingHttpServletRequest object containing the request body.
     * @return A map of key-value pairs representing the request body.
     * @throws IOException If any input/output error occurs while reading the request body.
     */
    public static Map<String, Object> getRequestBodyMap(final @NotNull SlingHttpServletRequest rq)
            throws IOException {
        return new Gson().fromJson(rq.getReader(), new TypeToken<Map<String, String>>() {}.getType());
    }

    /**
     * Transforms a date from one format to another.
     *
     * @param inputDate    The date string to be transformed.
     * @param inputFormat  The format of the input date string (e.g., "MM-dd-yyyy").
     * @param outputFormat The desired format for the transformed date string (e.g., "yyyy-MM-dd").
     * @return The transformed date string in the specified output format.
     * @throws ParseException If an error occurs while parsing or formatting the date.
     */
    public static @NotNull String transformDate(
            final @NotNull String inputDate,
            final @NotNull String inputFormat,
            final @NotNull String outputFormat
    ) throws ParseException {
        return new SimpleDateFormat(outputFormat).format(new SimpleDateFormat(inputFormat).parse(inputDate));
    }
}