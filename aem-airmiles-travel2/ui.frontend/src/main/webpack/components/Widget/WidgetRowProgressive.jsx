import React, { useContext } from "react";
import { getContext } from "../../helpers/getContext";

export const WidgetRowProgressive = ({
  children,
  parentWidget,
  setIsProgressive,
  ...props
}) => {
  const {
    progressiveButtonText,
    hasProgressive,
    isProgressive,
    classNameObject,
  } = useContext(getContext(parentWidget));

  const { xl: isDesktop } = classNameObject;

  const showProgressiveButton = hasProgressive && !isProgressive && isDesktop;

  return (
    <div className="row widget__row-progressive" {...props}>
      <div className="col">{children}</div>
      {showProgressiveButton && (
        <div className="col-auto">
          <button className="cta-button" onClick={() => setIsProgressive(true)}>
            {progressiveButtonText}
          </button>
        </div>
      )}
    </div>
  );
};
