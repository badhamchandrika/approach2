package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ImageGridItemModelTest {

    ImageGridItemModel imageGridItemModel = new ImageGridItemModel();

    @BeforeEach
    void setup(){
        imageGridItemModel.setFileReference("/content/dam/asset1.png");
        imageGridItemModel.setImageAltText("alt text");

    }

    @Test
    void test() {
        assertEquals("/content/dam/asset1.png", imageGridItemModel.getFileReference());
        assertEquals("alt text", imageGridItemModel.getImageAltText());


    }
}
