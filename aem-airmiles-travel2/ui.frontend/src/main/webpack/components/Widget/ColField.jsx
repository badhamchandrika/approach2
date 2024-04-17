import React, { useContext } from "react";
import classNames from "classnames";
import { getClassNamesByElement } from "../../helpers/getClassNamesByElement";
import { getContext } from "../../helpers/getContext";

export const ColField = ({ children, className, parentWidget, ...props }) => {
  const { classNameObject } = useContext(getContext(parentWidget));

  const colClass = classNames(`col-field`, {
    [className]: className,
    ...getClassNamesByElement(classNameObject, "col-field"),
  });
  return (
    <div className={colClass} {...props}>
      {children}
    </div>
  );
};
