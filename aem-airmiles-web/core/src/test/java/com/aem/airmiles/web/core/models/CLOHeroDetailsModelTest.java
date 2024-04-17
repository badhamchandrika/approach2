package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CLOHeroDetailsModelTest {

    CLOHeroDetailsModel cloHeroDetailsModel;
    @BeforeEach
    void setUp() {
        cloHeroDetailsModel = new CLOHeroDetailsModel();
        cloHeroDetailsModel.setProcess1ImagePath("/content/dam/aem-airmiles-web/offers/step-1.svg");
        cloHeroDetailsModel.setProcess2ImagePath("/content/dam/aem-airmiles-web/offers/step-2.svg");
        cloHeroDetailsModel.setProcess3ImagePath("/content/dam/aem-airmiles-web/offers/step-3.svg");
        cloHeroDetailsModel.setStep1Title("Link your cards");
        cloHeroDetailsModel.setStep2Title("Shop the offers");
        cloHeroDetailsModel.setStep3Title("Reap the rewards");
        cloHeroDetailsModel.setLinkYourCardCtaText("LINK YOUR CARD");
        cloHeroDetailsModel.setLinkYourCardExternalUrl("https://www.airmilescardlink.ca/#enrolFormPosition");
        cloHeroDetailsModel.setFaqText("Frequently Asked Questions");
        cloHeroDetailsModel.setFaqUrl("https://www.airmilescardlink.ca/how-it-works");
        cloHeroDetailsModel.setLearnMoreText("Learn More");
        cloHeroDetailsModel.setLearnMoreExternalUrl("https://www.airmilescardlink.ca/how-it-works");
        cloHeroDetailsModel.setStep1Title("Link your cards");
        cloHeroDetailsModel.setStep2Title("Shop the offers");
        cloHeroDetailsModel.setStep3Title("Reap the rewards");
        cloHeroDetailsModel.setLearnMoreClickId("learn more");
        cloHeroDetailsModel.setLearnMoreDataTrackId("learn more url");
        cloHeroDetailsModel.setLearnMoreDataTrackType("button");
        cloHeroDetailsModel.setLinkYourCardDataClickId("link your card");
        cloHeroDetailsModel.setLinkYourCardDataTrackId("link your card url");
        cloHeroDetailsModel.setLinkYourCardDataTrackType("button");
        cloHeroDetailsModel.setFaqTarget("_blank");
        cloHeroDetailsModel.setFaqDataClickId("faq");
        cloHeroDetailsModel.setFaqDataTrackId("page");
        cloHeroDetailsModel.setFaqDataTrackType("internal");
        cloHeroDetailsModel.setLearnMoreTarget("_blank");
        cloHeroDetailsModel.setLinkYourCardTarget("_blank");
        cloHeroDetailsModel.setInformationText("info text");
        cloHeroDetailsModel.setLinkYourCardUrlToDisplay("linkYourCardExternalUrl");
        cloHeroDetailsModel.setLearnMoreUrlToDisplay("learnMoreExternalUrl");
        cloHeroDetailsModel.setLinkYourCardInternalUrl("/content/aem-airmiles-web/ca/en/offers");
        cloHeroDetailsModel.setLearnMoreInternalUrl("/content/aem-airmiles-web/ca/en/offers");

    }
    @Test
    void getInformationText(){
        assertEquals("info text",cloHeroDetailsModel.getInformationText());
    }
    @Test
    void getStep1Title() {
        assertEquals("Link your cards",cloHeroDetailsModel.getStep1Title());
    }

    @Test
    void getStep2Title() {
        assertEquals("Shop the offers",cloHeroDetailsModel.getStep2Title());
    }

    @Test
    void getStep3Title() {
        assertEquals("Reap the rewards",cloHeroDetailsModel.getStep3Title());
    }
    @Test
    void getProcess1ImagePath() {
        assertEquals("/content/dam/aem-airmiles-web/offers/step-1.svg",cloHeroDetailsModel.getProcess1ImagePath());
    }

    @Test
    void getProcess2ImagePath() {
        assertEquals("/content/dam/aem-airmiles-web/offers/step-2.svg",cloHeroDetailsModel.getProcess2ImagePath());
    }

    @Test
    void getProcess3ImagePath() {
        assertEquals("/content/dam/aem-airmiles-web/offers/step-3.svg",cloHeroDetailsModel.getProcess3ImagePath());
    }

    @Test
    void getLinkYourCardCtaText() {
        assertEquals("LINK YOUR CARD",cloHeroDetailsModel.getLinkYourCardCtaText());
    }

    @Test
    void getLinkYourCardExternalUrl() {
        assertEquals("https://www.airmilescardlink.ca/#enrolFormPosition",cloHeroDetailsModel.getLinkYourCardExternalUrl());
    }

    @Test
    void getFaqText() {
        assertEquals("Frequently Asked Questions",cloHeroDetailsModel.getFaqText());
    }

    @Test
    void getFaqUrl() {
        assertEquals("https://www.airmilescardlink.ca/how-it-works",cloHeroDetailsModel.getFaqUrl());
    }

    @Test
    void getLearnMoreText() {
        assertEquals("Learn More",cloHeroDetailsModel.getLearnMoreText());
    }

    @Test
    void getLearnMoreExternalUrl() {
        assertEquals("https://www.airmilescardlink.ca/how-it-works",cloHeroDetailsModel.getLearnMoreExternalUrl());
    }


    @Test
    void getLinkYourCardDataClickId() {
        assertEquals("link your card",cloHeroDetailsModel.getLinkYourCardDataClickId());
    }

    @Test
    void getLinkYourCardDataTrackId() {
        assertEquals("link your card url",cloHeroDetailsModel.getLinkYourCardDataTrackId());
    }

    @Test
    void getLinkYourCardDataTrackType() {
        assertEquals("button",cloHeroDetailsModel.getLinkYourCardDataTrackType());
    }

    @Test
    void getLearnMoreClickId() {
        assertEquals("learn more",cloHeroDetailsModel.getLearnMoreClickId());
    }

    @Test
    void getLearnMoreDataTrackId() {
        assertEquals("learn more url",cloHeroDetailsModel.getLearnMoreDataTrackId());
    }

    @Test
    void getLearnMoreDataTrackType() {
        assertEquals("button",cloHeroDetailsModel.getLearnMoreDataTrackType());
    }

    @Test
    void getLinkYourCardTarget() {
            assertEquals("_blank",cloHeroDetailsModel.getLinkYourCardTarget());
    }

    @Test
    void getFaqTarget() {
        assertEquals("_blank",cloHeroDetailsModel.getFaqTarget());
    }

    @Test
    void getFaqDataClickId() {
        assertEquals("faq",cloHeroDetailsModel.getFaqDataClickId());
    }

    @Test
    void getFaqDataTrackId() {
        assertEquals("page",cloHeroDetailsModel.getFaqDataTrackId());
    }

    @Test
    void getFaqDataTrackType() {
        assertEquals("internal",cloHeroDetailsModel.getFaqDataTrackType());
    }

    @Test
    void getLearnMoreTarget() {
        assertEquals("_blank",cloHeroDetailsModel.getLearnMoreTarget());
    }
    @Test
    void getLinkYourCardUrlToDisplay() {
        assertEquals("linkYourCardExternalUrl",cloHeroDetailsModel.getLinkYourCardUrlToDisplay());
    }

    @Test
    void getLearnMoreUrlToDisplay() {
        assertEquals("learnMoreExternalUrl",cloHeroDetailsModel.getLearnMoreUrlToDisplay());
    }

    @Test
    void getLinkYourCardInternalUrl() {
        assertEquals("/content/aem-airmiles-web/ca/en/offers.html",cloHeroDetailsModel.getLinkYourCardInternalUrl());
    }

    @Test
    void getLearnMoreInternalUrl() {
        assertEquals("/content/aem-airmiles-web/ca/en/offers.html",cloHeroDetailsModel.getLearnMoreInternalUrl());
    }

}