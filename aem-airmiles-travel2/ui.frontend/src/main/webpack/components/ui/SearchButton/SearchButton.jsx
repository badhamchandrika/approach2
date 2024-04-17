import React from "react";

function SearchButton({submit, parent, buttonText}) {
  return (
    <button
      className="cta-button cta-button--primary"
      onClick={submit}
      data-track-id="submit-search"
      data-track-click={`${parent}-search`}
      data-track-type="button"
    >
        {buttonText}
    </button>
  );
}

export default SearchButton;
