package ca.airmiles.travel.core.services.impl;

import ca.airmiles.travel.core.utils.Utils;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.tagging.InvalidTagFormatException;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component(service = WorkflowProcess.class, property = {"process.label=Add Tag Process"})
public class AddTagProcess implements WorkflowProcess {

    private static final Logger log = LoggerFactory.getLogger(AddTagProcess.class);

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private Replicator replicator;


    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        try (ResourceResolver resourceResolver = Utils.getServiceResourceResolver(resolverFactory)) {
            String payload = (String) workItem.getWorkflowData().getPayload();
            PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
            if (null != pageManager) {
                Page page = pageManager.getPage(payload);
                if (null != page) {
                    String process = metaDataMap.get("PROCESS_ARGS", "");
                    Map<String, String> map = Arrays.stream(process.split(",")).map(s -> s.split("=")).collect(Collectors.toMap(a -> a[0], a -> a[1]));
                    String contentRoot = map.get("contentRoot");
                    String pagePath = page.getPath();
                    if (StringUtils.contains(pagePath, contentRoot)) {
                        String path = pagePath.replace(contentRoot, StringUtils.EMPTY);
                        if (StringUtils.startsWith(path, "/")) {
                            path = path.substring(1);
                        }
                        String[] tags = path.split("/");
                        createTags(tags, page.getTitle(), resourceResolver, map.get("tagRoot"));
                    }
                }
            }
        } catch (LoginException e) {
            log.error("Unable to create the tags", e);
        }
    }

    private void createTags(String[] tags, String pageTitle, ResourceResolver resourceResolver, String tagRoot) {
        TagManager tagManager = resourceResolver.adaptTo(TagManager.class);
        if (null != tagManager) {
            Tag tag = tagManager.resolve(tagRoot);
            for (int i = 0, tagsLength = tags.length; i < tagsLength; i++) {
                String tagTitle = tags[i];
                if (i == tags.length - 1) {
                    tagTitle = pageTitle;
                }
                if (null != tag) {
                    tag = createTag(tag.getTagID() + "/" + tags[i], tagTitle, tagManager);
                }
            }
            if (tag != null) {
                try {
                    replicator.replicate(resourceResolver.adaptTo(Session.class), ReplicationActionType.ACTIVATE, tag.getPath());
                } catch (ReplicationException e) {
                    log.error("Unable to replicate the tags", e);
                }
            }
        }
    }

    private Tag createTag(String tagID, String tagTitle, TagManager tagManager) {
        Tag tag = null;
        try {
            tag = tagManager.resolve(tagID);
            if (null == tag) {
                tag = tagManager.createTag(tagID, tagTitle, null, true);
            }
        } catch (InvalidTagFormatException e) {
            log.error("Unable to create the tags", e);
        }
        return tag;
    }

}
