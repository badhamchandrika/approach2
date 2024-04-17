import React from "react";

export default function NavButton({ children, onClick, disabled, ariaLabel }) {
  return (
    <button
      disabled={disabled}
      type="button"
      onClick={onClick}
      aria-label={ariaLabel}
      className={
        disabled
          ? "datepicker__monthBtn datepicker__monthBtn--disabled"
          : "datepicker__monthBtn"
      }
    >
      {children}
    </button>
  );
}
