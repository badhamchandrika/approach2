import React from "react";
import LocationDropdown from "../ui/LocationDropdown";
import Datepicker from "./Datepicker/components/Datepicker.js";
import { ColField, Row } from "../Widget";

function Flight({
  index,
  origin,
  destination,
  setOrigin,
  gsOriginCode,
  setgsOriginCode,
  setDestination,
  setgsDestinationCode,
  setDeparture,
  setReturn,
  authorConfig,
  json,
  tripType,
  setFlights,
  setErrorFields,
  errorFields,
  calendarState,
  setCalendarState,
  departureDateLabel,
  msg_datepicker_departure_date,
  returnDateLabel,
  msg_search_by_city_or,
  msg_datepicker_previous_month,
  msg_datepicker_next_month,
  msg_empty_field,
  msg_close,
  datepicker_months_string,
  datepicker_days_string,
  parentWidget,
  ...props
}) {
  const oneWay = tripType === "multi" || tripType === "oneway";
  const isMulti = tripType === "multi";

  return (
    <Row parentWidget={parentWidget}>
      <ColField parentWidget={parentWidget}>
        <LocationDropdown
          index={index}
          value={origin[index]}
          inputId={`originInput_flight_${index + 1}`}
          placeholder={authorConfig.fromLocationText}
          json={json}
          fromInstructionalText={
            authorConfig.fromInstructionalText
              ? authorConfig.fromInstructionalText
              : ""
          }
          toInstructionalText={
            authorConfig.toInstructionalText
              ? authorConfig.toInstructionalText
              : ""
          }
          setter={setOrigin}
          gsCode={gsOriginCode}
          setgsCode={setgsOriginCode}
          setErrorFields={setErrorFields}
          errorFields={errorFields}
          msg_search_by_city_or={msg_search_by_city_or}
          msg_empty_field={msg_empty_field}
          msg_close={msg_close}
        />
      </ColField>
      <ColField parentWidget={parentWidget}>
        <LocationDropdown
          index={index}
          value={destination[index]}
          inputId={`destinationInput_flight_${index + 1}`}
          placeholder={authorConfig.toLocationText}
          json={json}
          fromInstructionalText={
            authorConfig.fromInstructionalText
              ? authorConfig.fromInstructionalText
              : ""
          }
          toInstructionalText={
            authorConfig.toInstructionalText
              ? authorConfig.toInstructionalText
              : ""
          }
          setter={setDestination}
          setgsCode={setgsDestinationCode}
          setErrorFields={setErrorFields}
          errorFields={errorFields}
          msg_search_by_city_or={msg_search_by_city_or}
          msg_empty_field={msg_empty_field}
          msg_close={msg_close}
        />
      </ColField>
      <ColField parentWidget={parentWidget} forDatepicker={!oneWay}>
        {isMulti && (
          <button
            onClick={() => setFlights((prev) => prev - 1)}
            className="flights-widget__remove-flights-btn"
          >
            {index > 1 && (
              <>
                {"Remove"}
                <span className="am-icon am-icon-functional-secondary-close" />
              </>
            )}
          </button>
        )}
        <Datepicker
          setDeparture={setDeparture}
          setReturn={setReturn}
          index={index}
          text={{
            departureDateText: authorConfig.departureDateText,
            returnDateText: authorConfig.returnDateText,
          }}
          oneWay={oneWay}
          rootElementClass={oneWay ? "datepicker__input--oneway" : ""}
          firstDateLabel={msg_datepicker_departure_date}
          state={calendarState}
          setState={setCalendarState}
          canOverflow
          departureDateLabel={departureDateLabel}
          returnDateLabel={returnDateLabel}
          setErrorFields={setErrorFields}
          errorFields={errorFields}
          msg_datepicker_previous_month={msg_datepicker_previous_month}
          msg_datepicker_next_month={msg_datepicker_next_month}
          msg_close={msg_close}
          datepicker_months_string={datepicker_months_string}
          datepicker_days_string={datepicker_days_string}
          parentWidget={parentWidget}
          {...props}
        />
      </ColField>
    </Row>
  );
}

export default Flight;
