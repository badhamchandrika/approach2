package ca.airmiles.travel.core.models;

import lombok.Getter;
import lombok.Setter;

public class CardDetail extends LinkModel {

    @Getter
    @Setter
    private String cardTitle;

    @Getter
    @Setter
    private String cardDestinationCountry;

    @Getter
    @Setter
    private String cardDescription;

    @Getter
    @Setter
    private String cardImagePath;

    @Getter
    @Setter
    private String cardImageAlt;

    @Getter
    @Setter
    private String cardTagIcon;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String dateText;

    @Getter
    @Setter
    private String date;

    @Getter
    @Setter
    private String startDate;

    @Getter
    @Setter
    private String[] tags;

    @Getter
    @Setter
    private boolean hideInListing;

    @Getter
    @Setter
    private String dealType;

}
