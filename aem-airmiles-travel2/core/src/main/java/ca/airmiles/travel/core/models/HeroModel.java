package ca.airmiles.travel.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroModel {

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String fileReference;

    @ValueMapValue
    private String imageAltText;

    @ValueMapValue
    private String fileReference2;

    @ValueMapValue
    private String imageAltText2;

    @ValueMapValue
    private int startLevel;

    @ValueMapValue
    private String dataTrackId;

    @ValueMapValue
    private boolean darkbg;

    @ValueMapValue
    private String displayBreadcrumb;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileReference() {
        return fileReference;
    }

    public void setFileReference(String fileReference) {
        this.fileReference = fileReference;
    }

    public String getImageAltText() {
        return imageAltText;
    }

    public void setImageAltText(String imageAltText) {
        this.imageAltText = imageAltText;
    }

    public String getFileReference2() {
        return fileReference2;
    }

    public void setFileReference2(String fileReference2) {
        this.fileReference2 = fileReference2;
    }

    public String getImageAltText2() {
        return imageAltText2;
    }

    public void setImageAltText2(String imageAltText2) {
        this.imageAltText2 = imageAltText2;
    }

    public int getStartLevel() {
        return startLevel;
    }

    public void setStartLevel(int startLevel) {
        this.startLevel = startLevel;
    }

    public String getDataTrackId() {
        return dataTrackId;
    }

    public void setDataTrackId(String dataTrackId) {
        this.dataTrackId = dataTrackId;
    }

    public boolean isDarkbg() {
        return darkbg;
    }

    public void setDarkbg(boolean darkbg) {
        this.darkbg = darkbg;
    }

    public String isDisplayBreadcrumb() {
        return displayBreadcrumb;
    }
}
