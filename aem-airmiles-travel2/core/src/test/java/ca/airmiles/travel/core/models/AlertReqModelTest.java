package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlertReqModelTest {

    AlertReqModel alertReqModel = new AlertReqModel();

    @BeforeEach
    void setup(){
        alertReqModel.setTitle("New Title");
        alertReqModel.setDescription("New Description");
        alertReqModel.setDatatrackclick("alert-data-track-click");
        alertReqModel.setDatatrackid("alert-data-track-id");
        alertReqModel.setDataTrackSection("alert-data-track-section");
    }

    @Test
    void test() {
        assertEquals("New Title", alertReqModel.getTitle());
        assertEquals("New Description", alertReqModel.getDescription());
        assertEquals("alert-data-track-click", alertReqModel.getDatatrackclick());
        assertEquals("alert-data-track-id", alertReqModel.getDatatrackid());
        assertEquals("alert-data-track-section", alertReqModel.getDataTrackSection());
    }
}