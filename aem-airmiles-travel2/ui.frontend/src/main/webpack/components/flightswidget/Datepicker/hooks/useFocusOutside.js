import { useEffect } from "react";

export function useFocusOutside(ref, fn) {
  function handleFocusOutside(event) {
    if (ref?.current && !ref.current.contains(event.target)) {
      fn();
    }
  }

  useEffect(() => {
    if (ref) {
      document && document.addEventListener("focusin", handleFocusOutside);
    }
    return () => {
      document && document.removeEventListener("focusin", handleFocusOutside);
    };
  });
}
