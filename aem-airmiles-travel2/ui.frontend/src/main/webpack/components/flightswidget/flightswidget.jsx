import React, { useEffect, useState } from "react";
import PassengersBox from "./PassengersBox/PassengersBox.jsx";
import InLineDropdown from "../React/InlineDropdown/InlineDropdown.jsx";
import CONSTANTS from "../../constants";
import Flight from "./Flight.jsx";
import Alert from "../ui/Alert/Alert.jsx";
import {
  getAuthorConfig,
  getCollectorClusters,
  getFlightsJson,
} from "../../api/services.js";
import { START_DATE } from "./Datepicker/hooks/useDatepickerCustom/useDatepickerCustom.js";
import { getPackageId } from "../../helpers/getPackageId.js";
import { getCollectorId } from "../../helpers/getCollectorId.js";
import makeQueryString from "../../helpers/makeQueryString.js";
import { getEnvironment } from "../../helpers/getEnvironment.js";
import RadioGroup from "../ui/RadioGroup/RadioGroup.jsx";
import { higherPrecedenceCluster } from "../../helpers/higherPrecedenceCluster.js";
import { getTravelersGsAgesObject } from "../../helpers/getTravelersGsAgesObject.js";
import useFetch from "../../hooks/useFetch.js";
import { getSearchUrl } from "../../helpers/getURL.js";
import useClusters from "../../hooks/useClusters.js";

import { FlightsContext } from "./FlightsContext.jsx";
import {
  ColSelector,
  Row,
  WidgetContainer,
  WidgetFooter,
  WidgetHeader,
  WidgetRowProgressive,
} from "../Widget";
import { CreditCardSvg, MilesSvg } from "./Datepicker/components/Svgs.js";
import { useContainerQuery } from "react-container-query";
import useProgressiveFields from "../../hooks/useProgressiveFields.js";

const parentWidget = CONSTANTS.WIDGET.FLIGHTS;

function FlightsWidget({
  json_url,
  msg_adv_search_text_init = "Demo adv search text init",
  msg_adv_search_text_link = "Demo adv search text link ",
  header_error_type = "Demo error type",
  msg_flight = "Demo flight",
  add_flight_text = "Demo flight text",
  max_number_text = "Demo max number text",
  msg_to_many_passengers = "Demo to many passengers",
  children_travel_alone = "Demo travel alone",
  unaccompanied_minor_service = "Demo minor service",
  msg_empty_field = "Demo empty field",
  msg_empty_field_age = "Demo field age",
  msg_chronological_order = "Demo chronologicalorder",
  header_missing_info = "Demo missing info",
  header_see_fields = "Demo see fields",
  msg_to_many_children = "Demo many children",
  msg_add_adult = "Demo add adult",
  msg_remove_adult = "Demo remove_adult",
  msg_add_child = "Demo add child",
  msg_remove_child = "Demo remove child",
  msg_add_infant = "Demo add_infant",
  msg_remove_infant = "Demo remove_infant",
  runmodes,
  msg_multicity = "Demo multicity",
  msg_airline_age_rules = "Demo age rules",
  msg_passengerbox_travellers = "Demo passengers",
  payment_credit_card = "Demo select card",
  payment_dream_miles = "Demo dream miles",
  payment_preference = "Demo select payment",
  payment_select_card = "Demo select card",
  payment_select_miles = "Demo use miles",
  msg_search_by_city_or_airport = "Demo search by city or airport",
  msg_datepicker_previous_month = "Demo previous month",
  msg_datepicker_next_month = "Demo datepicker next month",
  msg_datepicker_departure_date = "Demo departure date",
  msg_datepicker_return_date = "Demo return date",
  msg_advanced_search_page = "Demo advanced search page",
  msg_advanced_search_page_capital = "Demo advanced search",
  msg_passengerbox_one_traveller = "Demo passengerbox one traveller",
  msg_adults = "Demo adults",
  msg_adult = "Demo adult",
  msg_children = "Demo children",
  msg_child = "Demo child",
  msg_infants = "Demo infants",
  msg_infant = "Demo infant",
  msg_children_ages_2_11 = "Demo children ages 2 11",
  msg_infant_ages_0_2 = "Demo infant ages 0 2",
  msg_update = "Demo update",
  msg_search = "Demo search",
  msg_trip_type = "Demo triptype",
  msg_cabin_preference = "Demo cabin",
  msg_notice = "Demo notice",
  msg_close = "Demo close",
  datepicker_months_string = "Demo months_string",
  datepicker_days_string = "Demo days_string",
  promo_code = "Demo promo code",
  add_promo_code = "Demo add promo code",
  promo_code_error = "Demo promo code error",
  title = "Demo title Flights",
  progressiveButtonText,
  isSmallWidget = false,
  hasWidgetUpdates = false,
  hasProgressive = false,
  show_multi_header_message = false,
  ...props
}) {
  const [classNameObject, containerRef] = useContainerQuery(
    CONSTANTS.CONTAINER_QUERIES.CONTAINER
  );

  const [gsOrigin, setgsOrigin] = useState([""]);
  const [gsOriginCode, setgsOriginCode] = useState([""]);
  const [gsDestination, setgsDestination] = useState([""]);
  const [gsDestinationCode, setgsDestinationCode] = useState([""]);
  const [gsDepartureDate, setgsDepartureDate] = useState([]);
  const [gsReturnDate, setgsReturnDate] = useState([]);
  const [gsPromotionCode, setGsPromotionCode] = useState("");
  const [travelers, setTravelers] = useState({
    adults: 1,
    child: [],
    inSeat: 0,
  });
  const [foCabinPreference, setfoCabinPreference] = useState("C");
  const [flights, setFlights] = useState(1);
  const [tripType, setTripType] = useState("Round trip");
  const [authorConfig, setAuthorConfig] = useState(null);
  const [errorFields, setErrorFields] = useState([]);
  const [isProgressive, setIsProgressive] = useState(false);

  const errorHeaderMessage = {
    type: header_error_type,
    message: [`${header_missing_info} ${header_see_fields}`],
  };
  const PROMO_CODE_ID = "PromoCodeId";
  /* //TODO: TRA-3105- Revert this changes in phase-2 launch
  const headerMultiMessage = {
    type: msg_notice,
    message: [
      `${msg_multicity} <a 
        href="${getAdvancedSearchUrl(runmodes)}"
        data-track-id="notice-advance-search" 
        data-track-click="flights-advance-search" 
        data-track-type="internal"
        >${msg_advanced_search_page}</a>.`,
    ],
  }; */
  const headerMultiMessage = {
    type: msg_notice,
    message: [`${msg_multicity}`],
  };
  const [collectorsClusterName, setCollectorsClusterName] = useState(
    CONSTANTS.DEFAULT_CLUSTER
  );

  const defaultCalendarState = {
    startDate: null,
    endDate: null,
    focusedInput: START_DATE,
  };

  const [calendarsStates, setCalendarsStates] = useState([
    defaultCalendarState,
    defaultCalendarState,
    defaultCalendarState,
    defaultCalendarState,
  ]);

  const updateSingleState = (newState, index) => {
    const currentState = [...calendarsStates];
    currentState[index] = newState;
    setCalendarsStates(currentState);
  };

  const [paymentOption, setPaymentOption] = useState(null);

  const { data: autoCompleteData } = useFetch(getFlightsJson);
  const [clustersPackages] = useClusters();
  useProgressiveFields(
    [gsOriginCode, gsDestinationCode, gsDepartureDate, gsReturnDate],
    setIsProgressive
  );

  useEffect(() => {
    getAuthorConfig(json_url).then((json) => {
      setAuthorConfig(json.data);
      setfoCabinPreference(json.data.cabinList?.[0].actualValue);
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

  const oneWay = tripType === "multi" || tripType === "oneway";

  const handleChangeTripType = (value) => {
    const defaultCalendarStates = [...calendarsStates].map(
      () => defaultCalendarState
    );

    setCalendarsStates(defaultCalendarStates);

    if (value === "multi") {
      setFlights(2);
      setgsOrigin([...gsOrigin, ""]);
      setgsDestination([...gsDestination, ""]);
      setTripType(value);
      return;
    }

    setTripType(value);
    setFlights(1);
    setErrorFields([]);
  };

  const nonChronological = (gsDepartureDate, flight) => {
    for (let i = flight; i > 0; i--) {
      // eslint-disable-next-line curly
      if (gsDepartureDate[flight] < gsDepartureDate[i - 1]) return true;
    }
  };

  const isErrors = () => {
    let isErrors = false;
    if (tripType) {
      const errorsPlaceholdersAcum = [];

      for (let i = 0; i < flights; i++) {
        if (/^\s*$/.test(gsOrigin[i])) {
          errorsPlaceholdersAcum.push({
            text: `${msg_flight} ${i + 1}: ${authorConfig.fromLocationText}`,
            inputId: `originInput_flight_${i + 1}`,
            message: msg_empty_field,
          });
          isErrors = true;
        }
        if (/^\s*$/.test(gsDestination[i])) {
          errorsPlaceholdersAcum.push({
            text: `${msg_flight} ${i + 1}: ${authorConfig.toLocationText}`,
            inputId: `destinationInput_flight_${i + 1}`,
            message: msg_empty_field,
          });
          isErrors = true;
        }
        if (!gsDepartureDate[i]) {
          errorsPlaceholdersAcum.push({
            text: `${msg_flight} ${i + 1}: ${
              authorConfig.departureDateText ?? msg_datepicker_departure_date
            }`,
            inputId: `${i}_departure_date_${parentWidget}`,
            message: msg_empty_field,
          });
          isErrors = true;
        }

        if (i > 0 && nonChronological(gsDepartureDate, i)) {
          errorsPlaceholdersAcum.push({
            text: `${msg_flight} ${i + 1}: ${
              authorConfig.departureDateText ?? msg_datepicker_departure_date
            }`,
            inputId: `${i}_departure_date_${parentWidget}`,
            message: msg_chronological_order,
          });
          isErrors = true;
        }

        if (!oneWay && !gsReturnDate[i]) {
          errorsPlaceholdersAcum.push({
            text: `${msg_flight} ${i + 1}: ${
              authorConfig.returnDateText ?? msg_datepicker_return_date
            }`,
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
    }
    return isErrors;
  };

  const buildSearchQuery = () => {
    let packageId = "";
    if (clustersPackages) {
      packageId = getPackageId({
        clustersPackages,
        collectorsClusterName,
        word: "- Air -",
        redemption: paymentOption ? CONSTANTS.MILES : CONSTANTS.CASH,
        tripType:
          tripType === CONSTANTS.TRIP_TYPE_MULTY ||
          tripType === CONSTANTS.TRIP_TYPE_ONEWAY
            ? CONSTANTS.FORMPOST_TRIP_TYPE_ONEWAY
            : CONSTANTS.FORMPOST_TRIP_TYPE_ROUND,
      });
    }

    return makeQueryString({
      gsOrigin: gsOriginCode,
      gsDestination: gsDestinationCode,
      gsDepartureDate,
      gsReturnDate,
      gsNumberOfTravelers: [
        travelers.adults + travelers.child.length + travelers.inSeat,
      ],
      gsAgesObject: getTravelersGsAgesObject(travelers.child, travelers.inSeat),
      gsSourceCode: collectorsClusterName,
      gsvacationtype: packageId,
      foCabinPreference,
      gsPromotionCode,
    });
  };

  const submit = () => {
    if (!isErrors()) {
      window.location.href = getSearchUrl(runmodes) + buildSearchQuery();
    }
  };

  const handleAddFlight = () => {
    setFlights((prev) => prev + 1);
    setgsOrigin([...gsOrigin, ""]);
    setgsDestination([...gsDestination, ""]);
  };

  const errorTotalPax = {
    type: header_error_type,
    message: [msg_to_many_passengers],
  };
  const warningChildren = [
    `${children_travel_alone}, ${unaccompanied_minor_service}`,
  ];

  const toManyChildren = {
    type: header_error_type,
    message: [msg_to_many_children],
  };

  const { xl: isDesktop } = classNameObject;
  const showProgressiveFields = !hasProgressive || isProgressive || !isDesktop;

  if (!authorConfig) {
    // in the future this can be replaced with a loading state
    return <div></div>;
  }

  return (
    <FlightsContext.Provider
      value={{
        classNameObject,
        errorFields,
        gsPromotionCode,
        hasProgressive,
        hasWidgetUpdates,
        isProgressive,
        isSmallWidget,
        add_promo_code,
        promo_code,
        progressiveButtonText,
        runmodes,
        setErrorFields,
        setGsPromotionCode,
        setIsProgressive,
      }}
    >
      <WidgetContainer
        className="flights-widget"
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
          advancedSearchText={
            authorConfig.advSearchText || msg_advanced_search_page_capital
          }
          buildSearchQuery={buildSearchQuery}
          parentWidget={parentWidget}
        >
          <Row parentWidget={parentWidget}>
            <ColSelector parentWidget={parentWidget}>
              <InLineDropdown
                tripType={tripType}
                label={authorConfig.tripTypeHeading || msg_trip_type}
                activeLabel={authorConfig.tripTypeList?.[0].actualValue}
                selectedValueCallBack={(value) => handleChangeTripType(value)}
                options={authorConfig.tripTypeList?.map((t, i) => ({
                  key: t + i,
                  value: t.actualValue,
                  text: t.displayType,
                }))}
              />
            </ColSelector>
            <ColSelector parentWidget={parentWidget}>
              <PassengersBox
                sendTravellers={setTravelers}
                warnings={{
                  errorTotalPax,
                  warningChildren,
                  errorSomeFields: errorHeaderMessage,
                  toManyChildren,
                }}
                updateButtonText={authorConfig.updateButtonText}
                dropdownLabel={msg_passengerbox_travellers}
                labels={{
                  adult: `${authorConfig?.passengerList?.adult || msg_adults}`,
                  child:
                    authorConfig?.passengerList?.child ||
                    msg_children_ages_2_11,
                  infant:
                    authorConfig?.passengerList?.infant || msg_infant_ages_0_2,
                }}
                ariaMessages={{
                  msg_add_adult,
                  msg_remove_adult,
                  msg_add_child,
                  msg_remove_child,
                  msg_add_infant,
                  msg_remove_infant,
                }}
                msg_airline_age_rules={msg_airline_age_rules}
                msg_update={msg_update}
                singularPersonsTitle={msg_passengerbox_one_traveller}
                pluralPersonsTitle={msg_passengerbox_travellers}
                msg_adv_search_text_init={msg_adv_search_text_init}
                msg_adv_search_text_link={msg_adv_search_text_link}
                msg_empty_field={msg_empty_field_age}
                msg_adults={msg_adults}
                msg_adult={msg_adult}
                msg_children={msg_children}
                msg_child={msg_child}
                msg_infant={msg_infant}
                msg_infants={msg_infants}
                msg_close={msg_close}
                parentWidget={parentWidget}
                airline_age_rules_url={authorConfig.linkUrl}
                airline_age_rules_text={authorConfig.linkText}
                runmodes={runmodes}
                {...props}
              />
            </ColSelector>
            <ColSelector parentWidget={parentWidget}>
              <InLineDropdown
                parentWidget={parentWidget}
                label={authorConfig.cabinHeading || msg_cabin_preference}
                activeLabel={authorConfig.cabinList?.[0].actualValue}
                selectedValueCallBack={(value) => setfoCabinPreference(value)}
                options={authorConfig.cabinList?.map((t, i) => ({
                  key: t + i,
                  value: t.actualValue,
                  text: t.displayType,
                }))}
              />
            </ColSelector>
          </Row>
        </WidgetHeader>
        <WidgetRowProgressive
          setIsProgressive={setIsProgressive}
          parentWidget={parentWidget}
        >
          <Flight
            index={0}
            key={0}
            origin={gsOrigin}
            destination={gsDestination}
            setOrigin={setgsOrigin}
            gsOriginCode={gsOriginCode}
            setgsOriginCode={setgsOriginCode}
            setDestination={setgsDestination}
            setgsDestinationCode={setgsDestinationCode}
            setDeparture={setgsDepartureDate}
            setReturn={setgsReturnDate}
            authorConfig={authorConfig}
            json={autoCompleteData}
            tripType={tripType}
            setFlights={setFlights}
            setErrorFields={setErrorFields}
            errorFields={errorFields}
            calendarState={calendarsStates[0]}
            setCalendarState={updateSingleState}
            departureDateLabel={
              authorConfig.departureDateText || msg_datepicker_departure_date
            }
            returnDateLabel={
              authorConfig.returnDateText ?? msg_datepicker_return_date
            }
            msg_search_by_city_or={msg_search_by_city_or_airport}
            msg_datepicker_previous_month={msg_datepicker_previous_month}
            msg_datepicker_next_month={msg_datepicker_next_month}
            msg_empty_field={msg_empty_field}
            msg_datepicker_departure_date={msg_datepicker_departure_date}
            msg_close={msg_close}
            datepicker_months_string={datepicker_months_string}
            datepicker_days_string={datepicker_days_string}
            parentWidget={parentWidget}
            {...props}
          />
        </WidgetRowProgressive>

        {showProgressiveFields && (
          <RadioGroup
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
          />
        )}
        <div className="flights-widget__create-flights-wrapper">
          {tripType === "multi" && flights < 4 && (
            <button
              onClick={handleAddFlight}
              className="flights-widget__create-flights-btn "
            >
              <span className="am-icon am-icon-functional-plus" />
              {add_flight_text}
            </button>
          )}
          {flights >= 4 && <span class="max-warning">{max_number_text}.</span>}
        </div>
        {showProgressiveFields && (
          <WidgetFooter
            advancedSearchText={
              authorConfig.advSearchText || msg_advanced_search_page_capital
            }
            parentWidget={parentWidget}
            searchButtonText={authorConfig.buttonText || msg_search}
            submit={submit}
            buildSearchQuery={buildSearchQuery}
          />
        )}
      </WidgetContainer>
    </FlightsContext.Provider>
  );
}

export default FlightsWidget;
