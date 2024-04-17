import React, { useMemo, useState } from 'react';
import PropTypes from 'prop-types';

import PillsItem from './PillsItem';


const MAX_PILLS_VISIBLE = 2;

const Pills = ({ pillItems, onClearPills, onClickPill }) => {
  const [isListExpanded, setIsListExpanded] = useState(false);

  const handleOnToggleList = () => {
    setIsListExpanded((prevState) => !prevState);
  };

  const pillsExceedsMax = useMemo(
    () => (pillItems ? pillItems.length > MAX_PILLS_VISIBLE : true),
    [pillItems]
  );

  const pillsVisible = useMemo(() => {
    return !isListExpanded && pillsExceedsMax
      ? pillItems.slice(0, MAX_PILLS_VISIBLE)
      : pillItems;
  }, [pillsExceedsMax, pillItems, isListExpanded]);

  const pillsHidden = useMemo(() => {
    return pillItems ? pillItems.length - MAX_PILLS_VISIBLE : 0;
  }, [pillItems]);

  return (
    !!pillsVisible.length && (
      <div className="pills">
        {!!pillsVisible.length &&
          pillsVisible.map((item, index) => (
            <PillsItem key={index} item={item} onClickPill={onClickPill} />
          ))}
        {pillsExceedsMax && (
          <button
            onClick={handleOnToggleList}
            className="pills__item pills__item-toggle"
          >
            <span className="pills__item-text">
              {isListExpanded ? (
                'Show less'
              ) : (
                <>
                  <span className="am-icon am-icon-functional-plus pills__item-icon" />
                  {pillsHidden}
                </>
              )}
            </span>
          </button>
        )}
        {!!pillItems.length && (
          <button className="pills__clear-button" onClick={onClearPills}>
            Clear all
          </button>
        )}
      </div>
    )
  );
};

Pills.propTypes = {
  pillItems: PropTypes.array,
  onClearPills: PropTypes.func,
  onClickPill: PropTypes.func,
};

export default Pills;
