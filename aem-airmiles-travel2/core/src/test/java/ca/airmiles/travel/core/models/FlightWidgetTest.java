package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FlightWidgetTest {

    FlightWidget flightWidget = new FlightWidget();

    @BeforeEach
    void setup(){
        flightWidget.setCabinHeading("Cabin");
        flightWidget.setButtonText("Search");
        flightWidget.setAdvSearchText("Advance Search Page");
        flightWidget.setLinkText("Airmiles age rules");
        flightWidget.setLinkUrl("https://airmiles.ca/rules");
        flightWidget.setInfantWarningMsg("Infant warning msg");
        flightWidget.setWarningMessage("Warning msg");
        flightWidget.setMaxWarningMsg("Max passengers allowed");
        flightWidget.setTripTypeHeading("TripType");
        flightWidget.setPassengerHeading("Passengers");
        flightWidget.setLocationSearchText("Where are you travelling from?");
        flightWidget.setFromLocationText("From");
        flightWidget.setToLocationText("To");
        flightWidget.setDepartureDateText("Departure Date");
        flightWidget.setReturnDateText("Return Date");
        flightWidget.setSearchSuggestionText("Search by city or Airport");
        flightWidget.setUpdateButtonText("Update");
        flightWidget.setFromInstructionalText("Where are you leaving from?");
        flightWidget.setToInstructionalText("Where are you going to?");
        flightWidget.setDefaultDestinationValue("Aalborg, DK (AAL)");
    }

    @Test
    void test_widget(){
        assertEquals("Cabin",flightWidget.getCabinHeading());
        assertEquals("Search",flightWidget.getButtonText());
        assertEquals("Advance Search Page",flightWidget.getAdvSearchText());
        assertEquals("Airmiles age rules",flightWidget.getLinkText());
        assertEquals("https://airmiles.ca/rules",flightWidget.getLinkUrl());
        assertEquals("Infant warning msg",flightWidget.getInfantWarningMsg());
        assertEquals("Warning msg",flightWidget.getWarningMessage());
        assertEquals("Max passengers allowed",flightWidget.getMaxWarningMsg());
        assertEquals("TripType",flightWidget.getTripTypeHeading());
        assertEquals("Passengers",flightWidget.getPassengerHeading());
        assertEquals("Where are you travelling from?",flightWidget.getLocationSearchText());
        assertEquals("From",flightWidget.getFromLocationText());
        assertEquals("To",flightWidget.getToLocationText());
        assertEquals("Departure Date",flightWidget.getDepartureDateText());
        assertEquals("Return Date",flightWidget.getReturnDateText());
        assertEquals("Search by city or Airport",flightWidget.getSearchSuggestionText());
        assertEquals("Update",flightWidget.getUpdateButtonText());
        assertEquals("Where are you leaving from?", flightWidget.getFromInstructionalText());
        assertEquals("Where are you going to?", flightWidget.getToInstructionalText());
        assertEquals("Aalborg, DK (AAL)", flightWidget.getDefaultDestinationValue());

        TypeList typeList = new TypeList();
        TypeList typeList1 = new TypeList();
        TypeList typeList2 = new TypeList();

        List<TypeList> tripTypeList = new ArrayList<>();
        tripTypeList.add(typeList);
        flightWidget.setTripTypeList(tripTypeList);
        assertEquals(tripTypeList,flightWidget.getTripTypeList());

        Map<String,String> passengerList = new HashMap<>();
        passengerList.put("key1", "value1");
        passengerList.put("key2", "value2");
        flightWidget.setPassengerList(passengerList);
        assertEquals(passengerList, flightWidget.getPassengerList());

        List<TypeList> cabinList = new ArrayList<>();
        cabinList.add(typeList2);
        flightWidget.setCabinList(cabinList);
        assertEquals(cabinList,flightWidget.getCabinList());

    }

}