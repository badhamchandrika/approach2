package com.aem.airmiles.web.core.models;


import com.aem.airmiles.web.core.bean.LogoItem;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith({AemContextExtension.class})
class LogoSaladModelTest {

    private final AemContext context = new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);

    public final static  String CONTENT_ROOT = "/content/logosalad";

    private LogoSaladModel model;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(LogoItem.class);
        context.addModelsForClasses(LogoSaladModel.class);
        context.load().json("/com.aem.airmiles.web.core/LogoSaladModelTest.json", CONTENT_ROOT);
        model = Objects.requireNonNull(context.currentResource(CONTENT_ROOT)).adaptTo(LogoSaladModel.class);
    }

    @Test
    void test_logos() {
        assertNotNull(model);
        List<LogoItem> actual = model.getLogos();
        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertEquals("/content/dam/aem-airmiles-web/partner-logo.jpg",actual.get(0).getLogoUrl());
        assertEquals("logo-salad-id",model.getUniqueID());
        assertEquals("Logo Salad",model.getLogoSaladTitle());
        assertTrue(actual.get(0).isHideInMobile());
        assertTrue(actual.get(0).isHideInTablet());
    }
}