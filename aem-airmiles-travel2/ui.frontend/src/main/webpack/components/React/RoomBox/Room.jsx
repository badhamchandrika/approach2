import React from "react";
import {
  maxValidation,
  minValidation,
  maxValidationChildren,
} from "../../../../../utils/validation.js";
import { passengersAriaLabel } from "../../../helpers/passegnersAriaLabel.js";
import Select from "../../ui/Select/Select.jsx";
import ValidationWrapper from "../../ui/ValidationWrapper/ValidationWrapper.jsx";
import Pax from '../../flightswidget/PassengersBox/Pax';
import CONSTANTS from "../../../constants.js";

const Room = ({
  index,
  totalPax,
  setTotalPax,
  setRooms,
  rooms,
  errorFields,
  setErrorFields,
  isEmptyFields,
  msg_empty_field,
  msg_adults,
  msg_adult,
  msg_children,
  msg_child,
  msg_children_ages,
  msg_remove,
  msg_room,
  msg_add_adult = "",
  msg_remove_adult = "",
  msg_add_child = "",
  msg_remove_child = "",
  ageSelected,
  setAgeSelected,
  msg_child_age_1,
  msg_child_age_2,
  msg_child_age_3,
  msg_child_age_4,
  msg_child_age_5,
}) => {
  const msgChildsArr = [
    msg_child_age_1,
    msg_child_age_2,
    msg_child_age_3,
    msg_child_age_4,
    msg_child_age_5,
  ];

  /**
   * Increase the value of the state and update the values of room, totalPax and rooms
   * @param {any} e - The event to prevent
   * @param {state} value - The state that change
   * @param {string} name - Name of the state to validate different situation
   */
  const increaseButton = (event, name) => {
    event.preventDefault();
    const roomsCopy = rooms;
    if (name === "adult") {
      roomsCopy[index].adults = roomsCopy[index].adults + 1;
      setRooms(roomsCopy);
      setTotalPax(totalPax + 1);
    } else {
      roomsCopy[index].children.push(0);
      setRooms(roomsCopy);
      setTotalPax(totalPax + 1);
    }
  };

  /**
   * Decrease the value of the state and update the values of room, totalPax and rooms
   * @param {any} e - The event to prevent
   * @param {state} value - The state that change
   * @param {string} name - Name of the state to validate different situation
   */
  const decreaseButton = (event, name) => {
    event.preventDefault();
    const roomsCopy = rooms;
    if (name === "adult") {
      roomsCopy[index].adults = roomsCopy[index].adults - 1;
      setRooms(roomsCopy);
      setTotalPax(totalPax - 1);
    } else {
      roomsCopy[index].children.pop();
      if (errorFields.length > 0) {
        isEmptyFields(roomsCopy.length);
      }
      setRooms(roomsCopy);
      setTotalPax(totalPax - 1);
    }
  };

  /**
   *  Delete the room and updated the totalPax state
   * @param {state} room - The state that will be removed
   */
  const removeRoom = (index) => {
    setTotalPax(totalPax - rooms[index].adults - rooms[index].children.length);
    const newRooms = rooms.filter((r) => r.id !== index + 1);
    newRooms.map((room, index) => (room.id = index + 1));
    setRooms(newRooms);
  };

  /**
   *
   * @param {key} event the key pressed
   * @param {state} room - The state that will be deleted
   */
  const handleEnter = (event, index) => {
    if (event.key === "Enter") {
      removeRoom(index);
    }
  };

  /**
   * Map the child array to create a select input to change child age
   * @param {number} indexChild indexChild iterator
   * @returns a select form to change the age of the child
   */
  const createSelectChild = (indexChild) => {
    const id = indexChild + 1;
    const currentAge =
      rooms[index].children[indexChild] > 0
        ? rooms[index].children[indexChild]
        : "";
    const handleChange = (event) => {
      const newAge = parseInt(event.target.value);
      const roomsCopy = rooms;
      roomsCopy[index].children[indexChild] = newAge;
      setRooms(roomsCopy);
      setAgeSelected({
        ...ageSelected,
        [`room_${index + 1}_child_${id}`]: true,
      });
      setErrorFields(
        errorFields.filter(
          (obj) => obj.inputId !== `room_${index + 1}_child_${id}`
        )
      );
    };

    const options = [];
    for (let i = 1; i <= CONSTANTS.MAX_CHILD_AGE_FLIGHTS ; i++) {
      options.push({ value: "" + i, label: i, key: i });
    }

    return (
      <ValidationWrapper
        key={id}
        validationError={
          errorFields.find(
            (field) => field?.inputId === `room_${[index + 1]}_child_${id}`
          )
            ? { message: msg_empty_field }
            : null
        }
      >
        <Select
          defaultValue={msgChildsArr[id - 1]}
          id={`room_${index + 1}_child_${id}`}
          onChange={handleChange}
          options={options}
          selected={ageSelected}
          value={currentAge}
        />
      </ValidationWrapper>
    );
  };

  return (
    <div className="room" key={index}>
      <div className="heading">
        <p className="room-title">
          {" "}
          {msg_room} {index + 1}{" "}
        </p>
        {index > 0 && (
          <div
            className="remove"
            onClick={() => removeRoom(index)}
            tabIndex={0}
            onKeyUp={(e) => handleEnter(e, index)}
          >
            <p>{msg_remove}</p>
            <p className="am-icon am-icon-functional-secondary-close"></p>
          </div>
        )}
      </div>
        <Pax
          paxLabel={msg_adults}
          disableDecButton={minValidation(rooms[index].adults, "adults")}
          decButtonClick={(e) => decreaseButton(e, "adult")}
          decButtonTxt={msg_remove_adult}
          disableIncButton={maxValidation(totalPax, "adults")}
          incButtonClick={(e) => increaseButton(e, "adult")}
          incButtonTxt={msg_add_adult}
          pArialabel={passengersAriaLabel(
            "adult",
            rooms[index].adults,
            msg_adults,
            msg_adult,
            msg_child,
            msg_children
          )}
          paxNumber={rooms[index].adults}
        />
        <Pax
          paxLabel={msg_children_ages}
          disableDecButton={minValidation(rooms[index].children.length)}
          decButtonClick={(e) => decreaseButton(e, "child")}
          decButtonTxt={msg_remove_child}
          disableIncButton={maxValidationChildren(
            totalPax,
            rooms[index].children.length
          )}
          incButtonClick={(e) => increaseButton(e, "child")}
          incButtonTxt={msg_add_child}
          pArialabel={passengersAriaLabel(
            "child",
            rooms[index].children.length,
            msg_adults,
            msg_adult,
            msg_child,
            msg_children
          )}
          paxNumber={rooms[index].children.length}
        />
      {rooms[index].children.length > 0 && (
        <div className="select-child">
          {rooms[index].children.map((ch, i) => (
            <div className="select-child__item" key={i}>
              {createSelectChild(i)}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Room;
