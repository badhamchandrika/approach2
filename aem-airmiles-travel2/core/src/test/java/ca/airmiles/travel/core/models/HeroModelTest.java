package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class HeroModelTest {

    HeroModel heroModel = new HeroModel();

    @BeforeEach
    void setup(){
        heroModel.setTitle("Travel");
        heroModel.setDescription("Travel with Confidence");
        heroModel.setFileReference("/path/to/image");
        heroModel.setImageAltText("imagealttext");
        heroModel.setDataTrackId("Darkbg");
        heroModel.setFileReference2("/path/to/image2");
        heroModel.setImageAltText2("imagealttext2");
        heroModel.setStartLevel(1);
        heroModel.setDarkbg(false);

    }

    @Test
    void test_hero(){
        assertEquals("Travel", heroModel.getTitle());
        assertEquals("Travel with Confidence", heroModel.getDescription());
        assertEquals("/path/to/image", heroModel.getFileReference());
        assertEquals("imagealttext", heroModel.getImageAltText());
        assertEquals("imagealttext2", heroModel.getImageAltText2());
        assertEquals("Darkbg", heroModel.getDataTrackId());
        assertEquals("/path/to/image2", heroModel.getFileReference2());
        assertEquals(1, heroModel.getStartLevel());
        assertFalse(heroModel.isDarkbg());
        assertNull(heroModel.isDisplayBreadcrumb());


    }
}
