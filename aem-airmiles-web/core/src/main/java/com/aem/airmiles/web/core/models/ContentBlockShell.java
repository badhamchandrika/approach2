package com.aem.airmiles.web.core.models;

import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ContentBlockShell{
    
    /* Background image  */
    private static final String BACKGROUND_COLOR = "backgroundColor";
    private static final String BACKGROUND_IMAGE = "backgroundImage";
    private static final String BACKGROUND_IMAGE_ALT_TEXT = "backgroundImageAltText";
    
    /* Foreground 1 image  */
    private static final String FOREGROUND_IMAGE = "foregroundImage";
    private static final String FOREGROUND_IMAGE_ONE_URL = "foregroundImageOneUrl";
    private static final String FOREGROUND_IMAGE_ONE_ALT_TEXT = "foregroundImageOneAltText";
    private static final String FOREGROUND_IMAGE_ONE_DATA_TRACK_ID = "foregroundImageOneDataTrackId";
    private static final String FOREGROUND_IMAGE_ONE_DATA_TRACK_CLICK = "foregroundImageOneDataTrackClick";
    private static final String FOREGROUND_IMAGE_ONE_DATA_TRACK_TYPE = "foregroundImageOneDataTrackType";
    
    /* Foreground 2 image  */
    private static final String FOREGROUND_IMAGE_TWO = "foregroundImageTwo";
    private static final String FOREGROUND_IMAGE_TWO_ALT_TEXT = "foregroundImageTwoAltText";
    private static final String FOREGROUND_IMAGE_TWO_URL = "foregroundImageTwoUrl";
    private static final String FOREGROUND_IMAGE_TWO_DATA_TRACK_ID = "foregroundImageTwoDataTrackId";
    private static final String FOREGROUND_IMAGE_TWO_DATA_TRACK_CLICK = "foregroundImageTwoDataTrackClick";
    private static final String FOREGROUND_IMAGE_TWO_DATA_TRACK_TYPE = "foregroundImageTwoDataTrackType";
    
    private static final String TITLE2 = "title";
    private static final String DESCRIPTION2 = "description";
    private static final String SWAP_PLACEMENT = "swapPlacement";

    private static final Logger LOG = LoggerFactory.getLogger(ContentBlockShell.class);

    @ChildResource(name = "cards")
    Resource cardsList;

    @ValueMapValue
    @Getter
    private String type;
    
    @ValueMapValue
    @Getter
    private String submitErrorMessage;

    public List<Map<String, String>> getCardsList() {
        List<Map<String, String>> cardsDataMap = new ArrayList<>();
        if(cardsList!=null){
            Iterable<Resource> cardsChildren = cardsList.getChildren();
            for (Resource card : cardsChildren) {
                Map<String, String> cardDataMap = new HashMap<>();

                String backgroundColor = card.getValueMap().get(BACKGROUND_COLOR,String.class);
                cardDataMap.put(BACKGROUND_COLOR, backgroundColor);

                String backgroundImage = card.getValueMap().get(BACKGROUND_IMAGE,String.class);
                cardDataMap.put(BACKGROUND_IMAGE, backgroundImage);

                String foregroundImage = card.getValueMap().get(FOREGROUND_IMAGE,String.class);
                cardDataMap.put(FOREGROUND_IMAGE, foregroundImage);
                
                String backgroundImageAltText = card.getValueMap().get(BACKGROUND_IMAGE_ALT_TEXT,String.class);
                cardDataMap.put(BACKGROUND_IMAGE_ALT_TEXT, backgroundImageAltText);

                String foregroundImageOneAltText = card.getValueMap().get(FOREGROUND_IMAGE_ONE_ALT_TEXT,String.class);
                cardDataMap.put(FOREGROUND_IMAGE_ONE_ALT_TEXT, foregroundImageOneAltText);

                String foregroundImageOneUrl = card.getValueMap().get(FOREGROUND_IMAGE_ONE_URL,String.class);
                cardDataMap.put(FOREGROUND_IMAGE_ONE_URL, foregroundImageOneUrl);

                String foregroundImageOneDataTrackId = card.getValueMap().get(FOREGROUND_IMAGE_ONE_DATA_TRACK_ID,String.class);
                cardDataMap.put(FOREGROUND_IMAGE_ONE_DATA_TRACK_ID, foregroundImageOneDataTrackId);

                String foregroundImageOneDataTrackClick = card.getValueMap().get(FOREGROUND_IMAGE_ONE_DATA_TRACK_CLICK,String.class);
                cardDataMap.put(FOREGROUND_IMAGE_ONE_DATA_TRACK_CLICK, foregroundImageOneDataTrackClick);

                String foregroundImageOneDataTrackType = card.getValueMap().get(FOREGROUND_IMAGE_ONE_DATA_TRACK_TYPE,String.class);
                cardDataMap.put(FOREGROUND_IMAGE_ONE_DATA_TRACK_TYPE, foregroundImageOneDataTrackType);

                String foregroundImage2 = card.getValueMap().get(FOREGROUND_IMAGE_TWO,String.class);
                cardDataMap.put(FOREGROUND_IMAGE_TWO, foregroundImage2);

                String foregroundImageTwoAltText = card.getValueMap().get(FOREGROUND_IMAGE_TWO_ALT_TEXT,String.class);
                cardDataMap.put(FOREGROUND_IMAGE_TWO_ALT_TEXT, foregroundImageTwoAltText);

                String foregroundImageTwoUrl = card.getValueMap().get(FOREGROUND_IMAGE_TWO_URL,String.class);
                cardDataMap.put(FOREGROUND_IMAGE_TWO_URL, foregroundImageTwoUrl);

                String foregroundImageTwoDataTrackId = card.getValueMap().get(FOREGROUND_IMAGE_TWO_DATA_TRACK_ID,String.class);
                cardDataMap.put(FOREGROUND_IMAGE_TWO_DATA_TRACK_ID, foregroundImageTwoDataTrackId);

                String foregroundImageTwoDataTrackClick = card.getValueMap().get(FOREGROUND_IMAGE_TWO_DATA_TRACK_CLICK,String.class);
                cardDataMap.put(FOREGROUND_IMAGE_TWO_DATA_TRACK_CLICK, foregroundImageTwoDataTrackClick);

                String foregroundImageTwoDataTrackType = card.getValueMap().get(FOREGROUND_IMAGE_TWO_DATA_TRACK_TYPE,String.class);
                cardDataMap.put(FOREGROUND_IMAGE_TWO_DATA_TRACK_TYPE, foregroundImageTwoDataTrackType);

                String title = card.getValueMap().get(TITLE2,String.class);
                cardDataMap.put(TITLE2, title);

                String description = card.getValueMap().get(DESCRIPTION2,String.class);
                cardDataMap.put(DESCRIPTION2, description);

                String swapPlacement = card.getValueMap().get(SWAP_PLACEMENT,String.class);
                cardDataMap.put(SWAP_PLACEMENT, swapPlacement);

                cardsDataMap.add(cardDataMap);
            }
        }
        LOG.info("\n SIZE {} ",cardsDataMap.size());
        return cardsDataMap;
    }



}
