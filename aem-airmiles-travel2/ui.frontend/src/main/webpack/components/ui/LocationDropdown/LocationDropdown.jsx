import React, { useState, useEffect, useRef, useCallback } from "react";
import { useMediaQuery } from "react-responsive";
import Modal from "react-modal";
import {
  lock as disableBodyScroll,
  unlock as enableBodyScroll,
} from "tua-body-scroll-lock";
import classNames from "classnames";

import CloseButton from "../CloseButton/CloseButton.jsx";
import Suggestion from "./Suggestion.jsx";
import ValidationWrapper from "../ValidationWrapper/ValidationWrapper.jsx";
import useOnMouseDown from "../../../hooks/useOnMouseDown.jsx";
import useEscapeKey from "../../../hooks/useEscapeKey.jsx";

import CONSTANTS from "../../../constants.js";

const TAB_KEY = "Tab";

(() => {
  if (document.querySelector(".root")) {
    Modal.setAppElement(".root");
  }
})();

function LocationDropdown({
  bottomElement = false,
  errorFields,
  index,
  inputId,
  json,
  msg_close,
  msg_empty_field,
  msg_search_by_city_or,
  parentWidget,
  placeholder,
  setErrorFields,
  setgsCode,
  setter,
  value,
}) {
  const [suggestions, setSuggestions] = useState([]);
  const [showSuggestions, setShowSuggestions] = useState(false);
  const [isFocused, setIsFocused] = useState(false);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const modalInputRef = useRef(null);
  const desktopInputRef = useRef(null);
  const modalContentRef = useRef(null);
  const itemsRef = useRef([]);
  const mainContainer = useRef(null);
  const refPersisted = useRef(false);
  const refPersistedValue = useRef("");
  const inputType = inputId.includes("origin")
    ? "originInput"
    : "destinationInput";
  const isTabletOrDesktop = useMediaQuery({
    minWidth: CONSTANTS.BREAKPOINTS.MD,
  });

  const containerFieldStyles = classNames(
    `location-input__container ${inputType}`,
    {
      "location-input__container--active": showSuggestions,
    }
  );

  const labelStyles = classNames(`moving-label`, {
    "moving-label--focused": value || isFocused,
  });

  const [nextFocusableElement, setNextFocusableElement] = useState(null);
  const [previousFocusableElement, setPreviousFocusableElement] =
    useState(null);

  const inputFieldStyles = classNames(`location-input__field ${inputType}`, {
    "location-input__field--filled": value,
  });

  const getSuggestions = (e, json) => {
    e.persist();
    const { value } = e.target;
    const { Origins, Destinations } = json.Package;
    const locations = inputType === "originInput" ? Origins : Destinations;

    if (value === "" || value === " ") {
      return [];
    }

    const results = locations.filter((o) => {
      let matches = false;
      if (
        o.Code.toLowerCase().includes(value.toLowerCase()) ||
        o.Name.toLowerCase().includes(value.toLowerCase()) ||
        o.CountryCode.toLowerCase().includes(value.toLowerCase())
      ) {
        matches = true;
      }

      return matches;
    });
    return results;
  };

  const makeNewLocationsArray = (val, prev, i) => {
    const locations = [...prev];
    locations[i] = val;

    return locations;
  };

  const focusableElement = (currentElement, next = true) => {
    const focusableElements = document.querySelectorAll(
      'input, button, [tabindex]:not([tabindex="-1"])'
    );
    const currentIndex = Array.from(focusableElements).indexOf(currentElement);

    if (next) {
      const nextIndex = (currentIndex + 1) % focusableElements.length;
      return focusableElements[nextIndex];
    } else {
      const previousIndex =
        currentIndex === 0 ? focusableElements.length - 1 : currentIndex - 1;
      return focusableElements[previousIndex];
    }
  };

  const clearInput = useCallback(() => {
    if (
      !refPersisted.current ||
      (refPersisted.current && refPersistedValue.current[0] !== value)
    ) {
      setter((prev) => makeNewLocationsArray("", prev, index));
    }
    setShowSuggestions(false);
  }, [index, setter, value]);

  const setSuggestion = (suggestion) => {
    const { Code, Name, CountryCode } = suggestion;

    if (setter) {
      setter((prev) => {
        const locations = makeNewLocationsArray(
          `${Name}, ${CountryCode} (${Code})`,
          prev,
          index
        );

        refPersistedValue.current = locations;

        return locations;
      });
    }

    if (setgsCode) {
      setgsCode((prev) => makeNewLocationsArray(`${Code}`, prev, index));
    }

    refPersisted.current = true;
  };

  const handleClickOutside = useCallback(
    (event) => {
      if (
        mainContainer.current &&
        !mainContainer.current.contains(event.target)
      ) {
        clearInput();
      }
    },
    [clearInput, mainContainer]
  );

  const handleEscape = (e) => {
    handleBlur(e);
    setShowSuggestions(false);
  };

  const handleKeySuggestion = (ev, id, suggestion) => {
    ev.preventDefault();
    if (ev.key === "ArrowDown") {
      itemsRef.current[id + 1]?.focus();
    }
    if (ev.key === "ArrowUp") {
      itemsRef.current[id - 1]?.focus();
    }
    if (ev.key === "Enter" || ev.key === TAB_KEY) {
      setSuggestion(suggestion);
      handleCloseModal();
      setShowSuggestions(false);
      if (ev.key === TAB_KEY && ev.shiftKey){
        previousFocusableElement.focus();
      }else{
        nextFocusableElement.focus();
      }
    }
  };

  const handleClickSuggestion = (suggestion) => {
    setSuggestion(suggestion);

    if (handleCloseModal) {
      handleCloseModal();
    }
    setShowSuggestions(false);
  };

  const handleChange = (e) => {
    setter((prev) => makeNewLocationsArray(e.target.value, prev, index));
    setSuggestions(getSuggestions(e, json));
    setErrorFields(errorFields.filter((obj) => obj.inputId !== e.target.id));
  };

  const handleFocus = (e) => {
    setIsFocused(true);
    // eslint-disable-next-line curly
    if (!value) setSuggestions([]);
    e
      ? e.target.focus()
      : (() => {
          // eslint-disable-next-line curly
          if (isTabletOrDesktop) desktopInputRef.current.focus();
        })();
  };

  const handleBlur = (e) => {
    setIsFocused(false);
    e ? e.target.blur() : desktopInputRef.current.blur();
  };

  const handleAfterOpenModal = () => {
    disableBodyScroll(modalContentRef.current);
    modalInputRef.current.focus();
  };

  const handleCloseModal = () => {
    // eslint-disable-next-line curly
    if (modalContentRef.current) enableBodyScroll(modalContentRef.current);
    setModalIsOpen(false);
  };

  const handleKey = (e) => {
    if (e.key === TAB_KEY) {
      e.preventDefault();
      if (e.shiftKey) {
        setShowSuggestions(false);
        if (previousFocusableElement) {
          previousFocusableElement.focus();
        }
      } else {
        nextFocusableElement.focus();
      }
      clearInput();
    }

    if (isTabletOrDesktop && (/^[\w\W]$/.test(e.key) || e.key === "Enter")) {
      handleClickAndFocus(e);
    }

    if (e.key === "ArrowDown") {
      e.preventDefault();
      setTimeout(() => {
        itemsRef.current[0]?.focus();
      }, 1);
    }
  };

  const handleClickModalClose = () => {
    handleCloseModal();
    setIsFocused(false);
    setShowSuggestions(false);
  };

  const handleClickAndFocus = (e) => {
    handleFocus(e);
    setShowSuggestions(true);

    if (!isTabletOrDesktop) {
      setModalIsOpen(true);
      handleBlur(e);
    }
  };

  const suggestionsArr = () =>
    suggestions.map((s, id) => (
      <Suggestion
        handleKeyDown={handleKeySuggestion}
        ref={itemsRef}
        idx={id}
        suggestion={s}
        key={`__SUGGESTION__${s.Code + s.CountryCode}`}
        value={value}
        handleClickSuggestion={handleClickSuggestion}
      />
    ));

  const suggestionsPlaceholder = () =>
    suggestions.length === 0 && (
      <div className="location-input__suggestions-placeholder">
        <span className="am-icon am-icon-search" />
        <span className="suggestion-search-text">{msg_search_by_city_or}</span>
      </div>
    );

  useOnMouseDown(handleClickOutside, true);
  useEscapeKey(handleEscape);

  useEffect(() => {
    itemsRef.current = itemsRef.current.slice(0, suggestions.length);
  }, [suggestions]);

  useEffect(() => {
    setNextFocusableElement(focusableElement(desktopInputRef.current));
    setPreviousFocusableElement(focusableElement(desktopInputRef.current, false));
  }, []);

  return (
    <ValidationWrapper
      bottomElement={bottomElement}
      parentWidget={parentWidget}
      validationError={
        errorFields?.find((field) => field?.inputId === inputId)
          ? { message: msg_empty_field }
          : null
      }
    >
      <div className={`location-input ${inputType}`}>
        <div className={containerFieldStyles} ref={mainContainer}>
          <svg
            width="12"
            height="20"
            viewBox="0 0 12 20"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M6 19.75C4.31667 19.75 2.93733 19.496 1.862 18.988C0.787333 18.4793 0.25 17.8167 0.25 17C0.25 16.5167 0.454333 16.0833 0.863 15.7C1.271 15.3167 1.83333 15.0083 2.55 14.775L3 16.2C2.68333 16.3167 2.41667 16.4457 2.2 16.587C1.98333 16.729 1.84167 16.8667 1.775 17C1.94167 17.35 2.43333 17.6457 3.25 17.887C4.06667 18.129 4.98333 18.25 6 18.25C7 18.25 7.91267 18.129 8.738 17.887C9.56267 17.6457 10.0583 17.35 10.225 17C10.175 16.8667 10.0417 16.729 9.825 16.587C9.60833 16.4457 9.34167 16.3167 9.025 16.2L9.475 14.775C10.1917 15.0083 10.75 15.3167 11.15 15.7C11.55 16.0833 11.75 16.5167 11.75 17C11.75 17.8167 11.2123 18.4793 10.137 18.988C9.06233 19.496 7.68333 19.75 6 19.75ZM6 13.85C6.3 13.2833 6.63767 12.7293 7.013 12.188C7.38767 11.646 7.75833 11.1333 8.125 10.65C8.75833 9.83333 9.26233 9.09167 9.637 8.425C10.0123 7.75833 10.2 6.93333 10.2 5.95C10.2 4.78333 9.79167 3.79167 8.975 2.975C8.15833 2.15833 7.16667 1.75 6 1.75C4.83333 1.75 3.84167 2.15833 3.025 2.975C2.20833 3.79167 1.8 4.78333 1.8 5.95C1.8 6.93333 1.99167 7.75833 2.375 8.425C2.75833 9.09167 3.25833 9.83333 3.875 10.65C4.24167 11.1333 4.61267 11.646 4.988 12.188C5.36267 12.7293 5.7 13.2833 6 13.85ZM6 16.45C5.83333 16.45 5.68333 16.396 5.55 16.288C5.41667 16.1793 5.31667 16.0333 5.25 15.85C4.83333 14.7333 4.34167 13.7957 3.775 13.037C3.20833 12.279 2.66667 11.5583 2.15 10.875C1.65 10.175 1.21667 9.454 0.85 8.712C0.483333 7.97067 0.3 7.05 0.3 5.95C0.3 4.35 0.85 3 1.95 1.9C3.05 0.8 4.4 0.25 6 0.25C7.6 0.25 8.95 0.8 10.05 1.9C11.15 3 11.7 4.35 11.7 5.95C11.7 7.05 11.521 7.97067 11.163 8.712C10.8043 9.454 10.3667 10.175 9.85 10.875C9.35 11.5583 8.81267 12.279 8.238 13.037C7.66267 13.7957 7.16667 14.7333 6.75 15.85C6.68333 16.0333 6.58333 16.1793 6.45 16.288C6.31667 16.396 6.16667 16.45 6 16.45ZM6 7.775C6.5 7.775 6.92933 7.59567 7.288 7.237C7.646 6.879 7.825 6.45 7.825 5.95C7.825 5.45 7.646 5.02067 7.288 4.662C6.92933 4.304 6.5 4.125 6 4.125C5.5 4.125 5.071 4.304 4.713 4.662C4.35433 5.02067 4.175 5.45 4.175 5.95C4.175 6.45 4.35433 6.879 4.713 7.237C5.071 7.59567 5.5 7.775 6 7.775Z"
              fill="currentColor"
            />
          </svg>
          <label htmlFor={inputId} className={labelStyles}>
            {placeholder}
          </label>

          <input
            type="text"
            autoComplete="off"
            className={inputFieldStyles}
            id={inputId}
            ref={desktopInputRef}
            value={value}
            onChange={handleChange}
            onKeyDown={handleKey}
            onFocus={handleClickAndFocus}
            onBlur={handleBlur}
            style={{
              display: modalIsOpen ? "none" : "flex",
            }}
          />

          {showSuggestions && isTabletOrDesktop && (
            <div className={`location-input__suggestions ${inputType}`}>
              {[suggestionsPlaceholder(), suggestionsArr()]}
            </div>
          )}
        </div>

        <Modal
          className="location__modal"
          contentLabel="Location"
          shouldFocusAfterRender={false}
          isOpen={modalIsOpen}
          onAfterOpen={handleAfterOpenModal}
          onRequestClose={handleCloseModal}
          style={{
            overlay: {
              zIndex: 1030,
            },
            content: {
              position: "absolute",
              backgroundColor: "white",
              bottom: 0,
              left: 0,
              right: 0,
              top: 0,
              WebkitOverflowScrolling: "touch",
            },
          }}
        >
          <div className="location-search-modal__input">
            <input
              type="text"
              autoComplete="off"
              className={`location-input__field location-input__field--mobile ${inputType}`}
              id={inputId}
              placeholder={placeholder}
              value={value}
              onChange={handleChange}
              onKeyDown={handleKey}
              ref={modalInputRef}
            />
            <CloseButton
              onClick={handleClickModalClose}
              ariaLabel={msg_close}
            />
          </div>

          <div
            className={`location-input__suggestions ${inputType}`}
            ref={modalContentRef}
          >
            {[suggestionsPlaceholder(), suggestionsArr()]}
          </div>
        </Modal>
      </div>
    </ValidationWrapper>
  );
}

export default LocationDropdown;
