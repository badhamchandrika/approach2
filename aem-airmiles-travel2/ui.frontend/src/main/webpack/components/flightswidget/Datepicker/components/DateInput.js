import React from "react";
import classNames from "classnames";

import { NewCalendarSvg } from "./Svgs";

export const DateInput = ({
  id,
  label,
  formattedDate,
  onClick,
  onKeyDown,
  style,
  rootElementClass,
}) => {
  const labelStyles = classNames("datepicker__inputLabel", {
    "datepicker__inputLabel--with-date": formattedDate,
  });

  return (
    <div
      id={id}
      onKeyDown={onKeyDown}
      tabIndex={0}
      onClick={onClick}
      className={classNames("datepicker__input", {
        rootElementClass,
      })}
      style={style}
    >
      <NewCalendarSvg />

      <div className="datepicker__inputContent">
        <span className={labelStyles}>{label}</span>
        {formattedDate && (
          <span
            aria-label={formattedDate || label}
            className={classNames("datepicker__inputDate", {})}
          >
            {formattedDate}
          </span>
        )}
      </div>
    </div>
  );
};
