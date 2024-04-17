package ca.airmiles.travel.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ExperienceTypeModel {

    @ValueMapValue
    private String experienceType;

    @ValueMapValue
    private String experienceFragment;

    public String getExperienceType() {
        return experienceType;
    }

    public String getExperienceFragment() {
        return experienceFragment;
    }

    public void setExperienceFragment(String experienceFragment) {
        this.experienceFragment = experienceFragment;
    }
}