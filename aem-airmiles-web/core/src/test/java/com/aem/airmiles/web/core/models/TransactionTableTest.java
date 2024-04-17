package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TransactionTableTest {

    private static final String COMPONENT_ROOT = "/transactiontable";
    private final AemContext context = new AemContext();
    private TransactionTable model;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(CTALink.class);
        context.addModelsForClasses(TransactionTable.class);
        context.load().json("/com.aem.airmiles.web.core/transactiontable.json", COMPONENT_ROOT);
        context.currentResource(COMPONENT_ROOT);
        model = context.request().adaptTo(TransactionTable.class);
    }

    @Test
    void modelIsNotNull(){
        assertNotNull(model);
    }

    @Test
    void test_modelValues() {
        assertEquals("Trasactions", model.getTableTitle());
        assertEquals("No transactions found", model.getNoTransText());
        assertEquals("Something went wrong", model.getApiErrorMessage());
        assertEquals("My CTA", model.getTableCTAText());
        assertEquals("browse-all-offers", model.getTableCTATrackID());
        assertEquals("recent-transaction", model.getTableCTAClickID());
        assertEquals("internal", model.getTableCTATrackType());
        assertEquals("/content/aem-airmiles-web/ca/en/offers", model.getTableCTALink());
        assertEquals("https://airmiles.ca/images/logo.jpg", model.getNoLogoPath());
        assertEquals("static", model.getType());
        assertFalse(model.isRounded());
        assertFalse(model.isShadow());
        assertFalse(model.isMockJson());
        assertFalse(model.isBackgroundcolor());
        assertFalse(model.isTableFooter());
        assertFalse(model.isTableHeader());
        assertFalse(model.isFullWidth());
        assertEquals("cmp-id", model.getId());
        assertEquals("My component id", model.getUniqueId());
        assertEquals("Partner", model.getColumnHead1());
        assertEquals("Description", model.getColumnHead2());
        assertEquals("Date", model.getColumnHead3());
        assertEquals("Miles", model.getColumnHead4());
        assertNotNull(model.getCta());
        assertNotNull(model.getTransactionValues());
    }

    @Test
    void replaceChildren(){
        assertNotNull(model.getTransactionValues());
        assertFalse(model.getTransactionValues().isEmpty());

        // Replace values with an empty list and ensure the list was deleted.
        model.setTransactionValues(emptyList());
        assertNotNull(model.getTransactionValues());
        assertTrue(model.getTransactionValues().isEmpty());
    }
}
