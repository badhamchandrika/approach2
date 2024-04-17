package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class used to cover {@link HighlightCardsModel}.
 *
 * @author pabpalac.
 */
@ExtendWith(MockitoExtension.class)
class HighlightCardsModelTest {

    /**
     * Sling model.
     */
    HighlightCardsModel model;

    /**
     * Test field.
     */
    final String title = "LOREM";

    /**
     * To be executed before each test.
     */
    @BeforeEach
    void setUp() {
        // Instantiate new model.
        model = new HighlightCardsModel(){{

            // Set any list.
            setCardsMap(emptyList());

            // Set component title.
            setTitle(title);
        }};
    }

    /**
     * Test retrieval of title property.
     */
    @Test
    void getTitle_shouldGetProvidedTitle() {
        // Check that model does not crash and the title is the provided one.
        assertDoesNotThrow(() -> assertEquals(title, model.getTitle()));
    }

    /**
     * Test retrieval of card map.
     */
    @Test
    void getCardsMap_shouldNotBeNullAndMustContainProvidedList() {
        // Check that model does not crash.
        assertDoesNotThrow(() -> {

            // Verify that cards map is not null.
            assertNotNull(model.getCardsMap());

            // Verify that the map is empty as the provided map.
            assertTrue(model.getCardsMap().isEmpty());
        });
    }
}