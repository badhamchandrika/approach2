package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DetailsModelTest {
    DetailsModel detailsModel = new DetailsModel();

    @BeforeEach
    void setup(){
        detailsModel.setImagePath("/content/dam/travel2/westjet.png");
        detailsModel.setLinkTarget("_self");
        detailsModel.setAltText("WestJet");
        detailsModel.setLinkText("See all partners");
        detailsModel.setText("Change/Refund");
        detailsModel.setLinkUrl("/content/airmiles/travel2/partners/westjet.html");
    }

    @Test
    void test_details(){
        assertEquals("/content/dam/travel2/westjet.png", detailsModel.getImagePath());
        assertEquals("_self", detailsModel.getLinkTarget());
        assertEquals("WestJet", detailsModel.getAltText());
        assertEquals("See all partners", detailsModel.getLinkText());
        assertEquals("Change/Refund", detailsModel.getText());
        assertEquals("/content/airmiles/travel2/partners/westjet.html", detailsModel.getLinkUrl());
    }
}
