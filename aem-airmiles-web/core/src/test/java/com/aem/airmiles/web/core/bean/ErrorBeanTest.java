package com.aem.airmiles.web.core.bean;

import org.junit.jupiter.api.Test;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ErrorBeanTest {

    @Test
    void emptyConstructor_shouldDeliverEmptyAttribs() {
        final ErrorBean errorBean = new ErrorBean();
        assertEquals(EMPTY, errorBean.getErrorCode());
        assertEquals(EMPTY, errorBean.getErrorMessage());
    }

    @Test
    void paramConstructor_shouldNotDeliverEmptyAttribs() {
        final ErrorBean errorBean = new ErrorBean("418", "I'm a teapot!!");
        assertNotEquals(EMPTY, errorBean.getErrorCode());
        assertNotEquals(EMPTY, errorBean.getErrorMessage());
    }
}