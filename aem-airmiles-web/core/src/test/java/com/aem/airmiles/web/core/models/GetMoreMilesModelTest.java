package com.aem.airmiles.web.core.models;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class GetMoreMilesModelTest extends TestUtil {

    @Test
    void getNullSubcategories() {
        getMoreMiles = aemctx.request().adaptTo(GetMoreMilesModel.class);
        System.out.println(getMoreMiles.subCategories);
        assertEquals(null, getMoreMiles.subCategories);
    }

    @Test
    void getNoSubCategories_size() {
        aemctx.currentResource("/getmoremilesmodelcontent/empty_subcategories");
        getMoreMiles = aemctx.request().adaptTo(GetMoreMilesModel.class);
        assertEquals(0, getMoreMiles.getSubCategories().size());
    }

    @Test
    void getNoSubCategories_data() {
        aemctx.currentResource("/getmoremilesmodelcontent/empty_subcategories");
        getMoreMiles = aemctx.request().adaptTo(GetMoreMilesModel.class);
        assertEquals("WayFinder", getMoreMiles.getWayFinderText());
        assertEquals("title", getMoreMiles.getMainTitleText());
        assertEquals("/content/dam/wknd-events/wknd-events.jpg", getMoreMiles.getBackgroundImagePath());
        assertEquals("body", getMoreMiles.getMainBodyText());
    }

    @Test
    void getAllSubCategories_data() {
        aemctx.currentResource("/getmoremilesmodelcontent/three_categories");
        getMoreMiles = aemctx.request().adaptTo(GetMoreMilesModel.class);
        GetMoreMilesLinksModel getMilesLinksModel = new GetMoreMilesLinksModel();
        List<GetMoreMilesLinksModel> getMilesLinksModelList = new ArrayList<>();
        getMilesLinksModelList.add(getMilesLinksModel);
        getMoreMiles.setSubCategories(getMilesLinksModelList);
        assertEquals(getMilesLinksModelList, getMoreMiles.getSubCategories());
        getMoreMiles.setDataTrackSection("get-more-miles");
        assertEquals("get-more-miles",getMoreMiles.getDataTrackSection());
    }
}