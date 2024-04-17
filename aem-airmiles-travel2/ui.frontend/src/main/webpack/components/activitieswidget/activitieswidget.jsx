import React, { useEffect, useState } from "react";
import CONSTANTS from "../../constants.js";
import sample from "./sampleconfig.js";
import Datepicker from "../flightswidget/Datepicker/components/Datepicker.js";
import Alert from "../ui/Alert/Alert.jsx";
import LocationDropdown from "../ui/LocationDropdown";
import PassengersBox from "../flightswidget/PassengersBox/PassengersBox.jsx";
import addDays from "date-fns/add_days";
import {
  getActivitiesJson,
  getAuthorConfig,
  getCollectorClusters,
} from "../../api/services.js";
import RadioGroup from "../ui/RadioGroup/RadioGroup.jsx";
import { getCollectorId } from "../../helpers/getCollectorId.js";
import { higherPrecedenceCluster } from "../../helpers/higherPrecedenceCluster.js";
import { getPackageId } from "../../helpers/getPackageId.js";
import { START_DATE } from "../flightswidget/Datepicker/hooks/useDatepickerCustom/useDatepickerCustom.js";
import { getEnvironment } from "../../helpers/getEnvironment.js";
import makeQueryString from "../../helpers/makeQueryString.js";
import { getSearchUrl } from "../../helpers/getURL.js";
import useClusters from "../../hooks/useClusters.js";
import useFetch from "../../hooks/useFetch.js";
import { ActivitiesContext } from "./ActivitiesContext.jsx";
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
} from "../flightswidget/Datepicker/components/Svgs.js";
import { useContainerQuery } from "react-container-query";
import useProgressiveFields from "../../hooks/useProgressiveFields.js";

const parentWidget = CONSTANTS.WIDGET.ACTIVITIES;

function ActivitiesWidget({
  json_url,
  msg_adv_search_text_init = "demo adv_search",
  msg_adv_search_text_middle = "demo adv_search_text",
  msg_adv_search_text_link = "demo adv_link",
  msg_empty_field = "demo empty_field",
  msg_empty_field_age = "demo empty_field_age",
  msg_notice = "demo notice",
  msg_disney_booking = "demo disney_booking",
  show_multi_header_message = false,
  header_error_type = "demo error_type",
  header_missing_info = "demo missing_info",
  header_see_fields = "demo see_fields",
  child_under_eight = "demo under_eight",
  child_under_eight_eleven = "demo under_eight",
  msg_to_many_passengers = "demo to_many_pass",
  msg_adults = "demo adults",
  msg_adult = "demo adult",
  msg_children = "demo children",
  msg_child = "demo child",
  msg_age = "demo age",
  msg_infants = "demo infants",
  msg_infant = "demo infant",
  msg_add_adult = "demo add_adult",
  msg_remove_adult = "demo remove_adult",
  msg_add_child = "demo add_child",
  msg_remove_child = "demo remove_child",
  msg_add_infant = "demo add_infant",
  msg_remove_infant = "demo remove_infant",
  msg_search_by_city_hotel_or_airport = "demo search_by_city_hotel",
  msg_passengerbox_one_passenger = "demo passenger_one",
  msg_passengerbox_one_traveller = "demo 1_traveller",
  msg_passengerbox_travellers = "demo travellers",
  msg_children_ages = "demo children_ages",
  msg_infant_ages_0_2 = "demo infant_ages_0_2",
  msg_datepicker_start_date = "demo start_date",
  msg_advanced_search_page = "demo advanced_search_page",
  msg_search = "demo search",
  payment_credit_card = "Demo select card",
  payment_dream_miles = "Demo dream miles",
  payment_preference = "Demo select payment",
  payment_select_card = "Demo select card",
  payment_select_miles = "Demo use miles",
  msg_update = "demo update",
  msg_location = "demo location",
  datepicker_months_string = "demo months",
  datepicker_days_string = "demo days",
  msg_close = "demo close",
  promo_code = "Demo promo code",
  add_promo_code = "Demo add code",
  promo_code_error = "Demo promo error",
  runmodes,
  isSmallWidget = false,
  title = "Demo title Activities", // TODO: pass this prop by data-prop
  hasWidgetUpdates = false,
  progressiveButtonText,
  hasProgressive = false,
  ...props
}) {
  const [classNameObject, containerRef] = useContainerQuery(
    CONSTANTS.CONTAINER_QUERIES.CONTAINER
  );
  const [gsDestination, setgsDestination] = useState([""]);
  const [gsDestinationCode, setgsDestinationCode] = useState([""]);
  const [mobileModalisOpen, setMobileModalisOpen] = useState(false);
  const [gsDepartureDate, setgsDepartureDate] = useState([]);
  const [gsReturnDate, setgsReturnDate] = useState([]);
  const [gsPromotionCode, setGsPromotionCode] = useState("");
  const [authorConfig, setAuthorConfig] = useState(sample);
  const [errorFields, setErrorFields] = useState([]);
  const [collectorsClusterName, setCollectorsClusterName] = useState(
    CONSTANTS.DEFAULT_CLUSTER
  );
  const [travelers, setTravelers] = useState({
    adults: 1,
    child: [],
    inSeat: 0,
  });
  const [paymentOption, setPaymentOption] = useState(null);
  const [isProgressive, setIsProgressive] = useState(false);

  const { xl: isDesktop } = classNameObject;
  const showProgressiveFields = !hasProgressive || isProgressive || !isDesktop;

  const [calendarState, setCalendarState] = useState({
    startDate: null,
    endDate: null,
    focusedInput: START_DATE,
  });
  const PROMO_CODE_ID = "PromoCodeId";

  const errorHeaderMessage = {
    type: header_error_type,
    message: [`${header_missing_info} ${header_see_fields}`],
  };

  const headerMultiMessage = {
    type: msg_notice,
    message: [`${msg_disney_booking}`],
  };

  const errorTotalPax = {
    type: header_error_type,
    message: [msg_to_many_passengers],
  };

  const warnings = {
    errorSomeFields: errorHeaderMessage,
    errorTotalPax,
    warningChildren: [`${child_under_eight}. ${child_under_eight_eleven}.`],
  };

  const { data: autoCompleteData } = useFetch(getActivitiesJson);
  const [clustersPackages] = useClusters();
  useProgressiveFields(
    [gsDestinationCode, gsDepartureDate, gsReturnDate],
    setIsProgressive
  );

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

  const isErrors = () => {
    let isErrors = false;
    const errorsPlaceholdersAcum = [];
    if (/^\s*$/.test(gsDestination[0])) {
      errorsPlaceholdersAcum.push({
        text: `${authorConfig.goingToPlaceholder || msg_location}`,
        inputId: `destinationInput`,
      });
      isErrors = true;
    }
    if (!gsDepartureDate[0]) {
      errorsPlaceholdersAcum.push({
        text: `${
          authorConfig.departurePlaceholder || msg_datepicker_start_date
        }`,
        inputId: `${0}_departure_date_${parentWidget}`,
        message: msg_empty_field,
      });
      isErrors = true;
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

  const setDeparture = (date) => {
    function padTo2Digits(num) {
      return num?.toString().padStart(2, "0");
    }

    const returnDate = addDays(date, 1);
    const formattedReturnDate = [
      padTo2Digits(returnDate?.getMonth() + 1),
      padTo2Digits(returnDate?.getDate()),
      date?.getFullYear(),
    ].join("/");

    setgsReturnDate([formattedReturnDate]);
  };

  const buildSearchQuery = () => {
    let packageId = "";
    if (clustersPackages) {
      packageId = getPackageId({
        clustersPackages,
        collectorsClusterName,
        word: "- Feature -",
        redemption: paymentOption ? CONSTANTS.MILES : CONSTANTS.CASH,
      });
    }

    return makeQueryString({
      gsDestination: gsDestinationCode,
      gsDepartureDate,
      gsNumberOfTravelers: [travelers.adults + travelers.child.length],
      gsAgesObject: travelers.child.reduce(
        (acum, age, index) => ({ ...acum, [`gsAge${index + 1}`]: age }),
        {}
      ),
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

  return (
    <ActivitiesContext.Provider
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
        isProgressive,
        setIsProgressive,
        hasProgressive,
        progressiveButtonText,
      }}
    >
      <WidgetContainer
        className="activities-widget"
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
          <Alert type="info" errorHeaderMessage={headerMultiMessage} />
        )}
        <WidgetHeader
          advancedSearchText={
            authorConfig.advanceSearchText || msg_advanced_search_page
          }
          buildSearchQuery={buildSearchQuery}
          optionsColumnClass="col-md-auto"
          parentWidget={parentWidget}
        >
          <PassengersBox
            sendTravellers={setTravelers}
            updateButtonText="Update"
            warnings={warnings}
            labels={{
              adult: `${msg_adults}`,
              child: msg_children_ages,
              infant: msg_infant_ages_0_2,
            }}
            showAirlineInfo={false}
            dropdownLabel={
              authorConfig.personsTitle ?? msg_passengerbox_travellers
            }
            ariaMessages={{
              msg_add_adult,
              msg_remove_adult,
              msg_add_child,
              msg_remove_child,
              msg_add_infant,
              msg_remove_infant,
            }}
            singularPersonsTitle={msg_passengerbox_one_traveller}
            pluralPersonsTitle={msg_passengerbox_travellers}
            msg_update={msg_update}
            msg_passengerbox_one_passenger={msg_passengerbox_one_passenger}
            msg_adv_search_text_init={msg_adv_search_text_init}
            msg_adv_search_text_middle={msg_adv_search_text_middle}
            msg_adv_search_text_link={msg_adv_search_text_link}
            msg_empty_field={msg_empty_field_age}
            msg_adults={msg_adults}
            msg_adult={msg_adult}
            msg_children={msg_children}
            msg_child={msg_child}
            msg_infant={msg_infant}
            msg_infants={msg_infants}
            msg_age={msg_age}
            runmodes={runmodes}
            parentWidget={parentWidget}
            msg_close={msg_close}
            {...props}
          />
        </WidgetHeader>

        <WidgetRowProgressive
          setIsProgressive={setIsProgressive}
          parentWidget={parentWidget}
        >
          <Row parentWidget={parentWidget}>
            <ColField parentWidget={parentWidget}>
              <LocationDropdown
                value={gsDestination[0]}
                placeholder={authorConfig.goingToPlaceholder || msg_location}
                setter={setgsDestination}
                setgsCode={setgsDestinationCode}
                mobileModalState={{
                  value: mobileModalisOpen,
                  set: setMobileModalisOpen,
                }}
                inputId="destinationInput"
                index={0}
                json={autoCompleteData}
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
            <ColField parentWidget={parentWidget}>
              <Datepicker
                setDepartureDate={setDeparture}
                setDeparture={setgsDepartureDate}
                setReturn={setgsDepartureDate}
                index={0}
                oneWay={true}
                validationError={false}
                departureDateLabel={
                  authorConfig.departurePlaceholder || msg_datepicker_start_date
                }
                firstDateLabel={
                  authorConfig.departurePlaceholder || msg_datepicker_start_date
                }
                state={calendarState}
                setState={setCalendarState}
                canOverflow
                setErrorFields={setErrorFields}
                errorFields={errorFields}
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
                authorConfig.advanceSearchText || msg_advanced_search_page
              }
              parentWidget={parentWidget}
              searchButtonText={authorConfig.searchButtonText || msg_search}
              submit={submit}
            />
          </>
        )}
      </WidgetContainer>
    </ActivitiesContext.Provider>
  );
}

export default ActivitiesWidget;
