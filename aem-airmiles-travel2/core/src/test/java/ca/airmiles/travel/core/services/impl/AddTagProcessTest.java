package ca.airmiles.travel.core.services.impl;

import ca.airmiles.travel.core.utils.Utils;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.Replicator;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class AddTagProcessTest {

    AemContext context = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    AddTagProcess fixture = new AddTagProcess();

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
    void setUp() throws NoSuchFieldException {
        context.load().json("/ca/airmiles/travel/core/services/impl/AddTagProcessTest.json", "/content");
        PrivateAccessor.setField(fixture, "resolverFactory", resolverFactory);
        PrivateAccessor.setField(fixture, "replicator", replicator);
        when(workItem.getWorkflowData()).thenReturn(workflowData);
        when(workflowData.getPayload()).thenReturn("/content/partner-1");

    }

    @Test
    void execute() throws WorkflowException, LoginException {
        ResourceResolver resolver = context.resourceResolver();
        when(Utils.getServiceResourceResolver(resolverFactory)).thenReturn(resolver);
        when(metaDataMap.get(eq("PROCESS_ARGS"), eq(""))).thenReturn("tagRoot=/content/cq:tags/travel,contentRoot=/content");
        fixture.execute(workItem, workflowSession, metaDataMap);
    }
}