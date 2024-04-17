package ca.airmiles.travel.core.schedulers;

import ca.airmiles.travel.core.utils.Utils;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({
        AemContextExtension.class,
        MockitoExtension.class
})
class JSONUpdateScheduledWorkflowTest {

    @Rule
    public AemContext aemContext = new AemContext();

    private ResourceResolverFactory resolverFactory;

    @Mock
    Scheduler scheduler;

    @Mock
    ScheduleOptions scheduleOptions;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    WorkflowSession workflowSession;

    @Mock
    WorkflowData workflowData;

    @Mock
    WorkflowModel workflowModel;

    JSONUpdateScheduledWorkflow jsonUpdateScheduledWorkflow;

    String[] payloadPaths = {
            "/content/dam/aem-airmiles-travel2/trisept-jsons/activities/activities.json",
            "/content/dam/aem-airmiles-travel2/trisept-jsons/flights/flights.json",
            "/content/dam/aem-airmiles-travel2/trisept-jsons/packages/packages.json",
            "/content/dam/aem-airmiles-travel2/trisept-jsons/rentals/rentals.json",
            "/content/dam/aem-airmiles-travel2/trisept-jsons/stays/stays.json"
    };

    @BeforeEach
    void setUp() {
        resolverFactory = mock(ResourceResolverFactory.class);
        aemContext.registerService(ResourceResolverFactory.class, resolverFactory);
        aemContext.registerService(Scheduler.class, scheduler);
        aemContext.registerService(ScheduleOptions.class, scheduleOptions);
        aemContext.registerService(WorkflowModel.class, workflowModel);
        aemContext.registerService(WorkflowData.class, workflowData);
        when(scheduler.EXPR("30 1 1,15 * *")).thenReturn(scheduleOptions);
        Map<String, Object> map = new HashMap<>();
        map.put("scheduler.name", "JSON Update Workflow Scheduler");
        map.put("scheduler.expression", "30 1 1,15 * *");
        map.put("concurrent.scheduler", false);
        map.put("enable.scheduler", true);
        map.put("model", "/var/workflow/models/airmiles-travel-json-update");
        map.put("payloadPaths", payloadPaths);
        jsonUpdateScheduledWorkflow = aemContext.registerInjectActivateService(JSONUpdateScheduledWorkflow.class, map);
    }

    @Test
    void test_JSONUpdateScheduledWorkflow_shouldExecute() throws LoginException, WorkflowException {

        resourceResolver = aemContext.resourceResolver();
        lenient().when(Utils.getServiceResourceResolver(resolverFactory)).thenReturn(resourceResolver);
        aemContext.registerAdapter(ResourceResolver.class, WorkflowSession.class, workflowSession);
        when(workflowSession.getModel("/var/workflow/models/airmiles-travel-json-update")).thenReturn(workflowModel);
        when(workflowSession.newWorkflowData("JCR_PATH", "/content/dam/aem-airmiles-travel2/trisept-jsons/activities/activities.json")).thenReturn(workflowData);

        jsonUpdateScheduledWorkflow.run();

        verify(workflowSession, times(5)).startWorkflow(any(), any());
    }

    @Test
    void test_JSONUpdateScheduledWorkflow_shouldFail() throws LoginException, WorkflowException {

        resourceResolver = aemContext.resourceResolver();
        lenient().when(Utils.getServiceResourceResolver(resolverFactory)).thenReturn(resourceResolver);

        jsonUpdateScheduledWorkflow.run();

        verify(workflowSession, times(0)).startWorkflow(any(), any());
    }
}