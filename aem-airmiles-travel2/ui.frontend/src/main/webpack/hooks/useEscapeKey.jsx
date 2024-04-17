import { useEffect } from "react";

const KEY_EVENT_TYPE = "keyup";

/**
 * Hook to handle the Escape key press and distpatch a callback
 * @param {Function} callback - The callback function to be executed when the Escape key is pressed.
 */
const useEscapeKey = (callback, submit = false) => {
  useEffect(() => {
    const handleEscKey = (event) => {
      if (event.key === "Escape") {
        if (submit) {
          callback(event);
        } else {
          callback();
        }
      }
    };

    document.addEventListener(KEY_EVENT_TYPE, handleEscKey);

    return () => {
      document.removeEventListener(KEY_EVENT_TYPE, handleEscKey);
    };
  }, [callback]);
};

export default useEscapeKey;
