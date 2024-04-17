package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChildPageTest {

    ChildPage childPage = new ChildPage();

    @BeforeEach
    void setup(){
        childPage.setPagePath("/content/aem-airmiles-travel2/ca/en/moredeals");
    }

    @Test
    void test_childPage(){
        assertEquals("/content/aem-airmiles-travel2/ca/en/moredeals", childPage.getPagePath());
    }

}