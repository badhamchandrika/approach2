package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ActivitiesWidgetTest {

    ActivitiesWidget activitiesWidget = new ActivitiesWidget();

    @BeforeEach
    void setup(){
        activitiesWidget.setPersonsTitle("Persons");
        activitiesWidget.setGoingToPlaceholder("Going to");
        activitiesWidget.setAdvanceSearchText("Advance search");
        activitiesWidget.setUrlAdvanceSearch("/content/advance_search");
        activitiesWidget.setSearchButtonText("Search");
        activitiesWidget.setDeparturePlaceholder("departure");
        activitiesWidget.setPassengerHeadingTxt("Passengers");
        activitiesWidget.setAdultTxt("adult");
        activitiesWidget.setChildTxt("child");
        activitiesWidget.setInfantTxt("infant");
        activitiesWidget.init();
    }

    @Test
    void test_widget(){
        assertEquals("Persons", activitiesWidget.getPersonsTitle());
        assertEquals("Going to", activitiesWidget.getGoingToPlaceholder());
        assertEquals("Advance search", activitiesWidget.getAdvanceSearchText());
        assertEquals("/content/advance_search", activitiesWidget.getUrlAdvanceSearch());
        assertEquals("Search", activitiesWidget.getSearchButtonText());
        assertEquals("departure", activitiesWidget.getDeparturePlaceholder());
        assertEquals("Passengers", activitiesWidget.getPassengerHeadingTxt());
        assertEquals("adult", activitiesWidget.getAdultTxt());
        assertEquals("child", activitiesWidget.getChildTxt());
        assertEquals("infant", activitiesWidget.getInfantTxt());
    }

}
