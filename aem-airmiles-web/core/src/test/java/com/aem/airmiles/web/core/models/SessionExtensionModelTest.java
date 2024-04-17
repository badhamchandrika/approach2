package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SessionExtensionModelTest {
    SessionExtensionModel sessionExtensionModel = new SessionExtensionModel();
    @BeforeEach
    void setUp() {
        sessionExtensionModel.setTitle("Your session will be expire in");
        sessionExtensionModel.setIconType("success");
        sessionExtensionModel.setBodyText("Session extension body text and description");
        sessionExtensionModel.setModalEnable("true");
        sessionExtensionModel.setExtensionSec("300");
        sessionExtensionModel.setStayLabel("Stay");
        sessionExtensionModel.setDataTrackID("session-extension");
        sessionExtensionModel.setDataClickID("stay-button");
        sessionExtensionModel.setDataTrackType("internal");
        sessionExtensionModel.setUidStay("stayButtonID");
        sessionExtensionModel.setExitLabel("Sing Out");
        sessionExtensionModel.setSignOutUrl("/en/sign-out-page");
        sessionExtensionModel.setDataTrackIDExit("session-extension");
        sessionExtensionModel.setDataClickIDExit("sign-out-button");
        sessionExtensionModel.setDataTrackTypeExit("internal");
        sessionExtensionModel.setUidExit("SingOutButtonID");
    }

    @Test
    void test_SessionExtensionMain() {
        assertEquals("Your session will be expire in", sessionExtensionModel.getTitle());
        assertEquals("success", sessionExtensionModel.getIconType());
        assertEquals("Session extension body text and description",sessionExtensionModel.getBodyText());
        assertEquals("300",sessionExtensionModel.getExtensionSec());
        assertEquals("true",sessionExtensionModel.getModalEnable());
    }
    @Test
    void test_SessionExtension_ctaDetails(){
        assertEquals("Stay",sessionExtensionModel.getStayLabel());
        assertEquals("stayButtonID",sessionExtensionModel.getUidStay());
        assertEquals("Sing Out",sessionExtensionModel.getExitLabel());
        assertEquals("SingOutButtonID",sessionExtensionModel.getUidExit());
        assertEquals("/en/sign-out-page",sessionExtensionModel.getSignOutUrl());
    }
    @Test
    void test_SessionExtension_getDataTrackSection() {
        assertEquals("session-extension",sessionExtensionModel.getDataTrackID());
        assertEquals("stay-button",sessionExtensionModel.getDataClickID());
        assertEquals("internal",sessionExtensionModel.getDataTrackType());
        assertEquals("session-extension",sessionExtensionModel.getDataTrackIDExit());
        assertEquals("sign-out-button",sessionExtensionModel.getDataClickIDExit());
        assertEquals("internal",sessionExtensionModel.getDataTrackTypeExit());
    }
}