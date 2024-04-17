package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TransactionDetailModelTest {
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
    void test_model() {
        assertNotNull(model);
        assertNotNull(model.getTransactionValues());
        TransactionDetailModel model2 = model.getTransactionValues().get(0);
        assertEquals("REDEMPTION", model2.getActivityType());
        assertEquals("POS", model2.getChannelType());
        assertEquals("AIR MILES Cash – In-store", model2.getChannelLabel());
        assertEquals("MILES_CREDITED", model2.getRedemptionStatus());
        assertEquals("https://airmiles.ca/images/logo.jpg", model2.getLogoSrc());
        assertEquals("logo", model2.getLogoAltText());
        assertNotNull(model2.getCharitySection());
        assertEquals("80062423255", model2.getCharitySection().get(0).getCharityDescription());
        assertEquals("Donation", model2.getCharitySection().get(0).getCharityName());
    }
}
