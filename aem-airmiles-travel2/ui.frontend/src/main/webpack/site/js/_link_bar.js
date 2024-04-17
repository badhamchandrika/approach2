var addEvent = (object, type, callback) => {
  if (object == null || typeof object == "undefined") {
    return;
  }
  if (object.addEventListener) {
    object.addEventListener(type, callback, false);
  } else if (object.attachEvent) {
    object.attachEvent("on" + type, callback);
  } else {
    object["on" + type] = callback;
  }
};

document.addEventListener("DOMContentLoaded", function (event) {
  const PILL_MARGIN = 0;
  let enhancedNodeList = []; //contains the with of the elemtents all the time
  let resizingCommand = false;
  const nextButton = document.querySelector(".links-container__next-button");
  if (!nextButton) {
    return;
  }
  const prevButton = document.querySelector(".links-container__prev-button");
  if (!prevButton) {
    return;
  }

  const linksContainerWidth = () =>
    document.getElementsByClassName("links-container")[0].offsetWidth;

  const pillsContainerWidth = () =>
    document.getElementsByClassName("pills-container")[0].offsetWidth + 30;

  const viewPortWidth = () => window.innerWidth;

  const nodeListArrObjFunc = () => {
    const nodeList = document.querySelectorAll(
      ".pills-container .pill-container-wrapper"
    );
    if (!nodeList) {
      return;
    }
    const nodeListArrObj = [];
    nodeList.forEach((node) => {
      let width = 0;
      if (node.classList.contains("none")) {
        node.classList.remove("none");
        width = node.offsetWidth;
        node.classList.add("none");
      } else {
        width = node.offsetWidth;
      }
      nodeListArrObj.push({ node, width });
    });
    return nodeListArrObj;
  };

  const activeLinkIndex = (() => {
    const nodeListArrObj = nodeListArrObjFunc();
    return nodeListArrObj.findIndex((elem) =>
      elem.node.children[0].classList.contains("active")
    );
  })();

  const resizingCarousel = () => {
    let nextIndex = 1;
    let partialSum = 0;
    let margins = 0;
    const nodeListArrObj = nodeListArrObjFunc();
    enhancedNodeList = nodeListArrObj.map((object, i) => {
      const containersWidth = pillsContainerWidth();
      partialSum = partialSum + object.width;
      margins = margins + (viewPortWidth() > 764 ? PILL_MARGIN * 2 : 0);
      if (!resizingCommand) {
        if (partialSum + margins >= containersWidth) {
          object.node.classList.add("none"); //hides elements that not fit
          nextButton.classList.remove("none"); //shows next button
          nextIndex++;
          return { ...object, display: "n", containersWidth };
        } else {
          nextIndex++;
          object.node.classList.remove("none");
          return { ...object, display: "y", containersWidth };
        }
      } else {
        return { ...object };
      }
    });
    return {
      nodeListArrObj,
      enhancedNodeList,
      linksContainerWidth: linksContainerWidth(),
      partialSum,
      margins,
      total: partialSum + margins,
      nextIndex,
    };
  };

  const resizingCarousel2 = () => {
    let partialSum = 0;
    let margins = 0;
    const nodeListArrObj = nodeListArrObjFunc();
    let indexRight = activeLinkIndex;
    let indexLeft = activeLinkIndex;
    const linksContainerWidthValue = linksContainerWidth();
    while (indexRight < nodeListArrObj.length || indexLeft >= 0) {
      const containersWidth = pillsContainerWidth();

      if (indexRight < nodeListArrObj.length) {
        nextIndex = indexRight;
        indexRight++;
        //got the object
        partialSum = partialSum + nodeListArrObj[nextIndex].width;
        margins = margins + (viewPortWidth() > 764 ? PILL_MARGIN * 2 : 0);

        // change dom
        if (!resizingCommand) {
          if (partialSum + margins > containersWidth) {
            nodeListArrObj[nextIndex].node.classList.add("none"); //hides elements that not fit
            nextButton.classList.remove("none"); //shows next button
          } else {
            nodeListArrObj[nextIndex].node.classList.remove("none");
          }
        }
      }
      if (indexLeft >= 0) {
        if (indexLeft !== activeLinkIndex) {
          nextIndex = indexLeft;
          //got the object
          partialSum = partialSum + nodeListArrObj[nextIndex].width;
          margins = margins + (viewPortWidth() > 764 ? PILL_MARGIN * 2 : 0);
          // change dom
          if (!resizingCommand) {
            if (partialSum + margins > containersWidth) {
              nodeListArrObj[nextIndex].node.classList.add("none"); //hides elements that not fit
              prevButton.classList.remove("none"); //shows prev button
            } else {
              nodeListArrObj[nextIndex].node.classList.remove("none");
            }
          }
        }
        indexLeft--;
      }
      if (!nodeListArrObj[0].node.classList.contains("none")) {
        prevButton.classList.add("none");
      }
      if (
        !nodeListArrObj[nodeListArrObj.length - 1].node.classList.contains(
          "none"
        )
      ) {
        nextButton.classList.add("none");
      }
    }

    enhancedNodeList = nodeListArrObjFunc().map((o) => o);
  };

  const next = () => {
    resizingCommand = true;
    resizingCarousel2();
    resizingCommand = false;

    prevButton.classList.remove("none"); //shows prev button
    let partialSum = 0;
    let nextIndex = -1;
    let lastDisplay = "x";
    let margins = 0;

    enhancedNodeList.forEach((obj, i) => {
      if (obj.node.classList.contains("none")) {
        obj.display = "n";
      } else {
        obj.display = "y";
      }
    });

    enhancedNodeList.forEach((obj, i) => {
      if (obj.display === "n" && lastDisplay === "y") {
        nextIndex = i;
      }
      lastDisplay = obj.display;
    });

    enhancedNodeList[nextIndex].node.classList.remove("none");
    enhancedNodeList[nextIndex].display = "y";
    const containersWidth = pillsContainerWidth();
    for (let index = nextIndex; index >= 0; index--) {
      partialSum = partialSum + enhancedNodeList[index].width;
      margins = margins + (viewPortWidth() > 764 ? PILL_MARGIN * 2 : 0);
      if (partialSum + margins >= containersWidth) {
        enhancedNodeList[index].node.classList.add("none");
        enhancedNodeList[index].display = "n";
      }
    }

    if (nextIndex === enhancedNodeList.length - 1) {
      nextButton.classList.add("none"); //removes next button
    }
  };

  const prev = () => {
    resizingCommand = true;
    resizingCarousel();
    resizingCommand = false;
    nextButton.classList.remove("none"); //shows next button

    let partialSum = 0;
    let nextIndex = -1;
    let margins = 0;

    enhancedNodeList.forEach((obj, i) => {
      if (obj.node.classList.contains("none")) {
        obj.display = "n";
      } else {
        obj.display = "y";
      }
    });

    nextIndex = enhancedNodeList.findIndex((obj) => obj.display === "y") - 1;

    enhancedNodeList[nextIndex].node.classList.remove("none");
    enhancedNodeList[nextIndex].display = "y";
    const containersWidth = pillsContainerWidth();
    for (let index = nextIndex; index <= enhancedNodeList.length - 1; index++) {
      partialSum = partialSum + enhancedNodeList[index].width;
      margins = margins + (viewPortWidth() > 764 ? PILL_MARGIN * 2 : 0);
      if (partialSum + margins > containersWidth) {
        enhancedNodeList[index].node.classList.add("none");
        enhancedNodeList[index].display = "n";
      }
    }

    if (nextIndex === 0) {
      prevButton.classList.add("none"); //removes prev button
    }
  };

  nextButton.addEventListener("click", (e) => {
    next();
  });

  prevButton.addEventListener("click", (e) => {
    prev();
  });

  addEvent(window, "resize", function (event) {
    resizingCarousel2();
  });
  window.dispatchEvent(new Event("resize"));
});
