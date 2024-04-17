package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SignInModelTest {

    SignInModel signInModel = new SignInModel();
    List<AllOffersCTA> allOffersCTAList = new ArrayList<>();
    @BeforeEach
    void setUp() {

        signInModel.setImageAltText("airmiles logo");
        signInModel.setLoginMessage("Sign in to see more offers");
        signInModel.setLogoReference("/content/dam/airmiles/logo.png");
        AllOffersCTA allOffersCTA = new AllOffersCTA();
        allOffersCTA.setLinkType("button");
        allOffersCTAList.add(allOffersCTA);
        signInModel.setLoginButtons(allOffersCTAList);
    }

    @Test
    void test_signinDetails() {

        assertEquals("Sign in to see more offers", signInModel.getLoginMessage());
        assertEquals("airmiles logo", signInModel.getImageAltText());
        assertEquals("/content/dam/airmiles/logo.png", signInModel.getLogoReference());
        assertEquals(allOffersCTAList, signInModel.getLoginButtons());

    }
}
