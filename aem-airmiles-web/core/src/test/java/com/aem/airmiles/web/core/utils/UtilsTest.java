package com.aem.airmiles.web.core.utils;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.FragmentData;
import com.aem.airmiles.web.core.models.Links;
import com.google.gson.JsonSyntaxException;
import org.apache.sling.api.SlingHttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static com.aem.airmiles.web.core.constants.ContentConstant.MM_DD_YYYY;
import static com.aem.airmiles.web.core.constants.ContentConstant.YYYY_MM_DD;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UtilsTest {

    @Mock
    ContentFragment cf;

    @Mock
    ContentElement ce;

    @Mock
    FragmentData fd;

    @Mock
    SlingHttpServletRequest rq;

    @Test
    void getElements_shouldProcessContentFragmentElements() {
        final String test = "test";
        when(ce.getValue()).thenReturn(fd);
        when(ce.getName()).thenReturn(test);
        when(fd.getValue()).thenReturn(test);
        when(cf.getElements()).thenReturn(singletonList(ce).listIterator());

        final Map<String, Object> map = Utils.getElements(cf);
        assertNotNull(map);
        assertFalse(map.isEmpty());
        assertEquals(1, map.size());
        assertNotNull(map.get(test));
        assertEquals(test, map.get(test));
    }

    @Test
    void getLinks_shouldDeliverListOfLinks() {
        assertDoesNotThrow(() -> {
            final List<Links> linksList =
                    Utils.getLinks(new String[]{"{\"link\":\"google.com\"}", "{\"link\":\"airmiles.ca\"}"});
            assertNotNull(linksList);
            assertFalse(linksList.isEmpty());
            assertEquals(2, linksList.size());
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {EMPTY, "google.com", "/content/dam/lam/asset.jpg", "/content/lam/ca/en.html"})
    void getFormattedURL_shouldNotAlterValuesInGivenCases(final String value) {
        assertEquals(value, Utils.getFormattedURL(value));
    }

    @Test
    void getFormattedURL_shouldNotAlterValue_whenURlDoesNotStartWithSlash() {
        assertEquals("/content/lam/ca/en.html", Utils.getFormattedURL("/content/lam/ca/en"));
    }

    @Test
    void rqBodyShouldBeNull() {
        assertDoesNotThrow(() -> {
            when(rq.getReader()).thenReturn(new BufferedReader(new StringReader(EMPTY)));
            assertNull(Utils.getRequestBodyMap(rq));

        });
    }

    @Test
    void rqBodyShouldNotBeNull() {
        assertDoesNotThrow(() -> {
            when(rq.getReader()).thenReturn(new BufferedReader(
                    new StringReader("{\"key1\": \"value1\", \"key2\": \"value2\"}")));
            final Map<String, Object> payload = Utils.getRequestBodyMap(rq);
            assertNotNull(payload);
            assertEquals(2, payload.size());
            assertEquals("value1", payload.get("key1"));
            assertEquals("value2", payload.get("key2"));
        });
    }

    @Test
    void shouldThrowJsonSyntaxExceptionForInvalidJson() {
        assertDoesNotThrow(() -> {
            when(rq.getReader()).thenReturn(new BufferedReader(new StringReader("{\"invalidJson")));
            assertThrows(JsonSyntaxException.class, () ->Utils.getRequestBodyMap(rq));
        });
    }

    @Test
    void shouldThrowIOException() {
        assertDoesNotThrow(() -> {
            when(rq.getReader()).thenThrow(new IOException());
            assertThrows(IOException.class, () ->Utils.getRequestBodyMap(rq));
        });
    }

    @Test
    void testTransformDate() {
        assertDoesNotThrow(() ->
                assertEquals("2022-12-25", Utils.transformDate("12-25-2022", MM_DD_YYYY, YYYY_MM_DD))
        );
    }

    @Test
    void testInvalidDate() {
        assertThrows(ParseException.class, () -> Utils.transformDate("invalid-date", MM_DD_YYYY, YYYY_MM_DD));
    }
}