package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class OffersPartnersModelTest {
    static OffersPartnersModel offersPartnersModel = new OffersPartnersModel();

    @BeforeEach
    void setUp() {
        offersPartnersModel.setSuggestPartnerNum(5);
        offersPartnersModel.setStandalone(true);
    }

    @Test
    void test_Offers_Details(){
        assertEquals(5, offersPartnersModel.getSuggestPartnerNum());
        assertEquals(true, offersPartnersModel.getStandalone());
    }
}
