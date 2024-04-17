package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.utils.Utils;
import com.adobe.cq.dam.cfm.ContentFragment;
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
public class OffersContentFragmentModelTest extends TestUtil{


    @Mock
    Resource res;

    @Mock
    ContentFragment cf;

    @Mock
    ResourceResolver rr;

    @Test
    void getNullOffersCarousel() {
        offersContentFragmentModel = aemctx.request().adaptTo(OffersContentFragmentModel.class);
        assertNotNull(offersContentFragmentModel);
        assertNull(offersContentFragmentModel.getFragmentPath());
        assertNull(offersContentFragmentModel.getOffersCarouselDetailsModel());
    }

    @Test
    void getOffersCarouselDetail() {
        offersContentFragmentModel = aemctx.request().adaptTo(OffersContentFragmentModel.class);
        OffersCarouselDetailsModel offersCarouselDetailsModel = new OffersCarouselDetailsModel();
        offersCarouselDetailsModel.setCarouselTitle("Card Linked Offers");
        offersCarouselDetailsModel.setCarouselDescription("Card Linked Description");
        offersCarouselDetailsModel.setCarouselCta("see all offers");
        offersCarouselDetailsModel.setCarouselCtaUrl("/content/aem-airmiles-web/ca/en/offers/clo.html");
        assertEquals("Card Linked Offers",offersCarouselDetailsModel.getCarouselTitle());
        assertEquals("Card Linked Description", offersCarouselDetailsModel.getCarouselDescription());
        assertEquals("see all offers", offersCarouselDetailsModel.getCarouselCta());
        assertEquals("/content/aem-airmiles-web/ca/en/offers/clo.html", offersCarouselDetailsModel.getCarouselCtaUrl());
        offersContentFragmentModel.setOffersCarouselDetailsModel(offersCarouselDetailsModel);
        assertEquals(offersCarouselDetailsModel, offersContentFragmentModel.getOffersCarouselDetailsModel());
    }

    @Test
    void getOffersCarouselFragmentPath() {
        aemctx.currentResource("/offerscarouselsmodelcontent/offerscarousel");
        offersContentFragmentModel = aemctx.request().adaptTo(OffersContentFragmentModel.class);
        assertNotNull(offersContentFragmentModel);
        assertEquals("/content/dam/aem-airmiles-web/offers-clo/offers-carousel", offersContentFragmentModel.getFragmentPath());
    }

   // @Test
    void init_shouldProcessContentFragments() {
        final String cfPath = "/content/dam/aem-airmiles-web/offers-clo/offers-carousel";
        when(rr.getResource(cfPath)).thenReturn(res);
        when(res.adaptTo(ContentFragment.class)).thenReturn(cf);
        assertDoesNotThrow(() -> {
            final OffersContentFragmentModel model = new OffersContentFragmentModel();
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
