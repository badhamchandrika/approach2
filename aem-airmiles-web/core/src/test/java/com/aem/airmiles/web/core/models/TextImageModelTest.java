package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TextImageModelTest {

    TextImageModel textImageModel = new TextImageModel();
    @Test
    void testVariables() {
        CTALink ctaLink = new CTALink();
        textImageModel.setActive("true");
        textImageModel.setTitle("new title");
        textImageModel.setTitleTrademark("new trademark");
        textImageModel.setDescription("new Description");
        textImageModel.setHeadline("new headline");
        ctaLink.setLinkText("New CtaText");
        ctaLink.setLinkUrl("new cta link");
        ctaLink.setLinkColor("Gold");
        ctaLink.setHexTextColor("#EFEFEF");
        ctaLink.setHexBtnColor("#13BDAA");
        ctaLink.setDataTrackType("internal");
        textImageModel.setLinkDisclaimerText("new disclaimer");
        textImageModel.setFileReference("new file reference");
        textImageModel.setSmallScreenFileReference("new optional small screen file reference");
        textImageModel.setAltText("new alt text");
        textImageModel.setUrl("new url");
        textImageModel.setImageTarget("new image target");
        textImageModel.setDataTrackID("new data track id");
        textImageModel.setDataClickID("new data track click");
        textImageModel.setCtaLink(ctaLink);
        textImageModel.setDataTrackSection("text-image");
        textImageModel.setTitleTrademark("MD");
        textImageModel.setMediumScreenFileReference("/path/to/image/for/tab");
        textImageModel.setDataClickIDImg("image-ClickID");
        textImageModel.setDataTrackIDImg("image-TrackID");
        textImageModel.setHasCTA("true");

        assertEquals("new title", textImageModel.getTitle());
        assertEquals("new Description", textImageModel.getDescription());
        assertEquals("new headline", textImageModel.getHeadline());
        assertEquals("New CtaText", textImageModel.getCtaLink().getLinkText());
        assertEquals("new cta link", textImageModel.getCtaLink().getLinkUrl());
        assertEquals("Gold", textImageModel.getCtaLink().getLinkColor());
        assertEquals("#EFEFEF", textImageModel.getCtaLink().getHexTextColor());
        assertEquals("#13BDAA", textImageModel.getCtaLink().getHexBtnColor());
        assertEquals("internal", textImageModel.getCtaLink().getDataTrackType());
        assertEquals("new disclaimer", textImageModel.getLinkDisclaimerText());
        assertEquals("new file reference", textImageModel.getFileReference());
        assertEquals("new alt text", textImageModel.getAltText());
        assertEquals("new url", textImageModel.getUrl());
        assertEquals("new image target", textImageModel.getImageTarget());
        assertEquals("new data track id", textImageModel.getDataTrackID());
        assertEquals("new data track click", textImageModel.getDataClickID());
        assertEquals("text-image", textImageModel.getDataTrackSection());
        assertEquals("MD", textImageModel.getTitleTrademark());
        assertEquals("new optional small screen file reference", textImageModel.getSmallScreenFileReference());
        assertEquals("/path/to/image/for/tab", textImageModel.getMediumScreenFileReference());
        assertEquals("image-ClickID", textImageModel.getDataClickIDImg());
        assertEquals("image-TrackID", textImageModel.getDataTrackIDImg());
        assertEquals("true", textImageModel.getHasCTA());
    }
}
