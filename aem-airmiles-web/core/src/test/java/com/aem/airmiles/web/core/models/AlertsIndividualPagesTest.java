package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AlertsIndividualPagesTest {

    @Test
    void getAlertPage_shouldGetProvidedAlertPage() {
        assertNotNull(new AlertsIndividualPages() {{
            setAlertPage("somePage");
        }}.getAlertPage());
    }
}