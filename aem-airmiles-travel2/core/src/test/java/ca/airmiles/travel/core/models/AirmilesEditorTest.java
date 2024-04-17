package ca.airmiles.travel.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import lombok.SneakyThrows;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class AirmilesEditorTest {

    @Mock
    RequestPathInfo requestPathInfo;
    private AemContext ctx = new AemContext();
    AirmilesEditor model;

    @SneakyThrows
    @BeforeEach
    void setup(){
        ctx.load().json("/ca/airmiles/travel/core/models/tabs.json","/item");
        ctx.load().json("/ca/airmiles/travel/core/models/accordion.json","/aem-airmiles-travel2/components/accordion");
        ctx.currentResource("/item");
        ctx.requestPathInfo().setResourcePath("/item");
        ctx.requestPathInfo().setSuffix("/item");
        model = ctx.request().adaptTo(AirmilesEditor.class);
    }

    @Test
    void test(){
        assertEquals(2, model.getItems().size());
        assertEquals(ctx.currentResource().getName(), model.getContainer().getName());
    }
}
