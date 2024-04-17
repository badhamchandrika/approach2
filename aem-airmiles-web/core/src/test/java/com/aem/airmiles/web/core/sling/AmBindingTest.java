package com.aem.airmiles.web.core.sling;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.script.SimpleBindings;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * The AmBindingTest class is a test class for the AmBinding class.
 * It tests the functionality of the addBindings method.
 *
 * @author pabpalac.
 */
@ExtendWith(AemContextExtension.class)
class AmBindingTest {

    /**
     * Tests the {@code addBindings} method of the {@code AmBinding} class to ensure it correctly adds
     * bindings to a {@link SimpleBindings} instance without throwing any exceptions. This test specifically
     * adds a "request" binding, using the request object obtained from the provided {@link AemContext}.
     *
     * @param ctx An {@link AemContext} object provided to the test method, which should not be {@code null}.
     *            It represents the AEM context in which the test is run, encapsulating request, response,
     *            and other AEM-specific objects required for testing.
     * @throws AssertionError if the {@code addBindings} method call throws an exception, indicating the test
     *                        has failed. This ensures the test only passes when {@code addBindings} executes
     *                        successfully and no exception is thrown.
     * @see AemContext#request() for details on the request object provided by the AEM context.
     * @see SimpleBindings for details on the bindings container used in this test.
     * @see AmBinding#addBindings(javax.script.Bindings) for details on the method being tested.
     */
    @Test
    void testAddBindings(final @NotNull AemContext ctx) {
        assertDoesNotThrow(() -> new AmBinding().addBindings(new SimpleBindings() {{
            put("request", ctx.request());
        }}));
    }
}