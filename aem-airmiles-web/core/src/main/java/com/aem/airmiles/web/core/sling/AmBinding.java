package com.aem.airmiles.web.core.sling;

import com.aem.airmiles.web.core.models.RunModeModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.scripting.api.BindingsValuesProvider;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;

import javax.script.Bindings;

/**
 * Class for injecting a custom Sling Models into the Sightly bindings object, quicker for developers to interact with
 * the website.
 *
 * @author pabpalac.
 */
@Component(immediate = true, service = BindingsValuesProvider.class)
public class AmBinding implements BindingsValuesProvider {

    /**
     * Adding new Sightly variables to allow developers calling them directly through components using HTL instead of
     * having to call a JAVA model to reference SlingSettingService.
     *
     * @param bindings binding objects.
     */
    @Override
    public void addBindings(final @NotNull Bindings bindings) {
        final SlingHttpServletRequest request = (SlingHttpServletRequest) bindings.get("request");
        if (null != (request))
            bindings.put("runMode", request.adaptTo(RunModeModel.class));
    }
}
