package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HereToHelpTest {

    HereToHelp hereToHelp = new HereToHelp();

    @BeforeEach
    void setup(){
        hereToHelp.setTitle("Here to Help");
        hereToHelp.setDescription("Description");
        hereToHelp.setDataTrackType("trackType");
        hereToHelp.setDataTrackID("trackId");
        hereToHelp.setDataClickID("clickId");

    }

    @Test
    void test_hereToHelp(){
        assertEquals("Here to Help", hereToHelp.getTitle());
        assertEquals("Description", hereToHelp.getDescription());
        List<DetailsModel> details = new ArrayList<>();
        DetailsModel detailsModel = new DetailsModel();
        details.add(detailsModel);
        hereToHelp.setDetails(details);
        assertEquals(details, hereToHelp.getDetails());
        assertEquals("trackId", hereToHelp.getDataTrackID());
        assertEquals("clickId", hereToHelp.getDataClickID());
        assertEquals("trackType", hereToHelp.getDataTrackType());

    }
}
