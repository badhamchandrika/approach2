package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.resource.Resource;

import javax.annotation.PostConstruct;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NavigationSectionBean {

    @Getter
    @Setter
    @ValueMapValue
    private String sectionTitle;

    @Getter
    @Setter
    @ValueMapValue
    private Boolean leftSeparator;

    @Getter
    @Setter
    @ValueMapValue
    private Boolean hideNavInMob;

    @Getter
    @Setter
    @ValueMapValue
    private Integer navColumnBreak;

    @Setter
    @ValueMapValue
    private String[] submenu;

    /**
     *
     * @return Map submenu array to List of Links Object
     * @throws JsonProcessingException this exception is thrown when mapping array of string to pojo
     */
    @PostConstruct
    public List<Links> getSubmenu() throws JsonProcessingException {
       return Utils.getLinks(submenu);
    }

}
