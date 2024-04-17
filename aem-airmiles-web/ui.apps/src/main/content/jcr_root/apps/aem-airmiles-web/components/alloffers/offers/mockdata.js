"use strict";
use( function() {
    var data = {};
    data.sortByData = [
        {"title":"Defaulty", "value":"default"},
        {"title":"End Soonest", "value":"endSoonest"},
    ];
    data.regionData = [
        {"title":"Alberta", "value":"AL"},
        {"title":"British Columbia", "value":"BC"},
        {"title":"Ontario", "value":"ON"},
        {"title":"Nova Scotia", "value":"NS"},
        {"title":"Prince Edward Island", "value":"PEI"},
        {"title":"Newfoundland and Labrador", "value":"NL"},
        {"title":"Saskatchewan", "value":"SK"},
        {"title":"New Brunswick", "value":"NB"},
        {"title":"Quebec", "value":"QC"},
        {"title":"Northwest Territories", "value":"NT"},
        {"title":"Yukon", "value":"YT"},
        {"title":"Nunavut", "value":"NU"},
    ];
    data.offersTypesData = [
        {"title":"Spend & Get Miles", "value":"spend"},
        {"title":"Product-specific", "value":"buy"},
        {"title":"AIR MILES Cash", "value":"amCashEarn,amCashDiscount"},
    ];
    data.mechanismsData = [
        {"title":"Ready to Use", "value":"noAction"},
        {"title":"Use Code", "value":"plu"},
        {"title":"Use Code", "value":"couponCode"},
        {"title":"Opt in", "value":"optIn"},
        {"title":"Use Code", "value":"barcode"},
        {"title":"Visit website to complete", "value":"button"},
    ];
    return data ;
});