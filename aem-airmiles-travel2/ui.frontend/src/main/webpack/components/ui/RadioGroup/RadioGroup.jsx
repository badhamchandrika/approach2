import React, { useContext, useEffect, useRef } from "react";
import classNames from "classnames";
import { v4 as uuidv4 } from "uuid";
import { getClassNamesByElement } from "../../../helpers/getClassNamesByElement";
import { getContext } from "../../../helpers/getContext";

function RadioGroup({
  radioOptions,
  selectedRadio,
  setSelectedRadio,
  header,
  parentWidget,
}) {
  const itemsRef = useRef([]);

  useEffect(() => {
    itemsRef.current = itemsRef.current.slice(0, radioOptions.length);
  }, [radioOptions]);

  const handleKeyDown = (key, idx) => {
    if (key === "Enter" || key == " ") {
      setSelectedRadio(idx);
    }

    if (key === "ArrowDown" || key === "ArrowRight") {
      if (idx < radioOptions.length - 1) {
        itemsRef.current[idx + 1].focus();
        setSelectedRadio(idx + 1);
      } else {
        itemsRef.current[0].focus();
        setSelectedRadio(0);
      }
    }

    if (key === "ArrowUp" || key === "ArrowLeft") {
      if (idx > 0) {
        itemsRef.current[idx - 1].focus();
        setSelectedRadio(idx - 1);
      } else {
        itemsRef.current[radioOptions.length - 1].focus();
        setSelectedRadio(radioOptions.length - 1);
      }
    }
  };

  const { classNameObject } = useContext(getContext(parentWidget));

  const radioGroupClass = classNames("radioGroup", {
    ...getClassNamesByElement(classNameObject, "radioGroup"),
  });

  const radioGroupOptionClass = (isSelected) =>
    classNames("radioGroup__option", {
      "radioGroup__option--selected": isSelected,
    });

  const radioGroupHeaderId = `radioGroupHeader-${uuidv4()}`;

  return (
    <div
      aria-labelledby={radioGroupHeaderId}
      className={radioGroupClass}
      role="radiogroup"
    >
      {header && (
        <div className="radioGroup__header">
          <p id={radioGroupHeaderId}>{header}</p>
        </div>
      )}
      <div className="radioGroup__options">
        {radioOptions.map(({ title, desc, icon }, idx) => {
          const isSelectedOption = idx === selectedRadio;
          return (
            <div
              aria-checked={isSelectedOption}
              className={radioGroupOptionClass(isSelectedOption)}
              key={title}
              onClick={() => setSelectedRadio(idx)}
              onKeyDown={({ key }) => handleKeyDown(key, idx)}
              ref={(el) => (itemsRef.current[idx] = el)}
              role="radio"
              tabIndex={0}
            >
              <div className="radioGroup__optionHeader">
                <div className="radioGroup__optionIcon">{icon}</div>
                <p>{title}</p>
              </div>
              <div className="radioGroup__optionBody">
                <p>{desc}</p>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default RadioGroup;
