import React, { useEffect, useRef, useState } from "react";
import { useMediaQuery } from "react-responsive";
import {
  lock as disableBodyScroll,
  unlock as enableBodyScroll,
} from "tua-body-scroll-lock";
import CONSTANTS from "../../../constants";

import {
  maxValidation,
  minValidation,
} from "../../../../../utils/validation.js";
import useEscapeKey from "../../../hooks/useEscapeKey.jsx";
import Alert from "../../ui/Alert/Alert.jsx";
import ValidationWrapper from "../../ui/ValidationWrapper/ValidationWrapper.jsx";
import { passengersAriaLabel } from "../../../helpers/passegnersAriaLabel";
import Select from "../../ui/Select/Select.jsx";
import { getContactUrl } from "../../../helpers/getURL";
import ButtonDropdown from "../../ui/ButtonDropdown/ButtonDropdown";
import DropdownBox from "../../ui/DropdownBox";
import Pax from "./Pax";
import { IconPassengers } from "./iconPassengers.jsx";

const PassengersBox = ({
  sendTravellers,
  warnings,
  labels,
  dropdownLabel,
  showAirlineInfo = false,
  ariaMessages = {
    msg_add_adult: "",
    msg_remove_adult: "",
    msg_add_child: "",
    msg_remove_child: "",
    msg_add_infant: "",
    msg_remove_infant: "",
  },
  msg_airline_age_rules,
  singularPersonsTitle,
  pluralPersonsTitle,
  msg_update,
  msg_adv_search_text_init,
  msg_adv_search_text_link,
  msg_adv_search_text_end,
  msg_empty_field,
  msg_adults,
  msg_adult,
  msg_children,
  msg_child,
  msg_child_age_1,
  msg_child_age_2,
  msg_child_age_3,
  msg_child_age_4,
  msg_child_age_5,
  msg_infants,
  msg_infant,
  msg_close,
  parentWidget,
  airline_age_rules_url = CONSTANTS.AIRLINE_AGE_RULES_URL,
  airline_age_rules_text = "",
}) => {
  const [openModal, setOpenModal] = React.useState(false);
  const [dataShow, setDataShow] = React.useState(`1  ${singularPersonsTitle}`);
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [data, setData] = React.useState();
  const [totalPax, setTotalPax] = React.useState(1);
  const [adults, setAdults] = React.useState(1);
  const [child, setChild] = React.useState([]);
  const [inSeat, setInSeat] = React.useState(0);
  const isMobile = useMediaQuery({ query: "(max-width: 767.98px)" });
  const { errorTotalPax, warningChildren, errorSomeFields, toManyChildren } =
    warnings;
  const [errorFields, setErrorFields] = React.useState([]);
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [showErrors, setShowErrors] = useState(false);
  const [additionalErrors, setAdditonalErrors] = useState([]);
  const [ageSelected, setAgeSelected] = useState({
    child_1: false,
    child_2: false,
    child_3: false,
    child_4: false,
    child_5: false,
  });
  const modalRef = useRef(null);
  const errorFieldsRef = useRef(null);

  const warningChildrenMessage = {
    type: "Warning:",
    message: warningChildren,
  };

  const msgChildsArr = [
    msg_child_age_1,
    msg_child_age_2,
    msg_child_age_3,
    msg_child_age_4,
    msg_child_age_5,
  ];

  const isErrorRegistered = (errorsCollection, errorText) => {
    return errorsCollection.find((error) => error.message[0] === errorText);
  };

  useEffect(() => {
    if (totalPax > 8) {
      if (!isErrorRegistered(additionalErrors, errorTotalPax.message[0])) {
        setAdditonalErrors([errorTotalPax]);
      }
    } else {
      if (child.length + inSeat > 5) {
        setAdditonalErrors([toManyChildren]);
      } else {
        setAdditonalErrors([]);
      }
    }
  }, [totalPax]);

  /**
   * Increase the value of the state -
   * If the name is child will generate a new object child, otherwise
   * increase the value
   * @param {any} e - The event to prevent
   * @param {state} value - The state that change
   * @param {state} setter - The state setter to change the value
   * @param {string} name - Name of the state to validate different situation
   */
  const increaseButton = (e, value, setter, name = "") => {
    e.preventDefault();
    if (name === "child") {
      const newChild = { id: value + 1, age: 0 };
      setter((child) => [...child, newChild]);
    } else {
      const newValue = value + 1;
      setter(newValue);
    }
    setTotalPax(totalPax + 1);
  };

  /**
   * Decrease the value of the state
   * If the name is child will filter the id and delete it
   * @param {any} e - The event to prevent
   * @param {state} value - The state that change
   * @param {state} setter - The state setter to change the value
   * @param {string} name - Name of the state to validate different situation
   */
  const decreaseButton = (e, value, setter, name = "") => {
    e.preventDefault();
    if (name === "child") {
      const newChilds = child.filter((ch) => ch.id !== value);
      if (errorFields.length > 0) {
        isEmptyFields(newChilds.length);
      }
      setter(newChilds);
      setAgeSelected({ ...ageSelected, [`child_${value}`]: false });
    } else {
      const adults = value - 1;
      setter(adults);
    }
    setTotalPax(totalPax - 1);
  };

  const isErrors = () => isEmptyFields() || additionalErrors.length > 0;

  /**
   * Send a parsed data, then update the dataShow state
   * and close the modal
   */
  const onSubmit = (e) => {
    e.preventDefault();
    if (!isErrors()) {
      const dataToSend = {
        adults,
        child: child.map((value) => value.age),
        inSeat,
      };
      sendTravellers(dataToSend);
      setDataShow(
        totalPax > 1
          ? `${totalPax} ${pluralPersonsTitle}`
          : `1 ${singularPersonsTitle}`
      );
      handleCloseBox();
    } else {
      errorFieldsRef?.current?.focus();
    }
  };

  /**
   * Check whether the child fields have errors
   */
  const isEmptyFields = (fieldsLenght = child.length) => {
    let isEmptyFields = false;
    const errorsAcum = [];
    for (let i = 0; i < fieldsLenght; i++) {
      if (child[i].age === 0) {
        errorsAcum[i] = {
          text: msgChildsArr[i],
          inputId: `child_${i + 1}`,
        };
        isEmptyFields = true;
      }
    }
    setErrorFields([...errorsAcum]);
    if (isEmptyFields) {
      setShowErrors(true);
    } else {
      setShowErrors(false);
    }
    return isEmptyFields;
  };

  /**
   * Map the child array to create a select input to change child age
   * @param {number} index index iterator
   * @returns a select form to change the age of the child
   */
  const createSelectChild = (index) => {
    const id = index + 1;
    const currentAge =
      child.find((ch) => ch.id === id).age > 0
        ? child.find((ch) => ch.id === id).age
        : "";

    const handleChange = (event) => {
      const newAge = parseInt(event.target.value);
      const updateChild = child.map((ch) =>
        ch.id === id ? { ...ch, age: newAge } : ch
      );
      setChild(updateChild);
      setAgeSelected({ ...ageSelected, [`child_${id}`]: true });
      setErrorFields(
        errorFields.filter((obj) => obj.inputId !== `child_${id}`)
      );
    };

    const initialChildAge =
      parentWidget === CONSTANTS.WIDGET.ACTIVITIES
        ? CONSTANTS.MIN_CHILD_AGE_NO_FLIGHTS
        : CONSTANTS.MIN_CHILD_AGE_FLIGHTS;
    const lastChildAge =
      parentWidget === CONSTANTS.WIDGET.ACTIVITIES
        ? CONSTANTS.MAX_CHILD_AGE_NO_FLIGHTS
        : CONSTANTS.MAX_CHILD_AGE_FLIGHTS;

    const options = [];
    for (let i = initialChildAge; i <= lastChildAge; i++) {
      options.push({ value: "" + i, label: i, key: i });
    }

    return (
      <ValidationWrapper
        key={id}
        validationError={
          errorFields.find((field) => field?.inputId === `child_${id}`)
            ? { message: msg_empty_field }
            : null
        }
      >
        <Select
          defaultValue={msgChildsArr[id - 1]}
          id={`child_${id}`}
          onChange={handleChange}
          options={options}
          selected={ageSelected}
          value={currentAge}
        />
      </ValidationWrapper>
    );
  };

  const handleOpenBox = () => {
    setOpenModal(true);
  };

  const handleCloseBox = () => {
    if (isMobile && modalRef.current) {
      enableBodyScroll(modalRef.current);
    }
    setOpenModal(false);
  };

  /**
   * Hook that implement close modal pressinng Esc Key
   */
  useEscapeKey(onSubmit, true);

  React.useEffect(() => {
    if (openModal && isMobile) {
      disableBodyScroll(modalRef.current);
    }
  }, [openModal, isMobile]);

  return (
    <>
      <ButtonDropdown
        content={{ label: dropdownLabel, text: dataShow.split(" ")[0] }}
        isActive={openModal}
        onClick={handleOpenBox}
        parentWidget={parentWidget}
        icon={<IconPassengers />}
      />
      {openModal && (
        <DropdownBox
          boxRef={modalRef}
          content={{
            ariaLabelClose: msg_close,
            submitText: msg_update,
            title: pluralPersonsTitle,
          }}
          onClose={onSubmit}
          onSubmit={onSubmit}
          parentWidget={parentWidget}
        >
          <>
            {(errorFields.length > 0 || additionalErrors.length > 0) && (
              <Alert
                ref={errorFieldsRef}
                errorFields={errorFields}
                errorHeaderMessage={
                  errorFields.length > 0 ? errorSomeFields : null
                }
                additionalErrors={additionalErrors}
                isDropdown
              />
            )}

            <div className="pax-list">
              <Pax
                paxLabel={labels.adult}
                disableDecButton={minValidation(adults, "adults")}
                decButtonClick={(e) => decreaseButton(e, adults, setAdults)}
                decButtonTxt={ariaMessages.msg_remove_adult}
                disableIncButton={maxValidation(adults, "adults")}
                incButtonClick={(e) =>
                  increaseButton(e, adults, setAdults, "adults")
                }
                incButtonTxt={ariaMessages.msg_add_adult}
                pArialabel={passengersAriaLabel(
                  "adult",
                  adults,
                  msg_adults,
                  msg_adult,
                  msg_child,
                  msg_children,
                  msg_infant,
                  msg_infants
                )}
                paxNumber={adults}
              />
              <Pax
                paxLabel={labels.child}
                disableDecButton={minValidation(child.length)}
                decButtonClick={(e) =>
                  decreaseButton(e, child.length, setChild, "child")
                }
                decButtonTxt={ariaMessages.msg_remove_child}
                disableIncButton={maxValidation(child.length)}
                incButtonClick={(e) =>
                  increaseButton(e, child.length, setChild, "child")
                }
                incButtonTxt={ariaMessages.msg_add_child}
                pArialabel={passengersAriaLabel(
                  "child",
                  child.length,
                  msg_adults,
                  msg_adult,
                  msg_child,
                  msg_children,
                  msg_infant,
                  msg_infants
                )}
                paxNumber={child.length}
              />
              {child.length > 0 && (
                <div className="select-child">
                  {child.map((ch, i) => (
                    <div className="select-child__item">
                      {createSelectChild(i)}
                    </div>
                  ))}
                </div>
              )}
              {parentWidget !== CONSTANTS.WIDGET.ACTIVITIES &&
                child.length > 0 && (
                  <div className="pax-child-error" aria-live="polite">
                    <Alert
                      errorHeaderMessage={warningChildrenMessage}
                      type="warning"
                      isDropdown
                    />
                  </div>
                )}
              {parentWidget !== CONSTANTS.WIDGET.ACTIVITIES && (
                <Pax
                  paxLabel={labels.infant}
                  disableDecButton={minValidation(inSeat)}
                  decButtonClick={(e) => decreaseButton(e, inSeat, setInSeat)}
                  decButtonTxt={ariaMessages.msg_remove_infant}
                  disableIncButton={maxValidation(inSeat)}
                  incButtonClick={(e) => increaseButton(e, inSeat, setInSeat)}
                  incButtonTxt={ariaMessages.msg_add_infant}
                  pArialabel={passengersAriaLabel(
                    "infant",
                    inSeat,
                    msg_adults,
                    msg_adult,
                    msg_child,
                    msg_children,
                    msg_infant,
                    msg_infants
                  )}
                  paxNumber={inSeat}
                />
              )}
            </div>
            {!isMobile && parentWidget !== CONSTANTS.WIDGET.ACTIVITIES && (
              <div className="notice">
                <p>
                  {`${msg_adv_search_text_init} `}
                  <a
                    href={getContactUrl()}
                    className="inner-link"
                    data-track-id="passengers-advance-search"
                    data-track-click={`${parentWidget}-passenger-dropdown`}
                    data-track-type="internal"
                  >
                    {`${msg_adv_search_text_link}`}
                  </a>
                  {` ${msg_adv_search_text_end}`}
                </p>
              </div>
            )}
            {showAirlineInfo && (
              <div className="link-to">
                <a
                  href={airline_age_rules_url}
                  data-track-id="airline-age-rules"
                  data-track-click={`${parentWidget}-passenger-dropdown`}
                  data-track-type="internal"
                >
                  {airline_age_rules_text || msg_airline_age_rules}&nbsp;
                  <span className="am-icon am-icon-external-link"></span>
                </a>
              </div>
            )}
          </>
        </DropdownBox>
      )}
    </>
  );
};

export default PassengersBox;
