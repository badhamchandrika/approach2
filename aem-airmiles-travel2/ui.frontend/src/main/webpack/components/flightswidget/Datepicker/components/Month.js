import { useMonth } from "@datepicker-react/hooks";
import Day from "./Day";
import React, { useEffect, useState } from "react";
import classNames from "classnames";

function Month({
  year,
  month,
  firstDayOfWeek,
  index,
  msg_datepicker_january,
  msg_datepicker_february,
  msg_datepicker_march,
  msg_datepicker_april,
  msg_datepicker_may,
  msg_datepicker_june,
  msg_datepicker_july,
  msg_datepicker_august,
  msg_datepicker_september,
  msg_datepicker_october,
  msg_datepicker_november,
  msg_datepicker_december,
  weekDaysArr,
  ...props
}) {
  const [toDisplayMonthLabel, setToDisplayMonthLabel] = useState();

  const { days, monthLabel } = useMonth({
    year,
    month,
    firstDayOfWeek,
  });

  useEffect(() => {
    if (/January/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_january} ${monthLabel.split(" ").pop()}`
      );
    if (/February/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_february} ${monthLabel.split(" ").pop()}`
      );
    if (/March/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_march} ${monthLabel.split(" ").pop()}`
      );
    if (/April/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_april} ${monthLabel.split(" ").pop()}`
      );
    if (/May/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_may} ${monthLabel.split(" ").pop()}`
      );
    if (/June/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_june} ${monthLabel.split(" ").pop()}`
      );
    if (/July/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_july} ${monthLabel.split(" ").pop()}`
      );
    if (/August/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_august} ${monthLabel.split(" ").pop()}`
      );
    if (/September/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_september} ${monthLabel.split(" ").pop()}`
      );
    if (/October/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_october} ${monthLabel.split(" ").pop()}`
      );
    if (/November/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_november} ${monthLabel.split(" ").pop()}`
      );
    if (/December/.test(monthLabel))
      setToDisplayMonthLabel(
        `${msg_datepicker_december} ${monthLabel.split(" ").pop()}`
      );
  }, [monthLabel]);

  let newDays;

  if (month === 8 || month === 11) {
    newDays = [0, 0, 0, 0, 0, 0, ...days];
  } else {
    newDays = [0, ...days];
  }
  
  const fullDays = [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
  ];
  return (
    <div className={classNames({ datepicker__firstMonth: index === 0 })}>
      <div className="datepicker__monthLabel">
        <span>{toDisplayMonthLabel}</span>
      </div>
      <div className="datepicker__dayLabels">
        {weekDaysArr.map((dayLabel, index) => (
          <div
            className="datepicker__dayLabel"
            aria-label={fullDays[index]}
            key={dayLabel}
          >
            {dayLabel}
          </div>
        ))}
      </div>
      <div className="datepicker__month">
        {newDays.map((day, index) => {
          if (typeof day === "object") {
            return (
              <Day
                date={day.date}
                key={day.date.toString()}
                dayLabel={day.dayLabel}
                {...props}
              />
            );
          }

          return <div key={index} />;
        })}
      </div>
    </div>
  );
}

export default Month;
