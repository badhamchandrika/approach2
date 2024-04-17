package com.aem.airmiles.web.core.models;


import com.aem.airmiles.web.core.bean.AppItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AppItemTest {

    AppItem appItem = new AppItem();

    @BeforeEach
    void init(){
        appItem.setImage("/content/dam/aem-airmiles-web/images/social-media-playstore.png");
        appItem.setAlt("Android App on Google Play");
        appItem.setLink("https://play.google.com/store/apps/details?id=com.airmiles");
        appItem.setLinkTarget("_blank");
        appItem.setDataClickID("footer-get-the-app");
        appItem.setDataTrackID("google-store");
        appItem.setDataTrackType("internal");
    }

    @Test
    void test_appItem(){
        assertEquals("/content/dam/aem-airmiles-web/images/social-media-playstore.png",appItem.getImage());
        assertEquals("Android App on Google Play",appItem.getAlt());
        assertEquals("https://play.google.com/store/apps/details?id=com.airmiles",appItem.getLink());
        assertEquals("_blank",appItem.getLinkTarget());
        assertEquals("footer-get-the-app",appItem.getDataClickID());
        assertEquals("google-store",appItem.getDataTrackID());
        assertEquals("internal",appItem.getDataTrackType());
    }
}
