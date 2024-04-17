package com.aem.airmiles.web.core.models;

import com.adobe.cq.export.json.ExporterConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.resource.Resource;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},resourceType="aem-airmiles-web/components/airmiles-core/welcomebanner", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name= ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions= ExporterConstants.SLING_MODEL_EXTENSION)
public class WelcomeBannerModel {

    @Setter
    @Getter
    @ValueMapValue
    @Default(values = "Good Morning")
    private String goodMorningText;

    @Setter
    @Getter
    @ValueMapValue
    @Default(values = "Good Afternoon")
    private String goodAfternoonText;

    @Setter
    @Getter
    @ValueMapValue
    @Default(values = "Good Evening")
    private String goodEveningText;

    @Setter
    @Getter
    @ValueMapValue
    @Default(values = "Blue Collector")
    private String blueCollectorText;

    @Setter
    @Getter
    @ValueMapValue
    @Default(values = "Gold Collector")
    private String goldCollectorText;

    @Setter
    @Getter
    @ValueMapValue
    @Default(values = "Onyx Collector")
    private String onyxCollectorText;

    @Setter
    @Getter
    @ValueMapValue
    @Default(values = "Cash Miles")
    private String cashMilesText;

    @Setter
    @Getter
    @ValueMapValue
    @Default(values = "Dream Miles")
    private String dreamMilesText;

    @Setter
    @Getter
    @ValueMapValue
    private String milesAllocationText;

    @Setter
    @Getter
    @ValueMapValue
    private String cashPercentageText;

    @Setter
    @Getter
    @ValueMapValue
    private String dreamPercentageText;

    @Setter
    @Getter
    @ValueMapValue
    private String blueTierColor;

    @Setter
    @Getter
    @ValueMapValue
    private String goldTierColor;

    @Setter
    @Getter
    @ValueMapValue
    private String onyxTierColor;

    @Getter
    @Setter
    @ValueMapValue
    private String setBalancePreferenceCTATxt;

    @Getter
    @Setter
    @ValueMapValue
    private String setBalancePreferenceCTAUrl;

    @Getter
    @Setter
    @ValueMapValue
    private String balancePreferenceUrlTarget;

    @ValueMapValue
    @Setter
    @Getter
    private String balDataTrackID;

    @ValueMapValue
    @Setter
    @Getter
    private String cta2HexTextColor;

    @ValueMapValue
    @Setter
    @Getter
    private String cta2LinkType;

    @ValueMapValue
    @Setter
    @Getter
    private String cta2HexBtnColor;

    @ValueMapValue
    @Setter
    @Getter
    private String cta2LnkColor;

    @ValueMapValue
    @Setter
    @Getter
    private String balDataClickID;

    @ValueMapValue
    @Setter
    @Getter
    private String balDataTrackType;

    @ValueMapValue
    @Setter
    @Getter
    private String mockJson;

    @ValueMapValue
    @Setter
    @Getter
    private String id;
}
