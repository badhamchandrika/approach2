package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AMToastsModelTest {

    AMToastsModel amToastsModel = new AMToastsModel();
    List<AMToast> toastDetails = new ArrayList<>();

    AMToast amToast = new AMToast();
    @BeforeEach
    void setUp() {
        amToast.setToastUID("save-offer");
        amToast.setToastMessage("offers are saved");
        amToast.setToastTitle("Offers");
        amToast.setIcon("success");
        amToast.setCustomIcon("success");
        amToast.setStyle("amtoast-sucess");
        amToast.setDuration(6);
        amToast.setHasCloseButton(true);
    }

    @Test
    void getToastDetails() {
        toastDetails.add(amToast);
        amToastsModel.setToastDetails(toastDetails);
        assertEquals(toastDetails,amToastsModel.getToastDetails());
    }
}