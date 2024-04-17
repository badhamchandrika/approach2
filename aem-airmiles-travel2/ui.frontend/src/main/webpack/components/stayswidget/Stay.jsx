import React, { useState } from "react";
import LocationDropdown from "../ui/LocationDropdown";
import Datepicker from "../flightswidget/Datepicker/components/Datepicker.js";
import { START_DATE } from "../flightswidget/Datepicker/hooks/useDatepickerCustom/useDatepickerCustom.js";

function Stay({
  index,
  destination,
  destinationError,
  setDestination,
  setgsDestinationCode,
  setDeparture,
  setReturn,
  authorConfig,
  json,
  tripType,
  departureDateText,
  returnDateText,
  departureDateLabel,
  returnDateLabel,
  datePlaceholder1,
  datePlaceholder2,
  toInstructionalText,
  setErrorFields,
  errorFields,
  msg_search_by_city_or_airport,
  msg_datepicker_previous_month,
  msg_datepicker_next_month,
  msg_empty_field,
  msg_datepicker_check_in,
  datepicker_months_string,
  datepicker_days_string,
  msg_close,
  ...props
}) {
  const [calendarState, setCalendarState] = useState({
    startDate: null,
    endDate: null,
    focusedInput: START_DATE,
  });

  const parentWidget = "stays";
  const oneWay = tripType === "multi" || tripType === "oneway";
  return (
    <div
      className={`d-flex justify-content-start`}
      key={`__LOCATION__DROPDOWN__${index}`}
    >
      <div>
        <div className={`location-input__wrapper d-flex `}>
          <LocationDropdown
            index={index}
            value={destination[index]}
            inputId="destinationInput_stay_1"
            placeholder={authorConfig.toLocationText}
            json={json}
            toInstructionalText={toInstructionalText}
            setter={setDestination}
            setgsCode={setgsDestinationCode}
            validationError={destinationError[index]}
            setErrorFields={setErrorFields}
            errorFields={errorFields}
            msg_search_by_city_or={msg_search_by_city_or_airport}
            msg_empty_field={msg_empty_field}
            msg_close={msg_close}
          />
        </div>
      </div>
      <div className="flights-widget__datepicker-wrapper">
        <Datepicker
          setDeparture={setDeparture}
          setReturn={setReturn}
          index={index}
          parentWidget={parentWidget}
          text={{
            departureDateText,
            returnDateText,
          }}
          oneWay={oneWay}
          rootElementClass={oneWay ? "datepicker__input--oneway" : ""}
          departureDateLabel={departureDateLabel}
          returnDateLabel={returnDateLabel}
          datePlaceholder1={datePlaceholder1}
          datePlaceholder2={datePlaceholder2}
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
          {...props}
        />
      </div>
    </div>
  );
}

export default Stay;
