import React from 'react';
import PropTypes from 'prop-types';

const PillsItem = ({ item, onClickPill }) => {
  const { group, label } = item;
  return (
    <button className="pills__item" onClick={() => onClickPill(item)}>
      <span className="pills__item-text">
      {group && `${group}:`} {label}
      </span>
      <span className="pills__item-icon am-icon am-icon-functional-secondary-close" />
    </button>
  );
};

PillsItem.prototype = {
  item: PropTypes.shape({
    group: PropTypes.string,
    label: PropTypes.string,
    value: PropTypes.string,
  }).isRequired,
  onClickPill: PropTypes.func,
};

export default PillsItem;
