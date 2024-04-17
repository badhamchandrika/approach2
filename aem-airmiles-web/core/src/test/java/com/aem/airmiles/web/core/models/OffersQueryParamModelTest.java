package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OffersQueryParamModelTest {

    List<String> massOffers = new ArrayList<>();
    List<String> partnerIDs = new ArrayList<>();
    List<String> categoriesIDs = new ArrayList<>();
    List<String> subCategoriesIDs = new ArrayList<>();
    List<String> programType = new ArrayList<>();
    OffersQueryParamModel offersQueryParamModel = new OffersQueryParamModel();

    @BeforeEach
    void setUp() {
        programType.add("traditionalcore");
        programType.add("cardlinked");
        offersQueryParamModel.setProgramType(programType);
        massOffers.add("massoffers");
        massOffers.add("targeted offers");
        offersQueryParamModel.setMassOffer(massOffers);
        partnerIDs.add("4556-6767-cf5563744");
        partnerIDs.add("4556-6767-cf5563745");
        offersQueryParamModel.setPartnerId(partnerIDs);
        categoriesIDs.add("4556-6767-cf5563746");
        categoriesIDs.add("4556-6767-cf5563747");
        offersQueryParamModel.setCategoryId(categoriesIDs);
        subCategoriesIDs.add("4556-6767-cf5563748");
        subCategoriesIDs.add("4556-6767-cf5563749");
        offersQueryParamModel.setSubcategoryId(subCategoriesIDs);
        offersQueryParamModel.setPromotionId("4556-6767-cf5563740");
        offersQueryParamModel.setSortOptions("default");

    }

    @Test
    void getSortOptions() {
        assertEquals("default",offersQueryParamModel.getSortOptions());
    }

    @Test
    void getMassOffer() {
        assertEquals(massOffers,offersQueryParamModel.getMassOffer());
    }

    @Test
    void getProgramType() {
        assertEquals(programType,offersQueryParamModel.getProgramType());
    }

    @Test
    void getPartnerId() {
        assertEquals(partnerIDs,offersQueryParamModel.getPartnerId());
    }

    @Test
    void getPromotionId() {
        assertEquals("4556-6767-cf5563740",offersQueryParamModel.getPromotionId());
    }

    @Test
    void getCategoryId() {
        assertEquals(categoriesIDs,offersQueryParamModel.getCategoryId());
    }

    @Test
    void getSubcategoryId() {
        assertEquals(subCategoriesIDs,offersQueryParamModel.getSubcategoryId());
    }
}