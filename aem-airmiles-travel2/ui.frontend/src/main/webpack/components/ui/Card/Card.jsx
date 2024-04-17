import React from "react";
import PropTypes from "prop-types";

import CONSTANTS from "../../../constants";

const Card = ({ item }) => {
  const {
    cardDescription,
    cardDestinationCountry,
    cardImageAlt,
    cardImagePath,
    cardTagIcon,
    cardTitle,
    dealType,
    dateText,
    icon,
    url,
    variant = "generic",
  } = item;

  const imageAlt = cardImageAlt ? cardImageAlt : cardTitle;

  const anchorLabel = `${cardTagIcon ? `${cardTagIcon}. ` : ""} ${cardTitle}. ${
    cardDescription ? `${cardDescription}. ` : ""
  }${cardDestinationCountry ? `${cardDestinationCountry}. ` : ""}${dealType}`;

  return (
    <article className={`card-container card-container--${variant}`}>
      <div className="card-container__tags">
        {cardTagIcon && (
          <div className="card-container__tags-category">{cardTagIcon}</div>
        )}
      </div>
      <div className="card-container__img-container">
        <img
          className="img-container__img"
          src={cardImagePath}
          alt={imageAlt}
        />
      </div>
      <div className="card-container__body">
        <p className="card-container__body-title">
          <a
            aria-label={anchorLabel}
            className="card-container__body-link nostyle"
            data-track-click={`${variant}-card`}
            data-track-id={`${variant}-detail`}
            data-track-type="internal"
            href={url}
          >
            {cardTitle}
          </a>
        </p>
        {variant === CONSTANTS.CARD_VARIANTS.DEAL && (
          <p className="card-container__body-desc card-transition">
            {cardDescription && <span>{cardDescription}</span>}
          </p>
        )}
        {variant === CONSTANTS.CARD_VARIANTS.DEAL && dateText && (
          <p className="card-container__body-date card-transition">
            {dateText}
          </p>
        )}
        {variant === CONSTANTS.CARD_VARIANTS.DESTINATION &&
          cardDestinationCountry && (
            <p className="card-container__body-destination">
              {cardDestinationCountry}
            </p>
          )}
      </div>
      {icon && icon != "no-icon" && (
        <div className="card-container__tags-icon" aria-label={dealType}>
          <span className={`am-icon am-icon-${icon}`} />
        </div>
      )}
    </article>
  );
};

Card.propTypes = {
  item: PropTypes.shape({
    cardDescription: PropTypes.string,
    cardDestinationCountry: PropTypes.string,
    cardImageAlt: PropTypes.string.isRequired,
    cardImagePath: PropTypes.string.isRequired,
    cardTagIcon: PropTypes.string,
    cardTitle: PropTypes.string.isRequired,
    date: PropTypes.string,
    dateText: PropTypes.string,
    dealType: PropTypes.string.isRequired,
    icon: PropTypes.string,
    url: PropTypes.string,
    variant: PropTypes.oneOf(["deal", "destination", "partner", "generic"]),
  }).isRequired,
  type: PropTypes.oneOf(["deal", "destination", "partner", "generic"]),
};

export default Card;
