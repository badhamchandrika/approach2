import React from "react";

export const AnotherItem = ({ title, subtitle }) => {
  return (
    <div className="another-item">
      <div className="another-item__icon">
        <span className="am-icon am-icon-search" />
      </div>
      {title && (
        <div className="another-item__title">
          <p>{title}</p>
        </div>
      )}
      {subtitle && (
        <div className="another-item__subtitle">
          <p>{subtitle}</p>
        </div>
      )}
    </div>
  );
};
