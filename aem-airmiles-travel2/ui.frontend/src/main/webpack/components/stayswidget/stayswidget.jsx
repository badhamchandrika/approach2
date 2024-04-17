import React, { useEffect, useState } from "react";

import CONSTANTS from "../../constants";
import RoomBox from "../React/RoomBox/RoomBox.jsx";
import Alert from "../ui/Alert/Alert.jsx";
import RadioGroup from "../ui/RadioGroup/RadioGroup";
import { getCollectorId } from "../../helpers/getCollectorId";
import {
  getAuthorConfig,
  getCollectorClusters,
  getStaysJson,
} from "../../api/services";
import { higherPrecedenceCluster } from "../../helpers/higherPrecedenceCluster";
import { getPackageId } from "../../helpers/getPackageId";
import { makeGsAgesObject } from "../../helpers/makeGsAgesObject";
import makeQueryString from "../../helpers/makeQueryString";
import { getEnvironment } from "../../helpers/getEnvironment";
import { getSearchUrl } from "../../helpers/getURL";
import useClusters from "../../hooks/useClusters";
import useFetch from "../../hooks/useFetch";
import { START_DATE } from "../flightswidget/Datepicker/hooks/useDatepickerCustom/useDatepickerCustom";
import LocationDropdown from "../ui/LocationDropdown";
import Datepicker from "../flightswidget/Datepicker/components/Datepicker";

import { StaysContext } from "./StaysContext";
import {
  ColField,
  Row,
  WidgetContainer,
  WidgetFooter,
  WidgetHeader,
  WidgetRowProgressive,
} from "../Widget";
import {
  CreditCardSvg,
  MilesSvg,
} from "../flightswidget/Datepicker/components/Svgs";
import { useContainerQuery } from "react-container-query";
import useProgressiveFields from "../../hooks/useProgressiveFields.js";

const parentWidget = CONSTANTS.WIDGET.STAYS;

function StaysWidget({
  json_url,
  msg_empty_field = "Demo empty field",
  msg_empty_field_age = "Demo empty age",
  msg_empty_field_check_in = "Demo empty field check in",
  msg_empty_field_check_out = "Demo empty field check out",
  msg_adv_search_text_init = "Demo adv search init",
  msg_adv_search_text_middle = "Demo adv search middle",
  msg_adv_search_text_link = "Demo adv search link",
  promo_code = "Demo promo code",
  add_promo_code = "Demo add promo code",
  promo_code_error = "Demo promo code error",
  header_error_type = "Demo error type",
  header_missing_info = "Demo missing info",
  header_see_fields = "Demo see fields",
  to_instructional_text,
  error_total_pax1 = "Demo error total pax",
  msg_errors_to_continue = "Demo error to continue",
  msg_search_by_city_or_airport = "Demo search by city or airport",
  msg_datepicker_previous_month = "Demo prev month",
  msg_datepicker_next_month = "Demo next month",
  msg_datepicker_check_in = "Demo check in",
  msg_datepicker_check_out = "Demo check out",
  payment_credit_card = "Demo select card",
  payment_dream_miles = "Demo dream miles",
  payment_preference = "Demo select payment",
  payment_select_card = "Demo select card",
  payment_select_miles = "Demo use miles",
  msg_room = "Demo room",
  msg_rooms = "Demo rooms",
  runmodes,
  msg_traveller = "Demo traveller",
  msg_travellers = "Demo travellers",
  msg_add_another_room = "Demo add room",
  msg_maximum_number_rooms = "Demo max room",
  msg_children_ages = "Demo children age",
  msg_remove = "Demo remove",
  msg_advanced_search_page = "Demo avanced search",
  msg_search = "Demo search",
  msg_update = "Demo update",
  datepicker_months_string,
  datepicker_days_string,
  msg_close = "Demo close",
  title = "Demo title Stays",
  progressiveButtonText,
  isSmallWidget = false,
  hasWidgetUpdates = false,
  hasProgressive = false,
  show_multi_header_message = false,
  msg_notice = "notice",
  msg_notification_message = "Demo Notification Message",
  ...props
}) {
  const [classNameObject, containerRef] = useContainerQuery(
    CONSTANTS.CONTAINER_QUERIES.CONTAINER
  );
  const [gsDestination, setgsDestination] = useState([""]);
  const [gsDestinationCode, setgsDestinationCode] = useState([""]);
  const [gsDestinationError, setgsDestinationError] = useState([false]);
  const [gsDepartureDate, setgsDepartureDate] = useState([]);
  const [gsReturnDate, setgsReturnDate] = useState([]);
  const [gsPromotionCode, setGsPromotionCode] = useState("");
  const [stays, setStays] = useState(1);
  const [authorConfig, setAuthorConfig] = useState(null);
  const [rooms, setRooms] = React.useState([
    { id: 1, adults: 1, children: [] },
  ]);
  const [errorFields, setErrorFields] = useState([]);
  const [collectorsClusterName, setCollectorsClusterName] = useState(
    CONSTANTS.DEFAULT_CLUSTER
  );
  const [paymentOption, setPaymentOption] = useState(null);
  const [calendarState, setCalendarState] = useState({
    startDate: null,
    endDate: null,
    focusedInput: START_DATE,
  });
  const headerMultiMessage = {
    type: msg_notice,
    message: [`${msg_notification_message}`],
  };
  const [isProgressive, setIsProgressive] = useState(false);

  const PROMO_CODE_ID = "PromoCodeId";
  const errorHeaderMessage = {
    type: header_error_type,
    message: [`${header_missing_info} ${header_see_fields}`],
  };

  const errorHeaderMessageRooms = {
    type: header_error_type,
    message: [msg_errors_to_continue],
  };

  const errorTotalPax = {
    type: header_error_type,
    message: [error_total_pax1],
  };

  const tripType = "Round trip";
  const oneWay = tripType === "multi" || tripType === "oneway";

  const { xl: isDesktop } = classNameObject;
  const showProgressiveFields = !hasProgressive || isProgressive || !isDesktop;

  const { data: autoCompleteData } = useFetch(getStaysJson);
  const [clustersPackages] = useClusters();
  useProgressiveFields(
    [gsDestinationCode, gsDepartureDate, gsReturnDate],
    setIsProgressive
  );

  const isErrors = () => {
    let isErrors = false;
    const errorsPlaceholdersAcum = [];
    for (let i = 0; i < stays; i++) {
      if (/^\s*$/.test(gsDestination[i])) {
        errorsPlaceholdersAcum.push({
          text: `${authorConfig.toLocationText}`,
          inputId: `destinationInput_stay_${i + 1}`,
          message: msg_empty_field,
        });
        isErrors = true;
      }
      if (!gsDepartureDate[i]) {
        errorsPlaceholdersAcum.push({
          text: `${authorConfig.checkinLabel || msg_datepicker_check_in}`,
          inputId: `${i}_departure_date_${parentWidget}`,
          message: msg_empty_field_check_in,
        });
        isErrors = true;
      }
      if (!gsReturnDate[i]) {
        errorsPlaceholdersAcum.push({
          text: `${authorConfig.checkoutLabel || msg_datepicker_check_out}`,
          inputId: `${i}_return_date`,
          message: msg_empty_field_check_out,
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

  useEffect(() => {
    getAuthorConfig(json_url).then((json) => {
      setAuthorConfig(json.data);
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

  const buildSearchQuery = () => {
    let packageId = "";
    if (clustersPackages) {
      packageId = getPackageId({
        clustersPackages,
        collectorsClusterName,
        word: "- Hotel -",
        redemption: paymentOption ? CONSTANTS.MILES : CONSTANTS.CASH,
      });
    }

    return makeQueryString({
      gsDestination: gsDestinationCode,
      gsDepartureDate,
      gsReturnDate,
      gsNumberOfTravelers: rooms.map(
        (room) => room.adults + room.children.length
      ),
      gsAgesObject: makeGsAgesObject(rooms),
      gsSourceCode: collectorsClusterName,
      gsvacationtype: packageId,
      gsPromotionCode,
    });
  };

  const submit = () => {
    if (!isErrors()) {
      window.location.href = getSearchUrl(runmodes) + buildSearchQuery();
    }
  };

  if (!authorConfig) {
    // in the future this can be replaced with a loading state
    return <div></div>;
  }

  return (
    <StaysContext.Provider
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
        className="stays-widget"
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
          optionsColumnClass="col-md-auto"
          parentWidget={parentWidget}
        >
          <RoomBox
            rooms={rooms}
            setRooms={setRooms}
            warnings={{
              errorTotalPax: errorTotalPax,
              errorSomeFields: errorHeaderMessageRooms,
            }}
            msg_room={msg_room}
            msg_rooms={msg_rooms}
            msg_traveller={msg_traveller}
            msg_travellers={msg_travellers}
            msg_add_another_room={msg_add_another_room}
            msg_maximum_number_rooms={msg_maximum_number_rooms}
            msg_empty_field={msg_empty_field_age}
            msg_children_ages={msg_children_ages}
            msg_remove={msg_remove}
            msg_update={msg_update}
            msg_adv_search_text_init={msg_adv_search_text_init}
            msg_adv_search_text_middle={msg_adv_search_text_middle}
            msg_adv_search_text_link={msg_adv_search_text_link}
            parentWidget={parentWidget}
            runmodes={runmodes}
            {...props}
          />
        </WidgetHeader>

        <WidgetRowProgressive
          setIsProgressive={setIsProgressive}
          parentWidget={parentWidget}
        >
          <Row className={`stays-widget__stay`} parentWidget={parentWidget}>
            <ColField parentWidget={parentWidget}>
              <LocationDropdown
                index={0}
                value={gsDestination[0]}
                inputId="destinationInput_stay_1"
                placeholder={authorConfig.toLocationText}
                json={autoCompleteData}
                toInstructionalText={to_instructional_text}
                setter={setgsDestination}
                setgsCode={setgsDestinationCode}
                validationError={gsDestinationError[0]}
                setErrorFields={setErrorFields}
                errorFields={errorFields}
                msg_search_by_city_or={msg_search_by_city_or_airport}
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
                text={{
                  departureDateText:
                    authorConfig.checkinText || msg_datepicker_check_in,
                  returnDateText:
                    authorConfig.checkoutText || msg_datepicker_check_out,
                }}
                oneWay={oneWay}
                rootElementClass={oneWay ? "datepicker__input--oneway" : ""}
                departureDateLabel={
                  authorConfig.checkinLabel || msg_datepicker_check_in
                }
                returnDateLabel={
                  authorConfig.checkoutLabel || msg_datepicker_check_out
                }
                datePlaceholder1={
                  authorConfig.checkinText || msg_datepicker_check_in
                }
                datePlaceholder2={
                  authorConfig.checkoutText || msg_datepicker_check_out
                }
                validationError={true}
                firstDateLabel={msg_datepicker_check_in}
                state={calendarState}
                setState={setCalendarState}
                canOverflow
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
              parentWidget={parentWidget}
              searchButtonText={authorConfig.buttonText || msg_search}
              submit={submit}
              buildSearchQuery={buildSearchQuery}
            />
          </>
        )}
      </WidgetContainer>
    </StaysContext.Provider>
  );
}

export default StaysWidget;
