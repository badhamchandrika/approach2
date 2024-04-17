import React from "react";

const AlertBodyMessage = ({ bodyMessage }) => {
  return (
    <>
      <span className="alert-message__body-type">{bodyMessage.type}</span>{" "}
      <span
        className="alert-message__body-message"
        dangerouslySetInnerHTML={{
          __html: bodyMessage.message?.[0],
        }}
      />
    </>
  );
};

export default AlertBodyMessage;
