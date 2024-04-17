package com.aem.airmiles.web.core.models;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AMModalTest {

    AMModal amModal = new AMModal();

    @BeforeEach
    void init() throws ParseException {
        amModal.setModalTitle("Modal Title");
        amModal.setUniqueID("id-xyz123");
        amModal.setTypePicker("pageload");
        amModal.setButtonText("Modal Button");
        amModal.setSizePicker("modal-lg");
        amModal.setLinkType("framebutton");
        amModal.setDataTrackID("modal-xyz123");
        amModal.setDataClickID("dashboard-modal");
        amModal.setDataTrackType("internal");
        amModal.setOnce(true);
    }

    @Test
    void test_amModal(){
        assertEquals("Modal Title",amModal.getModalTitle());
        assertEquals("id-xyz123",amModal.getUniqueID());
        assertEquals("pageload",amModal.getTypePicker());
        assertEquals("Modal Button",amModal.getButtonText());
        assertEquals("modal-lg",amModal.getSizePicker());
        assertEquals("framebutton",amModal.getLinkType());
        assertEquals("modal-xyz123",amModal.getDataTrackID());
        assertEquals("dashboard-modal",amModal.getDataClickID());
        assertEquals("internal",amModal.getDataTrackType());
        assertTrue(amModal.isOnce());
    }

}