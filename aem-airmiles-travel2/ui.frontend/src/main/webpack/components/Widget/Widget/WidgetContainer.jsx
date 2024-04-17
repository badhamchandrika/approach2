import React, { forwardRef, useContext } from "react";
import PropTypes from "prop-types";
import classNames from "classnames";

import { getContext } from "../../../helpers/getContext";
import { getClassNamesByElement } from "../../../helpers/getClassNamesByElement";

export const WidgetContainer = forwardRef(
  ({ children, className, parentWidget, title, ...props }, ref) => {
    const { isSmallWidget, classNameObject } = useContext(
      getContext(parentWidget)
    );

    const widgetWrapperStyles = classNames(`widget`, {
      [className]: className,
      "widget--small": isSmallWidget,
    });

    const widgetContainerStyles = classNames({
      "bsp-travel-container": !isSmallWidget,
      ...getClassNamesByElement(classNameObject, "bsp-travel-container"),
    });

    return (
      <section className={widgetWrapperStyles} {...props}>
        {isSmallWidget && title && (
          <div className={widgetContainerStyles}>
            <h2 className="widget__title">{title}</h2>
          </div>
        )}
        <div className={widgetContainerStyles} ref={ref}>
          <div className="widget__inner">{children}</div>
        </div>
      </section>
    );
  }
);

WidgetContainer.propTypes = {
  parentWidget: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
  className: PropTypes.string,
  title: PropTypes.string,
};
