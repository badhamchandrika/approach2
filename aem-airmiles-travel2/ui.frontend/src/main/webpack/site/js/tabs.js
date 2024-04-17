let tabsExist = document.querySelector(".cmp-tabsai");
if (typeof (tabsExist) != 'undefined' && tabsExist != null) {

  // Execute initDropdownTabs only if is not used for widgets eg. faq page
  if (!tabsExist.classList.contains("cmp-tabsai--widgets")) {
    initDropdownTabs();
  }

  function initDropdownTabs() {
    document.querySelectorAll("li.cmp-tabsai__tab--active").forEach(function (item) {
        let tabID = item.getAttribute("id").substring(0, item.getAttribute("id").indexOf("-", 6));
        document.querySelector('#' + tabID + ' [role="button"]').innerHTML = item.innerHTML
    })

    const SPACEBAR_KEY_CODE = 32;
    const ENTER_KEY_CODE = 13;
    const DOWN_ARROW_KEY_CODE = 40;
    const UP_ARROW_KEY_CODE = 38;
    const ESCAPE_KEY_CODE = 27;

    const list = document.querySelector(".dropdown__list");
    const listContainer = document.querySelector(".dropdown__list-container");
    const dropdownArrow = document.querySelector(".dropdown__arrow");
    const listItems = document.querySelectorAll(".dropdown__list-item");
    const dropdownSelectedNode = document.querySelector('.cmp-tabsai #dropdown__selected');
    const listItemIds = [];

    dropdownSelectedNode.addEventListener("click", e =>
        toggleListVisibility(e)
    );

    document.querySelector(".cmp-tabsai").addEventListener("focusout", e =>
        closeListFocusout(e)
    );

    dropdownSelectedNode.addEventListener("keydown", e =>
        toggleListVisibility(e)
    );

    listItems.forEach(item => listItemIds.push(item.id));

    listItems.forEach(item => {
        item.addEventListener("click", e => {
            setSelectedListItem(e);
            closeList();
        });

        item.addEventListener("keydown", e => {
            switch (e.keyCode) {
                case SPACEBAR_KEY_CODE:
                case ENTER_KEY_CODE:
                    setSelectedListItem(e);
                    closeList();
                    return;

                case ESCAPE_KEY_CODE:
                    closeList();
                    return;

                default:
                    return;
            }
        });
    });

    function setSelectedListItem(e) {
        dropdownSelectedNode.innerHTML = null;
        dropdownSelectedNode.innerHTML = e.currentTarget.innerHTML;
    }

    function closeListFocusout(e) {
        if (e.relatedTarget == null || e.relatedTarget.className.indexOf("dropdown_") < 0)
            closeList()
    }


    function closeList() {
        list.classList.remove("open");
        dropdownArrow.classList.remove("expanded");
        listContainer.setAttribute("aria-expanded", false);
    }

    function toggleListVisibility(e) {
        let openDropDown =
            e.keyCode === SPACEBAR_KEY_CODE || e.keyCode === ENTER_KEY_CODE;

        if (e.keyCode === ESCAPE_KEY_CODE) {
            closeList();
        }

        if (e.type === "click" || openDropDown) {
            if(!list.classList.contains("open")){
                list.classList.toggle("open");
                dropdownArrow.classList.toggle("expanded");
                listContainer.setAttribute(
                    "aria-expanded",
                    list.classList.contains("open")
                );
                // This line was commented because of this ticket: https://loyaltyone.atlassian.net/browse/TRA-1500
                // const activeElementId = document.querySelector(".cmp-tabsai__tab--active").focus();
            } else {
                closeList();
            }
        }
    }

    function focusNextListItem(direction) {
        const activeElementId = document.activeElement.getAttribute("aria-selected");
        const currentActiveElementIndex = listItemIds.indexOf(
            activeElementId
        );
        if (direction === DOWN_ARROW_KEY_CODE) {
            const currentActiveElementIsNotLastItem =
                currentActiveElementIndex < listItemIds.length - 1;
            if (currentActiveElementIsNotLastItem) {
                const nextListItemId = listItemIds[currentActiveElementIndex + 1];
                document.querySelector(`#${nextListItemId}`).focus();
            }
        } else if (direction === UP_ARROW_KEY_CODE) {
            const currentActiveElementIsNotFirstItem =
                currentActiveElementIndex > 0;
            if (currentActiveElementIsNotFirstItem) {
                const nextListItemId = listItemIds[currentActiveElementIndex - 1];
                document.querySelector(`#${nextListItemId}`).focus();
            }
        }

    }
  }
}