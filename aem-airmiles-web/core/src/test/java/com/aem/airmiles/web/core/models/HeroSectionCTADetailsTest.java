package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class HeroSectionCTADetailsTest {
    HeroSectionCTADetails heroSectionCTADetails = new HeroSectionCTADetails();

    @BeforeEach
    void setUp() {
        heroSectionCTADetails.setLinkText("Join Now");
        heroSectionCTADetails.setLinkUrl("Join Now Url");
        heroSectionCTADetails.setLinkType("button");
        heroSectionCTADetails.setLinkTarget("_blank");
    }

    @Test
    void test_heroSectionCtaDetails() {
        assertEquals("Join Now", heroSectionCTADetails.getLinkText());
        assertEquals("Join Now Url", heroSectionCTADetails.getLinkUrl());
        assertEquals("button", heroSectionCTADetails.getLinkType());
        assertEquals("_blank", heroSectionCTADetails.getLinkTarget());
    }

}
