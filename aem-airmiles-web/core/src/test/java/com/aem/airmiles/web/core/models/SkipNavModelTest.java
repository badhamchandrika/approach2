package com.aem.airmiles.web.core.models;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SkipNavModelTest {

    SkipNavModel skipNavModel = new SkipNavModel();

    @Test
    void test_skipText(){
        skipNavModel.setSkiptext("Skip Navigation");
        assertEquals("Skip Navigation",skipNavModel.getSkiptext());
    }

    @Test
    void test_skipToID(){
        skipNavModel.setSkiptoid("skipID");
        assertEquals("skipID",skipNavModel.getSkiptoid());
    }
}
