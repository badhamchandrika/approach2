(function () {
    "use strict";

    function switchClass(e) {
        if (e.srcElement.localName == "h2") {
            let elements = document.querySelectorAll(".cmp-solutionblock__rowContainer");
            for (const element of elements) {
                element.classList.remove("show");
            }
            e.target.nextElementSibling.parentElement.parentElement.classList.add("show");
        }
        if (e.srcElement.localName == "span") {
            let elements = document.querySelectorAll(".cmp-solutionblock__rowContainer");
            for (const element of elements) {
                element.classList.remove("show");
            }
            e.target.parentElement.nextElementSibling.parentElement.parentElement.classList.add("show");
        }
    }

    function switchIconClass() {
        let dataContainer = document.querySelector('.cmp-solutionblock__dataContainer');
        for (const el of dataContainer.children) {
            if (el.classList.contains('show')) {
               /*  el.querySelector('.cmp-solutionblock-arrow-right').classList.replace(
                    'am-icon-chev-right', 'am-icon-chev-down'); */
                    el.querySelector('.cmp-solutionblock-arrow-right').classList.add('turn');
                } else {
                el.querySelector('.cmp-solutionblock-arrow-right').classList.remove('turn');
            }
        }
    }

    document.addEventListener('DOMContentLoaded', function () {
        // your code here 
        let elements = document.querySelectorAll(".cmp-solutionblock__rowContainer");
        for (const element of elements) {
            element.addEventListener("click", switchClass);
            element.addEventListener("click", switchIconClass);
        }
    }, false);

}());
