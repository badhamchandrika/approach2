package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TextImageModelTest {

    TextImageModel textImage = new TextImageModel();

    @BeforeEach
    void setup(){
        textImage.setDataTrackSection("new-data-track-section");
        textImage.setTitle("new Title");
        textImage.setTitleTrademark("@");
        textImage.setDescription("new description");
        textImage.setLinkDisclaimerText("new disclaimer text");
        textImage.setHeadline("new headline");
        textImage.setFileReference("/some/path/to/file");
        textImage.setSmallScreenFileReference("/some/path/to/smallfile");
        textImage.setAltText("new alt text");
        textImage.setUrl("https://www.google.cl");
        textImage.setImageTarget("_self");
        textImage.setDataTrackIDImg("data-track-img-id");
        textImage.setDataClickIDImg("data-click-img-id");
    }

    @Test
    void testAllTextImage() {
        assertEquals("new-data-track-section", textImage.getDataTrackSection());
        assertEquals("new Title", textImage.getTitle());
        assertEquals("@", textImage.getTitleTrademark());
        assertEquals("new description", textImage.getDescription());
        assertEquals("new disclaimer text", textImage.getLinkDisclaimerText());
        assertEquals("new headline", textImage.getHeadline());
        assertEquals("/some/path/to/file", textImage.getFileReference());
        assertEquals("/some/path/to/smallfile", textImage.getSmallScreenFileReference());
        assertEquals("new alt text", textImage.getAltText());
        assertEquals("_self", textImage.getImageTarget());
        assertEquals("data-track-img-id", textImage.getDataTrackIDImg());
        assertEquals("data-click-img-id", textImage.getDataClickIDImg());
    }
}