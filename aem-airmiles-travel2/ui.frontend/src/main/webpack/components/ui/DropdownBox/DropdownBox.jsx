import React, { useLayoutEffect, useRef, useState } from "react";
import ClickAwayListener from "react-click-away-listener";
import { useMediaQuery } from "react-responsive";

import CONSTANTS from "../../../constants";

const DropdownBox = ({
  boxRef,
  children,
  content: { ariaLabelClose, submitText, title },
  onClose,
  onSubmit,
  parentWidget,
}) => {
  const isTabletOrDesktop = useMediaQuery({
    minWidth: CONSTANTS.BREAKPOINTS.MD,
  });

  const headerRef = useRef(null);
  const footerRef = useRef(null);

  const [heights, setHeights] = useState({ header: 0, footer: 0 });

  const contentInlineStyles = ({ header, footer }) => {
    if (!isTabletOrDesktop) {
      return {
        maxHeight: `calc(100% - ${header + footer}px)`,
        overflowY: "auto",
      };
    }
    return undefined;
  };

  useLayoutEffect(() => {
    if (!isTabletOrDesktop && headerRef.current && footerRef.current) {
      setHeights({
        header: headerRef.current.offsetHeight,
        footer: footerRef.current.offsetHeight,
      });
    }
  }, [isTabletOrDesktop]);

  return (
    <ClickAwayListener onClickAway={onClose}>
      <form onSubmit={onSubmit}>
        <div className="dropdown-box">
          <div className="dropdown-box__header" ref={headerRef}>
            {!isTabletOrDesktop && (
              <button
                aria-label={ariaLabelClose}
                className="dropdown-box__header-button"
                onClick={onClose}
              >
                <i
                  aria-hidden="true"
                  className="am-icon am-icon-functional-secondary-close"
                />
              </button>
            )}
            <h2 className="dropdown-box__header-title">{title}</h2>
          </div>
          <div
            className="dropdown-box__content"
            ref={boxRef}
            style={contentInlineStyles(heights)}
          >
            <div className="dropdown-box__content-inner">{children}</div>
          </div>
          <div className="dropdown-box__footer" ref={footerRef}>
            <button
              type="submit"
              className="dropdown-box__footer-submit"
              data-track-id="dropdown-search"
              data-track-click={`${parentWidget}-passenger-dropdown`}
              data-track-type="internal"
            >
              {submitText}
            </button>
          </div>
        </div>
      </form>
    </ClickAwayListener>
  );
};

export default DropdownBox;
