package ca.airmiles.travel.core.services.impl;

import ca.airmiles.travel.core.services.ExcelToJsonService;
import ca.airmiles.travel.core.utils.Utils;
import com.day.cq.dam.api.Asset;
import com.day.cq.replication.Replicator;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.metadata.MetaDataMap;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Binary;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;
import java.io.InputStream;

import static ca.airmiles.travel.core.constant.ContentConstant.TRISEPTS_DAM;
import static ca.airmiles.travel.core.services.impl.XLSToJsonWorkflow.JSON_FILE_PATH;
import static org.mockito.Mockito.when;


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class XLSToJsonWorkflowTest {

    AemContext context = new AemContext();

    XLSToJsonWorkflow fixture = new XLSToJsonWorkflow();

    @Mock
    ExcelToJsonService excelToJsonService;

    @Mock
    Replicator replicator;

    @Mock
    ResourceResolverFactory resolverFactory;
    @Mock
    ResourceResolver resolver;

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
        PrivateAccessor.setField(fixture,"excelToJsonService", excelToJsonService);
        context.load().json("/ca/airmiles/travel/core/services/impl/ExcelFileProcessTest.json",TRISEPTS_DAM);
        when(workItem.getWorkflowData()).thenReturn(workflowData);
        when(workflowData.getPayload()).thenReturn(JSON_FILE_PATH);

    }

    @Test
    void test_execute() throws WorkflowException, UnsupportedOperationException, LoginException {
        ResourceResolver resourceResolver = context.resourceResolver();
        when(Utils.getServiceResourceResolver(resolverFactory)).thenReturn(resolver);
        fixture.execute(workItem,workflowSession,metaDataMap);
    }

    @Test
    void test_exception() throws WorkflowException, UnsupportedOperationException, LoginException {
        when(Utils.getServiceResourceResolver(resolverFactory)).thenThrow(LoginException.class);
        fixture.execute(workItem,workflowSession,metaDataMap);
    }
}
