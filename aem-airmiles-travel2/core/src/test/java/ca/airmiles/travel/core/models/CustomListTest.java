package ca.airmiles.travel.core.models;

import com.adobe.acs.commons.genericlists.GenericList;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class CustomListTest {
    private AemContext ctx = new AemContext();
    CustomList model = new CustomList();

    @BeforeEach
    void setup(){
        ctx.load().json("/ca/airmiles/travel/core/models/generic-list.json","/etc/acs-commons/lists/airmiles/common-configurations/icons-list");
        ctx.request().setAttribute("listname","icons-list");
        ctx.addModelsForClasses(CustomList.class);
        model = ctx.request().adaptTo(CustomList.class);
    }

    @Test
    void test(){
        assertEquals(0, model.getList().size());
    }
}
