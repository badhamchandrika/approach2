package ca.airmiles.travel.core.services.impl;

import ca.airmiles.travel.core.services.BaseConfigurationService;
import com.day.cq.workflow.WorkflowException;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextBuilder;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.LoginException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.adobe.cq.wcm.core.components.testing.mock.ContextPlugins.CORE_COMPONENTS;
import static org.apache.sling.testing.mock.caconfig.ContextPlugins.CACONFIG;


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class BaseConfigurationServiceImplTest {

    AemContext context = new AemContext();

    BaseConfigurationService fixture;

    public final AemContext aemContext = new AemContextBuilder()
            .plugin(CORE_COMPONENTS)
            .plugin(CACONFIG)
            .<AemContext>afterSetUp(aemContext -> {
            }).build();




    @Test
    void test() throws  UnsupportedOperationException {
        fixture = new BaseConfigurationServiceImpl();
    }
}
