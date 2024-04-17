package ca.airmiles.travel.core.services.impl;

import ca.airmiles.travel.core.constant.ContentConstant;
import ca.airmiles.travel.core.services.NeuronService;
import ca.airmiles.travel.core.utils.Utils;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.jcr.JcrUtil;
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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Component(service = WorkflowProcess.class, property = {"process.label=Json Update Process"})
public class JsonUpdateProcess implements WorkflowProcess {

    private static final Logger log = LoggerFactory.getLogger(JsonUpdateProcess.class);

    @Reference
    NeuronService neuronService;

    @Reference
    Replicator replicator;

    @Reference
    ResourceResolverFactory resolverFactory;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        String payload = (String) workItem.getWorkflowData().getPayload();
        ResourceResolver resourceResolver = null;
        try {
            resourceResolver = Utils.getServiceResourceResolver(resolverFactory);
            if(StringUtils.isNotBlank(payload) && StringUtils.endsWith(payload,".json")){
                payload = StringUtils.substring(payload, 0, StringUtils.lastIndexOf(payload,"/"));
            }
            String title = getFolderTitle(payload, resourceResolver);
            if (StringUtils.isNotBlank(title)) {
                String packageID = neuronService.getPackageID(title);
                if (StringUtils.isNotBlank(packageID)) {
                    updateJsonFile(neuronService.getPackages(packageID), resourceResolver, payload, title);
                }
            }
        } catch (LoginException e) {
            log.error("Unable to login into the repository", e);
        } finally {
            if(null != resourceResolver && resourceResolver.isLive()){
                resourceResolver.close();
            }
        }
    }

    private void updateJsonFile(String packages, ResourceResolver resourceResolver, String payload, String title) {
        AssetManager assetManager = resourceResolver.adaptTo(AssetManager.class);
        String binaryPath = payload + "/"+ JcrUtil.createValidName(title) + ".json";
        Session session = resourceResolver.adaptTo(Session.class);
        InputStream stream = null;
        if (null != assetManager && null != session && StringUtils.isNotBlank(packages)) {
            stream = new ByteArrayInputStream(packages.getBytes(StandardCharsets.UTF_8));
            try {
                Binary binary = session.getValueFactory().createBinary(stream);
                log.info("Creating or Updating the Asset {}", binaryPath);
                Asset asset = assetManager.createOrUpdateAsset(binaryPath, binary, ContentConstant.MIMETYPE_JSON, true);
                if (asset != null) {
                    log.info("Replicating the Asset {}", binaryPath);
                    replicator.replicate(session, ReplicationActionType.ACTIVATE, binaryPath);
                }
            } catch (RepositoryException | ReplicationException e) {
                log.error("Unable to create or update or replicate file in DAM {}", binaryPath, e);
            }finally {
                try {
                    if(null != stream) stream.close();
                } catch (IOException e) {
                    log.error("Unable to close the input stream {}", binaryPath, e);
                }
            }
        } else {
            log.error("Unable to get the packages for path {}", binaryPath);
        }
    }

    private String getFolderTitle(String payload, ResourceResolver resourceResolver) {
        String title = null;
        Resource resource = resourceResolver.getResource(payload);
        if (null != resource) {
            Resource jcrResource = resource.getChild(JcrConstants.JCR_CONTENT);
            if (null != jcrResource)
                title = jcrResource.getValueMap().get(JcrConstants.JCR_TITLE, String.class);
        }
        return title;
    }
}
