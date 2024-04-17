import React, { useEffect, useState } from "react";
import CONSTANTS from "../../constants";
import CarRent from "./CarRent.jsx";
import Alert from "../ui/Alert/Alert";
import RadioGroup from "../ui/RadioGroup/RadioGroup";

import { getCollectorId } from "../../helpers/getCollectorId";
import {
  getAuthorConfig,
  getCollectorClusters,
  getRentalsJson,
} from "../../api/services";
import { getPackageId } from "../../helpers/getPackageId";
import makeQueryString from "../../helpers/makeQueryString";
import { getEnvironment } from "../../helpers/getEnvironment";
import { higherPrecedenceCluster } from "../../helpers/higherPrecedenceCluster";
import { getAdvancedSearchUrl, getSearchUrl } from "../../helpers/getURL";
import useClusters from "../../hooks/useClusters";
import useFetch from "../../hooks/useFetch";
import { CarRentalContext } from "./CarRentalContext";
import {
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

const parentWidget = CONSTANTS.WIDGET.RENTALS;

function CarRentalWidget({
  json_url,
  msg_empty_field = "demo empty field",
  header_error_type = "demo error_type",
  header_missing_info = "demo missing_info",
  header_see_fields = "demo see_fields",
  age_warning_message = "demo warning_message",
  msg_ldd_pickup_location = "demo pickup_location",
  msg_ldd_dropoff_location = "demo dropoff_location",
  msg_datepicker_previous_month = "demo previous_month",
  msg_datepicker_next_month = "demo next_month",
  msg_timedd_pickup_time = "demo pickup_time",
  msg_timedd_dropoff_time = "demo dropoff_time",
  msg_pickup_date = "demo pickup_date",
  msg_dropoff_date = "demo dropoff_date",
  msg_search_by_city_or_airport = "demo search_by_city_or",
  msg_renters_age_25 = "demo age 25",
  msg_advanced_search_page = "demo advanced search",
  msg_advanced_search_page_capital = "demo advanced cap",
  msg_search = "demo search",
  msg_rentals_booked_miles_only = "demo miles only",
  msg_rentals_minimun_miles = "demo minimun miles",
  runmodes,
  msg_notice = "demo notice",
  msg_ldd_pickoff_dropoff = "demo pickoff_dropoff",
  msg_return_rental_different = "demo rental_different",
  datepicker_months_string = "demo months",
  datepicker_days_string = "demo days",
  promo_code = "Demo promo code",
  add_promo_code = "Demo add promo code",
  promo_code_error = "Demo promo code error",
  payment_credit_card = "Demo select card",
  payment_dream_miles = "Demo dream miles",
  payment_preference = "Demo select payment",
  payment_select_card = "Demo select card",
  payment_select_miles = "Demo use miles",
  isSmallWidget = false,
  title = "Demo title Car", // TODO: pass this prop by data-prop
  hasWidgetUpdates = false,
  show_multi_header_message = false,
  show_default_header_message = false,
  progressiveButtonText,
  hasPaymentOption = false,
  hasProgressive = false,
  ...props
}) {
  const [classNameObject, containerRef] = useContainerQuery(
    CONSTANTS.CONTAINER_QUERIES.CONTAINER
  );
  const [gsOrigin, setgsOrigin] = useState([""]);
  const [gsOriginCode, setgsOriginCode] = useState([""]);
  const [gsDestination, setgsDestination] = useState([""]);
  const [gsDestinationCode, setgsDestinationCode] = useState([""]);
  const [gsPromotionCode, setGsPromotionCode] = useState("");
  const [gsDepartureDate, setgsDepartureDate] = useState([]);
  const [gsReturnDate, setgsReturnDate] = useState([]);
  const [gsPickUpTime, setgsPickUpTime] = useState(CONSTANTS.TIMES_ARR[12]);
  const [gsDropOffTime, setgsDropOffTime] = useState(CONSTANTS.TIMES_ARR[12]);
  const [authorConfig, setAuthorConfig] = useState(null);
  const [errorFields, setErrorFields] = useState([]);
  const [collectorsClusterName, setCollectorsClusterName] = useState(
    CONSTANTS.DEFAULT_CLUSTER
  );
  const crentals = 1;
  const [ageCheckbox, setAgeCheckbox] = useState({
    id: "checked1",
    checked: false,
    text: msg_renters_age_25,
  });
  const [isProgressive, setIsProgressive] = useState(false);
  const PROMO_CODE_ID = "PromoCodeId";
  const [paymentOption, setPaymentOption] = useState(null);

  const { xl: isDesktop } = classNameObject;
  const showProgressiveFields = !hasProgressive || isProgressive || !isDesktop;

  const errorHeaderMessage = {
    type: header_error_type,
    message: [`${header_missing_info} ${header_see_fields}`],
  };
  const defaultHeaderMultiMessage = {
    type: msg_notice,
    message: [
      `
      <ul>
        <li>
        ${msg_rentals_booked_miles_only} ${msg_rentals_minimun_miles}
        </li>
        <li>
        ${msg_return_rental_different} 
        <a 
          data-track-id="notice-advance-search" 
          data-track-click="rental-advance-search" 
          data-track-type="internal" 
          href="${getAdvancedSearchUrl(runmodes)}">
            ${msg_advanced_search_page}
        </a>.
        </li>
      </ul>  
      `,
    ],
  };

  const customHeaderMultiMessage = {
    type: msg_notice,
    message: [`${msg_rentals_booked_miles_only}`],
  };

  const { data: autoCompleteData } = useFetch(getRentalsJson);
  const [clustersPackages] = useClusters();
  useProgressiveFields(
    [gsOriginCode, gsDepartureDate, gsReturnDate],
    setIsProgressive
  );

  useEffect(() => {
    getAuthorConfig(json_url).then((json) => {
      setAuthorConfig(json.data);
      setAgeCheckbox((obj) => ({
        ...obj,
        text: json.data.renterAgeTitle || msg_renters_age_25,
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

  const isErrors = () => {
    let isErrors = false;
    const errorsPlaceholdersAcum = [];
    for (let i = 0; i < crentals; i++) {
      if (/^\s*$/.test(gsOrigin[i])) {
        errorsPlaceholdersAcum.push({
          text: `${authorConfig.fromLocationText ?? msg_ldd_pickup_location}`,
          inputId: `originInput_crent_${i + 1}_${parentWidget}`,
        });
        isErrors = true;
      }
      // if (/^\s*$/.test(gsDestination[i])) {                    //temporary commented as cars just can be pickup and dropoff in same location
      //   errorsPlaceholdersAcum.push({
      //     text: `${authorConfig.toLocationText ?? msg_ldd_dropoff_location}`,
      //     inputId: `destinationInput_crent_${i + 1}`,
      //   });
      //   isErrors = true;
      // }
      if (!ageCheckbox.checked) {
        errorsPlaceholdersAcum.push({
          text: "Driver Age confirmation",
          inputId: `crental-age-checkbox`,
        });
        isErrors = true;
      }
      if (!gsDepartureDate[i]) {
        errorsPlaceholdersAcum.push({
          text: `${authorConfig.pickUpDateText ?? msg_pickup_date}`,
          inputId: `${i}_departure_date_{parentWidget}`,
          message: msg_empty_field,
        });
        isErrors = true;
      }
      if (!gsReturnDate[i]) {
        errorsPlaceholdersAcum.push({
          text: `${authorConfig.dropOffDateText ?? msg_dropoff_date}`,
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
        word: "- Car -",
        redemption: hasPaymentOption
          ? paymentOption
            ? CONSTANTS.MILES
            : CONSTANTS.CASH
          : undefined,
      });
    }

    const gsReturnDateCopy = [...gsReturnDate];
    // gsReturnDateCopy.splice(0,1);    code needed when rentals can be dropoff in different locations

    return makeQueryString({
      gsDestination: gsOriginCode,
      gsDepartureDate,
      gsReturnDate: gsReturnDateCopy,
      gsPickUpTime,
      gsDropOffTime,
      collectorsClusterName,
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
    <CarRentalContext.Provider
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
        isProgressive,
        hasProgressive,
        progressiveButtonText,
        setGsPromotionCode,
        setIsProgressive,
      }}
    >
      <WidgetContainer
        className="crental-widget"
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
        {show_multi_header_message &&
          (show_default_header_message ? (
            <Alert type="info" errorHeaderMessage={defaultHeaderMultiMessage} />
          ) : (
            <Alert
              type="info"
              errorHeaderMessage={customHeaderMultiMessage}
              singleMessage
            />
          ))}

        <WidgetHeader
          advancedSearchText={
            authorConfig.advSearchText || msg_advanced_search_page_capital
          }
          buildSearchQuery={buildSearchQuery}
          parentWidget={parentWidget}
        />
        <WidgetRowProgressive
          setIsProgressive={setIsProgressive}
          parentWidget={parentWidget}
        >
          <CarRent
            index={0}
            key={0}
            origin={gsOrigin}
            setOrigin={setgsOrigin}
            setgsOriginCode={setgsOriginCode}
            destination={gsDestination}
            setDestination={setgsDestination}
            setgsDestinationCode={setgsDestinationCode}
            setDeparture={setgsDepartureDate}
            setReturn={setgsReturnDate}
            authorConfig={authorConfig}
            departureDateLabel={authorConfig.pickUpDateText}
            returnDateLabel={authorConfig.dropOffDateText ?? msg_dropoff_date}
            json={autoCompleteData}
            tripType="Round trip"
            departureDateText={authorConfig.pickUpDateText}
            returnDateText={authorConfig.dropOffDateText ?? msg_dropoff_date}
            setErrorFields={setErrorFields}
            errorFields={errorFields}
            gsDepartureDate={gsDepartureDate}
            gsReturnDate={gsReturnDate}
            gsPickUpTime={gsPickUpTime}
            setgsPickUpTime={setgsPickUpTime}
            gsDropOffTime={gsDropOffTime}
            setgsDropOffTime={setgsDropOffTime}
            timesArr={CONSTANTS.TIMES_ARR}
            ageCheckbox={ageCheckbox}
            setAgeCheckbox={setAgeCheckbox}
            ageWarningMsg={age_warning_message}
            msg_search_by_city_or_airport={msg_search_by_city_or_airport}
            msg_ldd_pickup_location={msg_ldd_pickup_location}
            msg_ldd_dropoff_location={msg_ldd_dropoff_location}
            msg_ldd_pickoff_dropoff={msg_ldd_pickoff_dropoff}
            msg_datepicker_previous_month={msg_datepicker_previous_month}
            msg_datepicker_next_month={msg_datepicker_next_month}
            msg_timedd_pickup_time={msg_timedd_pickup_time}
            msg_timedd_dropoff_time={msg_timedd_dropoff_time}
            msg_pickup_date={msg_pickup_date}
            msg_dropoff_date={msg_dropoff_date}
            msg_rentals_booked_miles_only={msg_rentals_booked_miles_only}
            msg_empty_field={msg_empty_field}
            datepicker_months_string={datepicker_months_string}
            datepicker_days_string={datepicker_days_string}
            parentWidget={parentWidget}
            {...props}
          />
        </WidgetRowProgressive>

        {showProgressiveFields && (
          <>
            {hasPaymentOption && (
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
    </CarRentalContext.Provider>
  );
}

export default CarRentalWidget;
