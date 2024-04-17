package com.aem.airmiles.web.core.bean;

import com.aem.airmiles.web.core.models.ComponentModel;

public class TopNavItem extends ComponentModel {

    private String siteName;
    private String webUrl;
    private String urlTarget;
    private String uid;
    private String clickAction;
    private String dataTrackID;
    private String dataClickID;
    private String dataTrackType;

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getUrlTarget() {
        return urlTarget;
    }

    public void setUrlTarget(String urlTarget) {
        this.urlTarget = urlTarget;
    }

    @Override
    public String getDataTrackID() {
        return dataTrackID;
    }

    @Override
    public void setDataTrackID(String dataTrackID) {
        this.dataTrackID = dataTrackID;
    }

    @Override
    public String getDataClickID() {
        return dataClickID;
    }

    @Override
    public void setDataClickID(String dataClickID) {
        this.dataClickID = dataClickID;
    }

    @Override
    public String getDataTrackType() {
        return dataTrackType;
    }

    @Override
    public void setDataTrackType(String dataTrackType) {
        this.dataTrackType = dataTrackType;
    }
}
