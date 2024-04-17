package ca.airmiles.travel.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LinksBarModelTest {

    LinksBarModel linksBarModel = new LinksBarModel();


    @Test
    void test_links(){
        LinkModel linksModel = new LinkModel();
        List<LinkModel> links = new ArrayList<>();
        links.add(linksModel);
        linksBarModel.setLinks(links);
        assertEquals(links,linksBarModel.getLinks());
    }
}
