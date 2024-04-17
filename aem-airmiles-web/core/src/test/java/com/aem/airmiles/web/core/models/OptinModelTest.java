package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class OptinModelTest extends TestUtil {
    
    @Test
    void getOptinIcon() {
        aemctx.currentResource("/optinmodelcontent/optin");
        optin = aemctx.request().adaptTo(OptinModel.class);
        assert optin != null;
        String icon ="am-icon-inbox-unopened";
        optin.setIcon(icon);
        assertEquals(icon, optin.getIcon());
    }

    @Test
    void getOptinTitle() {
        aemctx.currentResource("/optinmodelcontent/optin");
        optin = aemctx.request().adaptTo(OptinModel.class);
        assert optin != null;
        String title ="Keep connected";
        optin.setTitle(title);
        assertEquals(title, optin.getTitle());
    }

    @Test
    void getOptinDescription() {
        aemctx.currentResource("/optinmodelcontent/optin");
        optin = aemctx.request().adaptTo(OptinModel.class);
        assert optin != null;
        String description ="Never miss a chance to get Miles - plus contests, updates and more!";
        optin.setDescription(description);
        assertEquals(description, optin.getDescription());
    }

    @Test
    void getOptinLinkUrl() {
        aemctx.currentResource("/optinmodelcontent/optin");
        optin = aemctx.request().adaptTo(OptinModel.class);
        assert optin != null;
        String linkUrl ="https://oauth.airmiles.ca/login";
        optin.setLinkUrl(linkUrl);
        assertEquals(linkUrl, optin.getLinkUrl());
    }

    @Test
    void getOptinLinkText() {
        aemctx.currentResource("/optinmodelcontent/optin");
        optin = aemctx.request().adaptTo(OptinModel.class);
        assert optin != null;
        String linkText ="Opt in to emails";
        optin.setLinkText(linkText);
        assertEquals(linkText, optin.getLinkText());
    }

    @Test
    void getOptinLinkType() {
        aemctx.currentResource("/optinmodelcontent/optin");
        optin = aemctx.request().adaptTo(OptinModel.class);
        assert optin != null;
        String linkType ="extlink";
        optin.setLinkType(linkType);
        assertEquals(linkType, optin.getLinkType());
    }

    @Test
    void getOptinLinkTarget() {
        aemctx.currentResource("/optinmodelcontent/optin");
        optin = aemctx.request().adaptTo(OptinModel.class);
        assert optin != null;
        String linkTarget ="_self";
        optin.setLinkTarget(linkTarget);
        assertEquals(linkTarget, optin.getLinkTarget());
    }

    @Test
    void getDataTrackClick() {
        aemctx.currentResource("/optinmodelcontent/optin");
        optin = aemctx.request().adaptTo(OptinModel.class);
        assert optin != null;
        String dataClickID = "clickID";
        optin.setDataClickID(dataClickID);
        assertEquals(dataClickID, optin.getDataClickID());
    }

    @Test
    void getDataTrackID() {
        aemctx.currentResource("/optinmodelcontent/optin");
        optin = aemctx.request().adaptTo(OptinModel.class);
        assert optin != null;
        String dataTrackID = "TrackID";
        optin.setDataTrackID(dataTrackID);
        assertEquals(dataTrackID, optin.getDataTrackID());
    }
    
    @Test
    void getDataTrackType() {
        aemctx.currentResource("/optinmodelcontent/optin");
        optin = aemctx.request().adaptTo(OptinModel.class);
        assert optin != null;
        String dataTrackType = "internal";
        optin.setDataTrackType(dataTrackType);
        assertEquals(dataTrackType, optin.getDataTrackType());
    }
}
