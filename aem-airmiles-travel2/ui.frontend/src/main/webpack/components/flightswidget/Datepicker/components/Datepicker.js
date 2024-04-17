import React, { useState, useEffect, useRef } from "react";
import { END_DATE, START_DATE } from "@datepicker-react/hooks";
import classNames from "classnames";
import {
  lock as disableBodyScroll,
  unlock as enableBodyScroll,
} from "tua-body-scroll-lock";

import Month from "./Month";
import NavButton from "./NavButton";
import DatepickerContext from "./datepickerContext";
import { NextSvg, PrevSvg } from "./Svgs";
import { Button } from "./Button";
import { DateInput } from "./DateInput";
import { DatepickerHeader } from "./DatepickerHeader";
import { useDatepicker } from "../hooks/useDatepickerCustom/useDatepickerCustom";
import { useMobileCheck } from "../hooks/useDatepickerCustom/useMobileCheck";
import useOnClickOutside from "../hooks/useOnClickOutside";
import ValidationWrapper from "../../../ui/ValidationWrapper/ValidationWrapper";
import { useFocusOutside } from "../hooks/useFocusOutside";
import { ColField } from "../../../Widget";

function Datepicker({
  setDeparture,
  setReturn,
  setDepartureDate,
  setReturnDate,
  index,
  oneWay,
  validationError = false,
  departureDateLabel,
  returnDateLabel,
  canOverflow,
  firstDateLabel,
  state,
  setState,
  setErrorFields,
  errorFields,
  msg_datepicker_previous_month,
  msg_datepicker_next_month,
  msg_reset,
  msg_done,
  msg_close,
  datepicker_months_string,
  datepicker_days_string,
  parentWidget,
  ...props
}) {
  const [showDatePicker, setShowDatepicker] = useState(false);

  useEffect(() => {
    function padTo2Digits(num) {
      return num?.toString().padStart(2, "0");
    }

    function formatDate(date) {
      return [
        padTo2Digits(date?.getMonth() + 1),
        padTo2Digits(date?.getDate()),
        date?.getFullYear(),
      ].join("/");
    }

    const formattedStartDate = formatDate(state.startDate);
    const formattedEndDate = formatDate(state.endDate);

    if (typeof setDepartureDate === "function") {
      setDepartureDate(state?.startDate);
    }

    if (typeof setReturnDate === "function") {
      setReturnDate(state?.endDate);
    }

    setDeparture((prev) => {
      if (!state.startDate) {
        return prev;
      }

      const dates = [...prev];

      dates[index] = formattedStartDate;
      return dates;
    });

    setReturn((prev) => {
      if (!state.endDate) {
        return prev;
      }

      const dates = [...prev];
      dates[index] = formattedEndDate;

      return dates;
    });
  }, [state]);

  const { isMobile, width } = useMobileCheck();

  const isTablet = width < 1280;

  const {
    firstDayOfWeek,
    activeMonths,
    isDateSelected,
    isDateHovered,
    isFirstOrLastSelectedDate,
    isDateBlocked,
    isDateFocused,
    focusedDate,
    onDateHover,
    onDateSelect,
    onDateFocus,
    goToPreviousMonths,
    goToNextMonths,
    hoveredDate,
  } = useDatepicker({
    startDate: state.startDate,
    endDate: state.endDate,
    focusedInput: state.focusedInput,
    onDatesChange: handleDateChange,
    changeActiveMonthOnSelect: false,
    numberOfMonths: isMobile ? 12 : 2,
  });

  function handleDateChange(data) {
    if (oneWay) {
      setState({ ...data, focusedInput: START_DATE }, index);
    } else {
      if (!data.focusedInput) {
        setState({ ...data, focusedInput: START_DATE }, index);
      } else {
        if (data.endDate === data.startDate) {
          setState(
            {
              startDate: data.startDate,
              endDate: null,
              focusedInput: END_DATE,
            },
            index
          );
        } else {
          if (state.startDate !== null && data.startDate < state.startDate) {
            setState(
              {
                startDate: data.startDate,
                endDate: state.endDate,
                focusedInput: END_DATE,
              },
              index
            );
          } else {
            setState(
              {
                startDate: data.startDate,
                endDate: null,
                focusedInput: END_DATE,
              },
              index
            );
          }
        }
      }
    }
    if (data.startDate) {
      setErrorFields((errorFields) =>
        errorFields.filter(
          (errorField) =>
            errorField.inputId !== `${index}_departure_date_${parentWidget}`
        )
      );
    }
    if (data.endDate) {
      setErrorFields((errorFields) =>
        errorFields.filter(
          (errorField) => errorField.inputId !== `${index}_return_date`
        )
      );
    }
  }

  const isCurrentMonthAndYear = Boolean(
    activeMonths.find(
      (month) =>
        month.month === new Date().getMonth() &&
        month.year === new Date().getFullYear()
    )
  );

  const today = new Date();
  const nextTenMonths = new Date(today);

  nextTenMonths.setMonth(nextTenMonths.getMonth() + 10);

  const isWithinTenMonths = Boolean(
    activeMonths.find((month) => month.date > nextTenMonths)
  );

  const openDatepicker = () => {
    setShowDatepicker(true);
  };

  const closeDatepicker = () => {
    if (isTablet && modalRef.current) {
      enableBodyScroll(modalRef.current);
    }
    if (!state.startDate || (!state.endDate && !oneWay)) {
      setState(
        {
          startDate: null,
          endDate: null,
          focusedInput: START_DATE,
        },
        index
      );
    }
    setShowDatepicker(false);
  };

  const handleInputKeyDown = ({ key }) => {
    if (key === "Enter" || key === " ") {
      openDatepicker();
    }
  };

  const handleCloseBtnKeyDown = ({ key }) => {
    if (key === "Enter" || key === " ") {
      closeDatepicker();
    }
  };

  window.addEventListener("keydown", ({ key }) => {
    if (key === "Escape") {
      closeDatepicker();
    }
  });

  const ref = useRef();
  const dateInputRef = useRef();
  const modalRef = useRef(null);

  useOnClickOutside(ref, () => closeDatepicker());
  useFocusOutside(ref, () => closeDatepicker());

  const [y, setY] = useState();
  const [x, setX] = useState();

  window.addEventListener("scroll", () => {
    setY(dateInputRef.current?.getBoundingClientRect().y);
  });

  window.addEventListener("resize", () => {
    setX(dateInputRef.current?.getBoundingClientRect().x);
  });

  useEffect(() => {
    setY(dateInputRef.current?.getBoundingClientRect().y);
    setX(dateInputRef.current?.getBoundingClientRect().x);
  });

  const getContainerStyles = () => {
    const elWidth = 704;
    const isOverflowing = x + elWidth > width;

    if (canOverflow && isOverflowing) {
      return { top: isTablet ? 0 : y, left: isTablet ? 0 : width - 12 - 704 };
    }
    return { top: isTablet ? 0 : y, left: isTablet ? 0 : x };
  };

  React.useEffect(() => {
    if (showDatePicker && isTablet) {
      disableBodyScroll(modalRef.current);
    }
  }, [showDatePicker, isTablet]);

  const getValidationErrorMsgForId = (errorFields, fieldId) => {
    return errorFields?.find((field) => field?.inputId === fieldId)
      ? {
          message: errorFields?.find((field) => field?.inputId === fieldId)
            ?.message,
        }
      : null;
  };

  const getTranslatedText = (text) => {
    if (datepicker_months_string && datepicker_days_string) {
      const months = datepicker_months_string.split(",");
      const days = datepicker_days_string.split(",");

      const translations = {
        Jan: months[0],
        Feb: months[1],
        Mar: months[2],
        Apr: months[3],
        May: months[4],
        Jun: months[5],
        Jul: months[6],
        Aug: months[7],
        Sep: months[8],
        Oct: months[9],
        Nov: months[10],
        Dec: months[11],
        Mon: days[0],
        Tue: days[1],
        Wed: days[2],
        Thu: days[3],
        Fri: days[4],
        Sat: days[5],
        Sun: days[6],
      };

      return translations[text];
    } else {
      return text;
    }
  };

  const getFormattedDateComma = (date) => {
    const dateArr = String(date?.toDateString()).split(" ");
    const month = getTranslatedText(dateArr[1]);

    return `${month} ${dateArr[2]}`;
  };

  const formattedStartDate = getFormattedDateComma(state?.startDate);
  const formattedEndDate = getFormattedDateComma(state?.endDate);

  return (
    <DatepickerContext.Provider
      value={{
        state,
        focusedDate,
        isDateFocused,
        isDateSelected,
        isDateHovered,
        isDateBlocked,
        isFirstOrLastSelectedDate,
        onDateSelect,
        onDateFocus,
        onDateHover,
        hoveredDate,
      }}
    >
      <div
        className={classNames("datepicker", {
          "datepicker--oneway": oneWay,
        })}
        ref={dateInputRef}
      >
        {showDatePicker && (
          <div
            className="datepicker__container"
            ref={ref}
            style={getContainerStyles()}
          >
            <DatepickerHeader
              state={state}
              onClickClose={() => closeDatepicker()}
              onKeyDown={handleCloseBtnKeyDown}
              oneWay={oneWay}
              isTablet={isTablet}
              firstDateLabel={firstDateLabel}
              returnDateLabel={returnDateLabel}
              formattedCommaStartDate={formattedStartDate}
              formattedCommaEndDate={formattedEndDate}
              msg_close={msg_close}
            />

            <div className="datepicker__content">
              <div className="datepicker__monthButtons">
                <NavButton
                  onClick={goToPreviousMonths}
                  disabled={isCurrentMonthAndYear}
                  ariaLabel={msg_datepicker_previous_month}
                >
                  <PrevSvg disabled={isCurrentMonthAndYear} />
                </NavButton>
                <NavButton
                  onClick={goToNextMonths}
                  disabled={isWithinTenMonths}
                  ariaLabel={msg_datepicker_next_month}
                >
                  <NextSvg disabled={isWithinTenMonths} />
                </NavButton>
              </div>

              <div className="datepicker__monthsHolder" ref={modalRef}>
                <div className="datepicker__mobileDayLabels">
                  {["S", "M", "T", "W", "T", "F", "S"].map((day) => {
                    return (
                      <span className="datepicker__mobileDayLabel">{day}</span>
                    );
                  })}
                </div>
                {activeMonths.map((month, index) => (
                  <Month
                    key={`${month.year}-${month.month}`}
                    year={month.year}
                    month={month.month}
                    firstDayOfWeek={firstDayOfWeek}
                    oneWay={oneWay}
                    index={index}
                    weekDaysArr={[
                      getTranslatedText("Sun"),
                      getTranslatedText("Mon"),
                      getTranslatedText("Tue"),
                      getTranslatedText("Wed"),
                      getTranslatedText("Thu"),
                      getTranslatedText("Fri"),
                      getTranslatedText("Sat"),
                    ]}
                    {...props}
                  />
                ))}
              </div>

              <div className="datepicker__actionBtns">
                <Button
                  label={msg_reset}
                  onClick={() =>
                    setState(
                      {
                        startDate: null,
                        endDate: null,
                        focusedInput: START_DATE,
                      },
                      index
                    )
                  }
                  disabled={!state.startDate && !state.endDate}
                />
                <Button
                  onClick={() => closeDatepicker()}
                  label={msg_done}
                  variant="filled"
                  style={{
                    marginLeft: isTablet ? "0px" : "16px",
                    marginTop: isTablet ? "16px" : "0px",
                  }}
                  disabled={!state.startDate || (!state.endDate && !oneWay)}
                />
              </div>
            </div>
          </div>
        )}
        <div className={`row datepicker__inner`}>
          <ColField parentWidget={parentWidget}>
            <ValidationWrapper
              validationError={getValidationErrorMsgForId(
                errorFields,
                `${index}_departure_date_${parentWidget}`
              )}
            >
              <DateInput
                id={`${index}_departure_date_${parentWidget}`}
                onKeyDown={handleInputKeyDown}
                onClick={() => openDatepicker()}
                label={oneWay ? "Date" : "Dates"}
                formattedDate={
                  (state?.startDate || state.endDate) &&
                  `${state?.startDate ? formattedStartDate : ""} ${
                    oneWay ? "" : "-"
                  } ${state?.endDate ? formattedEndDate : ""}`
                }
                rootElementClass={classNames({
                  "datepicker__input--oneway": oneWay,
                })}
              />
            </ValidationWrapper>
          </ColField>
        </div>
      </div>
      <div
        className={classNames("field-error", {
          "d-none": !validationError,
        })}
      ></div>
    </DatepickerContext.Provider>
  );
}

export default Datepicker;
