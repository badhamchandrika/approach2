import React, { useState, useRef } from "react";
import {
  lock as disableBodyScroll,
  unlock as enableBodyScroll,
} from "tua-body-scroll-lock";
import Modal from "react-modal";
import RadioGroup from "../RadioGroup/RadioGroup";

const sortByValues = ["dateAdded", "expiryDateObj"];

const Sort = ({ label, setSortBy, buttonText, sortBy }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const modalRef = useRef(null);

  const handleChange = (e) => {
    setSortBy(e.target.value);
  };

  const handleAfterOpenModal = () => disableBodyScroll(modalRef.current);

  const handleCloseModal = () => {
    enableBodyScroll(modalRef.current);
    setIsModalOpen(false);
  };

  const setSortByWrapper = (idx) => {
    setSortBy(sortByValues[idx]);
  };

  return (
    <>
      <div className="d-flex gap-2 sort align-items-center">
        <button
          className="sort-list"
          onClick={() => setIsModalOpen(true)}
          onChange={handleChange}
        >
          <span className="am-icon am-icon-menu" /> Sort
        </button>
        <Modal
          className="sort-modal"
          contentLabel={label}
          isOpen={isModalOpen}
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
          <header className="filters__modal-header">
            <h3 className="sort-modal__title">{label}</h3>
            <button onClick={handleCloseModal} className="filters__modal-close">
              <div className="am-icon am-icon-functional-secondary-close" />
            </button>
          </header>
          <main className="filters__modal-content" ref={modalRef}>
            <RadioGroup
              selectedRadio={sortByValues.indexOf(sortBy)}
              radioOptions={[
                {
                  title: "Most recent",
                  desc: "",
                },
                {
                  title: "Expiring soon",
                  desc: "",
                },
              ]}
              setSelectedRadio={setSortByWrapper}
            />
          </main>
          <footer className="filters__modal-footer">
            <button
              className="sort-modal__submit-btn"
              onClick={handleCloseModal}
            >
              {buttonText}
            </button>
          </footer>
        </Modal>
      </div>
    </>
  );
};

export default Sort;
