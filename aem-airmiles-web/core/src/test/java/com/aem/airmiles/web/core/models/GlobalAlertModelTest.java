package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class GlobalAlertModelTest {

    GlobalAlertModel globalAlertModel = new GlobalAlertModel();

    @Test
    void test_GlobalAlert(){
        List<GlobalAlerts> alertDetails = new ArrayList<>();
        GlobalAlerts globalAlerts = new GlobalAlerts();
        alertDetails.add(globalAlerts);
        globalAlertModel.setDuration(20);
        globalAlertModel.setUniqueId("unique-id");
        globalAlertModel.setGlobalAlertDetails(alertDetails);
        globalAlertModel.setActive(true);
        globalAlertModel.setInjectable(true);
        globalAlertModel.setId("cmp-id");
        assertTrue(globalAlertModel.isActive());
        assertTrue(globalAlertModel.isInjectable());
        assertEquals(20,globalAlertModel.getDuration());
        assertEquals("unique-id",globalAlertModel.getUniqueId());
        assertEquals(alertDetails,globalAlertModel.getGlobalAlertDetails());
        assertEquals("cmp-id", globalAlertModel.getId());
    }
}
