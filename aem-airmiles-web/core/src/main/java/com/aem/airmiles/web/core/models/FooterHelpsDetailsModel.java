package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;

public class FooterHelpsDetailsModel {

    @Getter
    @Setter
    @ValueMapValue
    private String heading;

    @Setter
    @ValueMapValue
    private String[] footerhelps;

    @PostConstruct
    public List<Links> getFooterHelps() throws JsonProcessingException {        
        return Utils.getLinks(footerhelps);
    }
}