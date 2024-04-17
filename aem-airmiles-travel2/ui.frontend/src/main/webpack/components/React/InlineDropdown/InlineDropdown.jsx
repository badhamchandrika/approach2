import React, { useEffect, useRef } from 'react';
import { v4 as uuidv4 } from 'uuid';
import ClickAwayListener from 'react-click-away-listener';
import { IconTrip } from './iconTrip.jsx';

const InLineDropdown = ({
    label,
    dataTrackClick = '',
    options = [],
    activeLabel = '',
    selectedValueCallBack = value => value,
    tripType,
  }) => {
  const [isActiveSelect, setIsActiveSelect] = React.useState(false);
  const [selectValue, setSelectValue] = React.useState(activeLabel);
  const ddRef = React.useRef(null);
  const triggerRef = React.useRef(null);
  const listRef = React.useRef(null);
  const [openModal, setOpenModal] = React.useState(false);

  const removeSpecialChars = value =>
    value.toString().replace(/[^a-zA-Z0-9]/g, '_');

  const handleDropdownSelection = (value, e, isMouse) => {
    e.preventDefault();
    const el = e.target;
    el.classList.remove('hovered');
    if (isMouse || e.key === 'Enter') {
      setOpenModal(false);
      clearSelection();
      setSelectValue(value);
      makeSelection(el);
      closeDropDown();
    } else if (e.key === 'ArrowDown' || (e.key === 'Tab' && e.shiftKey === false)) {
      if (el.nextSibling != null) {
        el.nextSibling.classList.add('hovered');
        el.nextSibling.focus();
      } else {
        goToFirstItem();
      }
    } else if (e.key === 'ArrowUp' || (e.key === 'Tab' && e.shiftKey === true)) {
      if (el.previousSibling != null) {
        el.previousSibling.classList.add('hovered');
        el.previousSibling.focus();
      } else {
        goToLastItem();
      }
    } else if (e.key === 'PageUp' || e.key == 'Home') {
      goToFirstItem();
    } else if (e.key === 'PageDown' || e.key === 'End') {
      goToLastItem();
    } else if (e.key === 'Escape') {
      closeDropDown();
    }
  };

  const makeSelection = (target) => {
    target.classList.remove('hovered');
    target.classList.add('selected');
    target.setAttribute('aria-selected', 'true');
  };

  const clearSelection = () => {
    const listItems = listRef.current.children;
    for (let item of listItems) {
      if (item.classList.contains('selected')) {
        item.classList.remove('selected');
        item.setAttribute('aria-selected', 'false');
      } else if (item.classList.contains('hovered')) {
        item.classList.remove('hovered');
      }
    }
  };

  const goToFirstItem = () => {
    listRef.current.children[0].classList.add('hovered');
    listRef.current.children[0].focus();
  };

  const goToLastItem = () => {
    const lastListItem = listRef.current.children.length - 1;
    listRef.current.children[lastListItem].classList.add('hovered');
    listRef.current.children[lastListItem].focus();
  };

  const uniqId = uuidv4();

  useEffect(() => {
    setSelectValue(activeLabel);

    // Preselect the activeLabel
    if (activeLabel.toString() != '') {
      const preselectedClass = removeSpecialChars(getOptionFromValue(activeLabel).value);
      const preselected = document.querySelector(`.optionItem_${preselectedClass}`);
      if (preselected) {
        makeSelection(preselected);
      }
    }
  }, [activeLabel]);

  const getOptionFromValue = value => {
    return options.find(option => option.value === value) || { value: '' };
  };

  const prevSelectValue = useRef(selectValue);

  useEffect(() => {
    if (!isActiveSelect && selectValue !== prevSelectValue.current) {
      selectedValueCallBack(selectValue);
      prevSelectValue.current = selectValue;
    }
  }, [selectValue, isActiveSelect]);

  const renderLabel = getOptionFromValue(selectValue).text || '';
  const dataTrackId =
    getOptionFromValue(selectValue).dataTrackId || selectValue;

  const ddTriggerHandler = (ddRef, e, isMouse) => {
    if (isMouse || e.key === 'Enter') {
      e.preventDefault();
      if (ddRef.current.classList.contains('active') || openModal) {
        setOpenModal(false);
        closeDropDown();
      } else {
        setOpenModal(true);
        openDropdown();
        preselectFirstOrSelectedItem();
      }
    } else if (e.key === 'ArrowDown' || e.key === 'ArrowUp') {
      e.preventDefault();
      setOpenModal(true);
      openDropdown();
      preselectFirstOrSelectedItem();
    }
  };

  const openDropdown = () => {
    ddRef.current.classList.add('active');
    triggerRef.current.setAttribute('aria-expanded', 'true');
    triggerRef.current.focus();
  };

  const closeDropDown = () => {
    ddRef.current.classList.remove('active');
    triggerRef.current.removeAttribute('aria-expanded', 'true');
    triggerRef.current.focus();
  };

  const preselectFirstOrSelectedItem = () => {
    // find out if one is already selected and set focus on it
    let selectedExists = false;
    const listItems = listRef.current.children;
    for (let item of listItems) {
      if (item.classList.contains('selected')) {
        item.focus();
        selectedExists = true;
      }
    }

    // if none selected, set focus on first item
    if (!selectedExists) {
      const firstChild = listRef.current.children[0];
      firstChild?.focus({
        preventScroll: true
      });
      firstChild?.classList.add('hovered');
    }
  };

  const ddBlurHandler = (ddRef, e) => {
    if (!ddRef.current.contains(e.relatedTarget) && ddRef.current.classList.contains('active')) {
      ddRef.current.classList.remove('active');
    }
  };

  const generatePopUpButtonId = () => {
    if (!label) {return `InLineDropdownSelection${uniqId}`;}
    return label.replace(/\s/g, '');
  };

  const popUpButtonId = generatePopUpButtonId();

  const handleCloseBox = () => {
    setOpenModal(false);
    ddRef.current.classList.remove('active');
    triggerRef.current.setAttribute('aria-expanded', 'false');
  };

  return (
    <div className='InLineDropdown InLineDropdown__rebrand'> 
      <ClickAwayListener onClickAway={handleCloseBox}>
        <div
          className="ui inline dropdown"
          onBlur={(e) => ddBlurHandler(ddRef, e)}
          ref={ddRef}
        >
          <button
            type="button"
            className="text"
            ref={triggerRef}
            aria-haspopup="listbox"
            aria-labelledby={`${uniqId} ${popUpButtonId}`}
            data-track-id={dataTrackId}
            data-track-click={dataTrackClick}
            onClick={e => ddTriggerHandler(ddRef, e, true)}
            onKeyDown={e => ddTriggerHandler(ddRef, e, false)}
            id={popUpButtonId}
          >
            <div className='btn-text'>
              {tripType && <IconTrip/>}  {renderLabel}
            </div>
            <span className={openModal ? "am-icon am-icon-functional-arrow-up" : "am-icon am-icon-functional-arrow-down"}></span>
          </button>
          <ul
            role="listbox"
            ref={listRef}
            className="menu transition"
            aria-labelledby={uniqId}
          >
            {options.map((option, index) => {
              if (!option) {return null;}
              const { value = '', text = '' } = option;
              const itemClass = `item text optionItem_${removeSpecialChars(value)}`;
              return (
                <li
                  tabIndex="0"
                  onClick={e => handleDropdownSelection(value, e, true)}
                  onKeyDown={e => handleDropdownSelection(value, e, false)}
                  className={itemClass}
                  key={index}
                  role="option"
                  aria-selected="false"
                >
                  {text}
                </li>
              );
            })}
          </ul>
        </div>
      </ClickAwayListener>
    </div>


  );
};

export default InLineDropdown;
