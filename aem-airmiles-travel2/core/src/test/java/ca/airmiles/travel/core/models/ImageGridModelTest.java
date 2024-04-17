package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ImageGridModelTest {

    ImageGridModel imageGridModel = new ImageGridModel();
    List<ImageGridItemModel> items = new ArrayList<>();

    @BeforeEach
    void setup(){
        ImageGridItemModel imageGridItemModel = new ImageGridItemModel();
        imageGridItemModel.setImageAltText("altText");
        imageGridItemModel.setFileReference("/content/img");
        imageGridItemModel.setLinkBodyText("body text");
        imageGridItemModel.setLinkNameText("name text");
        imageGridItemModel.setLinkUrl("link url");
        imageGridItemModel.setDataClickID("dataclickid");
        imageGridItemModel.setDataTrackID("datatrackid");
        imageGridItemModel.setIcon("Icon");
        items.add(imageGridItemModel);
        imageGridModel.setItems(items);
        imageGridModel.setHeader("Header");
    }

    @Test
    void test() {
        assertEquals("Header", imageGridModel.getHeader());
        assertEquals(items,imageGridModel.getItems());
    }
}
