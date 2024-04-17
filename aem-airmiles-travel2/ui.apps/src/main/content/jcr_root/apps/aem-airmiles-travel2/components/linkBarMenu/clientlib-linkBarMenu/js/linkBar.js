let SETTINGS = {
    navBarTravelling: false,
    navBarTravelDirection: "",
    navBarTravelDistance: 150
}

document.documentElement.classList.remove("no-js");
document.documentElement.classList.add("js");

let pnAdvancerLeft = document.getElementById("pnAdvancerLeft"),
    pnAdvancerRight = document.getElementById("pnAdvancerRight"),
    pnProductNav = document.getElementById("pnProductNav"),
    pnProductNavContents = document.getElementById("pnProductNavContents");

let ticking = false;

pnProductNav.addEventListener("scroll", function () {
    if (!ticking) {
        window.requestAnimationFrame(function () {
            handleDataOverflowing()
            ticking = false;
        });
    }
    ticking = true;
});


pnAdvancerLeft.addEventListener("click", function () {
    if (SETTINGS.navBarTravelling === true) {
        return;
    }
    if (determineOverflow(pnProductNavContents, pnProductNav) === "left" || determineOverflow(pnProductNavContents, pnProductNav) === "both") {
        let availableScrollLeft = pnProductNav.scrollLeft,
            translateAmount = Math.min(availableScrollLeft, SETTINGS.navBarTravelDistance);

        pnProductNavContents.style.transform = "translateX(" + translateAmount + "px)";
        pnProductNavContents.classList.remove("pn-ProductNav__Contents-no-transition");
        SETTINGS.navBarTravelDirection = "left";
        SETTINGS.navBarTravelling = true;
    }
    handleDataOverflowing()
});

pnAdvancerRight.addEventListener("click", function () {
    if (SETTINGS.navBarTravelling === true) {
        return;
    }
    if (determineOverflow(pnProductNavContents, pnProductNav) === "right" || determineOverflow(pnProductNavContents, pnProductNav) === "both") {
        let navBarRightEdge = pnProductNavContents.getBoundingClientRect().right,
            navBarScrollerRightEdge = pnProductNav.getBoundingClientRect().right,
            availableScrollRight = Math.floor(navBarRightEdge - navBarScrollerRightEdge),
            translateAmount = Math.min(availableScrollRight, SETTINGS.navBarTravelDistance);

        pnProductNavContents.style.transform = "translateX(-" + translateAmount + "px)";
        pnProductNavContents.classList.remove("pn-ProductNav__Contents-no-transition");
        SETTINGS.navBarTravelDirection = "right";
        SETTINGS.navBarTravelling = true;
    }
    handleDataOverflowing()
});

pnProductNavContents.addEventListener(
    "transitionend",
    function () {
        let styleOfTransform = window.getComputedStyle(pnProductNavContents, null),
            tr = styleOfTransform.getPropertyValue("-webkit-transform") || styleOfTransform.getPropertyValue("transform"),
            amount = Math.abs(parseInt(tr.split(",")[4]) || 0);
        pnProductNavContents.style.transform = "none";
        pnProductNavContents.classList.add("pn-ProductNav__Contents-no-transition");
        if (SETTINGS.navBarTravelDirection === "left") {
            pnProductNav.scrollLeft = pnProductNav.scrollLeft - amount;
        } else {
            pnProductNav.scrollLeft = pnProductNav.scrollLeft + amount;
        }
        SETTINGS.navBarTravelling = false;
    },
    false
);

pnProductNavContents.addEventListener("click", function (e) {
    let links = [].slice.call(document.querySelectorAll(".pn-ProductNav__Contents-Link"));
    links.forEach(function (item) {
        item.setAttribute("aria-current", "false");
    })
    e.target.setAttribute("aria-current", "true");
});

let allNavLinks = document.querySelectorAll(".pn-ProductNav__Contents-Link")

allNavLinks.forEach((item) => {
    item.addEventListener("click", function (e) {
        //e.preventDefault();
        allNavLinks.forEach(function (restItems) {
            restItems.setAttribute("aria-current", "false");
            restItems.classList.remove("pn-ProductNav__Contents-Link--active");
        });

        if (e.target.classList.contains("pn-ProductNav__Contents-Link")) {
            e.target.setAttribute("aria-current", "true");
            e.target.classList.add("pn-ProductNav__Contents-Link--active");
        } else {
            e.target.parentElement.setAttribute("aria-current", "true");
            e.target.parentElement.classList.add("pn-ProductNav__Contents-Link--active");
        }
    })
})

function determineOverflow(content, container) {
    try {
        let containerMetrics = container.getBoundingClientRect(),
            containerMetricsRight = Math.floor(containerMetrics.right),
            containerMetricsLeft = Math.floor(containerMetrics.left),
            contentMetrics = content.getBoundingClientRect(),
            contentMetricsRight = Math.floor(contentMetrics.right),
            contentMetricsLeft = Math.floor(contentMetrics.left);


        if (containerMetricsLeft > contentMetricsLeft && containerMetricsRight < contentMetricsRight) {
            return "both";
        } else if (contentMetricsLeft < containerMetricsLeft) {
            return "left";
        } else if (contentMetricsRight > containerMetricsRight) {
            return "right";
        } else {
            return "none";
        }
    } catch (error) {
        console.log("Something went wrong with the overflow", error)
    }
}


function scrollToCenter() {
	let activeLink = document.querySelector('.pn-ProductNav__Contents-Link--active'),
		activelinkPosition = activeLink.offsetLeft,
        scrollAmount = activelinkPosition + activeLink.offsetWidth / 2 - pnProductNav.offsetWidth / 2;

	// fix to scroll the active nav to  the center
	setTimeout(function() {
		pnProductNav.scrollTo({
			left: scrollAmount,
			behavior: "instant"
		});
	}, 100);
}

function handleDataOverflowing() {
    pnProductNav.setAttribute("data-overflowing", determineOverflow(pnProductNavContents, pnProductNav));
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

document.addEventListener("DOMContentLoaded", function () {
    handleDataOverflowing();
    scrollToCenter();
})

window.addEventListener("resize", throttle(handleDataOverflowing));
