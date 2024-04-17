(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const _webbanner = document.querySelector(".webbanner");
        if (!_webbanner) {
            return;
        }
        function checkViewport() {
            if (window.matchMedia("(max-width: 991px)").matches) {
                // Mobile view or iPad view
                const colsContainer = document.querySelector(".columns .columns-inner .col-12.col-lg-8");
                const transactionElement = colsContainer.querySelector(".transactiontable");
                if(transactionElement != null){
                    colsContainer.appendChild(document.querySelector(".webbanner"));
                }
            } else {
                //Desktop view
                const colsContainer = document.querySelectorAll(".columns");
                colsContainer.forEach(function(parentElement) {
                    const transactionElement = parentElement.querySelector(".transactiontable");
                    if (transactionElement !== null) {
                        parentElement.insertAdjacentElement("afterend",document.querySelector(".webbanner"));
                    }
                });
            }
        }

        // Call the function on page load
        checkViewport();

    });
})();