import React from 'react';

// The function should receive a memoized callback otherwise
// the listener is detached and attached from the DOM on every
// render as it is a different function
const useOnMouseDown = (memoizedCallback, isDesktopOnly) => {
  React.useEffect(() => {
    document.addEventListener('mousedown', memoizedCallback);
    if(!isDesktopOnly) document.addEventListener('touchstart', memoizedCallback);
    return () => {
      document.removeEventListener('mousedown', memoizedCallback);
      if(!isDesktopOnly) document.removeEventListener('touchstart', memoizedCallback);
    };
  }, [memoizedCallback]);
};

export default useOnMouseDown;
