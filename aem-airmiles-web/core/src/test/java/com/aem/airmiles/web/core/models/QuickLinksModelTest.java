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
class QuickLinksModelTest extends TestUtil {

    @Mock
    Resource res;

    @Mock
    ContentFragment cf;

    @Mock
    ResourceResolver rr;

    @Test
    void getNullQuickLinks() {
        quickLink = aemctx.request().adaptTo(QuickLinksModel.class);
        assertNotNull(quickLink);
        assertNull(quickLink.getFragmentPath());
        assertNull(quickLink.getQuickLinksDetailsModel());
    }

    @Test
    void getQuickLinksDetail() {
        quickLink = aemctx.request().adaptTo(QuickLinksModel.class);
        QuickLinksDetailsModel details = new QuickLinksDetailsModel();
        details.setTitle("my title");
        details.setDescription("my description");
        details.setDataTrackSection("quick-links");
        String[] lks = {"title", "Help", "link", "https://www.google.cl", "icon",
                "help-circle", "linkTarget", "_blank", "dataTrackId", "trackid", "dataTrackClick", "trackclick"};
        details.setQuicklinks(lks);
        quickLink.setQuickLinksDetailsModel(details);
        assertEquals("my title", details.getTitle());
        assertEquals("my description", details.getDescription());
        assertEquals("quick-links", details.getDataTrackSection());
    }

    @Test
    void getQuickLinksFragmentPath() {
        aemctx.currentResource("/quicklinksmodelcontent/quicklinks");
        quickLink = aemctx.request().adaptTo(QuickLinksModel.class);
        assertNotNull(quickLink);
        assertEquals("/content/dam/aem-airmiles-web/cfs/quick-links", quickLink.getFragmentPath());
    }

    @Test
    void getQuickLinksFragmentPathInit() {
        aemctx.currentResource("/quicklinksmodelcontent/quicklinks");
        quickLink = aemctx.request().adaptTo(QuickLinksModel.class);
        assertNotNull(quickLink);
        assertDoesNotThrow(() -> quickLink.init());
        assertEquals("/content/dam/aem-airmiles-web/cfs/quick-links", quickLink.getFragmentPath());
    }

    @Test
    void getLinks() {
        quickLink = aemctx.request().adaptTo(QuickLinksModel.class);
        Links link = new Links();
        link.setTitle("Help");
        link.setLink("https://www.google.cl");
        link.setLinkTarget("_blank");
        link.setIcon("help");
        link.setDataTrackId("quicklinks_id");
        link.setDataTrackClick("quicklinks_click");

        assertEquals("Help", link.getTitle());
        assertEquals("https://www.google.cl", link.getLink());
        assertEquals("_blank", link.getLinkTarget());
        assertEquals("help", link.getIcon());
        assertEquals("quicklinks_id", link.getDataTrackId());
        assertEquals("quicklinks_click", link.getDataTrackClick());
    }

    @Test
    void quickLinksDetail_GetQuickLinks_null() throws JsonProcessingException {
        quickLink = aemctx.request().adaptTo(QuickLinksModel.class);
        assertNull(new QuickLinksDetailsModel().getQuicklinks());
    }

    @Test
    void init_shouldNotThrowNPE_whenNoResourceResolverProvided() {
        assertDoesNotThrow(() -> new QuickLinksModel().init());
    }

    @Test
    void init_shouldProcessContentFragments() {
        final String cfPath = "/content/dam/lam/cf1";
        when(rr.getResource(cfPath)).thenReturn(res);
        when(res.adaptTo(ContentFragment.class)).thenReturn(cf);
        assertDoesNotThrow(() -> {
            final QuickLinksModel model = new QuickLinksModel();
            writeField(model, "resourceResolver", rr, Boolean.TRUE);
            writeField(model, "fragmentPath", cfPath, Boolean.TRUE);
            model.setFragmentPath(cfPath);
            try (final MockedStatic<Utils> utils = mockStatic(Utils.class)) {
                utils.when(() -> getElements(any())).thenReturn(emptyMap());
                model.init();
                utils.verify(()-> getElements((any())), times(1));
            }
        });
    }
}