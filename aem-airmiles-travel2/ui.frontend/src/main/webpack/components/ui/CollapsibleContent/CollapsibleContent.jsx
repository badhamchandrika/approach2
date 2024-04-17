import React, { useState, useEffect } from 'react';
import classNames from 'classnames';
import { UnmountClosed } from 'react-collapse';

const CollapsibleContent = ({ children, count, title, index }) => {
  const [isCollapsed, setIsCollapse] = useState(true);


  const handleToggleCollapse = () => {
    setIsCollapse((prevState) => !prevState);
  };

  useEffect(() => {
    const isDesktopView = window.innerWidth > 768;

    if (isDesktopView && index === 0) {
        setIsCollapse(false);
    } else {
        setIsCollapse(true);
    }
 }, [index]);

  return (
    <li
      className={classNames('collapsible__item', {
        'collapsible__item--opened': !isCollapsed,
      })}
    >
      <button
        aria-expanded={!isCollapsed}
        className={classNames('collapsible__button', {
          'collapsible__button--active': !isCollapsed,
        })}
        type="button"
        onClick={handleToggleCollapse}
      >
        <div className="collapsible__button-text">{title} {count ? ` (${count})` : ' '}</div>
        <div
          className={classNames(
            'collapsible__button-icon am-icon am-icon-functional-arrow-down',
            {
              'am-icon-functional-arrow-up': !isCollapsed,
            }
          )}
        />
      </button>
      <UnmountClosed
        isOpened={!isCollapsed}
        theme={{
          collapse: 'collapsible__collapse',
          content: 'collapsible__collapse-content',
        }}
      >
        {children}
      </UnmountClosed>
    </li>
  );
};

export default CollapsibleContent;
