package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class LinkModelTest extends TestUtil {

    @Test
    void getLinkModel_url() {
        linkModel = aemctx.request().adaptTo(LinkModel.class);
        linkModel.setUrl("https://www.google.com");
        assertEquals("https://www.google.com", linkModel.getUrl());
    }

    @Test
    void getLinkModel_text() {
        linkModel = aemctx.request().adaptTo(LinkModel.class);
        linkModel.setText("some text");
        assertEquals("some text", linkModel.getText());
    }

    @Test
    void getLinkModel_target() {
        linkModel = aemctx.request().adaptTo(LinkModel.class);
        linkModel.setTarget("some target");
        assertEquals("some target", linkModel.getTarget());
    }

    @Test
    void getLinkModel_authenticated() {
        linkModel = aemctx.request().adaptTo(LinkModel.class);
        linkModel.setAuthenticated(false);
        assertEquals(false, linkModel.isAuthenticated());
    }

    @Test
    void getLinkModel_emptydata() {
        aemctx.currentResource("/linkmodelcontent/empty_linkmodel");
        linkModel = aemctx.request().adaptTo(LinkModel.class);
        assertEquals(null, linkModel.getUrl());
    }

    

    @Test
    void getLinkModel_withdata() {
        aemctx.currentResource("/linkmodelcontent/one_linkmodel");
        linkModel = aemctx.request().adaptTo(LinkModel.class);
        System.out.println(linkModel.getUrl());
        System.out.println(linkModel.getText());
        System.out.println(linkModel.getTarget());
        assertEquals("https://www.github.com", linkModel.getUrl());
        assertEquals("new text for test", linkModel.getText());
        assertEquals("new test target", linkModel.getTarget());
    }

}