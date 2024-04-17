import React from "react";

function CloseButton({onClick,onKeyDown,ariaLabel}) {
  return (
    <button
      className="am-icon am-icon-functional-secondary-close datepicker__closeBtn"
      onClick={onClick}
      onKeyDown={onKeyDown}
      aria-label={ariaLabel}
    />
  );
}

export default CloseButton;
