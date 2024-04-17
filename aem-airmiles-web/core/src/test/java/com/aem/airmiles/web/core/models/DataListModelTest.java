package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class used to cover {@link DataListModel}.
 *
 * @author pabpalac.
 */
class DataListModelTest {

    /**
     * Method should create an empty data list model.
     */
    @Test
    void shouldCreateDataListModel() {
        final DataListModel dataListModel = new DataListModel();
        assertNull(dataListModel.getId());
        assertNull(dataListModel.getDataListTitle());
        assertNull(dataListModel.getDataList());
    }
}