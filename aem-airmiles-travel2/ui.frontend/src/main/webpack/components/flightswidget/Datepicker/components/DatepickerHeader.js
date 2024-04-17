import { NextMonthSvg } from "./Svgs";
import React, { useRef, useEffect } from "react";
import CloseButton from "../../../ui/CloseButton/CloseButton";

export const DatepickerHeader = ({
  state,
  onClickClose,
  oneWay,
  onKeyDown,
  isTablet,
  firstDateLabel,
  returnDateLabel,
  formattedCommaStartDate,
  formattedCommaEndDate,
  msg_close,
}) => {
  const firstDateRef = useRef(null);

  useEffect(() => {
    if (firstDateRef.current) {
      setTimeout(() => {
        firstDateRef.current.focus();
      }, 1);
    }
  }, [firstDateRef]);

  return (
    <div className="datepicker__header">
      <div
        className="datepicker__dates"
        style={{
          display: "flex",
        }}
      >
        <div
          ref={firstDateRef}
          tabIndex={0}
          className="datepicker__headerDate"
          style={{
            marginRight: "16px",
          }}
        >
          <span className="datepicker__dateSpan">
            {state?.startDate ? formattedCommaStartDate : firstDateLabel}
          </span>
          {state.focusedInput === "startDate" && (
            <div className="datepicker__dateHighlight" />
          )}
        </div>
        {!oneWay && (
          <>
            <div>
              <NextMonthSvg />
            </div>
            <div
              tabIndex={0}
              className="datepicker__headerDate"
              style={{
                marginLeft: "16px",
              }}
            >
              <span className="datepicker__dateSpan">
                {state?.endDate ? formattedCommaEndDate : returnDateLabel}
              </span>
              {state.focusedInput === "endDate" && (
                <div className="datepicker__dateHighlight" />
              )}
            </div>
          </>
        )}
      </div>
      {isTablet && (
        <CloseButton
          onClick={onClickClose}
          onKeyDown={onKeyDown}
          ariaLabel={msg_close}
        />
      )}
    </div>
  );
};
