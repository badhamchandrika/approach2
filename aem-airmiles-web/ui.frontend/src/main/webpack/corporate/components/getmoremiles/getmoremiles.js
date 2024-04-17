(() => {
  // Check if component exists
  if (!document.getElementsByClassName("cmp-getmoremiles")[0]) {
    return;
  }

  // Add bg image
  const section = document.getElementsByClassName(
    "cmp-getmoremiles__subcategories"
    )[0];
    
  const bgPathSrc = document.getElementById("getMoreMilesBgPath");
  if (!bgPathSrc) {
    return;
  }
  const bgPath = bgPathSrc.innerText;
  section.style.backgroundImage = `url(${window.location.origin}${bgPath})`;
  
  // Add active class to first slide
  const carousel = document.getElementById("getmoremiles-carousel");
  carousel.children[0].classList.add("active");


  // Get buttons
  const prev = document.getElementsByClassName("carousel-control-prev")[0];
  const next = document.getElementsByClassName("carousel-control-next")[0];

  prev.style.display = "none";

  // Hide next button on last slide, hide prev button on first slide
  const carouselElement = document.querySelector("#getMoreMilesCarousel");
  carouselElement.addEventListener("slide.bs.carousel", (e) => {
    if (e.to == carousel.children.length - 1) {
      next.style.display = "none";
      prev.style.display = "inline-block";
    } else if (e.to == 0) {
      next.style.display = "inline-block";
      prev.style.display = "none";
    } else {
      next.style.display = "inline-block";
      prev.style.display = "inline-block";
    }
  });

  // Randlomly select one of 2 images sources for each image with 2 sources.
  const rotatingImages = Array.from(document.querySelectorAll('[src2]'));
  const whichImageToShow = Math.random();

  whichImageToShow > 0.5 && rotatingImages.forEach(img => img.src = img.attributes.src2.value);

})();
