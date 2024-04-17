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
import {
  ColField,
  ColSelector,
  Row,
  WidgetContainer,
  WidgetFooter,
  WidgetHeader,
  WidgetRowProgressive,
} from "../Widget";
import { BundlingContext } from "./BundlingContext";
import {
  CreditCardSvg,
  MilesSvg,
} from "../flightswidget/Datepicker/components/Svgs";
import { useContainerQuery } from "react-container-query";
import useProgressiveFields from "../../hooks/useProgressiveFields";

const { BUNDLING: parentWidget, FLIGHTS, RENTALS, STAYS } = CONSTANTS.WIDGET;

function BundlingWidget({
  json_url = "json_url",
  accommodationCheckboxLabel = "add an accommodation",
  age_confirmation = "confirmation",
  age_requirement_text = "requirement_text",
  age_warning_message = "warning_message",
  bundlingProductsHeading = "bundle and save",
  child_under_eight = "under_eight",
  child_under_eight_eleven = "under_eight_eleven",
  datepicker_days_string = "picker_days_string",
  datepicker_months_string = "picker_months_string",
  flightCheckboxLabel = "add a flight",
  hasProgressive = false,
  hasWidgetUpdates = false,
  header_error_type = "er_error_type",
  header_missing_info = "missing_info",
  header_see_fields = "see_fields",
  isSmallWidget = false,
  msg_add_adult = "add_adult",
  msg_add_another_room = "add_another_room",
  msg_add_child = "add_child",
  msg_add_infant = "add_infant",
  add_promo_code = "Demo add promo code",
  msg_adult = "adult",
  msg_adults = "adults",
  msg_adv_search_text_init = "adv_search_text_init",
  msg_adv_search_text_link = "adv_search_text_link",
  msg_adv_search_text_middle = "adv_middle",
  msg_advanced_search_page = "advanced_search_page",
  msg_airline_age_rules = "airline_age_rules",
  msg_cabin_preference = "cabin_preference",
  msg_car_rentals = "car_rentals",
  msg_children = "children",
  msg_children_ages = "children_ages",
  msg_children_ages_2_11 = "children_ages_2_11",
  msg_close = "close",
  msg_datepicker_check_in = "check_in",
  msg_datepicker_check_out = "check_out",
  msg_datepicker_departure_date = "departure_date",
  msg_datepicker_next_month = "next_month",
  msg_datepicker_previous_month = "previous_month",
  msg_datepicker_return_date = "return_date",
  msg_empty_field = "empty_field",
  msg_empty_field_age = "empty_field_age",
  msg_from = "from",
  msg_going_to = "going_to",
  msg_infant = "infant",
  msg_infant_ages_0_2 = "infant_ages_0_2",
  msg_infants = "infants",
  msg_maximum_number_rooms = "maximum_number_rooms",
  msg_notice = "notice",
  msg_notification_message = "Demo Notification Message",
  msg_passengerbox_one_traveller = "one_traveller",
  msg_passengerbox_travellers = "travellers",
  promo_code = "Demo promo code",
  promo_code_error = "Demo promo code error",
  msg_remove = "remove",
  msg_remove_adult = "remove_adult",
  msg_remove_child = "remove_child",
  msg_remove_infant = "remove_infant",
  msg_required_field = "required_field",
  msg_room = "room",
  msg_rooms = "rooms",
  msg_search = "search",
  msg_search_by_city_hotel_or_airport = "search_by_city_hotel_or_airport",
  msg_to = "to",
  msg_to_many_children = "to_many_children",
  msg_to_many_passengers = "to_many_passengers",
  msg_traveller = "traveller",
  msg_travellers = "travellers",
  msg_update = "update",
  payment_credit_card = "Demo select card",
  payment_dream_miles = "Demo dream miles",
  payment_preference = "Demo select payment",
  payment_select_card = "Demo select card",
  payment_select_miles = "Demo use miles",
  productBundling,
  progressiveButtonText,
  runmodes = "odes",
  show_multi_header_message = false,
  title = "Demo title Package", // TODO: pass this prop by data-prop
  triptypeDropdownLabel,
  ...props
}) {
  const [classNameObject, containerRef] = useContainerQuery(
    CONSTANTS.CONTAINER_QUERIES.CONTAINER
  );

  const { data: autoCompleteData } = useFetch(getPackagesJson);

  const [clustersPackages] = useClusters();

  const [gsOrigin, setgsOrigin] = useState([""]);
  const [gsOriginCode, setgsOriginCode] = useState([""]);
  const [gsDestination, setgsDestination] = useState([""]);
  const [gsDestinationCode, setgsDestinationCode] = useState([""]);
  const [gsDepartureDate, setgsDepartureDate] = useState([]);
  const [gsReturnDate, setgsReturnDate] = useState([]);
  const [gsPromotionCode, setGsPromotionCode] = useState("");
  const [authorConfig, setAuthorConfig] = useState(null);
  const [errorFields, setErrorFields] = useState([]);
  const [collectorsClusterName, setCollectorsClusterName] = useState(
    CONSTANTS.DEFAULT_CLUSTER
  );
  const [paymentOption, setPaymentOption] = useState(null);
  const [tripType, setTripType] = useState("Round trip");

  const [rooms, setRooms] = React.useState([
    { id: 1, adults: 1, children: [] },
  ]);
  const [travelers, setTravelers] = useState({
    adults: 1,
    child: [],
    inSeat: 0,
  });

  const [foCabinPreference, setfoCabinPreference] = useState(
    CONSTANTS.CABIN_TYPE_ECONOMY
  );

  const [productCheckboxes, setProductCheckboxes] = useState({
    [FLIGHTS]: {
      id: FLIGHTS,
      checked: false,
      text: flightCheckboxLabel,
    },
    [STAYS]: {
      id: STAYS,
      checked: false,
      text: accommodationCheckboxLabel,
    },
    [RENTALS]: {
      id: RENTALS,
      checked: false,
      text: msg_car_rentals,
    },
  });

  const [ageCheckbox, setAgeCheckbox] = useState({
    id: "checked4",
    checked: false,
    text: age_requirement_text,
  });

  const [fromLocationText, setFromLocationText] = useState(msg_from);
  const [toLocationText, setToLocationText] = useState(msg_to);

  const defaultCalendarState = {
    startDate: null,
    endDate: null,
    focusedInput: START_DATE,
  };

  const [calendarsState, setCalendarsState] = useState(defaultCalendarState);

  const [isProgressive, setIsProgressive] = useState(false);

  const showProgressiveFields = !hasProgressive || isProgressive;

  const crentals = 1;

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

  const errorHeaderMessage = {
    type: header_error_type,
    message: [`${header_missing_info} ${header_see_fields}`],
  };

  const toManyChildren = {
    type: header_error_type,
    message: [msg_to_many_children],
  };

  const handleOnConfirmAge = ({ checked }) => {
    setErrorFields(errorFields.filter((obj) => obj.inputId !== "ageCheckbox"));
    setAgeCheckbox((prev) => ({ ...prev, checked }));
  };

  const handleChangeTripType = (value) => {
    setCalendarsState(defaultCalendarState);
    setTripType(value);
    setErrorFields([]);
  };

  const handleCheckboxProduct = (checkboxId, checked) => {
    setProductCheckboxes((prev) => ({
      ...prev,
      [checkboxId]: { ...prev[checkboxId], checked },
    }));
    setIsProgressive(false);
    setCalendarsState(defaultCalendarState);
  };

  const isProductBundling = (productId) => productId === productBundling;

  const departureDateLabel = () =>
    productCheckboxes[FLIGHTS].checked
      ? authorConfig.departureDateText || msg_datepicker_departure_date
      : msg_datepicker_check_in;

  const returnDateLabel = () =>
    productCheckboxes[FLIGHTS].checked
      ? authorConfig.returnDateText || msg_datepicker_return_date
      : msg_datepicker_check_out;

  const flighOrRentals =
    productCheckboxes[FLIGHTS].checked || productCheckboxes[RENTALS].checked;

  const stays =
    productCheckboxes[STAYS].checked &&
    !productCheckboxes[RENTALS].checked &&
    !productCheckboxes[FLIGHTS].checked;

  const staysRentals =
    productCheckboxes[STAYS].checked &&
    productCheckboxes[RENTALS].checked &&
    !productCheckboxes[FLIGHTS].checked;

  const flightsStays =
    productCheckboxes[FLIGHTS].checked &&
    productCheckboxes[STAYS].checked &&
    !productCheckboxes[RENTALS].checked;

  const flightsRentals =
    productCheckboxes[FLIGHTS].checked &&
    productCheckboxes[RENTALS].checked &&
    !productCheckboxes[STAYS].checked;

  const allInclusive =
    productCheckboxes[FLIGHTS].checked &&
    productCheckboxes[RENTALS].checked &&
    productCheckboxes[STAYS].checked;

  const showAddRoom =
    isProductBundling(STAYS) ||
    (isProductBundling(FLIGHTS) && !productCheckboxes[STAYS].checked);

  const getRegex = () => {
    if (flightsStays) {
      return /^(?=.*Air.*Hotel)(?!.*Car).*$/;
    } else if (flightsRentals) {
      return /^(?=.*Air.*Car)(?!.*Hotel).*$/;
    } else if (staysRentals) {
      return /^(?=.*Hotel.*Car)(?!.*Air).*$/;
    } else if (stays) {
      return /^(?=.*Hotel)(?!.*Air.*Car).*$/;
    } else if (allInclusive) {
      return /^(?=.*Air.*Hotel.*Car).*$/;
    } else {
      return /^(?=.*Air)(?!.*Hotel.*Car).*$/;
    }
  };

  useEffect(() => {
    getAuthorConfig(json_url).then(({ data }) => {
      setAuthorConfig({
        ...data,
      });

      setfoCabinPreference(data.cabinList?.[0].actualValue);

      setProductCheckboxes((prev) => ({
        ...prev,
        [FLIGHTS]: {
          ...prev[FLIGHTS],
          text: data.flightsCheckboxText || flightCheckboxLabel,
        },
        [STAYS]: {
          ...prev[STAYS],
          text: data.hotelsCheckboxText || accommodationCheckboxLabel,
        },
        [RENTALS]: {
          ...prev[RENTALS],
          text: data.carsCheckboxText || msg_car_rentals,
        },
      }));
      setFromLocationText(data.fromLocationText || fromLocationText);
      setToLocationText(data.toLocationText || toLocationText);
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

    // Set initial bundling from author
    if (isProductBundling(FLIGHTS) || isProductBundling(STAYS)) {
      setProductCheckboxes((prev) => {
        return {
          ...prev,
          [productBundling]: {
            ...prev[productBundling],
            checked: true,
          },
        };
      });
    }
  }, []);

  useEffect(() => {
    productCheckboxes[STAYS].checked && productCheckboxes[RENTALS].checked
      ? setgsOriginCode(["dummy"])
      : setgsOriginCode([""]);
  }, [productCheckboxes[STAYS].checked, productCheckboxes[RENTALS].checked]);

  useEffect(() => {
    setErrorFields([]);
  }, [
    productCheckboxes[FLIGHTS].checked,
    productCheckboxes[STAYS].checked,
    productCheckboxes[RENTALS].checked,
  ]);

  const isErrors = () => {
    let isErrors = false;
    const errorsPlaceholdersAcum = [];
    for (let i = 0; i < crentals; i++) {
      if (flighOrRentals && isBlankStr(gsOrigin[i])) {
        errorsPlaceholdersAcum.push({
          text: `${authorConfig.fromLocationText || msg_from}`,
          inputId: `originInput_crent_${i + 1}`,
        });
        isErrors = true;
      }
      if (isBlankStr(gsDestination[i])) {
        errorsPlaceholdersAcum.push({
          text: `${authorConfig.toLocationText || msg_to}`,
          inputId: `destinationInput_crent_${i + 1}`,
        });
        isErrors = true;
      }
      if (productCheckboxes[RENTALS].checked && !ageCheckbox.checked) {
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
        inputId: CONSTANTS.PROMO_CODE_ID,
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
        redemption: paymentOption ? CONSTANTS.MILES : CONSTANTS.CASH,
      });
    }

    return makeQueryString({
      gsOrigin: productCheckboxes[FLIGHTS].checked ? gsOriginCode : null,
      gsDestination: gsDestinationCode,
      gsDepartureDate,
      gsReturnDate,
      gsNumberOfTravelers: productCheckboxes[STAYS].checked
        ? rooms.map((room) => room.adults + room.children.length)
        : [travelers.adults + travelers.child.length + travelers.inSeat],
      gsAgesObject: productCheckboxes[STAYS].checked
        ? makeGsAgesObject(rooms)
        : getTravelersGsAgesObject(travelers.child, travelers.inSeat),
      gsSourceCode: collectorsClusterName,
      gsvacationtype: packageId,
      foCabinPreference:
        productCheckboxes[FLIGHTS].checked && !isProductBundling(STAYS)
          ? foCabinPreference
          : CONSTANTS.CABIN_TYPE_ECONOMY,
      gsPromotionCode,
    });
  };

  const submit = () => {
    if (!isErrors()) {
      window.location.href = getSearchUrl(runmodes) + buildSearchQuery();
    }
  };

  useProgressiveFields(
    [gsOriginCode, gsDestinationCode, gsDepartureDate, gsReturnDate],
    setIsProgressive
  );

  if (!authorConfig) {
    // in the future this can be replaced with a loading state
    return <div></div>;
  }

  return (
    <BundlingContext.Provider
      value={{
        bundlingProductsHeading,
        classNameObject,
        errorFields,
        gsPromotionCode,
        handleCheckboxProduct,
        hasProgressive,
        hasWidgetUpdates,
        isProductBundling,
        isProgressive,
        isSmallWidget,
        add_promo_code,
        promo_code_error,
        promo_code,
        productCheckboxes,
        progressiveButtonText,
        runmodes,
        setErrorFields,
        setGsPromotionCode,
        setIsProgressive,
      }}
    >
      <WidgetContainer
        className="bundling-widget"
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
            authorConfig.advSearchText || msg_advanced_search_page
          }
          buildSearchQuery={buildSearchQuery}
          parentWidget={parentWidget}
        >
          <Row parentWidget={parentWidget}>
            {productCheckboxes[FLIGHTS].checked &&
              !isProductBundling(STAYS) && (
                <ColSelector parentWidget={parentWidget}>
                  <InLineDropdown
                    parentWidget={parentWidget}
                    tripType={tripType}
                    label={
                      authorConfig.tripTypeHeading || triptypeDropdownLabel
                    }
                    activeLabel={authorConfig.tripTypeList?.[0].actualValue}
                    selectedValueCallBack={(value) =>
                      handleChangeTripType(value)
                    }
                    options={authorConfig.tripTypeList?.map((t, i) => ({
                      key: t + i,
                      value: t.actualValue,
                      text: t.displayType,
                    }))}
                  />
                </ColSelector>
              )}
            <ColSelector parentWidget={parentWidget}>
              {isProductBundling(STAYS) ? (
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
                    !staysRentals && msg_adv_search_text_init
                  }
                  msg_adv_search_text_middle={
                    !staysRentals && msg_adv_search_text_middle
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
                  showAddRoom={showAddRoom}
                  {...props}
                />
              ) : (
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
              )}
            </ColSelector>

            {productCheckboxes[FLIGHTS].checked &&
              !isProductBundling(STAYS) && (
                <ColSelector parentWidget={parentWidget}>
                  <InLineDropdown
                    parentWidget={parentWidget}
                    label={authorConfig.cabinHeading || msg_cabin_preference}
                    activeLabel={authorConfig.cabinList?.[0].actualValue}
                    selectedValueCallBack={(value) =>
                      setfoCabinPreference(value)
                    }
                    options={authorConfig.cabinList?.map((t, i) => ({
                      key: t.displayType + i,
                      value: t.actualValue,
                      text: t.displayType,
                    }))}
                  />
                </ColSelector>
              )}
          </Row>
        </WidgetHeader>
        <WidgetRowProgressive
          setIsProgressive={setIsProgressive}
          parentWidget={parentWidget}
        >
          <Row parentWidget={parentWidget}>
            {productCheckboxes[FLIGHTS].checked &&
              !isProductBundling(STAYS) && (
                <ColField parentWidget={parentWidget}>
                  <LocationDropdown
                    index={0}
                    value={gsOrigin[0]}
                    inputId="originInput_crent_1"
                    placeholder={fromLocationText}
                    json={autoCompleteData}
                    toInstructionalText={authorConfig.fromInstructionalText}
                    setter={setgsOrigin}
                    setgsCode={setgsOriginCode}
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
                state={calendarsState}
                setState={setCalendarsState}
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
        </WidgetRowProgressive>
        {showProgressiveFields && (
          <>
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
            <WidgetFooter
              advancedSearchText={
                authorConfig.advSearchText || msg_advanced_search_page
              }
              buildSearchQuery={buildSearchQuery}
              parentWidget={parentWidget}
              searchButtonText={authorConfig.buttonText || msg_search}
              submit={submit}
            >
              <Row
                parentWidget={parentWidget}
                className="widget-footer__fields"
              >
                {productCheckboxes[RENTALS].checked && (
                  <ColField parentWidget={parentWidget}>
                    <ValidationWrapper
                      bottomElement
                      parentWidget={parentWidget}
                      validationError={
                        errorFields?.find(
                          (field) => field?.inputId === "ageCheckbox"
                        )
                          ? { message: age_warning_message }
                          : null
                      }
                    >
                      <CheckBox
                        checked={ageCheckbox.checked}
                        id={"ageCheckbox"}
                        label={ageCheckbox.text}
                        parentWidget={parentWidget}
                        setValue={handleOnConfirmAge}
                      />
                    </ValidationWrapper>
                  </ColField>
                )}
                {productCheckboxes[FLIGHTS].checked &&
                  isProductBundling(STAYS) && (
                    <ColField
                      parentWidget={parentWidget}
                      className="col-field--location"
                    >
                      <LocationDropdown
                        bottomElement
                        errorFields={errorFields}
                        index={0}
                        inputId="originInput_crent_1"
                        json={autoCompleteData}
                        msg_close={msg_close}
                        msg_empty_field={msg_empty_field}
                        msg_search_by_city_hotel_or_airport={
                          msg_search_by_city_hotel_or_airport
                        }
                        parentWidget={parentWidget}
                        placeholder={fromLocationText}
                        setErrorFields={setErrorFields}
                        setgsCode={setgsOriginCode}
                        setter={setgsOrigin}
                        toInstructionalText={authorConfig.fromInstructionalText}
                        value={gsOrigin[0]}
                      />
                    </ColField>
                  )}
              </Row>
            </WidgetFooter>
          </>
        )}
      </WidgetContainer>
    </BundlingContext.Provider>
  );
}

export default BundlingWidget;

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
