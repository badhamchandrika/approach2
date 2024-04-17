package com.aem.airmiles.web.core.models;


import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith({AemContextExtension.class, MockitoExtension.class})
 class GetAppModelTest extends TestUtil {

    @Test
     void testGetResources() {
        aemctx.currentResource("/getAppModelcontent/testAppsList");
        getAppModel = aemctx.request().adaptTo(GetAppModel.class);
        Assert.assertNotNull(getAppModel.getAppsList());

    }


}
