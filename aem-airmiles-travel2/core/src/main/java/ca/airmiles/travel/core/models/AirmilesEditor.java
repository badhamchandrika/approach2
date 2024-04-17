package ca.airmiles.travel.core.models;

import com.day.cq.wcm.api.components.Component;
import com.day.cq.wcm.api.components.ComponentManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class})
public class AirmilesEditor {

    @Self
    private SlingHttpServletRequest request;

    private Resource container;

    private List<AirmilesCoreItem> items;

    @PostConstruct
    private void initModel() {
        readChildren();
    }

    private void readChildren() {
        items = new ArrayList<>();
        String containerPath = request.getRequestPathInfo().getSuffix();
        if (StringUtils.isNotEmpty(containerPath)) {
            ResourceResolver resolver = request.getResourceResolver();
            container = resolver.getResource(containerPath);
            if (container != null) {
                ComponentManager componentManager = request.getResourceResolver().adaptTo(ComponentManager.class);
                for (Resource resource : container.getChildren()) {
                    if (componentManager != null && resource != null) {
                        items.add(new AirmilesCoreItem(request, resource));
                    }
                }
            }
        }
    }

    /**
     * Retrieves the child items associated with this children editor.
     *
     * @return a list of child items
     */
    public List<AirmilesCoreItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Retrieves the container resource associated with this children editor.
     *
     * @return the container resource, or {@code null} if no container can be found
     */
    public Resource getContainer() {
        return container;
    }
}
