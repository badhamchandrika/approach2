document.addEventListener("DOMContentLoaded", function () {
  const video = document.getElementById("localVideoPlayer");

  // eslint-disable-next-line curly
  if (!video) return;

  const [header] = document.getElementsByTagName("header");
  const HEADER_HEIGHT = header.offsetHeight;

  function isElementInViewport(el) {
    const rect = el.getBoundingClientRect();
    return rect.top < window.innerHeight && rect.bottom >= HEADER_HEIGHT;
  }

  function muteVideo() {
    video.muted = true;
  }

  function throttle(func, limit = 300) {
    let inThrottle = false;

    return function () {
      if (!inThrottle) {
        func.apply(this, arguments);
        inThrottle = true;

        setTimeout(() => {
          inThrottle = false;
        }, limit);
      }
    };
  }

  function isVideoPlaying(video) {
    return !!(
      video.currentTime > 0 &&
      !video.paused &&
      !video.ended &&
      video.readyState > 2
    );
  }

  function handleScroll() {
    if (!isElementInViewport(video) && isVideoPlaying(video)) {
      muteVideo();
    }
  }

  // Event listener for scrolling with debounce
  window.addEventListener("scroll", throttle(handleScroll));
});
