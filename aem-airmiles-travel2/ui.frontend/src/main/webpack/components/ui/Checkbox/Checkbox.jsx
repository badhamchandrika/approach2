import React, { useContext } from "react";
import classNames from "classnames";
import PropTypes from "prop-types";
import { getClassNamesByElement } from "../../../helpers/getClassNamesByElement";
import { getContext } from "../../../helpers/getContext";

const CheckBox = ({
  checked = false,
  error = false,
  setCount,
  htmlValue,
  group,
  id,
  label,
  setValue,
  disabled,
  parentWidget,
  ...rest
}) => {
  const { classNameObject } = useContext(getContext(parentWidget));

  const handleChange = (e) => {
    if (setValue) {
      setValue({
        ...checked,
        name: e.target.name,
        checked: e.target.checked,
        ...rest,
      });
    }

    if (setCount) {
      setCount((prev) => {
        const prevCount = prev[group];

        const newCountObject = {
          ...prev,
        };

        checked
          ? (newCountObject[group] = prevCount - 1)
          : (newCountObject[group] = prevCount + 1);

        return newCountObject;
      });
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      handleChange(e);
    }
  };

  const valueKey = htmlValue || id;

  return (
    <label
      htmlFor={id}
      className={classNames(`checkbox-container`, {
        ...getClassNamesByElement(classNameObject, "checkbox-container"),
        "checkbox-container--disabled": disabled,
        "checkbox-container--error": error,
        "checkbox-container--checked": checked,
      })}
    >
      <span className="checkbox-container__inner">
        <span className="checkbox-container__check">
          <input
            checked={checked}
            disabled={disabled}
            id={id}
            name={valueKey}
            onChange={handleChange}
            onKeyDown={handleKeyDown}
            type="checkbox"
            value={valueKey}
          />

          <span className="checkbox-container__check-square" />
          <span className="checkbox-container__check-icon am-icon am-icon-check-selected" />
        </span>

        <div className="checkbox-container__text-wrapper">{label}</div>
      </span>
    </label>
  );
};

CheckBox.propTypes = {
  id: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  checked: PropTypes.bool,
  disabled: PropTypes.bool,
  error: PropTypes.bool,
  htmlValue: PropTypes.string, // the "value" attribute on the input[type="checkbox"] element. Defaults to id.
  setValue: PropTypes.func,
};

export default CheckBox;
