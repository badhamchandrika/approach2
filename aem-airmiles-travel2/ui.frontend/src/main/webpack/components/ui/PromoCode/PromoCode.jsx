import React, { useContext, useState } from "react";
import { IconPromoCode } from "./IconPromoCode";
import classNames from "classnames";
import { getContext } from "../../../helpers/getContext";
import { getClassNamesByElement } from "../../../helpers/getClassNamesByElement";

const PromoCode = ({ parentWidget }) => {
  const {
    gsPromotionCode,
    setGsPromotionCode,
    promo_code,
    add_promo_code,
    errorFields,
    setErrorFields,
    classNameObject,
  } = useContext(getContext(parentWidget));

  const [promoCodeSelected, setPromoCodeSelected] = useState(false);

  const [isFocused, setIsFocused] = useState(false);

  const wrapperStyles = classNames(`promo-code`, {
    ...getClassNamesByElement(classNameObject, "promo-code"),
  });
  const labelStyles = classNames(`moving-label`, {
    "moving-label--focused": gsPromotionCode || isFocused,
  });

  const PROMO_CODE_ID = "PromoCodeId";

  const handleChange = (e) => {
    setGsPromotionCode(e.target.value);
    setErrorFields(errorFields.filter((obj) => obj.inputId !== e.target.id));
  };

  return (
    <div className={wrapperStyles}>
      {promoCodeSelected ? (
        <div className="promo-code__inner d-flex aling-items-center">
          <div className="promo-code__icon">
            <IconPromoCode />
          </div>
          <label htmlFor={PROMO_CODE_ID} className={labelStyles}>
            {promo_code}
          </label>
          <input
            id={PROMO_CODE_ID}
            type="text"
            className="promo-code__input"
            value={gsPromotionCode}
            onChange={handleChange}
            onFocus={() => setIsFocused(true)}
            onBlur={() => setIsFocused(false)}
          />
        </div>
      ) : (
        <button
          className="cta-button cta-button--text"
          onClick={() => setPromoCodeSelected(true)}
        >
          <IconPromoCode /> {add_promo_code}
        </button>
      )}
    </div>
  );
};

export default PromoCode;
