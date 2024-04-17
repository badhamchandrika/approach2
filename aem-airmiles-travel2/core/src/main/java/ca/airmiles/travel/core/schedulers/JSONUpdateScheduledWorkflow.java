package ca.airmiles.travel.core.schedulers;

import ca.airmiles.travel.core.utils.Utils;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Component(service = JSONUpdateScheduledWorkflow.class, immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = JSONUpdateScheduledWorkflowConfiguration.class)
public class JSONUpdateScheduledWorkflow implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private Scheduler scheduler;

    @Reference
    private ResourceResolverFactory resolverFactory;

    private String model;
    private List < String > payLoadPaths;

    @Activate
    protected void activate(final JSONUpdateScheduledWorkflowConfiguration config) {

        this.model = config.model();
        this.payLoadPaths = Arrays.asList(config.payloadPaths());

        addScheduler(config);
    }

    public void addScheduler(JSONUpdateScheduledWorkflowConfiguration config) {
        if (config.enable_scheduler()) {
            ScheduleOptions options = scheduler.EXPR(config.scheduler_expression());
            options.name(config.scheduler_name());
            options.canRunConcurrently(config.concurrent_scheduler());

            scheduler.schedule(this, options);
            logger.error("Scheduler added successfully name='{}'", config.scheduler_name());
        } else {
            logger.error("JSONUpdateScheduledWorkflow is disabled");
        }
    }

    public void removeScheduler(JSONUpdateScheduledWorkflowConfiguration config) {
        scheduler.unschedule(config.scheduler_name());
    }

    @Deactivate
    protected void deactivate(JSONUpdateScheduledWorkflowConfiguration config) {
        removeScheduler(config);
    }

    @Modified
    protected void modified(JSONUpdateScheduledWorkflowConfiguration config) {
        removeScheduler(config);
        addScheduler(config);
    }

    @Override
    public void run() {
        try (ResourceResolver resourceResolver = Utils.getServiceResourceResolver(resolverFactory)) {
            for (String payLoadPath: payLoadPaths) {
                WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);
                if (model != null && workflowSession != null) {
                    WorkflowModel workflowModel = workflowSession.getModel(model);
                    WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payLoadPath);
                    workflowSession.startWorkflow(workflowModel, workflowData);
                    logger.info("JSONUpdateScheduledWorkflow has been started: {}", workflowModel.getTitle());
                } else {
                    logger.warn("Workflow session or model is not available: {}", model);
                }
            }
        } catch (WorkflowException | LoginException e) {
            logger.error("Unable to start the scheduled workflow", e);
        }
    }
}