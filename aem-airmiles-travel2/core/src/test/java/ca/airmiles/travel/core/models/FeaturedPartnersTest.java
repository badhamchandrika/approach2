package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FeaturedPartnersTest {

    FeaturedPartners featuredPartners = new FeaturedPartners();

    @BeforeEach
    void setup(){
        featuredPartners.setTitle("Featured Partners");
        featuredPartners.setDisplayType("Featured Partners");
    }

    @Test
    void test_featuredPartners(){
        assertEquals("Featured Partners", featuredPartners.getTitle());
        assertEquals("Featured Partners", featuredPartners.getDisplayType());
    }

    @Test
    void test_details(){
        DetailsModel detailsModel = new DetailsModel();
        List<DetailsModel> details = new ArrayList<>();
        details.add(detailsModel);
        featuredPartners.setDetails(details);
        assertEquals(details,featuredPartners.getDetails());
    }
}
