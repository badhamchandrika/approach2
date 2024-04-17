import React from "react";
import classNames from "classnames";

const ButtonDropdown = ({
  content: { text },
  onClick,
  isActive,
  icon = null,
}) => {
  const handleKeyDown = ({ key }) => {
    if (key === " " || key === "Enter") {
      onClick();
    }
  };

  return (
    <div className="button-dropdown">
      <div
        className="button-dropdown__button"
        tabIndex={0}
        onClick={onClick}
        onKeyDown={handleKeyDown}
        role="button"
        aria-expanded={isActive}
        aria-haspopup="listbox"
      >
        <div className="button-dropdown__button-text">
          {icon} {text}
        </div>
        <i
          aria-hidden="true"
          className={classNames(
            "button-dropdown__button-icon am-icon am-icon-functional-arrow-down",
            {
              "am-icon-functional-arrow-up": isActive,
            }
          )}
        />
      </div>
    </div>
  );
};

export default ButtonDropdown;
