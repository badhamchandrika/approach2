package ca.airmiles.travel.core.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeList {

    @Getter
    @Setter
    @ValueMapValue
    private String typeList;

    @Getter
    @Setter
    @ValueMapValue
    private String actualValue;

    @Getter
    @Setter
    @ValueMapValue
    private String displayType;
}
