package ca.airmiles.travel.core.schedulers;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "JSON Update Workflow Scheduler Configuration",
        description = "JSON Update Workflow Scheduler Configuration"
)
public @interface JSONUpdateScheduledWorkflowConfiguration {

    @AttributeDefinition(
            name = "JSON Update Workflow Scheduler",
            description = "JSON Update Workflow Scheduler",
            type = AttributeType.STRING)
    String scheduler_name() default "JSON Update Workflow Scheduler";

    @AttributeDefinition(
            name = "Cron job expression",
            description = "Cron job expression",
            type = AttributeType.STRING)
    String scheduler_expression() default "0 0 0 ? * MON,THU";

    @AttributeDefinition(
            name = "Enable Scheduler",
            description = "Enable Scheduler",
            type = AttributeType.BOOLEAN)
    boolean enable_scheduler() default true;

    @AttributeDefinition(
            name = "Concurrent Scheduler",
            description = "Concurrent Scheduler",
            type = AttributeType.BOOLEAN)
    boolean concurrent_scheduler() default false;

    /**
     * This method returns the list of Payload Paths
     *
     * @return {@link String}
     */

    @AttributeDefinition(name = "Payload paths", description = "Payload paths", type = AttributeType.STRING)
    String[] payloadPaths() default {
            "/content/dam/aem-airmiles-travel2/trisept-jsons/activities/activities.json",
            "/content/dam/aem-airmiles-travel2/trisept-jsons/flights/flights.json",
            "/content/dam/aem-airmiles-travel2/trisept-jsons/packages/packages.json",
            "/content/dam/aem-airmiles-travel2/trisept-jsons/rentals/rentals.json",
            "/content/dam/aem-airmiles-travel2/trisept-jsons/stays/stays.json"
    };

    /**
     * This method return the model path
     *
     * @return {@model String}
     */
    @AttributeDefinition(name = "Workflow Model", description = "Airmiles Travel Json Update Workflow Model", type = AttributeType.STRING)
    String model() default "/var/workflow/models/airmiles-travel-json-update";

}