package ca.airmiles.travel.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class StaysWidgetTest {

    AemContext context = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    StaysWidget model;

    @BeforeEach
    void setUp() {
        context.load().json("/ca/airmiles/travel/core/models/StaysWidgetTest.json","/content/stayswidget");
        context.currentResource("/content/stayswidget");
        this.model = Objects.requireNonNull(context.currentResource()).adaptTo(StaysWidget.class);
    }

    @Test
    void test_model() {
        assertNotNull(model);
        assertEquals("Advanced Search Page", model.getAdvSearchText());
        assertEquals("Search", model.getButtonText());
        assertEquals("Check-in", model.getCheckinLabel());
        assertEquals("Select Check in date", model.getCheckinText());
        assertEquals("Select checkout date", model.getCheckoutText());
        assertEquals("Check-out", model.getCheckoutLabel());
        assertEquals("Going to", model.getToLocationText());
        assertEquals("Rooms", model.getRoomsTypeHeading());
        assertNotNull(model.getRoomsTypeList());
        assertFalse(model.getRoomsTypeList().isEmpty());
        assertEquals("1 Room, 2 Adults", model.getRoomsTypeList().get(0).getTypeList());
    }
}
