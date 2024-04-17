package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OffersFilterListItemTest {
    OffersFilterListItem offersFilterListItem = new OffersFilterListItem();
    @BeforeEach
    void setUp() {
        offersFilterListItem.setTitle("Ready To Use");
        offersFilterListItem.setValue("No Action");
    }
    @Test
    void test_Details(){
        assertEquals("Ready To Use",offersFilterListItem.getTitle());
        assertEquals("No Action",offersFilterListItem.getValue());
    }
}
