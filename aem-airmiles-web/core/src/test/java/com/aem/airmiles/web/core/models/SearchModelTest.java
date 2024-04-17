package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class SearchModelTest extends TestUtil {

    @Test
    void testGetSearchIcon() {
        aemctx.currentResource("/searchmodelcontent/search");
        search = aemctx.request().adaptTo(SearchModel.class);
        assert search != null;
        String expectedIcon = "/content/dam/aem-airmiles-web/icons/search-icon.PNG";
        search.setSearchIcon(expectedIcon);
        //String actualRoot = search.getSearchIcon();
        assertEquals(expectedIcon, search.getSearchIcon());
    }

    @Test
    void testGetSearchLabel() {
        aemctx.currentResource("/searchmodelcontent/search");
        search = aemctx.request().adaptTo(SearchModel.class);
        assert search != null;
        String expectedPag = "20";
        search.setPagination(expectedPag);
        String actualPag = search.getPagination();
        assertEquals(expectedPag, actualPag);
    }

    @Test
    void validateSearchLabelGivenTest() {
        aemctx.currentResource("/searchmodelcontent/initialTest");
        search = aemctx.request().adaptTo(SearchModel.class);
        assert search != null;
        String searchLabelExpected = "Search";
        search.setSearchLabel(searchLabelExpected);
        assertEquals(searchLabelExpected, search.getSearchLabel());
    }

    @Test
    void validateCancelLabelGivenTest() {
        aemctx.currentResource("/searchmodelcontent/initialTest");
        search = aemctx.request().adaptTo(SearchModel.class);
        assert search != null;
        String cancelLabelExpected = "Cancel";
        search.setCancelLabel(cancelLabelExpected);
        assertEquals(cancelLabelExpected, search.getCancelLabel());
    }

    @Test
    void validatePaginationGivenTest() {
        aemctx.currentResource("/searchmodelcontent/initialTest");
        search = aemctx.request().adaptTo(SearchModel.class);
        assert search != null;
        String paginationExpected = "5";
        search.setPagination(paginationExpected);
        assertEquals(paginationExpected, search.getPagination());
    }

    @Test
    void validateSearchPlaceHolderGivenTest() {
        aemctx.currentResource("/searchmodelcontent/initialTest");
        search = aemctx.request().adaptTo(SearchModel.class);
        assert search != null;
        String searchPlaceHolderTextExpected = "Search Here";
        search.setSearchPlaceholderText(searchPlaceHolderTextExpected);
        assertEquals(searchPlaceHolderTextExpected, search.getSearchPlaceholderText());
    }

    @Test
    void validateSearchIcon() {
        aemctx.currentResource("/searchmodelcontent/initialTest");
        search = aemctx.request().adaptTo(SearchModel.class);
        assert search != null;
        String searchIconExpected = "/content/dam/aem-airmiles-web/asset.jpg";
        search.setSearchIcon(searchIconExpected);
        assertEquals(searchIconExpected, search.getSearchIcon());
    }

    @Test
    void test_Analytics() {
        aemctx.currentResource("/searchmodelcontent/searchAnalyticsTest");
        search = aemctx.request().adaptTo(SearchModel.class);
        String searchButtonTrackID = "button-trackID";
        String searchButtonClickID = "button-clickID";
        String searchInputTrackID = "input-trackID";
        String searchInputClickID = "input-clickID";
        String searchResultPage = "/searchmodelcontent/language-masters/en/search.html";
        String searchCancelClickID = "search-cancel";
        String searchCancelTrackID = "search-cancelID";
        String searchSubmitClickID = "submit-clickID";
        String searchSubmitTrackID = "submit-trackID";
        String searchCancelTrackType = "cancel-trackType";
        String searchSubmitTrackType = "submit-trackType";
        String searchInputTrackType = "input-trackType";
        String searchButtonTrackType = "button-trackType";
        assert search != null;
        search.setSearchButtonTrackID(searchButtonTrackID);
        search.setSearchButtonClickID(searchButtonClickID);
        search.setSearchInputTrackID(searchInputTrackID);
        search.setSearchInputClickID(searchInputClickID);
        search.setSearchResultPage(searchResultPage);
        search.setSearchCancelClickID(searchCancelClickID);
        search.setSearchCancelTrackID(searchCancelTrackID);
        search.setSearchSubmitClickID(searchSubmitClickID);
        search.setSearchSubmitTrackID(searchSubmitTrackID);
        search.setSearchCancelTrackType(searchCancelTrackType);
        search.setSearchSubmitTrackType(searchSubmitTrackType);
        search.setSearchInputTrackType(searchInputTrackType);
        search.setSearchButtonTrackType(searchButtonTrackType);
        assertEquals(searchButtonTrackID,search.getSearchButtonTrackID());
        assertEquals(searchButtonClickID,search.getSearchButtonClickID());
        assertEquals(searchInputTrackID,search.getSearchInputTrackID());
        assertEquals(searchInputClickID,search.getSearchInputClickID());
        assertEquals(searchResultPage,search.getSearchResultPage());
        assertEquals(searchCancelTrackID,search.getSearchCancelTrackID());
        assertEquals(searchCancelClickID,search.getSearchCancelClickID());
        assertEquals(searchSubmitClickID,search.getSearchSubmitClickID());
        assertEquals(searchSubmitTrackID,search.getSearchSubmitTrackID());
        assertEquals(searchCancelTrackType,search.getSearchCancelTrackType());
        assertEquals(searchSubmitTrackType,search.getSearchSubmitTrackType());
        assertEquals(searchInputTrackType,search.getSearchInputTrackType());
        assertEquals(searchButtonTrackType,search.getSearchButtonTrackType());
    }

    @Test
    void test_searchQuickLinks(){
        SearchModel searchModel = new SearchModel();
        SearchQuickLinks searchQuickLinks = new SearchQuickLinks();
        List<SearchQuickLinks> searchQuickLinksList = new ArrayList<>();
        searchQuickLinksList.add(searchQuickLinks);
        searchModel.setSearchQuickLinks(searchQuickLinksList);
        assertEquals(searchQuickLinksList,searchModel.getSearchQuickLinks());
    }
}



