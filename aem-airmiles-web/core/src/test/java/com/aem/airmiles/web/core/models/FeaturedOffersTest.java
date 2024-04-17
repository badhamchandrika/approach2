package com.aem.airmiles.web.core.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class FeaturedOffersTest {
    static FeaturedOffers featuredOffers = new FeaturedOffers();

    List<AllOffersCTA> filterButtons = new ArrayList<>();
    List<AllOffersTabs> allOffersTabsList = new ArrayList<>();
    OffersFilterListItem offersFilterListItem = new OffersFilterListItem();
    List<OffersFilterListItem> mechanismList = new ArrayList<>();
    List<OffersFilterListItem> sortByOptionsList = new ArrayList<>();
    List<OffersFilterListItem> offersTypeList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        featuredOffers.setSortFilterText("Filter/Sort");
        featuredOffers.setCollapseSortText("Collapse");
        featuredOffers.setFilterByText("FilterBy");
        featuredOffers.setFilterCategoryTitle("All Categories");
        featuredOffers.setSortByText("SortBy");
        featuredOffers.setResultShowingText("Showing for:");
        featuredOffers.setRegionText("region");
        featuredOffers.setPagination(12);
        featuredOffers.setTrendingLimit(10);
        featuredOffers.setNoResultTitle("No Result Found");
        featuredOffers.setNoResultDescription("No Result Found Description");
        featuredOffers.setFilterTypesTitle("Offer types");
        AllOffersTabs allOffersTabs = new AllOffersTabs();
        allOffersTabs.setTabName("Offers for you");
        allOffersTabsList.add(allOffersTabs);
        featuredOffers.setFeaturedOffersTabs(allOffersTabsList);
        AllOffersCTA allOffersCTA = new AllOffersCTA();
        allOffersCTA.setLinkType("button");
        filterButtons.add(allOffersCTA);
        featuredOffers.setFilterButtons(filterButtons);
        featuredOffers.setFilterSubCategoryTitle("All Sub Categories");
        featuredOffers.setFilterPartnersTitle("All Partners");
        featuredOffers.setFeaturedOffersTitle("Featured Offers");
        featuredOffers.setMessageNoSavedOffers("you have no saved offers");
        featuredOffers.setMessageAbort("Abort the request");
        featuredOffers.setMessageNoOffersForYou("You no personalised offers");
        featuredOffers.setFilterSortText("Filter/Sort");
        featuredOffers.setExpandSortText("Expand Filter/Sort");
        featuredOffers.setClearAllText("Clear All");
        featuredOffers.setResultOffersText("Result");
        featuredOffers.setNoResultWhenApiFails("Sorry couldn't fetch the offers. Please try again in sometime");
        featuredOffers.setBackText("back");
        featuredOffers.setTermsTitle("Terms & Conditions");
        featuredOffers.setSeeAllPartnersTitle("See All Partner Offers");
        featuredOffers.setFullDetailText("...see full detail");
        featuredOffers.setMiniFeedTitle("You might also be interested in");
        featuredOffers.setOfferEndsText("Ends:");
        featuredOffers.setOfferStartText("Starts:");
        featuredOffers.setBackToOffersDataClickID("back to offers click ID");
        featuredOffers.setBackToOffersDataTrackID("back to offers track ID");
        featuredOffers.setCloseIconDataClickID("close");
        featuredOffers.setCloseIconDataTrackID("filter/sort");
        featuredOffers.setCloseIconDataTrackType("internal");
        featuredOffers.setBackToOffersDataTrackType("internal");
        featuredOffers.setBackTextDataClickID("back");
        featuredOffers.setBackTextDataTrackID("back");
        featuredOffers.setBackTextDataTrackType("internal");
        featuredOffers.setBackToOfferText("back to Offers");
        featuredOffers.setDisplayOffersList("true");
        featuredOffers.setMenuHide("true");
        featuredOffers.setOffersCardTrackID("offers-card-ID");
        featuredOffers.setOffersCardClickID("offer-click-id");
        featuredOffers.setOffersCardTrackType("offer-card-type");
        featuredOffers.setCtaDetailCLOText("Card linked");
        featuredOffers.setCtaDetailCLOUrl("airmiles.ca");
        featuredOffers.setCtaDetailClickID("clo-click-id");
        featuredOffers.setCtaDetailTrackID("clo-track-id");
        featuredOffers.setCtaDetailTrackType("clo-track-type");
        featuredOffers.setCtaDetailTarget(true);
        featuredOffers.setOptInDetailClickID("clo-opt-in-click-id");
        featuredOffers.setOptInDetailTrackID("clo-opt-in-track-id");
        featuredOffers.setOptInDetailTrackType("clo-opt-in-track-type");
        featuredOffers.setOptInDetailTarget(true);

    }

    @Test
    void test_Offers_Details(){
        assertEquals("Filter/Sort",featuredOffers.getSortFilterText());
        assertEquals("Collapse",featuredOffers.getCollapseSortText());
        assertEquals("FilterBy",featuredOffers.getFilterByText());
        assertEquals("All Categories",featuredOffers.getFilterCategoryTitle());
        assertEquals("SortBy",featuredOffers.getSortByText());
        assertEquals("Showing for:",featuredOffers.getResultShowingText());
        assertEquals("region",featuredOffers.getRegionText());
        assertEquals(12, featuredOffers.getPagination());
        assertEquals(10, featuredOffers.getTrendingLimit());
        assertEquals("No Result Found",featuredOffers.getNoResultTitle());
        assertEquals("No Result Found Description",featuredOffers.getNoResultDescription());
        assertEquals("All Sub Categories",featuredOffers.getFilterSubCategoryTitle());
        assertEquals("Offer types",featuredOffers.getFilterTypesTitle());
        assertEquals("All Partners",featuredOffers.getFilterPartnersTitle());
        assertEquals(filterButtons,featuredOffers.getFilterButtons());
        assertEquals(allOffersTabsList,featuredOffers.getFeaturedOffersTabs());
        assertEquals("Featured Offers",featuredOffers.getFeaturedOffersTitle());
        assertEquals("you have no saved offers",featuredOffers.getMessageNoSavedOffers());
        assertEquals("Abort the request",featuredOffers.getMessageAbort());
        assertEquals("You no personalised offers",featuredOffers.getMessageNoOffersForYou());
        assertEquals("Filter/Sort",featuredOffers.getFilterSortText());
        assertEquals("Expand Filter/Sort",featuredOffers.getExpandSortText());
        assertEquals("Clear All",featuredOffers.getClearAllText());
        assertEquals("Result",featuredOffers.getResultOffersText());
        assertEquals("Sorry couldn't fetch the offers. Please try again in sometime",
         featuredOffers.getNoResultWhenApiFails());
        assertEquals("Terms & Conditions",featuredOffers.getTermsTitle());
        assertEquals("See All Partner Offers",featuredOffers.getSeeAllPartnersTitle());
        assertEquals("...see full detail",featuredOffers.getFullDetailText());
        assertEquals("You might also be interested in",featuredOffers.getMiniFeedTitle());
        assertEquals("Ends:",featuredOffers.getOfferEndsText());
        assertEquals("Starts:",featuredOffers.getOfferStartText());
        assertEquals("close",featuredOffers.getCloseIconDataClickID());
        assertEquals("filter/sort",featuredOffers.getCloseIconDataTrackID());
        assertEquals("internal",featuredOffers.getCloseIconDataTrackType());
        assertEquals("back to offers click ID",featuredOffers.getBackToOffersDataClickID());
        assertEquals("back to offers track ID",featuredOffers.getBackToOffersDataTrackID());
        assertEquals("internal",featuredOffers.getBackToOffersDataTrackType());
        assertEquals("internal",featuredOffers.getBackTextDataTrackType());
        assertEquals("back",featuredOffers.getBackTextDataTrackID());
        assertEquals("back",featuredOffers.getBackTextDataClickID());
        assertEquals("back to Offers",featuredOffers.getBackToOfferText());
        assertEquals("back",featuredOffers.getBackText());
        assertEquals("true",featuredOffers.getDisplayOffersList());
        assertEquals("true",featuredOffers.getMenuHide());

        assertEquals("offers-card-ID",featuredOffers.getOffersCardTrackID());
        assertEquals("offer-click-id",featuredOffers.getOffersCardClickID());
        assertEquals("offer-card-type",featuredOffers.getOffersCardTrackType());
        assertEquals("Card linked",featuredOffers.getCtaDetailCLOText());
        assertEquals("airmiles.ca",featuredOffers.getCtaDetailCLOUrl());

        assertEquals("clo-click-id",featuredOffers.getCtaDetailClickID());
        assertEquals("clo-track-id",featuredOffers.getCtaDetailTrackID());
        assertEquals("clo-track-type",featuredOffers.getCtaDetailTrackType());
        assertEquals(true,featuredOffers.getCtaDetailTarget());

        assertEquals("clo-opt-in-click-id",featuredOffers.getOptInDetailClickID());
        assertEquals("clo-opt-in-track-id",featuredOffers.getOptInDetailTrackID());
        assertEquals("clo-opt-in-track-type",featuredOffers.getOptInDetailTrackType());
        assertEquals(true,featuredOffers.getOptInDetailTarget());

    }



    @Test
    void test_Mechs(){
        offersFilterListItem.setTitle("Ready To Use");
        offersFilterListItem.setValue("No Action");
        mechanismList.add(offersFilterListItem);
        featuredOffers.setMechanismList(mechanismList);
        assertEquals(mechanismList,featuredOffers.getMechanismList());
    }

    @Test
    void test_SortBy(){
        offersFilterListItem.setTitle("Default");
        offersFilterListItem.setValue("default");
        offersFilterListItem.setTitle("Ending Soonest");
        offersFilterListItem.setValue("endingSoonest");
        sortByOptionsList.add(offersFilterListItem);
        featuredOffers.setSortByOptionsList(sortByOptionsList);
        assertEquals(sortByOptionsList,featuredOffers.getSortByOptionsList());
    }

    @Test
    void test_OfferTypes(){
        offersFilterListItem.setTitle("Aimiles Cash");
        offersFilterListItem.setValue("cash");
        offersTypeList.add(offersFilterListItem);
        featuredOffers.setOffersTypeList(offersTypeList);
        assertEquals(offersTypeList,featuredOffers.getOffersTypeList());
    }

}
