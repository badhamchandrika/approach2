import React from 'react';

function Suggestion(
  {
    suggestion,
    value,
    idx,
    handleKeyDown,
    handleClickSuggestion
  },
  ref
) {
  const { Code, Name, CountryCode } = suggestion;

  const generateSuggestionMarkup = (value, string) => {

    
    /* eslint-disable curly */
    if(typeof string === 'object') string = string[0];
    if(typeof value === 'object') value = value[0];
    
    const match = string.toLowerCase().indexOf(value.toLowerCase());

    if (match === -1) {
      return string;
    }

    const beginningSlice = string.slice(0, match);
    const matchingSlice = string.slice(match, match + value.length);
    const endSlice = string.slice(match + value.length);

    return `${beginningSlice}<span class="suggestion__text--match">${matchingSlice}</span>${endSlice}`;
  };

  return (
    <div
      className="location-input__suggestion"
      onClick={()=> handleClickSuggestion(suggestion)}
      ref={(el) => (ref.current[idx] = el)}
      tabIndex={0}
      onKeyDown={(e) => {
        return handleKeyDown(e, idx, suggestion);
      }}
    >
      <span
        className="suggestion__text"
        dangerouslySetInnerHTML={{
          __html: generateSuggestionMarkup(value, Name),
        }}
      ></span>
      {', '}
      <span
        className="suggestion__text"
        dangerouslySetInnerHTML={{
          __html: generateSuggestionMarkup(value, CountryCode),
        }}
      ></span>{' '}
      {'('}
      <span
        className="suggestion__text"
        dangerouslySetInnerHTML={{
          __html: generateSuggestionMarkup(value, Code),
        }}
      ></span>
      {')'}
    </div>
  );
}

export default React.forwardRef(Suggestion);
