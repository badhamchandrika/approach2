import React from "react";
import { useMediaQuery } from "react-responsive";
import CONSTANTS from "../../../constants";

const Pax = ({
  paxLabel,
  disableDecButton,
  decButtonClick,
  decButtonTxt,
  disableIncButton,
  incButtonClick,
  incButtonTxt,
  pArialabel,
  paxNumber,
}) => {
  const isDesktop = useMediaQuery({ minWidth: CONSTANTS.BREAKPOINTS.XL });

  return (
    <div className="pax">
      <p className={isDesktop ? "pax-widget__text" : "pax__font-size"}>
        {paxLabel}
      </p>
      <div className={isDesktop ? "buttons" : "buttons buttons__mobile"}>
        <button
          disabled={disableDecButton}
          onClick={decButtonClick}
          aria-label={decButtonTxt}
        >
          <span className="am-icon am-icon-functional-minus"></span>
        </button>
        <p aria-label={pArialabel} tabIndex="0">
          {paxNumber}
        </p>
        <button
          disabled={disableIncButton}
          onClick={incButtonClick}
          aria-label={incButtonTxt}
        >
          <span className="am-icon am-icon-functional-plus"></span>
        </button>
      </div>
    </div>
  );
};

export default Pax;
