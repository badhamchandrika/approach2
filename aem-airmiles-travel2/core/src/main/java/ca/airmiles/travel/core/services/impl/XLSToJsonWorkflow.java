package ca.airmiles.travel.core.services.impl;

import ca.airmiles.travel.core.constant.ContentConstant;
import ca.airmiles.travel.core.services.ExcelToJsonService;
import ca.airmiles.travel.core.utils.Utils;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Binary;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.io.InputStream;

import static ca.airmiles.travel.core.constant.ContentConstant.TRISEPTS_DAM;


@Component(service = WorkflowProcess.class, property = {"process.label=Transform Excel to Json"})
public class XLSToJsonWorkflow implements WorkflowProcess {

    private static final Logger log = LoggerFactory.getLogger(XLSToJsonWorkflow.class);
    public static final String JSON_FILE_PATH = TRISEPTS_DAM.concat("/clusters.json");

    @Reference
    ExcelToJsonService excelToJsonService;

    @Reference
    Replicator replicator;

    @Reference
    ResourceResolverFactory resolverFactory;

    /**
     *
     * @param workItem
     * @param workflowSession
     * @param metaDataMap
     * @throws WorkflowException
     */
    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        String payload = (String) workItem.getWorkflowData().getPayload();
        ResourceResolver resourceResolver = null;

        try {
            resourceResolver = Utils.getServiceResourceResolver(resolverFactory);
            if(StringUtils.isNotBlank(payload) && (StringUtils.contains(payload.toLowerCase(),".xls") || StringUtils.contains(payload.toLowerCase(),".xlsx"))){
                AssetManager assetMgr = resourceResolver.adaptTo(AssetManager.class);
                Session session = workflowSession.getSession();

                Resource resource = resourceResolver.getResource(payload);
                Asset asset = resource.getParent().getParent().getParent().adaptTo(Asset.class);
                InputStream originalXLS = asset.getOriginal().getStream();
                XSSFWorkbook workbook = new XSSFWorkbook(originalXLS);
                InputStream iStreamJson = excelToJsonService.getJson(workbook);
                Binary binary = session.getValueFactory().createBinary(iStreamJson);
                assetMgr.createOrUpdateAsset(JSON_FILE_PATH, binary, ContentConstant.MIMETYPE_JSON, true);
                log.info("Replicating the Asset {}", JSON_FILE_PATH);
                replicator.replicate(session, ReplicationActionType.ACTIVATE, JSON_FILE_PATH);

            }
        } catch (LoginException e) {
            log.error("Unable to login into the repository", e);
        } catch (ReplicationException | IOException | RepositoryException e) {
            log.error("Exception: ", e);
        } finally {
            if(null != resourceResolver && resourceResolver.isLive()){
                resourceResolver.close();
            }
        }
    }
}
