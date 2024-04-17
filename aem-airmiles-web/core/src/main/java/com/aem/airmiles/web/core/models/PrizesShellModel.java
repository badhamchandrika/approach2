package com.aem.airmiles.web.core.models;

import lombok.Getter;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

/**
 * A Sling model that provides a list of cards from JCR Child Resource.
 *
 * @author jesus-nicolas-manrique.
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = OPTIONAL)
public class PrizesShellModel {

    /**
     * The child resource that contains the list of cards.
     */
    @ChildResource(name = "cards")
    private Resource cardsList;

    /**
     * The error message to be displayed if the user submits an invalid form.
     */
    @Getter
    @ValueMapValue
    private String submitErrorMessage;

    /**
     * The keys for the different fields in a card map.
     */
    static final String TITLE = "title";
    static final String TEXT_SIZE = "textSize";
    static final String SUBHEADER = "subheader";
    static final String TEXT_COLOR = "textColor";
    static final String DESCRIPTION = "description";
    static final String SWAP_PLACEMENT = "swapPlacement";
    static final String BACKGROUND_COLOR = "backgroundColor";
    static final String BACKGROUND_IMAGE = "backgroundImage";
    static final String BACKGROUND_IMAGE_ALT_TEXT = "backgroundImageAltText";

    /**
     * A logger for this class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PrizesShellModel.class);

    /**
     * Gets a list of cards from JCR Child Resource. Each card is a map of strings.
     *
     * @return a list of cards, each card is a map of strings with the following keys:
     *   * BACKGROUND_COLOR: The background color of the card.
     *   * BACKGROUND_IMAGE: The background image of the card.
     *   * BACKGROUND_IMAGE_ALT_TEXT: The alt text for the background image of the card.
     *   * TEXT_COLOR: The text color of the card.
     *   * TEXT_SIZE: The text size of the card.
     *   * TITLE: The title of the card.
     *   * SUBHEADER: The subheader of the card.
     *   * DESCRIPTION: The description of the card.
     *   * SWAP_PLACEMENT: The swap placement of the card.
     */
    public List<Map<String, String>> getCardsList() {
        final List<Map<String, String>> cardsDataMap = new ArrayList<>();
        if (cardsList != null) {
            final Iterable<Resource> cardsChildren = cardsList.getChildren();
            for (final Resource card : cardsChildren) {
                final Map<String, String> cardDataMap = new HashMap<>();
                final ValueMap vm = card.getValueMap();
                cardDataMap.put(BACKGROUND_COLOR, vm.get(BACKGROUND_COLOR, String.class));
                cardDataMap.put(BACKGROUND_IMAGE, vm.get(BACKGROUND_IMAGE, String.class));
                cardDataMap.put(BACKGROUND_IMAGE_ALT_TEXT, vm.get(BACKGROUND_IMAGE_ALT_TEXT, String.class));
                cardDataMap.put(TEXT_COLOR, vm.get(TEXT_COLOR, String.class));
                cardDataMap.put(TEXT_SIZE, vm.get(TEXT_SIZE, String.class));
                cardDataMap.put(TITLE, vm.get(TITLE, String.class));
                cardDataMap.put(SUBHEADER, vm.get(SUBHEADER, String.class));
                cardDataMap.put(DESCRIPTION, vm.get(DESCRIPTION, String.class));
                cardDataMap.put(SWAP_PLACEMENT, vm.get(SWAP_PLACEMENT, String.class));
                cardsDataMap.add(cardDataMap);
            }
        }
        LOG.info("\n SIZE {} ", cardsDataMap.size());
        return cardsDataMap;
    }
}