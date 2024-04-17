package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CardDetailTest {

    CardDetail cardDetail = new CardDetail();
    String dDate="20/02/2022";
    @BeforeEach
    void setup() throws ParseException {
        cardDetail.setCardTitle("Title");
        cardDetail.setCardDestinationCountry("England");
        cardDetail.setCardDescription("Description");
        cardDetail.setCardImagePath("/content/dam/image");
        cardDetail.setCardImageAlt("alt text");
        cardDetail.setCardTagIcon("/content/dam/tagicon");
        cardDetail.setUrl("https://airmiles.ca");
        cardDetail.setDateText("Book by");
        cardDetail.setDate(dDate);
        cardDetail.setStartDate(dDate);
        cardDetail.setLinkTarget("_self");
        cardDetail.setIcon("car_icon");
        cardDetail.setDealType("Car Rental");
    }

    @Test
    void test_cardDetails(){
        assertEquals("Title",cardDetail.getCardTitle());
        assertEquals("England", cardDetail.getCardDestinationCountry());
        assertEquals("Description",cardDetail.getCardDescription());
        assertEquals("/content/dam/image",cardDetail.getCardImagePath());
        assertEquals("alt text",cardDetail.getCardImageAlt());
        assertEquals("/content/dam/tagicon",cardDetail.getCardTagIcon());
        assertEquals("_self",cardDetail.getLinkTarget());
        assertEquals("Book by",cardDetail.getDateText());
        assertEquals(dDate,cardDetail.getDate());
        assertEquals(dDate,cardDetail.getStartDate());
        assertEquals("https://airmiles.ca",cardDetail.getUrl());
        assertEquals("car_icon",cardDetail.getIcon());
        assertEquals("Car Rental", cardDetail.getDealType());

    }
}
