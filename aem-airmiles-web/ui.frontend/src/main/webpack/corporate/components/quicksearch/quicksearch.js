/**
 * This IIFE (Immediately-Invoked Function Expression) module provides a functionality that initializes an autocomplete
 * search box on DOM elements with the 'quicksearch' element.
 *
 * @module AutocompleteSearchBox.
 * @version 1.0.0
 */
(() => {
    /**
     * 'use strict' directive helps in catching common coding mistakes and "unsafe".
     *  With this directive, the code will be operating in a "strict mode".
     */
    'use strict';

    /**
     * The event name for 'onkeyup' event.
     *
     * @constant {string}
     */
    const ONCHANGE_EV = 'change';

    /**
     * The event name for 'DOMContentLoaded' event.
     *
     * @constant {string}
     */
    const DCL_EV = 'DOMContentLoaded';

    /**
     * The CSS selector used to select all DOM elements with class `quicksearch`.
     *
     * @constant {string}
     */
    const QS_SELECTOR = '.quicksearch';

    /**
     * The tag name of button element.
     *
     * @constant {string}
     */
    const BUTTON = 'button';

    /**
     * The HTML selector used to select search box element.
     *
     * @constant {string}
     */
    const SEARCH_BOX_SELECTOR = '#help-search > label > input[type=search]';


    /**
     * Sets event listeners for quick searches.
     *
     * @function setEventListeners
     * @description This function sets event listeners for quick search functionality.
     * @returns {void}
     */
    const setEventListeners = () => {
        const quicksearches = document.querySelectorAll(QS_SELECTOR);
        if (!quicksearches) return;

        quicksearches.forEach((quicksearch) => {
            const btn = quicksearch.querySelector(BUTTON);
            const searchBox = quicksearch.querySelector(SEARCH_BOX_SELECTOR);

            if (!btn || !searchBox) return;

            btn.disabled = true;

            searchBox.addEventListener(ONCHANGE_EV, function () {
                // As defined by business, autocomplete will be triggered when collectors type three or more chars.
                if (searchBox.value.trimStart().trimEnd().length >= 3) {
                    btn.disabled = false;
                    const suggestiveTexts = quicksearch.dataset.suggestiveTexts;
                    if (suggestiveTexts !== '0') {
                        // TODO: consume Genesys API.
                    }
                } else btn.disabled = false;
            });
        });
    }

    /**
     * This function initializes the application by setting up event listeners after DOM Content is loaded.
     * @private
     */
    const initialize = () => {
        setEventListeners();
    };


    // Adds an event listener when the document content is loaded to initialize scripts.
    document.addEventListener(DCL_EV, function () {
        initialize();
    });
})();