package com.aem.airmiles.web.core.models;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.aem.airmiles.web.core.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.aem.airmiles.web.core.utils.Utils.getElements;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.reflect.FieldUtils.writeField;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class FooterHelpsModelTest extends TestUtil {

    @Mock
    Resource res;

    @Mock
    ContentFragment cf;

    @Mock
    ResourceResolver rr;

    @Test
    void getNullFooterHelps() {
        footerHelps = aemctx.request().adaptTo(FooterHelpsModel.class);
        assertNotNull(footerHelps);
        assertNull(footerHelps.getFragmentPath());
        assertNull(footerHelps.getFooterHelpsDetailsModel());
    }

    @Test
    void getFooterHelpsDetail() {
        footerHelps = aemctx.request().adaptTo(FooterHelpsModel.class);
        FooterHelpsDetailsModel details = new FooterHelpsDetailsModel();
        details.setHeading("Here to help");
        String[] fhs = {"title", "FAQs", "link", "http://www.airmiles.ca/en/get-help.html#faq)", "linkTarget", "_blank", "description", "Find answers to a variety of AIR MILES questions.", "dataTrackId", "faqs", "dataTrackClick", "footer", "dataTrackType", "internal"};
        details.setFooterhelps(fhs);
        footerHelps.setFooterHelpsDetailsModel(details);
        assertEquals("Here to help", details.getHeading());
    }

    @Test
    void getFooterHelpsFragmentPath() {
        aemctx.currentResource("/footerhelpsmodelcontent/footerhelps");
        footerHelps = aemctx.request().adaptTo(FooterHelpsModel.class);
        assertNotNull(footerHelps);
        assertEquals("/content/dam/aem-airmiles-web/cfs/cf-footer-helps", footerHelps.getFragmentPath());
    }

    @Test
    void getFooterHelpsFragmentPathInit() throws JsonProcessingException {
        aemctx.currentResource("/footerhelpsmodelcontent/footerhelps");
        footerHelps = aemctx.request().adaptTo(FooterHelpsModel.class);
        assertNotNull(footerHelps);
        footerHelps.init();
        assertEquals("/content/dam/aem-airmiles-web/cfs/cf-footer-helps", footerHelps.getFragmentPath());
    }

    @Test
    void getLinks() {
        footerHelps = aemctx.request().adaptTo(FooterHelpsModel.class);
        Links link = new Links();
        link.setTitle("FAQs");
        link.setLink("http://www.airmiles.ca/en/get-help.html#faq)");
        link.setLinkTarget("_blank");
        link.setDescription("Find answers to a variety of AIR MILES questions.");
        link.setDataTrackId("faqs");
        link.setDataTrackClick("footer");
        link.setDataTrackType("internal");

        assertEquals("FAQs", link.getTitle());
        assertEquals("http://www.airmiles.ca/en/get-help.html#faq)", link.getLink());
        assertEquals("_blank", link.getLinkTarget());
        assertEquals("Find answers to a variety of AIR MILES questions.", link.getDescription());
        assertEquals("faqs", link.getDataTrackId());
        assertEquals("footer", link.getDataTrackClick());
        assertEquals("internal", link.getDataTrackType());
    }

    @Test
    void footerHelpsDetail_GetFooterHelps_null() throws JsonProcessingException {
        footerHelps = aemctx.request().adaptTo(FooterHelpsModel.class);
        FooterHelpsDetailsModel details = new FooterHelpsDetailsModel();
        assertNull(details.getFooterHelps());
    }

    @Test
    void init_shouldNotThrowNPE_whenNoResourceResolverProvided() {
        assertDoesNotThrow(() -> new FooterHelpsModel().init());
    }

    @Test
    void init_shouldProcessContentFragments() {
        final String cfPath = "/content/dam/lam/cf1";
        when(rr.getResource(cfPath)).thenReturn(res);
        when(res.adaptTo(ContentFragment.class)).thenReturn(cf);
        assertDoesNotThrow(() -> {
            final FooterHelpsModel model = new FooterHelpsModel();
            writeField(model, "resourceResolver", rr, Boolean.TRUE);
            writeField(model, "fragmentPath", cfPath, Boolean.TRUE);
            try (final MockedStatic<Utils> utils = mockStatic(Utils.class)) {
                utils.when(() -> getElements(any())).thenReturn(emptyMap());
                model.init();
                utils.verify(()-> getElements((any())), times(1));
            }
        });
    }
}
