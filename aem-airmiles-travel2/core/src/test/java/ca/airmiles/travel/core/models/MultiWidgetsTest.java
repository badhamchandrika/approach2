package ca.airmiles.travel.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class MultiWidgetsTest {
    private AemContext ctx = new AemContext();
    MultiWidgets model;

    @BeforeEach
    void setup(){
        ctx.load().json("/ca/airmiles/travel/core/models/multiwidgets.json","/content/multiwidgets");
        ctx.load().json("/ca/airmiles/travel/core/models/flightWidgetXF.json","/content/experience-fragments/aem-airmiles-travel2/ca/en/widgets/flights-widget");
        ctx.currentResource("/content/multiwidgets");
        model = ctx.currentResource().adaptTo(MultiWidgets.class);
    }

    @Test
    void test(){
        assertEquals(2,  model.getWidgets().size());
        Widget first =  model.getWidgets().get(0);
        Widget second =  model.getWidgets().get(1);
        assertEquals("/content/experience-fragments/aem-airmiles-travel2/ca/en/widgets/flights-widget/master",first.getPath());
        assertEquals("",second.getPath());
        assertEquals("test 1", first.getTabName());
        assertEquals("test 2", second.getTabName());
    }
}
