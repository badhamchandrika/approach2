package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ThemeNameModelTest {

    ThemeNameModel themeNameModel = new ThemeNameModel();
    String themeName = "corporate";
    @Test
    void test_themeName(){
        themeNameModel.setThemeName(themeName);
        assertEquals(themeName,themeNameModel.getThemeName());
    }
    @Test
    void test_themeNameSite(){
        themeNameModel.setThemeName(themeName);
        String themeNameSite = "aem-airmiles-web-"+themeName+".site";
        assertEquals(themeNameSite,themeNameModel.getThemeNameSite());
    }

    @Test
    void test_themeNameDependency(){
        themeNameModel.setThemeName(themeName);
        String themeNameDependency = "aem-airmiles-web-"+themeName+".dependencies";
        assertEquals(themeNameDependency,themeNameModel.getThemeNameDependency());
    }
}
