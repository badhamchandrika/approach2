package ca.airmiles.travel.core.pojo;

import java.util.List;

public class FilterPojo {
    String tagTitle;
    String tagId;
    List<ChildFilterPojo> tags;

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public List<ChildFilterPojo> getTags() {
        return tags;
    }

    public void setTags(List<ChildFilterPojo> tags) {
        this.tags = tags;
    }
}
