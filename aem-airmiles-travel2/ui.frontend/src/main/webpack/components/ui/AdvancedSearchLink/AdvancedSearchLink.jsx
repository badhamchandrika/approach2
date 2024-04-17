import React from "react";
import { getAdvancedSearchUrl } from "../../../helpers/getURL";
import classNames from "classnames";

function AdvancedSearchLink({
  anchorText,
  buildSearchQuery,
  className,
  parent,
  runmodes,
}) {
  const wrapperClass = classNames("advanced-search-link", {
    "cta-link": !className,
    [className]: className,
  });

  return (
    <button
      onClick={() => {
        window.location.href =
          getAdvancedSearchUrl(runmodes) + buildSearchQuery();
      }}
      className={wrapperClass}
      data-track-id="to-advance-search"
      data-track-click={`${parent}-advance-search`}
      data-track-type="internal"
    >
      <span className="am-icon am-icon-search" />
      <span>{anchorText}</span>
    </button>
  );
}

export default AdvancedSearchLink;
