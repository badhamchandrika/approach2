package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PackagesWidgetTest {

    PackagesWidget packagesWidget = new PackagesWidget();
    List<TypeList> roomTypeList = new ArrayList<TypeList>();

    @BeforeEach
    void setup(){
        packagesWidget.setBuildATripText("Build a trip");
        packagesWidget.setAllInclusiveText("All inclusive");
        packagesWidget.setFlightsCheckboxText("Flight");
        packagesWidget.setHotelsCheckboxText("Hotel");
        packagesWidget.setCarsCheckboxText("Cars");
        packagesWidget.setRoomsTypeHeadingTxt("Rooms");
        packagesWidget.setRoomsTypeLst(roomTypeList);
        packagesWidget.setRenterAgeTitleText("Renters age");
    }

    @Test
    void test_widget(){
        assertEquals("Build a trip", packagesWidget.getBuildATripText());
        assertEquals("All inclusive", packagesWidget.getAllInclusiveText());
        assertEquals("Flight", packagesWidget.getFlightsCheckboxText());
        assertEquals("Hotel", packagesWidget.getHotelsCheckboxText());
        assertEquals("Cars", packagesWidget.getCarsCheckboxText());
        assertEquals("Rooms", packagesWidget.getRoomsTypeHeadingTxt());
        assertEquals(roomTypeList, packagesWidget.getRoomsTypeLst());
        assertEquals("Renters age", packagesWidget.getRenterAgeTitleText());
    }
}