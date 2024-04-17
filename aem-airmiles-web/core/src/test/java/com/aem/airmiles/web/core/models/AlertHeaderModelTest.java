package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AlertHeaderModelTest {

    AlertHeaderModel alertHeaderModel = new AlertHeaderModel();

    @Test
    void test_AlertHeader(){
        List<Alerts> alertDetails = new ArrayList<>();
        Alerts alerts = new Alerts();
        alertDetails.add(alerts);
        alertHeaderModel.setAlertDetails(alertDetails);
        assertEquals(alertDetails,alertHeaderModel.getAlertDetails());
    }
}
