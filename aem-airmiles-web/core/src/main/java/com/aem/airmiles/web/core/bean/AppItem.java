package com.aem.airmiles.web.core.bean;

public class AppItem {

    private String image;
    private String alt;
    private String link;
    private String linkTarget;
    private String dataClickID;
    private String dataTrackID;
    private String dataTrackType;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkTarget() {
        return linkTarget;
    }

    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;
    }

    public String getDataClickID() { return dataClickID; }

    public void setDataClickID(String dataClickID) { this.dataClickID = dataClickID; }

    public String getDataTrackID() {
        return dataTrackID;
    }

    public void setDataTrackID(String dataTrackID) {
        this.dataTrackID = dataTrackID;
    }

    public String getDataTrackType() {
        return dataTrackType;
    }

    public void setDataTrackType(String dataTrackType) {
        this.dataTrackType = dataTrackType;
    }
}
