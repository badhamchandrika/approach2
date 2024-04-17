package com.aem.airmiles.web.core.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class GlobalAlertsTest {

    GlobalAlerts globalAlert = new GlobalAlerts();

    Date startDate = null;
    Date endDate = null;

    DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
    @BeforeEach
    void init() throws ParseException {
        globalAlert.setGlobalAlertTitle("Important!!");
        globalAlert.setGlobalAlertText("Go to Sobeys");
        globalAlert.setGlobalAlertName("alert");
        globalAlert.setGlobalAlertCategory("alertCategory");
        String dDate="Sat Oct 10 12:16:44 IST 2022";
        startDate = dateFormat.parse(dDate);
        globalAlert.setGlobalAlertStartTime(startDate);
        String eDate="Sat Oct 12 12:16:44 IST 2022";
        endDate = dateFormat.parse(eDate);
        globalAlert.setGlobalAlertEndTime(endDate);
        globalAlert.setGlobalAlertIsTrue(true);
        globalAlert.setGlobalAlertHasRole(true);
        globalAlert.setGlobalAlertType("Warning");
    }

    @Test
    void test_Alerts(){
        assertEquals("Important!!",globalAlert.getGlobalAlertTitle());
        assertEquals("Go to Sobeys",globalAlert.getGlobalAlertText());
        assertEquals("alert",globalAlert.getGlobalAlertName());
        assertEquals("alertCategory",globalAlert.getGlobalAlertCategory());
        assertEquals(startDate,globalAlert.getGlobalAlertStartTime());
        assertEquals(endDate,globalAlert.getGlobalAlertEndTime());
        assertEquals("Warning",globalAlert.getGlobalAlertType());
        assertTrue(globalAlert.isGlobalAlertIsTrue());
        assertTrue(globalAlert.isGlobalAlertHasRole());
    }

    @Test
    void test_alertPage(){
        List<AlertsIndividualPages> alertPages = new ArrayList<>();
        AlertsIndividualPages alertsIndividualPages = new AlertsIndividualPages();
        alertPages.add(alertsIndividualPages);
        globalAlert.setAlertPages(alertPages);
        assertEquals(alertPages,globalAlert.getAlertPages());
    }
}
