package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AMToastTest {

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
    void getUniqueId(){
        assertEquals("save-offer",amToast.getToastUID());
    }
    @Test
    void getToastTitle() {
        assertEquals("Offers",amToast.getToastTitle());
    }

    @Test
    void getToastMessage() {
        assertEquals("offers are saved",amToast.getToastMessage());
    }

    @Test
    void getIcon() {
        assertEquals("success",amToast.getIcon());
    }

    @Test
    void getCustomIcon() {
        assertEquals("success",amToast.getCustomIcon());
    }

    @Test
    void getStyle() {
        assertEquals("amtoast-sucess",amToast.getStyle());
    }

    @Test
    void getDuration() {
        assertEquals(6,amToast.getDuration());
    }

    @Test
    void isHasCloseButton() {
        assertEquals(true,amToast.isHasCloseButton());
    }
}