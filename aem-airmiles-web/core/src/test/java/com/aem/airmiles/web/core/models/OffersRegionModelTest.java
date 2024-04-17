package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OffersRegionModelTest {

    OffersFilterListItem offersFilterListItem = new OffersFilterListItem();
    List<OffersFilterListItem> regionList = new ArrayList<>();
    OffersRegionModel offersRegionModel = new OffersRegionModel();
    @BeforeEach
    void setUp() {
        offersFilterListItem.setTitle("Ontario");
        offersFilterListItem.setValue("ON");
        offersFilterListItem.setTitle("Alberta");
        offersFilterListItem.setValue("AB");
        regionList.add(offersFilterListItem);
        offersRegionModel.setRegionList(regionList);
    }

    @Test
    void getRegionList() {
            assertEquals(regionList,offersRegionModel.getRegionList());
        }

}