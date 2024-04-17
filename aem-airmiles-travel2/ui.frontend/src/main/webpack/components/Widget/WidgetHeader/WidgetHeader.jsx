import React, { Children, useContext } from "react";
import classNames from "classnames";
import { getContext } from "../../../helpers/getContext";
import CONSTANTS from "../../../constants";
import { getClassNamesByElement } from "../../../helpers/getClassNamesByElement";
import AdvancedSearchLink from "../../ui/AdvancedSearchLink/AdvancedSearchLink";

export const WidgetHeader = ({
  advancedSearchText,
  buildSearchQuery,
  children,
  optionsColumnClass,
  parentWidget,
}) => {
  const { runmodes, classNameObject } = useContext(getContext(parentWidget));

  const [firstChild, secondChild] = Children.toArray(children);

  const showSecondChild =
    secondChild && parentWidget == CONSTANTS.WIDGET.PACKAGES;

  const optionsColumnStyles = classNames(`widget-header-options`, {
    [optionsColumnClass]: optionsColumnClass,
    "mb-4": firstChild,
    col: !optionsColumnClass,
    ...getClassNamesByElement(classNameObject, "widget-header-options"),
  });

  const advancedColumnStyles = classNames(`widget-header-advanced col-auto`, {
    ...getClassNamesByElement(classNameObject, "widget-header-advanced"),
  });

  const optionsTopStyles = classNames("row widget-header__options-top", {
    ...getClassNamesByElement(classNameObject, "widget-header__options-top"),
  });

  const optionsBottomStyles = classNames("row widget-header__options-bottom", {
    ...getClassNamesByElement(classNameObject, "widget-header__options-bottom"),
  });

  if (Children.count(children) > 2) {
    throw new Error(
      "WidgetHeader allows 2 children maximun. Check number of children inside of it."
    );
  }

  if (secondChild && parentWidget != CONSTANTS.WIDGET.PACKAGES) {
    throw new Error(
      "WidgetHeader allows second child only for Packages widget."
    );
  }

  return (
    <div className={classNames("widget-header")}>
      <div className="row">
        <div className={optionsColumnStyles}>{firstChild}</div>
        <div className={advancedColumnStyles}>
          <AdvancedSearchLink
            anchorText={advancedSearchText}
            buildSearchQuery={buildSearchQuery}
            parent={parentWidget}
            runmodes={runmodes}
          />
          {showSecondChild && (
            <div className={optionsTopStyles}>{secondChild}</div>
          )}
        </div>
      </div>
      {showSecondChild && (
        <div className={optionsBottomStyles}>{secondChild}</div>
      )}
    </div>
  );
};
