import React, { useEffect, useState } from "react";
import CONSTANTS from "../../constants";
import Alert from "../ui/Alert/Alert";
import InLineDropdown from "../React/InlineDropdown/InlineDropdown";
import RoomBox from "../React/RoomBox/RoomBox";
import PassengersBox from "../flightswidget/PassengersBox/PassengersBox";
import LocationDropdown from "../ui/LocationDropdown";
import Datepicker from "../flightswidget/Datepicker/components/Datepicker";
import CheckBox from "../ui/Checkbox/Checkbox";
import RadioGroup from "../ui/RadioGroup/RadioGroup";
import {
    getAuthorConfig,
    getCollectorClusters,
    getPackagesJson,
} from "../../api/services";
import makeQueryString from "../../helpers/makeQueryString";
import { getCollectorId } from "../../helpers/getCollectorId";
import { makeGsAgesObject } from "../../helpers/makeGsAgesObject";
import { START_DATE } from "../flightswidget/Datepicker/hooks/useDatepickerCustom/useDatepickerCustom";
import { higherPrecedenceCluster } from "../../helpers/higherPrecedenceCluster";
import { getPackageId } from "../../helpers/getPackageId";
import ValidationWrapper from "../ui/ValidationWrapper/ValidationWrapper";
import { isBlankStr } from "../../helpers/isBlankStr";
import { getEnvironment } from "../../helpers/getEnvironment";
import { getTravelersGsAgesObject } from "../../helpers/getTravelersGsAgesObject";
import { getSearchUrl } from "../../helpers/getURL";
import useClusters from "../../hooks/useClusters";
import useFetch from "../../hooks/useFetch";
import { ColField, ColSelector, Row, WidgetContainer, WidgetFooter, WidgetHeader, WidgetRowProgressive } from "../Widget";
import { PackagesContext } from "./PackagesContext";
import { AnotherItem } from "./AnotherItem";
import { CreditCardSvg, MilesSvg } from "../flightswidget/Datepicker/components/Svgs";
import { useContainerQuery } from "react-container-query";
import useProgressiveFields from "../../hooks/useProgressiveFields";

const parentWidget= CONSTANTS.WIDGET.PACKAGES;

function PackagesWidget({
                            json_url = "json_url",
                            msg_adv_search_text_init = "adv_search_text_init",
                            msg_adv_search_text_middle = "adv_middle",
                            msg_adv_search_text_link = "adv_search_text_link",
                            msg_empty_field = "empty_field",
                            msg_empty_field_age = "empty_field_age",
                            msg_going_to = "going_to",
                            msg_to_many_passengers = "to_many_passengers",
                            header_error_type = "er_error_type",
                            header_missing_info = "missing_info",
                            msg_to_many_children = "to_many_children",
                            header_see_fields = "see_fields",
                            child_under_eight = "under_eight",
                            child_under_eight_eleven = "under_eight_eleven",
                            age_warning_message = "warning_message",
                            age_confirmation = "confirmation",
                            age_requirement_text = "requirement_text",
                            msg_adults = "adults",
                            msg_adult = "adult",
                            msg_children = "children",
                            msg_infants = "infants",
                            msg_infant = "infant",
                            msg_add_adult = "add_adult",
                            msg_remove_adult = "remove_adult",
                            msg_add_child = "add_child",
                            msg_remove_child = "remove_child",
                            msg_add_infant = "add_infant",
                            msg_remove_infant = "remove_infant",
                            msg_passengerbox_one_traveller = "one_traveller",
                            msg_passengerbox_travellers = "travellers",
                            msg_datepicker_previous_month = "previous_month",
                            msg_datepicker_next_month = "next_month",
                            msg_datepicker_departure_date = "departure_date",
                            msg_datepicker_return_date = "return_date",
                            msg_datepicker_check_in = "check_in",
                            msg_datepicker_check_out = "check_out",
                            msg_timedd_pickup_time = "timedd_pickup_time",
                            msg_timedd_dropoff_time = "timedd_dropoff_time",
                            msg_pickup_date = "pickup_date",
                            msg_dropoff_date = "dropoff_date",
                            msg_search_by_city_hotel_or_airport = "search_by_city_hotel_or_airport",
                            msg_traveller = "traveller",
                            msg_travellers = "travellers",
                            msg_add_another_room = "add_another_room",
                            msg_maximum_number_rooms = "maximum_number_rooms",
                            msg_children_ages = "children_ages",
                            msg_children_ages_2_11 = "children_ages_2_11",
                            msg_remove = "remove",
                            msg_required_field = "required_field",
                            msg_airline_age_rules = "airline_age_rules",
                            msg_choose_two_or_more_products = "choose_two_or_more_products",
                            msg_select_another_product = "select_another_product",
                            msg_to_start_building_package = "to_start_building_package",
                            msg_flights = "Flights",
                            msg_accommodations = "accommodations",
                            msg_car_rentals = "car_rentals",
                            msg_update = "update",
                            msg_room = "room",
                            msg_rooms = "rooms",
                            msg_advanced_search_page = "advanced_search_page",
                            msg_search = "search",
                            msg_infant_ages_0_2 = "infant_ages_0_2",
                            msg_from = "from",
                            msg_to = "to",
                            msg_cabin_preference = "cabin_preference",
                            datepicker_months_string = "picker_months_string",
                            datepicker_days_string = "picker_days_string",
                            runmodes = "odes",
                            msg_close = "close",
                            payment_credit_card = "Demo select card",
                            payment_dream_miles = "Demo dream miles",
                            payment_preference = "Demo select payment",
                            payment_select_card = "Demo select card",
                            payment_select_miles = "Demo use miles",
                            msg_notice = "notice",
                            msg_packages_booking = "packages_booking",
                            promo_code = "Demo promo code",
                            add_promo_code = "Demo add promo code",
                            promo_code_error = "Demo promo code error",
                            isCarRentalEnabled = false,
                            isSmallWidget = false,
                            title = "Demo title Package", // TODO: pass this prop by data-prop
                            hasWidgetUpdates = false,
                            show_multi_header_message = false,
                            msg_notification_message = "Demo Notification Message",
                            hasProgressive = false,
                            progressiveButtonText,
                            ...props
                        }) {

    const [classNameObject, containerRef] = useContainerQuery(
        CONSTANTS.CONTAINER_QUERIES.CONTAINER
        );

    const [gsOrigin, setgsOrigin] = useState([""]);
    const [gsOriginCode, setgsOriginCode] = useState([""]);
    const [gsOriginError, setgsOriginError] = useState([false]);
    const [gsDestination, setgsDestination] = useState([""]);
    const [gsDestinationCode, setgsDestinationCode] = useState([""]);
    const [gsDestinationError, setgsDestinationError] = useState([false]);
    const [gsDepartureDate, setgsDepartureDate] = useState([]);
    const [gsReturnDate, setgsReturnDate] = useState([]);
    const [gsPromotionCode, setGsPromotionCode] = useState("");
    const [authorConfig, setAuthorConfig] = useState(null);
    const [errorFields, setErrorFields] = useState([]);
    const [collectorsClusterName, setCollectorsClusterName] = useState(CONSTANTS.DEFAULT_CLUSTER);
    const [paymentOption, setPaymentOption] = useState(null);
    const crentals = 1;
    const [rooms, setRooms] = React.useState([
        { id: 1, adults: 1, children: [] },
    ]);
    const [travelers, setTravelers] = useState({
        adults: 1,
        child: [],
        inSeat: 0,
    });
    const [gsPickUpTime, setgsPickUpTime] = useState(CONSTANTS.TIMES_ARR[12]);
    const [gsDropOffTime, setgsDropOffTime] = useState(CONSTANTS.TIMES_ARR[12]);
    const [showPickUpDropdown, setShowPickUpDropdown] = useState(false);
    const [showDropOffDropdown, setShowDropOffDropdown] = useState(false);
    const errorTotalPax = {
        type: header_error_type,
        message: [msg_to_many_passengers],
    };
    const headerMultiMessage = {
        type: msg_notice,
        message: [`${msg_notification_message}`],
    };
    const warningChildren = [
        `${child_under_eight}. ${child_under_eight_eleven}.`,
    ];
    const [foCabinPreference, setfoCabinPreference] = useState(
        CONSTANTS.CABIN_TYPE_ECONOMY
    );
    const PROMO_CODE_ID = "PromoCodeId";

    const [flightsCheckbox, setFlightsCheckbox] = useState({
        id: "checked1",
        checked: !isCarRentalEnabled,
        text: msg_flights,
    });
    const [hotelsCheckbox, setHotelsCheckbox] = useState({
        id: "checked2",
        checked: false,
        text: msg_accommodations,
    });
    const [carsCheckbox, setCarsCheckbox] = useState({
        id: "checked3",
        checked: false,
        text: msg_car_rentals,
    });
    const [ageCheckbox, setAgeCheckbox] = useState({
        id: "checked4",
        checked: false,
        text: age_requirement_text,
    });

    const [fromLocationText, setFromLocationText] = useState(msg_from);
    const [toLocationText, setToLocationText] = useState(msg_to);
    const [isEnoughSelections, setIsEnoughSelections] = useState(true);

    const defaultCalendarState = {
        startDate: null,
        endDate: null,
        focusedInput: START_DATE,
    };
    const [calendarState0, setCalendarState0] = useState(defaultCalendarState);

    const [calendarState1, setCalendarState1] = useState(defaultCalendarState);

    const [pickUpDate, setPickUpDate] = useState();
    const [dropOffDate, setDropOffDate] = useState();


    const [isProgressive, setIsProgressive] = useState(false);
    const { xl: isDesktop } = classNameObject;
    const showProgressiveFields = !hasProgressive || isProgressive || !isDesktop;

    const errorHeaderMessage = {
        type: header_error_type,
        message: [`${header_missing_info} ${header_see_fields}`],
    };

    const headerPackagesMessage = {
        type: msg_notice,
        message: [
            msg_packages_booking,
        ],
    };

    const toManyChildren = {
        type: header_error_type,
        message: [msg_to_many_children],
    };

    const {data:autoCompleteData} = useFetch(getPackagesJson);
    const [clustersPackages] = useClusters();
    useProgressiveFields([gsOriginCode,gsDestinationCode,gsDepartureDate,gsReturnDate], setIsProgressive);

    const setFields = () => {
        if (carsCheckbox.checked) {
            setFromLocationText(msg_from);
            setToLocationText(msg_going_to);
        } else {
            setFromLocationText(authorConfig?.fromLocationText || fromLocationText);
            setToLocationText(authorConfig?.toLocationText || toLocationText);
        }
    };

    const handleOnConfirmAge = ({ checked }) => {
        setErrorFields(errorFields.filter((obj) => obj.inputId !== "ageCheckbox"));
        setAgeCheckbox((prev) => ({ ...prev, checked }));
    };

    const isLessThanTwoSelected = () => {
        let sum = 0;
        if (flightsCheckbox.checked) {
            sum = sum + 1;
        }
        if (hotelsCheckbox.checked) {
            sum = sum + 1;
        }
        if (carsCheckbox.checked) {
            sum = sum + 1;
        }
        if (sum <= 1) {
            setIsEnoughSelections(false);
        } else {
            setIsEnoughSelections(true);
        }
    };

    const departureDateLabel = () =>
        flightsCheckbox.checked
            ? authorConfig.departureDateText || msg_datepicker_departure_date
            : msg_datepicker_check_in;
    const returnDateLabel = () =>
        flightsCheckbox.checked
            ? authorConfig.returnDateText || msg_datepicker_return_date
            : msg_datepicker_check_out;

    const getRegex = () => {
        if (
            flightsCheckbox.checked &&
            hotelsCheckbox.checked &&
            !carsCheckbox.checked
        ) {
            return /^(?=.*Air.*Hotel)(?!.*Car).*$/;
        } else if (
            flightsCheckbox.checked &&
            carsCheckbox.checked &&
            !hotelsCheckbox.checked
        ) {
            return /^(?=.*Air.*Car)(?!.*Hotel).*$/;
        } else if (
            hotelsCheckbox.checked &&
            carsCheckbox.checked &&
            !flightsCheckbox.checked
        ) {
            return /^(?=.*Hotel.*Car)(?!.*Air).*$/;
        } else {
            return /^(?=.*Air.*Hotel.*Car).*$/;
        }
    };

    useEffect(() => {
        getAuthorConfig(json_url).then((json) => {
            setAuthorConfig(json.data);
            setfoCabinPreference(json.data.cabinList?.[0].actualValue);
            setFlightsCheckbox((prev) => ({
                ...prev,
                text: json.data.flightsCheckboxText || msg_flights,
            }));
            setHotelsCheckbox((prev) => ({
                ...prev,
                text: json.data.hotelsCheckboxText || msg_accommodations,
            }));
            setCarsCheckbox((prev) => ({
                ...prev,
                text: json.data.carsCheckboxText || msg_car_rentals,
            }));
        });

        const collectorId = getCollectorId();
        if (collectorId) {
            getCollectorClusters(collectorId, getEnvironment(runmodes)).then(
                (response) => {
                    const collectorsClusterName = higherPrecedenceCluster(
                        response?.data?.data
                    )?.clusterID;
                    setCollectorsClusterName(collectorsClusterName);
                }
            );
        }
    }, []);

    useEffect(() => {
        hotelsCheckbox.checked && carsCheckbox.checked ? setgsOriginCode(['dummy']):setgsOriginCode(['']);
    }, [hotelsCheckbox.checked,carsCheckbox.checked]);    

    useEffect(() => {
        setFields();
        isLessThanTwoSelected();
        setErrorFields([]);
    }, [flightsCheckbox.checked, hotelsCheckbox.checked, carsCheckbox.checked]);

    const isErrors = () => {
        let isErrors = false;
        const errorsPlaceholdersAcum = [];
        for (let i = 0; i < crentals; i++) {
            if (
                !(
                    hotelsCheckbox.checked &&
                    carsCheckbox.checked &&
                    !flightsCheckbox.checked
                ) &&
                isBlankStr(gsOrigin[i])
            ) {
                errorsPlaceholdersAcum.push({
                    text: `${authorConfig.fromLocationText || msg_from}`,
                    inputId: `originInput_crent_${i + 1}_${parentWidget}`,
                });
                isErrors = true;
            }
            if (isBlankStr(gsDestination[i])) {
                errorsPlaceholdersAcum.push({
                    text: `${authorConfig.toLocationText || msg_to}`,
                    inputId: `destinationInput_crent_${i + 1}_${parentWidget}`,
                });
                isErrors = true;
            }
            if (carsCheckbox.checked && !ageCheckbox.checked) {
                errorsPlaceholdersAcum.push({
                    text: age_confirmation,
                    inputId: `ageCheckbox`,
                });
                isErrors = true;
            }
            if (!gsDepartureDate[i]) {
                errorsPlaceholdersAcum.push({
                    text: `${departureDateLabel()}`,
                    inputId: `${i}_departure_date_${parentWidget}`,
                    message: msg_empty_field,
                });
                isErrors = true;
            }
            if (!gsReturnDate[i]) {
                errorsPlaceholdersAcum.push({
                    text: `${returnDateLabel()}`,
                    inputId: `${i}_return_date`,
                    message: msg_empty_field,
                });
                isErrors = true;
            }
        }
        if (!CONSTANTS.REGEX_ALPHANUMERIC_MAX20.test(gsPromotionCode)) {
            errorsPlaceholdersAcum.push({
              text: `${authorConfig.promoCode || promo_code}`,
              inputId: PROMO_CODE_ID,
              message: promo_code_error,
            });
            isErrors = true;
          }
        setErrorFields([...errorsPlaceholdersAcum]);

        return isErrors;
    };

    const buildSearchQuery = () => {
        let packageId = "";
        if (clustersPackages) {
            packageId = getPackageId({
                clustersPackages,
                collectorsClusterName,
                word: getRegex(),
                redemption: paymentOption ? CONSTANTS.MILES : CONSTANTS.CASH
            });
        }

        return makeQueryString({
            gsOrigin: flightsCheckbox.checked ? gsOriginCode : null,
            gsDestination: gsDestinationCode,
            gsDepartureDate,
            gsReturnDate,
            gsNumberOfTravelers: hotelsCheckbox.checked
                ? rooms.map((room) => room.adults + room.children.length)
                : [travelers.adults + travelers.child.length + travelers.inSeat],
            gsAgesObject: hotelsCheckbox.checked
                ? makeGsAgesObject(rooms)
                : getTravelersGsAgesObject(travelers.child, travelers.inSeat),
            gsSourceCode: collectorsClusterName,
            gsvacationtype: packageId,
            foCabinPreference: flightsCheckbox.checked ? foCabinPreference:null,
            gsPromotionCode,
        });
    };

    const submit = () => {
        if (!isErrors()) {
            window.location.href =
                getSearchUrl(runmodes) + buildSearchQuery();
        }
    };

    if (!authorConfig) {
        // in the future this can be replaced with a loading state
        return <div></div>;
    }

    return (
        <PackagesContext.Provider 
            value={{ 
                classNameObject,
                errorFields,
                gsPromotionCode,
                hasWidgetUpdates,
                isSmallWidget,
                add_promo_code, 
                promo_code,
                runmodes,
                setErrorFields,
                setGsPromotionCode,
                hasProgressive,
                isProgressive,
                setIsProgressive,
                progressiveButtonText,
            }}>
            <WidgetContainer
                className="packages-widget"
                parentWidget={parentWidget}
                ref={containerRef}
                title={title}
            >
                {errorFields.length > 0 && (
                    <Alert
                        errorFields={errorFields}
                        errorHeaderMessage={errorHeaderMessage}
                    />
                )}

                {show_multi_header_message && (
                   <Alert
                    type="info"
                    errorHeaderMessage={headerMultiMessage}
                    singleMessage
                  />
                )}
                <WidgetHeader
                    advancedSearchText={authorConfig.advSearchText || msg_advanced_search_page}
                    buildSearchQuery={buildSearchQuery}
                    parentWidget={parentWidget}
                >
                    <>
                        <div className="products-heading">
                            {msg_choose_two_or_more_products}
                        </div>
                        <Row parentWidget={parentWidget}>
                            <div className="col-md-auto">
                                <CheckBox
                                    id="checked1"
                                    setValue={({ checked }) => {
                                        setIsProgressive(false);
                                        setFlightsCheckbox((prev) => ({ ...prev, checked }));
                                        setCalendarState0(defaultCalendarState);
                                        setCalendarState1(defaultCalendarState);
                                    }}
                                    label={flightsCheckbox.text}
                                    checked={flightsCheckbox.checked}
                                    disabled = {!isCarRentalEnabled}
                                />
                            </div>
                            <div className="col-md-auto">
                                <CheckBox
                                    id="checked2"
                                    setValue={({ checked }) => {
                                        setIsProgressive(false);
                                        setHotelsCheckbox((prev) => ({ ...prev, checked }));
                                        setCalendarState0(defaultCalendarState);
                                        setCalendarState1(defaultCalendarState);
                                    }}
                                    label={hotelsCheckbox.text}
                                    checked={hotelsCheckbox.checked}
                                    disabled={isCarRentalEnabled ? false:carsCheckbox.checked}
                                />
                            </div>
                            <div className="col-md-auto">
                                <CheckBox
                                    id="checked3"
                                    setValue={({ checked }) => {
                                        setIsProgressive(false);
                                        setCarsCheckbox((prev) => ({ ...prev, checked }));
                                        setCalendarState0(defaultCalendarState);
                                        setCalendarState1(defaultCalendarState);
                                    }}
                                    label={carsCheckbox.text}
                                    checked={carsCheckbox.checked}
                                    disabled={isCarRentalEnabled ? false:hotelsCheckbox.checked}
                                />
                            </div>
                        </Row>    
                    </>
                    <>
                        {flightsCheckbox.checked &&
                            !hotelsCheckbox.checked &&
                            carsCheckbox.checked && (
                                <ColSelector parentWidget={parentWidget}>
                                    <PassengersBox
                                        sendTravellers={setTravelers}
                                        warnings={{
                                            errorTotalPax,
                                            warningChildren,
                                            errorSomeFields: errorHeaderMessage,
                                            toManyChildren,
                                        }}
                                        labels={{
                                            adult: `${
                                                authorConfig?.passengerList?.adult || msg_adults
                                            }`,
                                            child:
                                                authorConfig?.passengerList?.child ||
                                                msg_children_ages_2_11,
                                            infant:
                                                authorConfig?.passengerList?.infant ||
                                                msg_infant_ages_0_2,
                                        }}
                                        updateButtonText={authorConfig.updateButtonText}
                                        dropdownLabel={msg_passengerbox_travellers}
                                        ariaMessages={{
                                            msg_add_adult,
                                            msg_remove_adult,
                                            msg_add_child,
                                            msg_remove_child,
                                            msg_add_infant,
                                            msg_remove_infant,
                                        }}
                                        msg_airline_age_rules={msg_airline_age_rules}
                                        singularPersonsTitle={msg_passengerbox_one_traveller}
                                        pluralPersonsTitle={msg_passengerbox_travellers}
                                        msg_update={msg_update}
                                        msg_adv_search_text_init={msg_adv_search_text_init}
                                        msg_adv_search_text_middle={msg_adv_search_text_middle}
                                        msg_adv_search_text_link={msg_adv_search_text_link}
                                        msg_empty_field={msg_empty_field_age}
                                        msg_adults={msg_adults}
                                        msg_adult={msg_adult}
                                        msg_children={msg_children}
                                        msg_infant={msg_infant}
                                        msg_infants={msg_infants}
                                        msg_close={msg_close}
                                        runmodes={runmodes}
                                        dataTrackId="dropdown-advance-search"
                                        dataTrackClick="packages-advance-search"
                                        dataTrackType="internal"
                                        parentWidget={parentWidget}
                                        {...props}
                                    />
                                </ColSelector>
                            )}

                        {hotelsCheckbox.checked &&
                            !(
                                !flightsCheckbox.checked &&
                                hotelsCheckbox.checked &&
                                !carsCheckbox.checked
                            ) && (
                                <ColSelector parentWidget={parentWidget}>
                                    <RoomBox
                                        rooms={rooms}
                                        setRooms={setRooms}
                                        warnings={{
                                            errorTotalPax: errorTotalPax,
                                            errorSomeFields: errorHeaderMessage,
                                        }}
                                        msg_room={msg_room}
                                        msg_rooms={msg_rooms}
                                        msg_traveller={msg_traveller}
                                        msg_travellers={msg_travellers}
                                        msg_add_another_room={msg_add_another_room}
                                        msg_maximum_number_rooms={msg_maximum_number_rooms}
                                        msg_required_field={msg_required_field}
                                        msg_adults={msg_adults}
                                        msg_adult={msg_adult}
                                        msg_children={msg_children}
                                        msg_infant={msg_infant}
                                        msg_infants={msg_infants}
                                        msg_children_ages={msg_children_ages}
                                        msg_remove={msg_remove}
                                        msg_update={msg_update}
                                        msg_adv_search_text_init={
                                            !(
                                                hotelsCheckbox.checked &&
                                                carsCheckbox.checked &&
                                                !flightsCheckbox.checked
                                            ) && msg_adv_search_text_init
                                        }
                                        msg_adv_search_text_middle={
                                            !(
                                                hotelsCheckbox.checked &&
                                                carsCheckbox.checked &&
                                                !flightsCheckbox.checked
                                            ) && msg_adv_search_text_middle
                                        }
                                        msg_adv_search_text_link={msg_adv_search_text_link}
                                        parentWidget={parentWidget}
                                        msg_add_adult={msg_add_adult}
                                        msg_remove_adult={msg_remove_adult}
                                        msg_add_child={msg_add_child}
                                        msg_remove_child={msg_remove_child}
                                        msg_add_infant={msg_add_infant}
                                        msg_remove_infant={msg_remove_infant}
                                        msg_empty_field={msg_empty_field_age}
                                        runmodes={runmodes}
                                        msg_close={msg_close}
                                        {...props}
                                    />
                                </ColSelector>
                            )}

                        {flightsCheckbox.checked && isEnoughSelections && (
                            <ColSelector parentWidget={parentWidget}>
                                <InLineDropdown
                                    parentWidget={parentWidget}
                                    label={authorConfig.cabinHeading || msg_cabin_preference}
                                    activeLabel={authorConfig.cabinList?.[0].actualValue}
                                    selectedValueCallBack={(value) =>
                                        setfoCabinPreference(value)
                                    }
                                    options={authorConfig.cabinList?.map((t, i) => ({
                                        key: t + i,
                                        value: t.actualValue,
                                        text: t.displayType,
                                    }))}
                                />
                            </ColSelector>
                        )}
                    </>
                </WidgetHeader>
                {isEnoughSelections && (                    
                    <WidgetRowProgressive
                        setIsProgressive={setIsProgressive}
                        parentWidget={parentWidget}
                    >
                        <Row parentWidget={parentWidget}>
                            {!(
                                hotelsCheckbox.checked &&
                                carsCheckbox.checked &&
                                !flightsCheckbox.checked
                            ) && (
                                <ColField parentWidget={parentWidget}>
                                    <LocationDropdown
                                        index={0}
                                        value={gsOrigin[0]}
                                        inputId={"originInput_crent_1_"+ parentWidget}
                                        placeholder={fromLocationText}
                                        json={autoCompleteData}
                                        toInstructionalText={authorConfig.fromInstructionalText}
                                        setter={setgsOrigin}
                                        setgsCode={setgsOriginCode}
                                        validationError={gsOriginError[0]}
                                        setErrorFields={setErrorFields}
                                        errorFields={errorFields}
                                        msg_search_by_city_hotel_or_airport={
                                            msg_search_by_city_hotel_or_airport
                                        }
                                        msg_empty_field={msg_empty_field}
                                        msg_close={msg_close}
                                        parentWidget={parentWidget}
                                    />
                                </ColField>
                            )}

                            <ColField parentWidget={parentWidget}>
                                <LocationDropdown
                                    index={0}
                                    value={gsDestination[0]}
                                    inputId="destinationInput_crent_1"
                                    placeholder={toLocationText}
                                    json={autoCompleteData}
                                    toInstructionalText={authorConfig.toInstructionalText}
                                    setter={setgsDestination}
                                    setgsCode={setgsDestinationCode}
                                    validationError={gsDestinationError[0]}
                                    setErrorFields={setErrorFields}
                                    errorFields={errorFields}
                                    msg_search_by_city_hotel_or_airport={
                                        msg_search_by_city_hotel_or_airport
                                    }
                                    msg_empty_field={msg_empty_field}
                                    msg_close={msg_close}
                                    parentWidget={parentWidget}
                                />
                            </ColField>

                            <ColField parentWidget={parentWidget} forDatepicker>
                                <Datepicker
                                    setDeparture={setgsDepartureDate}
                                    setReturn={setgsReturnDate}
                                    index={0}
                                    departureDateLabel={departureDateLabel()}
                                    returnDateLabel={returnDateLabel()}
                                    firstDateLabel={departureDateLabel()}
                                    canOverflow
                                    state={calendarState0}
                                    setState={setCalendarState0}
                                    setErrorFields={setErrorFields}
                                    errorFields={errorFields}
                                    msg_datepicker_previous_month={msg_datepicker_previous_month}
                                    msg_datepicker_next_month={msg_datepicker_next_month}
                                    datepicker_months_string={datepicker_months_string}
                                    datepicker_days_string={datepicker_days_string}
                                    msg_close={msg_close}
                                    parentWidget={parentWidget}
                                    {...props}
                                />
                            </ColField>
                        </Row>

                        {carsCheckbox.checked && (
                            <Row parentWidget={parentWidget}>
                                <div className="col col-12 mt-1">
                                    <ValidationWrapper
                                        validationError={
                                            errorFields?.find(
                                                (field) => field?.inputId === "ageCheckbox"
                                                )
                                                ? { message: age_warning_message }
                                                : null
                                            }
                                            >
                                        <CheckBox
                                            id="ageCheckbox"
                                            setValue={handleOnConfirmAge}
                                            label={ageCheckbox.text}
                                            checked={ageCheckbox.checked}
                                            />
                                    </ValidationWrapper>
                                </div>
                            </Row>
                        )}
                    </WidgetRowProgressive>
                )}
                {showProgressiveFields && isEnoughSelections && <RadioGroup
                    radioOptions={[
                        {
                        icon: <CreditCardSvg />,
                        title: payment_credit_card,
                        desc: payment_select_card,
                        },
                        {
                        icon: <MilesSvg />,
                        title: payment_dream_miles,
                        desc: payment_select_miles,
                        },
                    ]}
                    selectedRadio={paymentOption}
                    setSelectedRadio={setPaymentOption}
                    header={payment_preference}
                    parentWidget={parentWidget}
                />}


                {showProgressiveFields && isEnoughSelections && (
                    <WidgetFooter
                        advancedSearchText={authorConfig.advSearchText || msg_advanced_search_page}
                        buildSearchQuery={buildSearchQuery}
                        parentWidget={parentWidget}
                        searchButtonText={authorConfig.buttonText || msg_search}
                        submit={submit}
                        />
                )}
            
                {!isEnoughSelections && (
                    <AnotherItem
                        title={msg_select_another_product}
                        subtitle={msg_to_start_building_package}
                    />
                )}
            </WidgetContainer>
        </PackagesContext.Provider>
    );
}

export default PackagesWidget;


/* 

https://bookings.beta.airmiles.ca/search/externalformpost.aspx?currentCulture=en-CA

&gsVendor=LAM

&gsdateformat=d

&gsOrigin=YYC

&gsDestination=YXX

&gsDepartureDate=11%2F14%2F2023

&gsReturnDate=11%2F15%2F2023

&gsNumberOfTravelers=1

&foCabinPreference=Y

&gsSourceCode=BASE

&gsvacationtype=AH15

*/

