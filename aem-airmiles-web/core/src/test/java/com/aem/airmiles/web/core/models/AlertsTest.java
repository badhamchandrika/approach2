package com.aem.airmiles.web.core.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AlertsTest {

    Alerts alerts;

    @BeforeEach
    void setUp() {
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
        assertDoesNotThrow(() -> alerts = new Alerts() {{
                    setAlertText("Go to Sobeys");
                    setDataTrackSection("alert-section");
                    setStartTime(sdf.parse("Sat Oct 10 12:16:44 IST 2022"));
                    setEndTime(sdf.parse("Sat Oct 12 12:16:44 IST 2022"));
                    setGlobalAlert(true);
                    setHasRoleAlert(true);
                    setDisplayClose(true);
                    setAlertType("Warning");
                    setShowAlert(true);
                }});
    }

    @Test
    void test_Alerts() {
        assertEquals("Go to Sobeys", alerts.getAlertText());
        assertEquals("alert-section", alerts.getDataTrackSection());
        assertNotNull(alerts.getStartTime());
        assertNotNull(alerts.getEndTime());
        assertTrue(alerts.getEndTime().getTime() > alerts.getStartTime().getTime());
        assertEquals("Warning", alerts.getAlertType());
        assertTrue(alerts.isGlobalAlert());
        assertTrue(alerts.isHasRoleAlert());
        assertTrue(alerts.isDisplayClose());
        assertTrue(alerts.isShowAlert());
    }

    @Test
    void test_alertPage() {
        alerts.setAlertPages(Collections.singletonList(new AlertsIndividualPages()));
        assertNotNull(alerts.getAlertPages());
        assertNotNull(alerts.getAlertPages().get(0));
    }
}
