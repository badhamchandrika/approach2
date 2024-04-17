import React, { useEffect, useState } from "react";
import LocationDropdown from "../ui/LocationDropdown";
import Datepicker from "../flightswidget/Datepicker/components/Datepicker.js";
import { START_DATE } from "../flightswidget/Datepicker/hooks/useDatepickerCustom/useDatepickerCustom.js";
import CheckBox from "../ui/Checkbox/Checkbox.jsx";
import ValidationWrapper from "../ui/ValidationWrapper/ValidationWrapper.jsx";
import { ColField } from "../Widget/ColField.jsx";
import { Row } from "../Widget/Row.jsx";

function CarRent({
  index,
  origin,
  setOrigin,
  setgsOriginCode,
  destination,
  setDestination,
  setgsDestinationCode,
  setDeparture,
  setReturn,
  authorConfig,
  json,
  tripType,
  departureDateLabel,
  returnDateLabel,
  fromInstructionalText,
  toInstructionalText,
  setErrorFields,
  errorFields,
  gsPickUpTime,
  setgsPickUpTime,
  gsDropOffTime,
  setgsDropOffTime,
  timesArr,
  ageCheckbox,
  setAgeCheckbox,
  ageWarningMsg,
  msg_ldd_pickup_location,
  msg_ldd_dropoff_location,
  msg_ldd_pickoff_dropoff,
  msg_timedd_pickup_time,
  msg_timedd_dropoff_time,
  msg_pickup_date,
  msg_dropoff_date,
  msg_search_by_city_or_airport,
  msg_rentals_booked_miles_only,
  msg_empty_field,
  datepicker_months_string,
  datepicker_days_string,
  msg_close,
  parentWidget,
  ...props
}) {
  const oneWay = tripType === "multi" || tripType === "oneway";
  const [showPickUpDropdown, setShowPickUpDropdown] = useState(false);
  const [showDropOffDropdown, setShowDropOffDropdown] = useState(false);

  const [calendarState0, setCalendarState0] = useState({
    startDate: null,
    endDate: null,
    focusedInput: START_DATE,
  });

  const [calendarState1, setCalendarState1] = useState({
    startDate: null,
    endDate: null,
    focusedInput: START_DATE,
  });

  const [pickUpDate, setPickUpDate] = useState();
  const [dropOffDate, setDropOffDate] = useState();

  const agesList = [];

  const getNext30Days = () => {
    if (calendarState0.startDate !== null) {
      const next30Days = new Date(calendarState0.startDate);
      next30Days.setHours(0, 0, 0, 0);
      next30Days.setDate(next30Days.getDate() + 30);
      return next30Days;
    } else {
      return null;
    }
  };

  const next30Days = getNext30Days();

  agesList.push({ key: 25, value: 25, text: `25+` });
  for (let i = 26; i < 110; i++) {
    agesList.push({ key: i, value: i, text: i + "" });
  }

  const handleOnConfirmAge = ({ checked }) => {
    setErrorFields(
      errorFields.filter((obj) => obj.inputId !== "crental-age-checkbox")
    );
    setAgeCheckbox((prev) => ({ ...prev, checked }));
  };

  useEffect(() => {
    if (getNext30Days() < calendarState1.startDate) {
      setCalendarState1({
        startDate: null,
        endDate: null,
        focusedInput: START_DATE,
      });
    }

    if (calendarState1.startDate < calendarState0.startDate) {
      setCalendarState1({
        startDate: null,
        endDate: null,
        focusedInput: START_DATE,
      });
    }
  }, [calendarState0]);

  return (
    <>
      <Row parentWidget={parentWidget}>
        <ColField parentWidget={parentWidget}>
          <LocationDropdown
            index={index}
            value={origin[index]}
            inputId={"originInput_crent_1" + parentWidget}
            placeholder={
              authorConfig?.fromLocationText || msg_ldd_pickoff_dropoff
            }
            json={json}
            toInstructionalText={fromInstructionalText}
            setter={setOrigin}
            setgsCode={setgsOriginCode}
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
            setDeparture={setDeparture}
            setReturn={setReturn}
            setDepartureDate={setPickUpDate}
            index={0}
            rootElementClass={oneWay ? "datepicker__input--oneway" : ""}
            departureDateLabel={departureDateLabel || msg_pickup_date}
            returnDateLabel={returnDateLabel || msg_dropoff_date}
            validationError={true}
            firstDateLabel={msg_pickup_date}
            state={calendarState0}
            setState={setCalendarState0}
            disableAfterDate={
              calendarState0.startDate === null
                ? calendarState1.startDate
                : null
            }
            setErrorFields={setErrorFields}
            errorFields={errorFields}
            datepicker_months_string={datepicker_months_string}
            datepicker_days_string={datepicker_days_string}
            msg_close={msg_close}
            parentWidget={parentWidget}
            {...props}
          />
        </ColField>

        {/* <div className={`location-input__wrapper d-flex col-12 col-md-6 `}>    temporary commented code: later on, pickup time, dropoff time and another location field should be included
          <LocationDropdown
            index={index}
            value={destination[index]}
            inputId="destinationInput_crent_1"
            placeholder={authorConfig?.toLocationText || msg_ldd_dropoff_location}
            json={json}
            toInstructionalText={toInstructionalText}
            setter={setDestination}
            setgsCode={setgsDestinationCode}
            setErrorFields={setErrorFields}
            errorFields={errorFields}
            msg_search_by_city_or_airport={msg_search_by_city_or_airport}
          />
        </div> */}
      </Row>
      {/* <div className="row">
        <div className="col-12 col-md-6 col-xl-3">
          <Datepicker
            setDeparture={setDeparture}
            setReturn={setDeparture}
            setDepartureDate={setPickUpDate}
            index={0}
            oneWay={true}
            rootElementClass={oneWay ? "datepicker__input--oneway" : ""}
            departureDateLabel={departureDateLabel || msg_pickup_date}
            validationError={true}
            firstDateLabel={"Departure date"}
            state={calendarState0}
            setState={setCalendarState0}
            disableAfterDate={
              calendarState0.startDate === null
                ? calendarState1.startDate
                : null
            }
            setErrorFields={setErrorFields}
            errorFields={errorFields}
            parentWidget={parentWidget}
            {...props}
          />
        </div>

        <div className="col-12 col-md-6 col-xl-3">
          <TimeDropdown
            text={msg_timedd_pickup_time}
            pickupTime={gsPickUpTime}
            dropOffTime={gsDropOffTime}
            setgsPickUpTime={setgsPickUpTime}
            setgsDropOffTime={setgsDropOffTime}
            showPickUpDropdown={showPickUpDropdown}
            setShowPickUpDropdown={setShowPickUpDropdown}
            showDropOffDropdown={showDropOffDropdown}
            setShowDropOffDropdown={setShowDropOffDropdown}
            gsDepartureDate={pickUpDate}
            gsReturnDate={dropOffDate}
            timesArr={timesArr}
            msg_timedd_pickup_time={msg_timedd_pickup_time}
            msg_timedd_dropoff_time={msg_timedd_dropoff_time}
            msg_pickup_date={msg_pickup_date}
            msg_dropoff_date={msg_dropoff_date}
          />
        </div>

        <div className="col-12 col-md-6 col-xl-3">
          <Datepicker
            setDeparture={setReturn}
            setReturn={setReturn}
            setDepartureDate={setDropOffDate}
            index={1}
            oneWay={true}
            rootElementClass={oneWay ? "datepicker__input--oneway" : ""}
            departureDateLabel={returnDateLabel || msg_dropoff_date}
            validationError={true}
            firstDateLabel={"Return date"}
            canOverflow
            state={calendarState1}
            setState={setCalendarState1}
            disableBeforeDate={calendarState0.startDate}
            disableAfterDate={next30Days}
            setErrorFields={setErrorFields}
            errorFields={errorFields}
            parentWidget={parentWidget}
            {...props}
          />
        </div>

        <div className="col-12 col-md-6 col-xl-3">
          <TimeDropdown
            isDropOff
            text={msg_timedd_dropoff_time}
            pickupTime={gsPickUpTime}
            dropOffTime={gsDropOffTime}
            setgsPickUpTime={setgsPickUpTime}
            setgsDropOffTime={setgsDropOffTime}
            showPickUpDropdown={showPickUpDropdown}
            setShowPickUpDropdown={setShowPickUpDropdown}
            showDropOffDropdown={showDropOffDropdown}
            setShowDropOffDropdown={setShowDropOffDropdown}
            gsDepartureDate={pickUpDate}
            gsReturnDate={dropOffDate}
            timesArr={timesArr}
          />
        </div>
      </div> */}

      <Row parentWidget={parentWidget}>
        <ValidationWrapper
          validationError={
            errorFields?.find(
              (field) => field?.inputId === "crental-age-checkbox"
            )
              ? { message: ageWarningMsg }
              : null
          }
        >
          <CheckBox
            id="crental-age-checkbox"
            setValue={handleOnConfirmAge}
            label={ageCheckbox.text}
            checked={ageCheckbox.checked}
          />
        </ValidationWrapper>
      </Row>
    </>
  );
}

export default CarRent;
