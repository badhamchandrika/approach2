package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CarouselModelTest {

    CarouselModel carouselModel = new CarouselModel();

    @BeforeEach
    void setup(){
        CarouselItemModel itemModel = new CarouselItemModel();
        List<CarouselItemModel> items = new ArrayList<>();
        items.add(itemModel);
        carouselModel.setCarouselSlides(items);
        assertEquals(items,carouselModel.getCarouselSlides());

        carouselModel.setImgRight("true");
        carouselModel.setDataTrackClick("123456");

    }

    @Test
    void test() {
        assertEquals(true, carouselModel.getImgRight());
        assertEquals("123456", carouselModel.getDataTrackClick());

    }
}
