package com.aem.airmiles.web.core.services.impl;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentVariation;
import com.aem.airmiles.web.core.services.MindfulSurveyService;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MindfulSurveyServiceImplTest {

    /**
     * Values to be inserted and retrieved by the service.
     */
    private static final String
            UTF_8 = "utf-8",
            APPLICATION_JAVASCRIPT = "application/javascript",
            C0001 = "C0001",
            FAKE_SCRIPT = "https://something.com/fake.js";

    /**
     * Represents the timeout value for the mindful survey widget.
     * The value is specified in milliseconds.
     */
    private static final int TIMEOUT = 500;

    /**
     * The SurveyService class is responsible for configuring and providing the necessary settings for the service.
     */
    private static MindfulSurveyService svc;

    /**
     * Simulates SurveyService activation with the mocked configuration.
     */
    @BeforeAll
    static void activate() {
        final MindfulSurveyServiceImpl.Config config = mock(MindfulSurveyServiceImpl.Config.class);

        when(config.enabled()).thenReturn(true);
        when(config.charset()).thenReturn(UTF_8);
        when(config.scriptType()).thenReturn(APPLICATION_JAVASCRIPT);
        when(config.consentCategory()).thenReturn(C0001);
        when(config.scriptSrc()).thenReturn(FAKE_SCRIPT);
        when(config.timeout()).thenReturn(TIMEOUT);

        svc = new MindfulSurveyServiceImpl();
        assertDoesNotThrow(() -> {
            final Method mtd = svc.getClass()
                    .getDeclaredMethod("activate", MindfulSurveyServiceImpl.Config.class);
            mtd.setAccessible(true);
            mtd.invoke(svc, config);
        });
    }

    /**
     * Ensures that the service is enabled.
     */
    @Test
    void isEnabled() {
        assertTrue(svc.isEnabled());
    }

    /**
     * Ensures that the charset is UTF-8.
     */
    @Test
    void getCharset() {
        assertEquals(UTF_8, svc.getCharset());
    }

    /**
     * Ensures that the script type is JavaScript.
     */
    @Test
    void getScriptType() {
        assertEquals(APPLICATION_JAVASCRIPT, svc.getScriptType());
    }

    /**
     * Ensures that OneTrust Cookie Category is C0001.
     */
    @Test
    void getConsentCategory() {
        assertEquals(C0001, svc.getConsentCategory());
    }

    /**
     * Ensures that the script source is the provided one.
     */
    @Test
    void getScriptSrc() {
        assertEquals(FAKE_SCRIPT, svc.getScriptSrc());
    }

    /**
     * Checks that the survey fragment is null when there is no resource in the specified path.
     */
    @Test
    void getSurveyFragment_shouldBeNull_whenNoResource() {
        final SlingHttpServletRequest rq = mock(SlingHttpServletRequest.class);
        final ResourceResolver rr = mock(ResourceResolver.class);

        when(rq.getResourceResolver()).thenReturn(rr);

        assertNull(svc.getSurveyFragment(rq, "/any/path"));
    }

    /**
     * Checks that the survey fragment is null when the resource in the specified path is not a ContentFragment.
     */
    @Test
    void getSurveyFragment_shouldBeNull_whenNoCfResource() {
        final SlingHttpServletRequest rq = mock(SlingHttpServletRequest.class);
        final ResourceResolver rr = mock(ResourceResolver.class);
        final Resource res = mock(Resource.class);

        when(rq.getResourceResolver()).thenReturn(rr);
        when(rr.getResource(anyString())).thenReturn(res);

        assertNull(svc.getSurveyFragment(rq, "/any/non-cf-path"));
    }

    /**
     * Checks that the survey fragment is not null when there is a content fragment in the specified.
     */
    @Test
    void getSurveyFragment_shouldNotBeNull_whenCfResource() {
        final SlingHttpServletRequest rq = mock(SlingHttpServletRequest.class);
        final ResourceResolver rr = mock(ResourceResolver.class);
        final Resource res = mock(Resource.class);

        when(rq.getResourceResolver()).thenReturn(rr);
        when(rr.getResource(anyString())).thenReturn(res);
        when(res.adaptTo(ContentFragment.class)).thenReturn(mock(ContentFragment.class));

        assertNotNull(svc.getSurveyFragment(rq, "/any/non-cf-path"));
    }

    /**
     * Checks that the service result is empty when there's no content element within the content fragment.
     */
    @Test
    void getElementVariation_shouldBeEmpty_whenNoContentElement() {
        assertEquals(StringUtils.EMPTY,
                svc.getElementVariationContent(mock(ContentFragment.class), "example", "aa"));
    }

    /**
     * Checks that the service result is empty when the variation does not exist.
     */
    @Test
    void getElementVariation_shouldBeEmpty_whenNonExistingVariation() {
        final ContentFragment cf = mock(ContentFragment.class);
        final String elName = "element-name";

        when(cf.getElement(elName)).thenReturn(mock(ContentElement.class));

        assertEquals(StringUtils.EMPTY, svc.getElementVariationContent(cf, elName, "non-existing-variation"));
    }

    /**
     * Checks that the service result is the provided one when everything is ok.
     */
    @Test
    void getElementVariation_shouldNotBeEmpty() {
        final ContentFragment cf = mock(ContentFragment.class);
        final ContentElement ce = mock(ContentElement.class);
        final ContentVariation cv = mock(ContentVariation.class);
        final String elName = "element-name-field";
        final String variation = "existing-variation";
        final String content = "Hello, I am your content and I am pleased to serve you ☺.";

        when(cf.getElement(elName)).thenReturn(ce);
        when(ce.getVariation(variation)).thenReturn(cv);
        when(cv.getContent()).thenReturn(content);

        assertEquals(content, svc.getElementVariationContent(cf, elName, variation));
    }

    /**
     * Ensures the timeout is the provided one.
     */
    @Test
    void getTimeout() {
        assertEquals(TIMEOUT, svc.getTimeout());
    }
}