import React, { useContext } from "react";
import classNames from "classnames";

import CONSTANTS from "../../../constants";
import { getContext } from "../../../helpers/getContext";
import { getClassNamesByElement } from "../../../helpers/getClassNamesByElement";

import { Row } from "../Row";
import SearchButton from "../../ui/SearchButton/SearchButton";
import AdvancedSearchLink from "../../ui/AdvancedSearchLink/AdvancedSearchLink";
import CheckBox from "../../ui/Checkbox/Checkbox";
import ValidationWrapper from "../../ui/ValidationWrapper/ValidationWrapper";
import PromoCode from "../../ui/PromoCode/PromoCode";

const { BUNDLING, RENTALS, STAYS } = CONSTANTS.WIDGET;

export const WidgetFooter = ({
  advancedSearchText,
  buildSearchQuery,
  children,
  className,
  parentWidget,
  searchButtonText,
  submit,
}) => {
  const {
    bundlingProductsHeading,
    classNameObject,
    errorFields,
    handleCheckboxProduct,
    isProductBundling,
    productCheckboxes,
    runmodes,
  } = useContext(getContext(parentWidget));

  const isCheckboxDisabled = (id) =>
    (id === STAYS && productCheckboxes[RENTALS].checked) ||
    (id === RENTALS && productCheckboxes[STAYS].checked);

  const { sm: isMobile, md: isTablet, xl: isDesktop } = classNameObject;

  const wrapperClass = classNames("widget-footer", {
    ...getClassNamesByElement(classNameObject, "widget-footer"),
    [className]: className,
  });

  const widgetFooterRight = classNames("col-auto widget-footer__right", {
    ...getClassNamesByElement(classNameObject, "widget-footer__right"),
  });

  const columnRightClass = classNames("col-auto");

  const promoColumnStyle = {
    ...((isTablet || isDesktop) && { minWidth: "290px" }),
  };

  const promoValidationError = errorFields?.find(
    (field) => field?.inputId === CONSTANTS.PROMO_CODE_ID
  );

  return (
    <footer className={wrapperClass}>
      <Row parentWidget={parentWidget} className="widget-footer__actions">
        <div className="col-auto widget-footer__left">
          {parentWidget === BUNDLING && (
            <>
              <div className="products-heading">{bundlingProductsHeading}</div>
              <Row parentWidget={parentWidget}>
                {Object.values(productCheckboxes).map(
                  ({ checked, id, text }) => {
                    return (
                      !isProductBundling(id) &&
                      !(isProductBundling(STAYS) && id === RENTALS) && (
                        <div className="col-md-auto">
                          <CheckBox
                            checked={checked}
                            disabled={isCheckboxDisabled(id)}
                            id={id}
                            label={text}
                            setValue={({ checked }) =>
                              handleCheckboxProduct(id, checked)
                            }
                          />
                        </div>
                      )
                    );
                  }
                )}
              </Row>
            </>
          )}
          {!isDesktop && children}
        </div>
        <div className={widgetFooterRight}>
          <div className={classNames(`row`)}>
            <div className={columnRightClass} style={promoColumnStyle}>
              <ValidationWrapper
                bottomElement
                parentWidget={parentWidget}
                validationError={
                  promoValidationError
                    ? { message: promoValidationError.message }
                    : null
                }
              >
                <PromoCode parentWidget={parentWidget} />
              </ValidationWrapper>
            </div>
            <div className={columnRightClass}>
              <SearchButton
                submit={submit}
                parent={parentWidget}
                buttonText={searchButtonText}
              />
            </div>
          </div>
          {isMobile && (
            <AdvancedSearchLink
              anchorText={advancedSearchText}
              buildSearchQuery={buildSearchQuery}
              parent={parentWidget}
              runmodes={runmodes}
            />
          )}
        </div>
      </Row>
      {isDesktop && children}
    </footer>
  );
};
