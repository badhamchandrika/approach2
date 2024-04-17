import React from "react";
import classNames from "classnames";
import PropTypes from "prop-types";
import Checkbox from "../Checkbox";

const CheckBoxGroup = ({
  items,
  label,
  vertical = true,
  setValue,
  setCount,
}) => {
  return (
    <fieldset className="checkbox-group">
      {label && (
        <legend className="checkbox-group__label" tabIndex="0">
          {label}
        </legend>
      )}
      <div
        className={classNames("checkbox-group__list", {
          "checkbox-group__list--vertical": vertical,
        })}
      >
        {items &&
          items.map((item, index) => {
            return (
              <Checkbox
                key={index}
                setValue={setValue}
                setCount={setCount}
                {...item}
              />
            );
          })}
      </div>
    </fieldset>
  );
};

CheckBoxGroup.propTypes = {
  items: PropTypes.array.isRequired,
  label: PropTypes.string,
  vertical: PropTypes.bool,
  setValue: PropTypes.func,
};

export default CheckBoxGroup;
