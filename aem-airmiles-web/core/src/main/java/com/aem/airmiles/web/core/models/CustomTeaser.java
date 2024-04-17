package com.aem.airmiles.web.core.models;

import com.adobe.cq.wcm.core.components.models.Teaser;

public interface CustomTeaser extends Teaser {
    String getMobileImageSrc();
    String getTabletImageSrc();
    String getDesktopImageSrc();
    String getImageAlt();

    String getDataTrackID();

    String getDataClickID();

    String getDataTrackType();
}
