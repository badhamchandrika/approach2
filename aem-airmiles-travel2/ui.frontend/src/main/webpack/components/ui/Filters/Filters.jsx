import React, {
  useMemo,
  useState,
  useCallback,
  useRef,
  useEffect,
} from "react";
import Modal from "react-modal";
import { useMediaQuery } from "react-responsive";
import {
  lock as disableBodyScroll,
  unlock as enableBodyScroll,
} from "tua-body-scroll-lock";

import { IconFilter } from "./IconFilter.jsx";
import CheckboxGroup from "../CheckboxGroup";
import CollapsibleContent from "../CollapsibleContent";

import CONSTANTS from "../../../constants.js";

(() => {
  if (document.querySelector(".root")) {
    Modal.setAppElement(".root");
  }
})();

const Filters = ({
  button_mobile,
  filter_clear,
  title,
  title_modal,
  filterItems,
  filterGroups,
  numberOfResults,
  handleClearFilter,
  selectedFiltersCount,
  setSelectedFiltersCount,
  handleFilters,
  resetFilterCount,
}) => {
  const isDesktop = useMediaQuery({
    minWidth: CONSTANTS.BREAKPOINTS.XL,
  });
  const [isModalOpen, setIsModalOpen] = useState(false);
  const modalRef = useRef(null);

  const isFilterApplied = useMemo(
    () => filterItems.some((item) => item.checked === true),
    [filterItems]
  );

  useEffect(() => {
    resetFilterCount();
  }, [filterGroups]);

  const filtersByGroup = useCallback(
    (groupName) => {
      return filterItems.filter(({ group }) => group === groupName);
    },
    [filterItems]
  );

  const disableBodyOptions = {
    allowTouchMove: (el) =>
      el.getElementsByClassName("collapsible__collapse-content"),
  };

  const handleCloseModal = () => {
    enableBodyScroll(modalRef.current);
    setIsModalOpen(false);
  };

  const handleAfterOpenModal = () =>
    disableBodyScroll(modalRef.current, disableBodyOptions);

  return (
    <div className="filters">
      {!isDesktop ? (
        <>
          <button
            onClick={() => setIsModalOpen(true)}
            className="filters__button"
          >
            <IconFilter /> {button_mobile}
          </button>
          <Modal
            className="filters__modal"
            contentLabel={title_modal}
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
              <h3 className="filters__modal-title">{title_modal}</h3>
              <button
                onClick={handleCloseModal}
                className="filters__modal-close"
              >
                <div className="am-icon am-icon-functional-secondary-close" />
              </button>
            </header>
            <main className="filters__modal-content" ref={modalRef}>
              <ul className="collapsible">
                {filterGroups.map((group, index) => (
                  <CollapsibleContent
                    title={group}
                    count={selectedFiltersCount[group]}
                    index={index}
                  >
                    <CheckboxGroup
                      vertical
                      items={filtersByGroup(group)}
                      setValue={handleFilters}
                      setCount={setSelectedFiltersCount}
                    />
                  </CollapsibleContent>
                ))}
              </ul>
            </main>
            <footer className="filters__modal-footer">
              <button
                className="cta-button cta-button--secondary"
                disabled={!isFilterApplied}
                onClick={() => handleClearFilter(resetFilterCount)}
              >
                {filter_clear}
              </button>
              <button className="cta-button" onClick={handleCloseModal}>
                Show {numberOfResults} results
              </button>
            </footer>
          </Modal>
        </>
      ) : (
        <>
          <h3 className="filters__title">{title}</h3>
          <ul className="collapsible">
            {filterGroups.map((group, index) => {
              return (
                <CollapsibleContent
                  title={group}
                  count={selectedFiltersCount[group]}
                  index={index}
                >
                  <CheckboxGroup
                    vertical
                    items={filtersByGroup(group)}
                    setValue={handleFilters}
                    setCount={setSelectedFiltersCount}
                    showCount
                  />
                </CollapsibleContent>
              );
            })}
          </ul>
        </>
      )}
    </div>
  );
};

export default Filters;
