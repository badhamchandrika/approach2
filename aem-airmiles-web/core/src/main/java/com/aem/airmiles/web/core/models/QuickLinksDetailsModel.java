package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;

public class QuickLinksDetailsModel {

    @Getter
    @Setter
    @ValueMapValue
    private String dataTrackSection;

    @Getter
    @Setter
    @ValueMapValue
    private String title;

    @Getter
    @Setter
    @ValueMapValue
    private String description;

    @Setter
    @ValueMapValue
    private String[] quicklinks;

    @PostConstruct
    public List<Links> getQuicklinks() throws JsonProcessingException {
        return Utils.getLinks(quicklinks);
    }


}
