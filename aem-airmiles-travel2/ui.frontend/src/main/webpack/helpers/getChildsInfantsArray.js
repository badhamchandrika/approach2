export const getChildsInfantsArray = (childsArr, infants) => {
  let childsArrCopy = childsArr.slice();
  for (let i = 1; i <= infants; i++) {
    childsArrCopy.push(1);
  }
  return childsArrCopy;
};
