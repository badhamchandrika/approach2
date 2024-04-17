package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class used to cover {@link DataList}.
 *
 * @author pabpalac.
 */
class DataListTest {

    /**
     * Method should create an empty data list item.
     */
    @Test
    void shouldCreateDataListItem() {
        final DataList dataList = new DataList();
        assertNull(dataList.getItemID());
        assertNull(dataList.getDataListItem());
    }
}