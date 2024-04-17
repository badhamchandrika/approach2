import { useEffect } from "react";
import { isNotFilled } from "../helpers/isNotFilled";

const useProgressiveFields = (dependencies, setIsProgressive) => {
  useEffect(() => {
    const readyForDisclosure = dependencies.reduce((accum, dependencie) => {
      return accum && !isNotFilled(dependencie);
    }, true);

    if (readyForDisclosure) {
      setIsProgressive(true);
    }
  }, dependencies);
};

export default useProgressiveFields;
