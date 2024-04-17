export const passengersAriaLabel = (passengerType, passengerCount, msg_adults, msg_adult, msg_child, msg_children,msg_infant,msg_infants) => {
  if (passengerType === "adult") {
    return `${passengerCount} ${passengerCount > 1 ? msg_adults : msg_adult}`;
  } else if (passengerType === "child") {
    return `${passengerCount} ${
      passengerCount === 1 ? msg_child : msg_children
    }`;
  } else {
    return `${passengerCount} ${
      passengerCount === 1 ? msg_infant : msg_infants
    }`;
  }
};
