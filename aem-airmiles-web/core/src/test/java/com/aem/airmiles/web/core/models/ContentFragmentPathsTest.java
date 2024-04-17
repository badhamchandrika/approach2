package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ContentFragmentPathsTest {

    ContentFragmentPaths contentFragmentPaths = new ContentFragmentPaths();

    @BeforeEach
    public void setUp() {
        contentFragmentPaths.setSubSectionPaths("/content/dam/aem-airmiles-web/cf/test");
    }

    @Test
    void test_subSectionPaths(){
        String sectionPaths = "/content/dam/aem-airmiles-web/cf/test";
        assertEquals(sectionPaths,contentFragmentPaths.getSubSectionPaths());
    }
}
