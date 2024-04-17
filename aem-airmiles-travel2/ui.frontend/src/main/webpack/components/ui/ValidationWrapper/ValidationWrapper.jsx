import React, { useContext } from "react";
import classNames from "classnames";
import { getClassNamesByElement } from "../../../helpers/getClassNamesByElement";
import { getContext } from "../../../helpers/getContext";

const ValidationWrapper = ({
  bottomElement = false,
  children,
  parentWidget,
  validationError,
}) => {
  const { classNameObject } = useContext(getContext(parentWidget));

  const { md: isTablet, xl: isDesktop } = classNameObject;

  const applyBottomClass = bottomElement && (isTablet || isDesktop);

  const isTabletOrDesktop = isTablet || isDesktop;

  const messageStyles = classNames("validation-wrapper__message", {
    "mb-4":
      validationError &&
      (!bottomElement || (bottomElement && !isTabletOrDesktop)),
    "validation-wrapper__message--bottom": applyBottomClass,
    ...getClassNamesByElement(
      classNameObject,
      "validation-wrapper__message--bottom",
      applyBottomClass
    ),
    invisible: !validationError,
  });

  return (
    <div
      className={classNames("validation-wrapper", {
        error: validationError,
      })}
      aria-live="polite"
    >
      {children}
      <div className={messageStyles}>
        {validationError?.message || "placeholder"}
      </div>
    </div>
  );
};

export default ValidationWrapper;
