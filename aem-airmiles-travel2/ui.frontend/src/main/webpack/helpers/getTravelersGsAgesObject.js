import { getChildsInfantsArray } from "./getChildsInfantsArray"

export const getTravelersGsAgesObject = (child,inSeat) => getChildsInfantsArray(child,inSeat).reduce(
    (acum, age, index) => ({ ...acum, [`gsAge${index + 1}`]: age }),
    {}
  )