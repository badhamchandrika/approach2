package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CarRentalWidgetTest {

    CarRentalWidget carRentalWidget = new CarRentalWidget();

    @BeforeEach
    void setup(){
        List<String> renderAgeList = new ArrayList<String>();
        renderAgeList.add(">18 <25");
        renderAgeList.add(">25 <45");
        renderAgeList.add(">45 <60");
        renderAgeList.add(">60");

        carRentalWidget.setButtonText("Search");
        carRentalWidget.setAdvSearchText("Advanced search");
        carRentalWidget.setFromLocationText("From");
        carRentalWidget.setToLocationText("To");
        carRentalWidget.setRenterAgeTitle("renter's age");
        carRentalWidget.setRenderAgeList(renderAgeList);
        carRentalWidget.setPickUpDateText("24/12/2022");
        carRentalWidget.setDropOffDateText("26/12/2022");
        carRentalWidget.setPickUpTimeText("12:00");
        carRentalWidget.setDropOffTimeText("18:00");
        carRentalWidget.setUrlAdvSearch("https://www.google.cl");
    }

    @Test
    void test_widget(){
        assertEquals("Search", carRentalWidget.getButtonText());
        assertEquals("Advanced search", carRentalWidget.getAdvSearchText());
        assertEquals("From", carRentalWidget.getFromLocationText());
        assertEquals("To", carRentalWidget.getToLocationText());
        assertEquals("renter's age", carRentalWidget.getRenterAgeTitle());
        assertEquals(">18 <25", carRentalWidget.getRenderAgeList().get(0));
        assertEquals("24/12/2022", carRentalWidget.getPickUpDateText());
        assertEquals("26/12/2022", carRentalWidget.getDropOffDateText());
        assertEquals("12:00", carRentalWidget.getPickUpTimeText());
        assertEquals("18:00", carRentalWidget.getDropOffTimeText());
        assertEquals("https://www.google.cl", carRentalWidget.getUrlAdvSearch());
    }

}
