package com.aem.airmiles.web.core.models;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.aem.airmiles.web.core.utils.Utils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class TopNavigationModelTest {

    TopNavigationModel topNavigationModel = new TopNavigationModel();

    ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    Resource resource;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    ContentFragment cf;


    @BeforeEach
    public void setUp() throws NoSuchFieldException {
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    @Test
    void testTopNavigation_init() {
        try(final MockedStatic<Utils> utils = mockStatic(Utils.class)){
            ContentFragmentPaths contentFragmentPaths = new ContentFragmentPaths();
            contentFragmentPaths.setSubSectionPaths("Path/to/content/fragments");
            List<ContentFragmentPaths> contentFragmentPathsList = new ArrayList<>();
            contentFragmentPathsList.add(contentFragmentPaths);
            NavigationItemModel navigationItemModel = new NavigationItemModel();
            navigationItemModel.setSubSectionPaths(contentFragmentPathsList);
            List<NavigationItemModel> navigationItemModelList = new ArrayList<>();
            navigationItemModelList.add(navigationItemModel);
            topNavigationModel.setNavItem(navigationItemModelList);
            topNavigationModel.setResourceResolver(resourceResolver);
            assertNotNull(contentFragmentPaths.getSubSectionPaths());
            when(resourceResolver.getResource(contentFragmentPaths.getSubSectionPaths())).thenReturn(resource);
            when(resource.adaptTo(ContentFragment.class)).thenReturn(cf);
            topNavigationModel.init();
            assertNotNull(navigationItemModel);
            assertNotNull(navigationItemModel.navSectionList);
            assertNotNull(navigationItemModel.subSectionPaths);
            utils.verify(()-> Utils.getElements(any()), times(1));
            assertNotNull(topNavigationModel.getResourceResolver());
        }
    }

    @Test
    void testTopNavigation_navItem() {
        NavigationItemModel navigationItemModel = new NavigationItemModel();
        List<NavigationItemModel> navigationItemModelList = new ArrayList<>();
        navigationItemModelList.add(navigationItemModel);
        topNavigationModel.setNavItem(navigationItemModelList);
        assertEquals(navigationItemModelList, topNavigationModel.getNavItem());
    }

    @Test
    void testTopNavigation_navSectionList() {
        NavigationSectionBean navigationSectionBean = new NavigationSectionBean();
        List<NavigationSectionBean> navigationSectionBeanList = new ArrayList<>();
        navigationSectionBeanList.add(navigationSectionBean);
        topNavigationModel.setNavSectionList(navigationSectionBeanList);
        assertEquals(navigationSectionBeanList, topNavigationModel.getNavSectionList());
    }
}