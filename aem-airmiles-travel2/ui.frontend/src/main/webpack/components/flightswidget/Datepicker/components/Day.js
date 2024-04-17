import React, { useRef, useContext } from "react";
import { useDay } from "@datepicker-react/hooks";
import DatepickerContext from "./datepickerContext";
import getColor from "../utils/getColor";

function Day({ dayLabel, date, oneWay, disableBeforeDate, disableAfterDate }) {
  const dayRef = useRef(null);
  const {
    focusedDate,
    isDateFocused,
    isDateSelected,
    isDateHovered,
    isDateBlocked,
    isFirstOrLastSelectedDate,
    onDateSelect,
    onDateFocus,
    onDateHover,
    state,
    hoveredDate,
  } = useContext(DatepickerContext);
  
  const {
    isSelected,
    isSelectedStartOrEnd,
    isWithinHoverRange,
    disabledDate,
    onClick,
    onKeyDown,
    onMouseEnter,
  } = useDay({
    date,
    focusedDate,
    isDateFocused,
    isDateSelected,
    isDateHovered,
    isDateBlocked,
    isFirstOrLastSelectedDate,
    onDateFocus,
    onDateSelect,
    onDateHover,
    dayRef,
  });

  if (!dayLabel) {
    return <div />;
  }

  const getColorFn = getColor(
    isSelected,
    isSelectedStartOrEnd,
    isWithinHoverRange,
    disabledDate
  );

  const isSelectedStart = date?.getTime() === state.startDate?.getTime();

  const getFirstAndLastBackground = () => {
    if (isSelectedStartOrEnd) {
      const side = isSelectedStart ? "right" : "left";

      return `linear-gradient(
        to ${side}, 
        #ffffff 0%, 
        #ffffff 50%, 
        #F0F5F8 50%, 
        #F0F5F8 100%
      )`;
    } else {
      return "#FFFFFF";
    }
  };

  const today = new Date();

  const nextThreeDays = new Date(today);
  nextThreeDays.setHours(0, 0, 0, 0);
  nextThreeDays.setDate(nextThreeDays.getDate() + 3);

  const next330Days = new Date(today);
  next330Days.setHours(0, 0, 0, 0);
  next330Days.setDate(next330Days.getDate() + 330);

  const isAfterTwoDays = nextThreeDays > date;
  const isBefore330Days = next330Days < date;
  const isBeforeStartDate = date < state.startDate;
  const isDisableBefore =
    disableBeforeDate !== undefined &&
    disableBeforeDate !== null &&
    date < disableBeforeDate;
  const isDisableAfter =
    disableAfterDate !== undefined &&
    disableAfterDate !== null &&
    date > disableAfterDate;

  const isDisabled =
    isAfterTwoDays ||
    (isBeforeStartDate && !oneWay && !state.endDate) ||
    isBefore330Days ||
    isDisableBefore ||
    isDisableAfter;

  const getClassName = () => {
    if (isSelectedStartOrEnd) {
      return "day day__first-or-last";
    } else if (!isSelected && !isDisabled) {
      return "day day__not-selected";
    } else if (isSelected && !isDisabled) {
      return "day day__selected";
    } else {
      return "day";
    }
  };

  const isFirstSelected = state.startDate === date;
  const isLastSelected = state.endDate === date;
  const newLabelDate = String(date)
    .split(" ")
    .filter((date, index) => index < 4)
    .join(" ");
  const getLabel = () => {
    if (isFirstSelected) {
      return `Departure ${newLabelDate}`;
    }
    if (isLastSelected) {
      return `Return ${newLabelDate}`;
    }

    return newLabelDate;
  };

  const getHoveredBackground = () => {
    if(oneWay) {
      return "#FFFFFF";
    }
    
    if(date === hoveredDate) {
        return `linear-gradient(
          to left, 
          #ffffff 0%, 
          #ffffff 50%, 
          #F0F5F8 50%, 
          #F0F5F8 100%
        )`;
      } else {
        return "#F0F5F8";
      }

  };
  
  return (
    <button
      disabled={isDisabled}
      onClick={onClick}
      onKeyDown={onKeyDown}
      onMouseEnter={onMouseEnter}
      tabIndex={0}
      type="button"
      ref={dayRef}
      daylabelattr={dayLabel}
      className={getClassName()}
      aria-label={getLabel()}
      style={{
        padding: "8px",
        border: 0,
        height: 33,
        overflow: "hidden",
        color: getColorFn({
          selectedFirstOrLastColor: "#FFFFFF",
          normalColor: isDisabled ? "#1C2D3F40" : "#1C2D3F",
          selectedColor: "#1F68DA",
          rangeHoverColor: oneWay ? "#1C2D3F" : "#1F68DA",
          disabledColor: "#808285",
        }),
        background: getColorFn({
          selectedFirstOrLastColor:
            oneWay || state.startDate === state.endDate
              ? "#FFFFFF"
              : getFirstAndLastBackground(),
          normalColor: "#FFFFFF",
          selectedColor: "#F0F5F8",
          rangeHoverColor: getHoveredBackground(),
          disabledColor: "#FFFFFF",
        }),
      }}
    >
      {dayLabel}
    </button>
  );
}

export default Day;
