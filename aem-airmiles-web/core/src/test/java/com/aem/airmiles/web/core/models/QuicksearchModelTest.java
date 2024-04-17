package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static junitx.util.PrivateAccessor.setField;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class represents a set of test cases for {@link QuicksearchModel quick search model}.
 *
 * @author pabpalac.
 */
class QuicksearchModelTest {

    /**
     * The QuicksearchModel instance that will be covered.
     */
    private static QuicksearchModel model;

    /**
     * A set of constant values we'll use for testing.
     */
    private static final String id = "model-id", placeholder = "Search for topics.", suggestiveTexts = "5";

    /**
     * This method sets up testing environment before all tests. It creates a new instance of QuicksearchModel
     * and sets its fields with reflection.
     */
    @BeforeAll
    static void setUp() {
        model = new QuicksearchModel();
        assertDoesNotThrow(() -> {
            setField(model, "id", id);
            setField(model, "placeholder", placeholder);
            setField(model, "suggestiveTexts", suggestiveTexts);
        });
    }

    /**
     * This test method checks if the `id` field was properly set in our model.
     */
    @Test
    void getId() {
        assertEquals(id, model.getId());
    }

    /**
     * This test method checks if the `placeholder` field was properly set in our model.
     */
    @Test
    void getPlaceholder() {
        assertEquals(placeholder, model.getPlaceholder());
    }

    /**
     * This test method checks if the `suggestiveTexts` field was properly set in our model.
     */
    @Test
    void getSuggestiveTexts() {
        assertEquals(suggestiveTexts, model.getSuggestiveTexts());
    }
}