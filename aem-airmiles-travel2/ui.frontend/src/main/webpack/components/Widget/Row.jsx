import React, { useContext } from "react";
import classNames from "classnames";
import { getClassNamesByElement } from "../../helpers/getClassNamesByElement";
import { getContext } from "../../helpers/getContext";

export const Row = ({ children, className = null, parentWidget }) => {
  const { classNameObject } = useContext(getContext(parentWidget));

  const rowClass = classNames("row", {
    [className]: className,
    ...getClassNamesByElement(classNameObject, className, className),
  });

  return <div className={rowClass}>{children}</div>;
};
