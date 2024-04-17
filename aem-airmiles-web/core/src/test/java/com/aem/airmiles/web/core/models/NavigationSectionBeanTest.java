package com.aem.airmiles.web.core.models;


import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class NavigationSectionBeanTest {

    private NavigationSectionBean navigationSectionBean;

    @BeforeEach
    public void setUp() {
        navigationSectionBean = new NavigationSectionBean();
    }

    @Test
    void getNavSection_sectionTitle() {
        navigationSectionBean.setSectionTitle("All Offers");
        assertEquals("All Offers", navigationSectionBean.getSectionTitle());
    }

    @Test
    void getNavSection_leftSeparator() {
        navigationSectionBean.setLeftSeparator(true);
        assertEquals(true, navigationSectionBean.getLeftSeparator());
    }

    @Test
    void getNavSection_hideNavInMob() {
        navigationSectionBean.setHideNavInMob(true);
        assertEquals(true, navigationSectionBean.getHideNavInMob());
    }

    @Test
    void getNavSection_navColumnBreak() {
        navigationSectionBean.setNavColumnBreak(4);
        assertEquals(4, navigationSectionBean.getNavColumnBreak());
    }

    @Test
    void getSubmenu() {
        navigationSectionBean.setSubmenu(new String[]{});
        assertDoesNotThrow(() -> assertNotNull(navigationSectionBean.getSubmenu()));
    }
}
