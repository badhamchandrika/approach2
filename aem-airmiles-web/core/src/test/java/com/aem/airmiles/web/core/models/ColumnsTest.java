package com.aem.airmiles.web.core.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ColumnsTest {

    ColumnsModel columns = new ColumnsModel();

    @BeforeEach
    void init() throws ParseException {
        columns.setColumnnumber("onenarrow");
        columns.setFullwidth(true);
        columns.setVerticalCenter(true);
        columns.setHorizontalCenter(true);
        columns.setBackgroundcolor("bg-transparent");
    }

    @Test
    void test_Columns(){
        assertEquals("onenarrow",columns.getColumnnumber());
        assertTrue(columns.isFullwidth());
        assertTrue(columns.isVerticalCenter());
        assertTrue(columns.isHorizontalCenter());
        assertEquals("bg-transparent",columns.getBackgroundcolor());
    }

}
