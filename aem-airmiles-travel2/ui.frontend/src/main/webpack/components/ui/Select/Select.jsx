import classNames from "classnames";
import React from "react";

function Select({ selected, value, defaultValue, onChange, id, options }) {
  return (
    <div className={classNames("select-wrapper", "d-flex", "flex-column")}>
      <label
        className={classNames("select-wrapper__label", {
          "select-wrapper__label--selected": selected[id],
        })}
        htmlFor={id}
      >
        {defaultValue}
      </label>

      <select
        id={id}
        value={value}
        onChange={onChange}
        className={classNames("select-wrapper__select", {
          "select-wrapper__select--selected": selected[id],
        })}
      >
        <option value="" defaultValue hidden>
          {defaultValue}
        </option>
        {options.map((option) => {
          return (
            <option key={options.key} value={option.value}>
              {option.label}
            </option>
          );
        })}
      </select>
    </div>
  );
}

export default Select;
