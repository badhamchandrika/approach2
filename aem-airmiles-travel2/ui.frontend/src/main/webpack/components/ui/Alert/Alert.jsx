import React, {
  useEffect,
  useImperativeHandle,
  useRef,
  forwardRef,
} from "react";
import classNames from "classnames";
import PropTypes from "prop-types";
import AlertBodyMessage from "./AlertBodyMessage";

const Alert = forwardRef(function ErrorFields(
  {
    additionalErrors = [],
    errorFields,
    errorHeaderMessage,
    isDropdown = false,
    singleMessage = false,
    type = "error",
  },
  ref
) {
  const childRef = useRef(null);

  useImperativeHandle(ref, () => ({
    focus: () => {
      childRef.current.focus();
    },
  }));

  const alertStyles = classNames(`alert-message`, {
    "alert-message--dropdown": isDropdown,
    "alert-message--single": singleMessage,
  });

  const alertEdgeStyles = classNames(`alert-message__edge`, {
    "alert-message__edge--error": type === "error",
    "alert-message__edge--info": type === "info",
    "alert-message__edge--warning": type === "warning",
  });

  const alertIconWrapperStyles = classNames(`alert-message__icon`, {
    "alert-message__icon--error": type === "error",
    "alert-message__icon--info": type === "info",
    "alert-message__icon--warning": type === "warning",
  });

  const alertIconItemStyles = classNames(`am-icon`, {
    "am-icon-travel-alert": type === "error",
    "am-icon-travel-warning": type === "warning",
    "am-icon-info-small": type === "info",
  });

  useEffect(() => {
    if (type === "error") {
      childRef.current.focus();
    }
  }, []);

  return (
    <div className={alertStyles} ref={childRef} role="alert" tabIndex={type==="error" ? 0 : -1}>
      {/* line */}
      <div className={alertEdgeStyles}></div>
      {/* icon */}
      <div className={alertIconWrapperStyles}>
        <span className={alertIconItemStyles} />
      </div>
      {/* alert type and message */}
      <div className="alert-message__body">
        {errorHeaderMessage && (
          <AlertBodyMessage bodyMessage={errorHeaderMessage} />
        )}
        {/* list go to error */}
        {!!errorFields?.length && (
          <ul
            className={`error-fields-list ${
              !!additionalErrors?.length ? "pb-3" : "pb-2"
            }`}
          >
            {errorFields.map((obj) => (
              <li key={obj.inputId}>
                <a href={`#${obj.inputId}`}>{obj.text}</a>
              </li>
            ))}
          </ul>
        )}
        {!!additionalErrors?.length &&
          additionalErrors.map((additionalError, index) => (
            <AlertBodyMessage key={index} bodyMessage={additionalError} />
          ))}
      </div>
    </div>
  );
});

Alert.propTypes = {
  errorFields: PropTypes.arrayOf(
    PropTypes.shape({
      inputId: PropTypes.string.isRequired,
      text: PropTypes.string.isRequired,
    })
  ),
  errorHeaderMessage: PropTypes.shape({
    type: PropTypes.string.isRequired,
    message: PropTypes.arrayOf(PropTypes.string).isRequired,
  }),
  type: PropTypes.oneOf(["error", "info", "warning"]),
  isDropdown: PropTypes.bool,
  additionalErrors: PropTypes.arrayOf(
    PropTypes.shape({
      type: PropTypes.string.isRequired,
      message: PropTypes.arrayOf(PropTypes.string).isRequired,
    })
  ),
};

export default Alert;
