import * as React from "react";
import { useMediaQuery } from "react-responsive";
import { lock as disableBodyScroll, unlock as enableBodyScroll } from "tua-body-scroll-lock";

import Alert from "../../ui/Alert/Alert.jsx";
import Room from "./Room.jsx";
import useEscapeKey from "../../../hooks/useEscapeKey.jsx";

import CONSTANTS from "../../../constants.js";
import { getContactUrl } from "../../../helpers/getURL.js";
import ButtonDropdown from "../../ui/ButtonDropdown";
import DropdownBox from "../../ui/DropdownBox/DropdownBox.jsx";


const RoomBox = ({
  rooms,
  setRooms,
  warnings,
  msg_adv_search_text_init,
  msg_adv_search_text_end,
  msg_adv_search_text_middle,
  msg_adv_search_text_link,
  msg_room,
  msg_rooms,
  msg_child,
  msg_traveller,
  msg_travellers,
  msg_add_another_room,
  msg_maximum_number_rooms,
  msg_empty_field,
  msg_children_ages,
  msg_remove,
  msg_update,
  msg_close,
  parentWidget,
  showAddRoom = true,
  ...props
}) => {
  const [openModal, setOpenModal] = React.useState(false);
  const [dataShow, setDataShow] = React.useState(`1 ${msg_room}, 1 ${msg_traveller}`);
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [data, setData] = React.useState();
  const [totalPax, setTotalPax] = React.useState(1);
  const isMobile = useMediaQuery({ query: "(max-width: 767.98px)" });
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [showErrors, setShowErrors] = React.useState(false);
  const [errorFields, setErrorFields] = React.useState([]);
  const [additionalErrors, setAdditonalErrors] = React.useState([]);
  const modalRef = React.useRef(null);
  const [ageSelected, setAgeSelected] = React.useState({});

  const { errorTotalPax, errorSomeFields } = warnings;

  const errorFieldsRef = React.useRef(null);

  React.useEffect(() => {
    if (totalPax > 8) {
      setAdditonalErrors([...additionalErrors, errorTotalPax]);
    }
    if (totalPax <= 8) {
      setAdditonalErrors([]);
    }
  }, [totalPax]);

  /**
   * Add a Room to the rooms state
   * @param {any} e - The event to prevent
   */
  const addRoom = () => {
    setRooms((rooms) => [
      ...rooms,
      { id: rooms.length + 1, adults: 1, children: [] },
    ]);
    setTotalPax(totalPax + 1);
  };

  /**
   * Send a parsed data, then update the dataShow state
   * and close the modal
   */
  const onSubmit = (e) => {
    e.preventDefault();
    if (!isErrors()) {
      setDataShow(setButtonText(rooms));
      handleCloseBox();
      setData(rooms);
    } else {
      errorFieldsRef?.current?.focus();
    }
  };

  const isErrors = () => isEmptyFields() || additionalErrors.length > 0;

  /**
   * Check whether the child fields have errors
   */
  const isEmptyFields = (roomsLenght = rooms.length) => {
    let isEmptyFields = false;
    const errorsAcum = [];
    for (let j = 0; j < roomsLenght; j++) {
      for (let i = 0; i < rooms[j].children.length; i++) {
        if (rooms[j].children[i] === 0) {
          errorsAcum.push({
            message: `${msg_empty_field}`,
            text: `${msg_room}_${j + 1}_${msg_child} ${i + 1}`,
            inputId: `room_${j + 1}_child_${i + 1}`,
          });
          isEmptyFields = true;
        }
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
   * Set the button text according the number of rooms, adults and children
   * @param {state} rooms - Rooms state
   * @returns Text to show in button
   */
  const setButtonText = (rooms) => {
      const totalRooms = rooms.length;
      let room =
        totalRooms < 2
          ? `${totalRooms} ${msg_room}`
          : `${totalRooms} ${msg_rooms}`;
      let traveller =
        totalPax < 2
          ? `, ${totalPax} ${msg_traveller}`
          : `, ${totalPax} ${msg_travellers}`;
      const text = room + traveller;
      return text;
  };

  const openRoomBox = () => {
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
  useEscapeKey(onSubmit,true);

  React.useEffect(() => {
    if (openModal && isMobile) {
      disableBodyScroll(modalRef.current);
    }
  }, [openModal, isMobile]);

  return (
    <>
      <ButtonDropdown
        content={{ label: msg_rooms, text: dataShow }}
        isActive={openModal}
        onClick={openRoomBox}
        parentWidget={parentWidget}
      />
      {openModal && (
        <DropdownBox
          boxRef={modalRef}
          content={{
            ariaLabelClose: msg_close,
            submitText: msg_update,
            title: msg_travellers,
          }}
          onClose={onSubmit}
          onSubmit={onSubmit}
          parentWidget={parentWidget}
        >
          {(errorFields.length > 0 || additionalErrors.length > 0) && (
            <Alert
              ref={errorFieldsRef}
              errorFields={errorFields}
              errorHeaderMessage={
                errorFields.length > 0 ? errorSomeFields : null
              }
              additionalErrors={
                additionalErrors !== [] ? additionalErrors : undefined
              }
              isDropdown
            />
          )}
          <div className="d-flex flex-column gap-4">
            {rooms.map((room, index) => (
              <Room
                key={room.id}
                index={index}
                totalPax={totalPax}
                setTotalPax={setTotalPax}
                rooms={rooms}
                setRooms={setRooms}
                errorFields={errorFields}
                setErrorFields={setErrorFields}
                isEmptyFields={isEmptyFields}
                msg_empty_field={msg_empty_field}
                msg_children_ages={msg_children_ages}
                msg_remove={msg_remove}
                msg_room={msg_room}
                msg_child={msg_child}
                ageSelected={ageSelected}
                setAgeSelected={setAgeSelected}
                {...props}
              />
            ))}
            {showAddRoom && rooms.length < 4 && isMobile && (
              <button
                className="cta-link add-room"
                disabled={totalPax >= 8}
                onClick={addRoom}
                type="button"
              >
                <span className="am-icon am-icon-functional-plus"></span>
                {msg_add_another_room}
              </button>
            )}
          </div>
          {rooms.length === 4 && (
            <div className="notice">
              <p>{msg_maximum_number_rooms}</p>
            </div>
          )}
          {showAddRoom && rooms.length < 4 && !isMobile && (
            <button
              className="cta-link add-room"
              disabled={totalPax >= 8}
              onClick={addRoom}
              type="button"
            >
              <span className="am-icon am-icon-functional-plus"></span>
              {msg_add_another_room}
            </button>
          )}
          {!isMobile && parentWidget !== CONSTANTS.WIDGET.STAYS && (
            <>
              {msg_adv_search_text_middle && (
                <p>{msg_adv_search_text_middle}</p>
              )}
              {msg_adv_search_text_init && (
                <p>
                  {`${msg_adv_search_text_init} `}
                  <a
                    href={getContactUrl()}
                    className="inner-link"
                    data-track-id="rooms-advance-search"
                    data-track-click={`${parentWidget}-advance-search`}
                    data-track-type="internal"
                  >
                    {`${msg_adv_search_text_link}`}
                  </a>
                   {` ${msg_adv_search_text_end}`}
                </p>
              )}
            </>
          )}
        </DropdownBox>
      )}
    </>
  );
};

export default RoomBox;
