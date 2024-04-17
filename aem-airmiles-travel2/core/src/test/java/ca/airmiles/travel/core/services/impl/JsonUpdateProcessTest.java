package ca.airmiles.travel.core.services.impl;

import ca.airmiles.travel.core.services.NeuronService;
import ca.airmiles.travel.core.utils.Utils;
import com.day.cq.replication.Replicator;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.metadata.MetaDataMap;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.RepositoryException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class JsonUpdateProcessTest {

    AemContext context = new AemContext();

    JsonUpdateProcess fixture = new JsonUpdateProcess();

    @Mock
    private NeuronService neuronService;

    @Mock
    ResourceResolverFactory resolverFactory;

    @Mock
    Replicator replicator;

    @Mock
    WorkItem workItem;

    @Mock
    WorkflowData workflowData;

    @Mock
    WorkflowSession workflowSession;

    @Mock
    MetaDataMap metaDataMap;

    @BeforeEach
    void setUp() throws LoginException, NoSuchFieldException, RepositoryException {
        PrivateAccessor.setField(fixture,"resolverFactory", resolverFactory);
        PrivateAccessor.setField(fixture,"replicator", replicator);
        PrivateAccessor.setField(fixture,"neuronService", neuronService);
        context.load().json("/ca/airmiles/travel/core/services/impl/JsonUpdateProcessTest.json","/content/dam/aem-airmiles-travel2/travel-json/flights");
        when(workItem.getWorkflowData()).thenReturn(workflowData);
        when(workflowData.getPayload()).thenReturn("/content/dam/aem-airmiles-travel2/travel-json/flights/flights.json");
    }

    @Test
    void test_execute() throws WorkflowException, UnsupportedOperationException, LoginException {
        ResourceResolver resolver = context.resourceResolver();
        when(Utils.getServiceResourceResolver(resolverFactory)).thenReturn(resolver);
        when(neuronService.getPackageID(eq("Flights"))).thenReturn("A01");
        String response = "{\"Package\": {\"Id\": \"A01\",\"Name\": \"BLUE TEST - AIR ONLY - TEST FILE\",\"Description\": \"\"}";
        when(neuronService.getPackages(eq("A01"))).thenReturn(response);
        fixture.execute(workItem,workflowSession,metaDataMap);
    }
    @Test
    void test_exception() throws WorkflowException, UnsupportedOperationException, LoginException {
        when(Utils.getServiceResourceResolver(resolverFactory)).thenThrow(LoginException.class);
        fixture.execute(workItem,workflowSession,metaDataMap);
    }
}