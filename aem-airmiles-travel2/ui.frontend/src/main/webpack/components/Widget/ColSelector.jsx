import React, { useContext } from "react";
import classNames from "classnames";
import { getClassNamesByElement } from "../../helpers/getClassNamesByElement";
import { getContext } from "../../helpers/getContext";

export const ColSelector = ({ children, parentWidget }) => {
  const { classNameObject } = useContext(getContext(parentWidget));

  const colClass = classNames("col-selector", {
    ...getClassNamesByElement(classNameObject, "col-selector"),
  });
  return <div className={colClass}>{children}</div>;
};
