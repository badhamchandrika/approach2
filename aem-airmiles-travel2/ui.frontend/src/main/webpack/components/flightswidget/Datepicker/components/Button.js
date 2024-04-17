import classNames from "classnames";
import React from "react";

export const Button = ({ label, variant, onClick, style, disabled }) => {
  return (
    <button
      disabled={disabled}
      onClick={onClick}
      className={classNames("datepicker__actionBtn", {
        "datepicker__actionBtn--disabled": disabled,
        "datepicker__actionBtn--filled": variant === "filled",
      })}
      style={style}
    >
      {label}
    </button>
  );
};
