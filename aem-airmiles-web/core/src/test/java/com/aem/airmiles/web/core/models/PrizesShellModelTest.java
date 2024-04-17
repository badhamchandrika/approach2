package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.models.factory.ModelFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class to cover {@link PrizesShellModel model}. It uses AEM Context Extension to mock AEM Resources.
 *
 * @author pabpalac.
 */
@ExtendWith(AemContextExtension.class)
class PrizesShellModelTest {

    /**
     * Model to be tested.
     */
    private static PrizesShellModel model;

    /**
     * Initialize AEM Context.
     */
    static final AemContext context = new AemContext();

    /**
     * Init the resources only once before all the tests.
     */
    @BeforeAll
    static void beforeAll() {
        context.addModelsForPackage("com.pc.core.models");
        context.load().json("/com.aem.airmiles.web.core/PrizesShellModelTest.json",
                "/prizesshellmodel");
        context.currentResource("/prizesshellmodel/content");
        model = Objects.requireNonNull(context.getService(ModelFactory.class)).createModel(
                Objects.requireNonNull(context.currentResource()), PrizesShellModel.class);
    }

    /**
     * Check that the message is properly set.
     */
    @Test
    void getSubmitErrorMessage() {
        assertEquals("We are sorry", model.getSubmitErrorMessage());
    }

    /**
     * Check that children properties are properly set.
     */
    @Test
    void getCardsList() {
        final List<Map<String, String>> cards = model.getCardsList();
        assertEquals("Large", cards.get(0).get("textSize"));
        assertEquals("#ffffff", cards.get(0).get("textColor"));
        assertEquals("left", cards.get(0).get("swapPlacement"));
        assertEquals("#ffffff", cards.get(0).get("backgroundColor"));
        assertEquals("redtag.ca Travel Gift Card", cards.get(0).get("title"));
        assertEquals("/content/dam/aem-airmiles-web/shellgoplus/Screen-partial.png",
                cards.get(0).get("backgroundImage"));
        assertEquals("consectetur adipiscing elit. Curabitur non sollicitudin odio. Curabitur congue " +
                "elementum metus.", cards.get(0).get("description"));
    }
}
