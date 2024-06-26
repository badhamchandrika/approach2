import isWithinRange from "date-fns/is_within_range";
import isSameDay from "date-fns/is_same_day";
import eachDay from "date-fns/each_day";
import isBefore from "date-fns/is_before";
import isAfter from "date-fns/is_after";
import getYear from "date-fns/get_year";
import getMonth from "date-fns/get_month";
import startOfToday from "date-fns/start_of_today";
import startOfMonth from "date-fns/start_of_month";
import addMonths from "date-fns/add_months";
import format from "date-fns/format";
import addDays from "date-fns/add_days";

export function isDateSelected(date, startDate, endDate) {
  if (startDate && endDate) {
    return isWithinRange(date, startDate, endDate);
  }

  return false;
}

export function isFirstOrLastSelectedDate(date, startDate, endDate) {
  return !!(
    (startDate && isSameDay(date, startDate)) ||
    (endDate && isSameDay(date, endDate))
  );
}

export function isDateBlocked({
  date,
  minBookingDate,
  maxBookingDate,
  isDateBlockedFn,
  startDate,
  endDate,
  minBookingDays = 1,
}) {
  const compareMinDate = minBookingDate
    ? new Date(
        minBookingDate.getFullYear(),
        minBookingDate.getMonth(),
        minBookingDate.getDate(),
        0,
        0,
        0
      )
    : minBookingDate;
  const compareMaxDate = maxBookingDate
    ? new Date(
        maxBookingDate.getFullYear(),
        maxBookingDate.getMonth(),
        maxBookingDate.getDate(),
        0,
        0,
        0
      )
    : maxBookingDate;

  return !!(
    (compareMinDate && isBefore(date, compareMinDate)) ||
    (compareMaxDate && isAfter(date, compareMaxDate)) ||
    (startDate &&
      !endDate &&
      minBookingDays > 1 &&
      isWithinRange(date, startDate, addDays(startDate, minBookingDays - 2))) ||
    (isDateBlockedFn && isDateBlockedFn(date))
  );
}

// export interface MonthType {
//   year: number
//   month: number
//   date: Date
// }

export function getDateMonthAndYear(date) {
  const today = startOfMonth(date);
  const year = getYear(today);
  const month = getMonth(today);
  return {
    year,
    month,
    date: today,
  };
}

export function getCurrentYearMonthAndDate() {
  return getDateMonthAndYear(startOfToday());
}

export function getInitialMonths(numberOfMonths, startDate) {
  const firstMonth = startDate
    ? getDateMonthAndYear(startDate)
    : getCurrentYearMonthAndDate();
  let prevMonthDate = firstMonth.date;
  let months = [firstMonth];

  if (numberOfMonths > 1) {
    months = Array.from(Array(numberOfMonths - 1).keys()).reduce((m) => {
      prevMonthDate = addMonths(m[m.length - 1].date, 1);
      return m.concat([getDateMonthAndYear(prevMonthDate)]);
    }, months);
  }

  return months;
}

export function getNextActiveMonth(activeMonth, numberOfMonths, counter) {
  const prevMonth = counter > 0 ? activeMonth.length - 1 : 0;
  let prevMonthDate = activeMonth[prevMonth].date;

  return Array.from(Array(numberOfMonths).keys()).reduce((m) => {
    prevMonthDate = addMonths(prevMonthDate, counter);
    return counter > 0
      ? m.concat([getDateMonthAndYear(prevMonthDate)])
      : [getDateMonthAndYear(prevMonthDate)].concat(m);
  }, []);
}

// export type FormatFunction = (date: Date) => string
export function getInputValue(date, displayFormat, defaultValue) {
  if (date && typeof displayFormat === "string") {
    return format(date, displayFormat);
  } else if (date && typeof displayFormat === "function") {
    return displayFormat(date);
  } else {
    return defaultValue;
  }
}

// export interface CanSelectRangeProps {
//   startDate: Date
//   endDate: Date | null
//   isDateBlocked(date: Date): boolean
//   minBookingDays: number
//   exactMinBookingDays?: boolean
//   minBookingDate?: Date
//   maxBookingDate?: Date
// }
export function canSelectRange({
  startDate,
  endDate,
  isDateBlocked,
  minBookingDays,
  exactMinBookingDays,
  minBookingDate,
  maxBookingDate,
}) {
  const isStartDateAfterOrEqualMinDate = minBookingDate
    ? !isBefore(startDate, addDays(minBookingDate, -1))
    : true;
  const isStartDateBeforeOrEqualMaxDate = maxBookingDate
    ? !isAfter(addDays(startDate, minBookingDays - 1), maxBookingDate)
    : true;

  if (
    startDate &&
    minBookingDays === 1 &&
    !endDate &&
    !isDateBlocked(startDate)
  ) {
    return true;
  } else if (
    (startDate && minBookingDays > 1 && !endDate && !exactMinBookingDays) ||
    (startDate &&
      minBookingDays > 0 &&
      exactMinBookingDays &&
      isStartDateAfterOrEqualMinDate &&
      isStartDateBeforeOrEqualMaxDate) ||
    (startDate &&
      minBookingDays > 0 &&
      exactMinBookingDays &&
      !minBookingDate &&
      !maxBookingDate)
  ) {
    return eachDay(startDate, addDays(startDate, minBookingDays - 1)).reduce(
      (returnValue, date) => {
        if (!returnValue) {
          return returnValue;
        }

        return !isDateBlocked(date);
      },
      true
    );
  } else if (startDate && endDate && !exactMinBookingDays) {
    const minBookingDaysDate = addDays(startDate, minBookingDays - 1);

    if (isBefore(endDate, minBookingDaysDate)) {
      return false;
    }

    return eachDay(startDate, endDate).reduce((returnValue, date) => {
      if (!returnValue) {
        return returnValue;
      }

      return !isDateBlocked(date);
    }, true);
  }

  return false;
}

export function isDateHovered({
  date,
  startDate,
  endDate,
  isDateBlocked,
  hoveredDate,
  minBookingDays,
  exactMinBookingDays,
}) {
  if (
    // exact min booking days
    hoveredDate &&
    minBookingDays > 1 &&
    exactMinBookingDays &&
    isWithinRange(date, hoveredDate, addDays(hoveredDate, minBookingDays - 1))
  ) {
    return eachDay(
      hoveredDate,
      addDays(hoveredDate, minBookingDays - 1)
    ).reduce((returnValue, date) => {
      if (!returnValue) {
        return returnValue;
      }

      return !isDateBlocked(date);
    }, true);
  } else if (
    // min booking days
    startDate &&
    !endDate &&
    hoveredDate &&
    isWithinRange(date, startDate, addDays(startDate, minBookingDays - 1)) &&
    isSameDay(startDate, hoveredDate) &&
    minBookingDays > 1
  ) {
    return eachDay(startDate, addDays(startDate, minBookingDays - 1)).reduce(
      (returnValue, date) => {
        if (!returnValue) {
          return returnValue;
        }

        return !isDateBlocked(date);
      },
      true
    );
  } else if (
    // normal
    startDate &&
    !endDate &&
    hoveredDate &&
    !isBefore(hoveredDate, startDate) &&
    isWithinRange(date, startDate, hoveredDate)
  ) {
    return eachDay(startDate, hoveredDate).reduce((returnValue, date) => {
      if (!returnValue) {
        return returnValue;
      }

      return !isDateBlocked(date);
    }, true);
  }

  return false;
}
