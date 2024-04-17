import classNames from 'classnames';
import React, { useEffect, useRef, useState } from 'react';
import { lock as disableBodyScroll, clearBodyLocks as clearAllBodyScrollLocks } from "tua-body-scroll-lock";

import {
  CloseSvg,
  DoneSvg,
  DownArrowSvg,
  NextSvg,
  TimeSvg,
} from '../../flightswidget/Datepicker/components/Svgs';
import { useMobileCheck } from '../../flightswidget/Datepicker/hooks/useDatepickerCustom/useMobileCheck';
import useOnClickOutside from '../../flightswidget/Datepicker/hooks/useOnClickOutside';
import CONSTANTS from '../../../constants';
import { useFocusOutside } from '../../flightswidget/Datepicker/hooks/useFocusOutside';

const numbers = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"];

const TimeDropdown = ({
  text,
  isDropOff = false,
  pickupTime,
  dropOffTime,
  setgsPickUpTime,
  setgsDropOffTime,
  showPickUpDropdown,
  setShowPickUpDropdown,
  showDropOffDropdown,
  setShowDropOffDropdown,
  gsDepartureDate,
  gsReturnDate,
  timesArr,
  msg_timedd_pickup_time,
  msg_timedd_dropoff_time,
  msg_pickup_date,
  msg_dropoff_date,
}) => {
  const [selectedIndex, setSelectedIndex] = useState(CONSTANTS.TIMES_DROP_DEFAULT_VALUE);
  const dropdownBoxRef = useRef(null);

  const inputRef = useRef(null);

  const { isMobile } = useMobileCheck();

  useOnClickOutside(dropdownBoxRef, () => handleClose());

  const itemsRef = useRef([]);

  useEffect(() => {
    itemsRef.current = itemsRef.current.slice(0, timesArr.length);
  }, [timesArr, showPickUpDropdown, showDropOffDropdown]);

  const handleSelect = (idx) => {
    setSelectedIndex(idx);
    if (!isDropOff) {
      setgsPickUpTime(timesArr[idx]);
      if (isMobile) {
        setShowPickUpDropdown(false);
        setShowDropOffDropdown(true);
      } else {
        setShowPickUpDropdown(false);
      }
    } else {
      setgsDropOffTime(timesArr[idx]);

      if (!isMobile) {
        setShowDropOffDropdown(false);
      }
    }
  };

  const [selectString, setSelectString] = useState("");

  useEffect(() => {
    if (selectString.length === 2) {
      setSelectString("");
    }

    const delayDebounceFn = setTimeout(() => {
      setSelectString("");
    }, 300);

    return () => clearTimeout(delayDebounceFn);
  }, [selectString]);

  useEffect(() => {
    for (const [index, time] of timesArr.entries()) {
      const timeNumber = time.split(":")[0];
      const lastDigit = selectString.split("")[selectString.length - 1];

      if (selectString.length === 1 && timeNumber === lastDigit) {
          setTimeout(() => {
            itemsRef.current[index]?.focus();
          }, 1);
          return;
      } else if (selectString.length === 2 && timeNumber === selectString) {
          setTimeout(() => {
            itemsRef.current[index]?.focus();
          }, 1);
      }
    }
  }, [selectString]);

  const handleOpenKeyDown = (ev) => {
    if (ev.key === " " || ev.key === "Enter") {
      ev.preventDefault();
      handleDropdown();
      setTimeout(() => {
        itemsRef.current[selectedIndex]?.focus();
      }, 1);
    }
  };

  const handleSelectKeyDown = (ev, key, idx) => {
    if (numbers.includes(key)) {
      setSelectString(selectString + String(key));
    }

    if (key === "Escape") {
      handleClose();
    }

    if(key === " ") {
      ev.preventDefault();
      setSelectedIndex(idx);
    }

    if (key === "Enter") {
      ev.preventDefault();
      setSelectedIndex(idx);
      handleClose();
    }

    if (key === "ArrowDown") {
      ev.preventDefault();
      if (idx < timesArr.length - 1) {
        itemsRef.current[idx + 1].focus();
      } else {
        itemsRef.current[0].focus();
      }
    }

    if (key === "ArrowUp") {
      ev.preventDefault();
      if (idx > 0) {
        itemsRef.current[idx - 1].focus();
      } else {
        itemsRef.current[timesArr.length - 1].focus();
      }
    }
  };

  const handleDropdown = () => {
    if (!isDropOff) {
      setShowPickUpDropdown(true);
    } else {
      setShowDropOffDropdown(true);
    }
    setTimeout(() => {
      itemsRef.current[selectedIndex]?.focus();
    }, 1);
  };

  const handleClose = () => {
    clearAllBodyScrollLocks();

    setShowPickUpDropdown(false);
    setShowDropOffDropdown(false);

    setTimeout(() => {
      inputRef.current.focus();
    },1);
  };

  const handleDateClick = () => {
    if (isDropOff && isMobile) {
      setShowDropOffDropdown(false);
      setShowPickUpDropdown(true);
    }
  };

  const openBox =
    (!isDropOff && showPickUpDropdown) || (isDropOff && showDropOffDropdown);

  useEffect(() => {
    if (openBox && isMobile) {
      disableBodyScroll(dropdownBoxRef.current);
    }
  }, [openBox, isMobile]);

  useFocusOutside(dropdownBoxRef, () => handleClose());

  return (
    <>
      <div
        className="timedropdown__inputContainer"
        onKeyDown={handleOpenKeyDown}
      >
        <div
          className="timedropdown__input"
          tabIndex={0}
          onClick={handleDropdown}
          ref={inputRef}
        >
          <TimeSvg />
          <div className="timedropdown__inputContent">
            <span className="timedropdown__inputLabel">{text}</span>
            <span className="timedropdown__inputDate">
              {timesArr[selectedIndex]}
            </span>
          </div>
        </div>
        <div className="timedropdown__chevron">
          <DownArrowSvg />
        </div>
      </div>
      {openBox && (
        <div className="dropdown__container" ref={dropdownBoxRef}>
          <div className="dropdown__header">
            <div className="dropdown__closeContainer">
              <div
                className="dropdown__closeBtn"
                tabIndex={0}
                onClick={handleClose}
              >
                <CloseSvg />
              </div>
            </div>
            <div className="dropdown__headerContent">
              <div
                className="dropdown__dateContainer"
                onClick={handleDateClick}
              >
                <p className="dropdown__date">
                  {gsDepartureDate
                    ? String(gsDepartureDate.toDateString())
                    : msg_pickup_date}
                </p>
                <p className="dropdown__dateLabel">
                  {pickupTime ? pickupTime : msg_timedd_pickup_time}
                </p>
                {!isDropOff && <div className="dropdown__dateSelection" />}
              </div>
              <div className="dropdown__dateIcon">
                <NextSvg />
              </div>
              <div className="dropdown__dateContainer">
                <p className="dropdown__date">
                  {gsReturnDate
                    ? String(gsReturnDate.toDateString())
                    : msg_dropoff_date}
                </p>
                <p className="dropdown__dateLabel">
                  {dropOffTime ? dropOffTime : msg_timedd_dropoff_time}
                </p>
                {isDropOff && <div className="dropdown__dateSelection" />}
              </div>
            </div>
          </div>
          {timesArr.map((time, idx) => {
            return (
              <button
                key={idx}
                ref={(el) => (itemsRef.current[idx] = el)}
                onKeyDown={(ev) => handleSelectKeyDown(ev, ev.key, idx)}
                className={classNames(`dropdown__timeItem`, {
                  'dropdown__timeItem--active': idx === selectedIndex,
                })}
                onClick={() => handleSelect(idx)}
              >
                <div className="dropdown__iconDiv">
                  <DoneSvg />
                </div>
                <p>{time}</p>
              </button>
            );
          })}
        </div>
      )}
    </>
  );
};

export default TimeDropdown;
