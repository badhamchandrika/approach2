package com.aem.airmiles.web.core.models;

import com.adobe.cq.wcm.core.components.models.Text;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CopyrightModelTest {

    private static final String cmpTxt = "Draco Dormiens Nunquam Titilandum";

    @Mock
    Text text;

    @InjectMocks
    CopyrightModel model = new CopyrightModel();

    @Test
    void getText_shouldProvidedTextCmpText() {
        when(text.getText()).thenReturn(cmpTxt);
        assertEquals(cmpTxt, model.getText());
    }

    @Test
    void isRichText_shouldProvidedRichTextFlag() {
        when(text.isRichText()).thenReturn(true);
        assertTrue(model.isRichText());
    }
}



