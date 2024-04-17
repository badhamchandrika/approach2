package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class ContentBlockShellTest extends TestUtil {
    @Test
    void getType() {
        aemctx.currentResource("/contentblockshell/content");
        contentBlockShell = aemctx.request().adaptTo(ContentBlockShell.class);
        assertEquals("prizes", contentBlockShell.getType());
    }

    @Test
    void getSubmitErrorMessage() {
        aemctx.currentResource("/contentblockshell/content");
        contentBlockShell = aemctx.request().adaptTo(ContentBlockShell.class);
        assertEquals("We are sorry", contentBlockShell.getSubmitErrorMessage());
    }

    @Test
    void getCardsList() {
        aemctx.currentResource("/contentblockshell/content");
        contentBlockShell = aemctx.request().adaptTo(ContentBlockShell.class);
        assertEquals("consectetur adipiscing elit. Curabitur non sollicitudin odio. Curabitur congue elementum metus.", contentBlockShell.getCardsList().get(0).get("description"));
        assertEquals("/content/dam/aem-airmiles-web/shellgoplus/Screen-partial.png", contentBlockShell.getCardsList().get(0).get("foregroundImage"));
        assertEquals("left", contentBlockShell.getCardsList().get(0).get("swapPlacement"));
        assertEquals("redtag.ca Travel Gift Card", contentBlockShell.getCardsList().get(0).get("title"));
        assertEquals("#ffffff", contentBlockShell.getCardsList().get(0).get("backgroundColor"));
    }
}
