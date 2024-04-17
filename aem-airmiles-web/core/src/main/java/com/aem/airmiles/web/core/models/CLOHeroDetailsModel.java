package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class CLOHeroDetailsModel {

    @Getter
    @Setter
    @ValueMapValue
    private String process1ImagePath;

    @Getter
    @Setter
    @ValueMapValue
    private String process2ImagePath;

    @Getter
    @Setter
    @ValueMapValue
    private String process3ImagePath;

    @Getter
    @Setter
    @ValueMapValue
    private String step1Title;

    @Getter
    @Setter
    @ValueMapValue
    private String step2Title;

    @Getter
    @Setter
    @ValueMapValue
    private String step3Title;

    @Getter
    @Setter
    @ValueMapValue
    private String linkYourCardCtaText;

    @Getter
    @Setter
    @ValueMapValue
    private String  linkYourCardUrlToDisplay;

    @Getter
    @Setter
    @ValueMapValue
    private String linkYourCardExternalUrl;

    @Setter
    @ValueMapValue
    private String linkYourCardInternalUrl;
    @Getter
    @Setter
    @ValueMapValue
    private String faqText;

    @Getter
    @Setter
    @ValueMapValue
    private String faqUrl;

    @Getter
    @Setter
    @ValueMapValue
    private String learnMoreText;

    @Getter
    @Setter
    @ValueMapValue
    private String linkYourCardDataClickId;

    @Getter
    @Setter
    @ValueMapValue
    private String linkYourCardDataTrackId;

    @Getter
    @Setter
    @ValueMapValue
    private String linkYourCardDataTrackType;

    @Getter
    @Setter
    @ValueMapValue
    private String learnMoreClickId;

    @Getter
    @Setter
    @ValueMapValue
    private String learnMoreDataTrackId;

    @Getter
    @Setter
    @ValueMapValue
    private String learnMoreDataTrackType;

    @Getter
    @Setter
    @ValueMapValue
    private String linkYourCardTarget;

    @Getter
    @Setter
    @ValueMapValue
    private String faqTarget;

    @Getter
    @Setter
    @ValueMapValue
    private String faqDataClickId;

    @Getter
    @Setter
    @ValueMapValue
    private String faqDataTrackId;

    @Getter
    @Setter
    @ValueMapValue
    private String faqDataTrackType;

    @Getter
    @Setter
    @ValueMapValue
    private String learnMoreTarget;

    @Getter
    @Setter
    @ValueMapValue
    private String learnMoreUrlToDisplay;

    @Getter
    @Setter
    @ValueMapValue
    private String learnMoreExternalUrl;
    @Setter
    @ValueMapValue
    private String learnMoreInternalUrl;

    @Getter
    @Setter
    @ValueMapValue
    private String informationText;

    public String getLinkYourCardInternalUrl() {
        return Utils.getFormattedURL(linkYourCardInternalUrl);
    }

    public String getLearnMoreInternalUrl() {
        return Utils.getFormattedURL(learnMoreInternalUrl);
    }
}

