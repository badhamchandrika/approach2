package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.services.ExcelToJsonService;
import com.day.cq.i18n.I18n;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class AirmilesCoreItemTest {

    private AemContext ctx=new AemContext();

    AirmilesCoreItem model;

    @Mock
    I18n i18n;

    @SneakyThrows
    @BeforeEach
    void setup(){
        ctx.load().json("/ca/airmiles/travel/core/models/item.json","/item");
        ctx.load().json("/ca/airmiles/travel/core/models/accordion.json","/aem-airmiles-travel2/components/accordion");
        ctx.currentResource("/item");
        model =  new AirmilesCoreItem(ctx.request(),ctx.currentResource());
        model.setTabImage("about");
    }

    @Test
    void test(){
        assertEquals("about", model.getTabImage());
        assertEquals("dataTrackID", model.getDataTrackID());
        assertEquals("dataClickID", model.getDataClickID());
        assertEquals("dataTrackType", model.getDataTrackType());
    }
}
