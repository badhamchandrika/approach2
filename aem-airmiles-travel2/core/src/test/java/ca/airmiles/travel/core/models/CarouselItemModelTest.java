package ca.airmiles.travel.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.ModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@ExtendWith(AemContextExtension.class)
public class CarouselItemModelTest {

    CarouselItemModel carouselItemModel;
    private final AemContext ctx = new AemContext();
    private Resource resource;

    private final String resourcePath = "/content";
    @BeforeEach
    void setup(){
        ctx.load().json("/ca/airmiles/travel/core/models/carousel.json", resourcePath);
        resource = ctx.resourceResolver().getResource("/content");
        ctx.registerService(Resource.class,resource);
        carouselItemModel = ctx.getService(ModelFactory.class).createModel(resource, CarouselItemModel.class);

    }

    @Test
    void test() {
        assertEquals("#61C1EE", carouselItemModel.getBgColor());
        assertEquals("description", carouselItemModel.getDescription());
        assertEquals("Header", carouselItemModel.getHeaderText());
        assertEquals("datatrack", carouselItemModel.getDataTrackId());
        assertEquals("#0000", carouselItemModel.getHeaderColor());
        assertEquals("https://google.com", carouselItemModel.getImageLink());
        assertEquals("/content/dam/airmiles-ai/success-mail-icon.png", carouselItemModel.getPng().getAltSrc());
        assertEquals("/content/dam/aem-airmiles-travel2/dummy.png", carouselItemModel.getSlideImage().getAltSrc());
        assertEquals("/content/aem-airmiles-travel2/ca/en.html",carouselItemModel.getActions().get(0).getLinkUrl());
        assertEquals("2023-09-11T09:31:00.000-06:00",carouselItemModel.getStartDate());
        assertEquals("2023-09-11T23:31:00.000-06:00",carouselItemModel.getEndDate());


    }
}
