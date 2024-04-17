package com.aem.airmiles.web.core.models;

import com.aem.airmiles.web.core.services.RunModeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static junitx.util.PrivateAccessor.setField;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The RunModeModelTest class is a JUnit test class for testing the RunModeModel class.
 * It verifies that the RunModeModel class correctly retrieves the current run mode environment.
 */
@ExtendWith(MockitoExtension.class)
class RunModeModelTest {

    /**
     * Represents a static instance of the RunModeModel class.
     */
    private static RunModeModel model;

    /**
     * The RUN_MODE variable represents the current run mode environment.
     */
    private static final String RUN_MODE = "local";

    /**
     * Performs setup before running all tests in the RunModeModelTest class.
     */
    @BeforeAll
    static void beforeAll() {
        model = new RunModeModel();
        final RunModeService svc = mock(RunModeService.class);
        when(svc.getEnvironment()).thenReturn(RUN_MODE);
        assertDoesNotThrow(() -> setField(model, "runModes", svc));
    }

    /**
     * Tests the current run mode environment in model.
     */
    @Test
    void getEnvironment() {
        assertEquals(RUN_MODE, model.getEnvironment());
    }
}