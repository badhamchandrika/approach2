package ca.airmiles.travel.core.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AdBanners extends ComponentModel {

    @Setter
    @Getter
    @ValueMapValue
    private String adSlotAdPath;

    @Setter
    @Getter
    @ValueMapValue
    private String adSlotAdID;

    @Setter
    @Getter
    @ValueMapValue
    private String adSlotAdSize;

}
