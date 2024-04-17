package ca.airmiles.travel.core.models;

import ca.airmiles.travel.core.pojo.ChildFilterPojo;
import ca.airmiles.travel.core.pojo.FilterPojo;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class FilterModelTest {

    AemContext context=new AemContext(ResourceResolverType.RESOURCERESOLVER_MOCK);
    @InjectMocks
    private FilterModel filterModel;

    @Mock
    TagManager tagManager;
    @Mock
    Tag tag;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(FilterModel.class);
        context.load().json("/ca/airmiles/travel/core/models/dealslist.json","/dealslist");
        context.currentResource("/dealslist");
        context.create().tag("properties:style");
        context.create().tag("properties:style/ChldTag1");
        context.create().tag("properties:style/ChldTag1/SubChild1");
        context.request().setPathInfo("");
        filterModel = context.request().adaptTo(FilterModel.class);

    }

    @Test
    void getFilters() {
        List<FilterPojo> filterList=filterModel.getFilters();
        assertNotNull(filterList);
        FilterPojo filterPojo=filterList.get(0);
        assertEquals("properties:style",filterPojo.getTagId());
        assertEquals("style",filterPojo.getTagTitle());
        ChildFilterPojo childfilterPojo= filterPojo.getTags().get(0);
        assertEquals("properties:style/ChldTag1",childfilterPojo.getTagId());
        assertEquals("ChldTag1",childfilterPojo.getTagTitle());
    }
}