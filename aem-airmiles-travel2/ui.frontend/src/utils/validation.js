/**
 * Check if the value is validate to decrease
 * @param {state} value The state to check value
 * @param {string} name The state to check value - by default is '' - enter only 'adults'
 * @returns true if apply
 */
export const minValidation = (value, name = '') => {
  return name === 'adults' ? (value <= 1 ? true : false) : value <= 0 ? true : false;
};

/**
 * Check if the value is validate to increase
 * @param {state} value The state to check value
 * @param {string} name The state to check value - by default is '' - enter only 'adults'
 * @returns true if apply
 */
export const maxValidation = (value, name = '') => {
  return name === 'adults' ? (value >= 8 ? true : false) : value >= 5 ? true : false;
};


export const maxValidationChildren = (totalPax,totalChildren) => {
  return (totalPax >= 9 || totalChildren >=5 ? true : false);
};
