package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.inject.Inject;
import java.util.List;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, resourceType="aem-airmiles-web/components/airmiles-core/transactiontable", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name= ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions= ExporterConstants.SLING_MODEL_EXTENSION)
public class TransactionTable {

    @ValueMapValue
    @Getter
    private String tableTitle;

    @ValueMapValue
    @Getter
    private String noTransText;

    @ValueMapValue
    @Getter
    private String apiErrorMessage;

    @ValueMapValue
    @Getter
    private String tableCTAText;

    @ValueMapValue
    @Getter
    private String tableCTALink;

    @ValueMapValue
    @Getter
    private String tableCTATrackID;

    @ValueMapValue
    @Getter
    private String tableCTAClickID;

    @ValueMapValue
    @Getter
    private String tableCTATrackType;

    @ValueMapValue
    @Getter
    private String type;

    @ValueMapValue
    @Getter
    private boolean rounded;

    @ValueMapValue
    @Getter
    private boolean shadow;

    @ValueMapValue
    @Getter
    private boolean tableHeader;

    @ValueMapValue
    @Getter
    private boolean tableFooter;

    @ValueMapValue
    @Getter
    private boolean fullWidth;

    @ValueMapValue
    @Getter
    private boolean backgroundcolor;

    @ValueMapValue
    @Getter
    private String columnHead1;

    @ValueMapValue
    @Getter
    private String columnHead2;

    @ValueMapValue
    @Getter
    private String columnHead3;

    @ValueMapValue
    @Getter
    private String columnHead4;

    @ValueMapValue
    @Getter
    private String uniqueId;

    @ValueMapValue
    @Getter
    private String id;

    @ValueMapValue
    @Getter
    private boolean mockJson;

    @ValueMapValue
    @Getter
    private String noLogoPath;

    @Self
    @Getter
    private CTALink cta;

    @Getter
    @Setter
    @Inject
    @JsonProperty("transactionValues")
    @ChildResource(name = "transactionValues")
    List<TransactionDetailModel> transactionValues;

}
