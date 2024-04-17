import {getMemdeets} from "../../../../dependencies/js/utils";

export const activeInputLabel = (formTextList) => {
    Array.from(formTextList, element => {
        const input = element.querySelector('[class*="cmp-form-text"]')
        const label = element.querySelector('label');
        input.value && label.classList.add('active');
        input.onfocus = () => {
            label.classList.add('active');
        }
        input.onblur = () => {
            !input.value && label.classList.remove('active');
        }
    })
};

export const activeSelectLabel = (formOptionsList) => {
    Array.from(formOptionsList, element => {
        const select = element.querySelector('.cmp-form-options__field')
        const label = element.querySelector('label');
        select.onchange = () => {
            label.classList.add('active');
            select.parentElement.classList.remove('error')
        }
    })
};

export const setDataOnDataList = (object, dataList ) => {
    const objectArray = Object.keys(object);
    if(objectArray.length == 0) return false;
    objectArray.forEach((key, index) => {
        if(!dataList[index]) return false;
        dataList[index].innerHTML = object[key];
    })
};

export const buildCollectorObj = () => {
    let collectorObject = {};
    if(sessionStorage.getItem('am_emailStatus') != "VERIFIED") return false;
    collectorObject = {
        collectorName: getMemdeets()['firstName']+' '+sessionStorage.getItem('am_lastName'),
        collectorNumber: getMemdeets()['cardNumber'],
        collectorPhone: firstNotNull(
            sessionStorage.getItem('am_mobilePhone'),
            sessionStorage.getItem('am_homePhone'),
            '--'
        ),
        collectorEmail: sessionStorage.getItem('am_userEmail')
    };
    
    return collectorObject;
};

export const validateInput = (inputParent, conditional) => {
    if(conditional) {
        inputParent.classList.add('error')
        return 0;
    } else {
        inputParent.classList.remove('error')
        return 1;
    }
}

//Clear dropdown values when navigate to external browser button back
export const clearDropdownValues = (formOptionsList) => {
    window.onbeforeunload = () => {
        Array.from(formOptionsList, element => {
            const select = element.querySelector('.cmp-form-options__field')
            const label = element.querySelector('label');
            select.value = '';
            select.selectedIndex = 0;
            label.classList.remove('active');
        })
    };
}

//Disable trigger modal by uniqueID on button
export const disableModal = (button) => {
    button.removeAttribute("href");
    button.removeAttribute("data-bs-toggle");
}

export const cancelModal = (component) => {
    const modal = component.querySelector('.ammodal .modal');
    const cancelButton = modal.querySelector('.ctalink .cmp-link');
    cancelButton.setAttribute('data-bs-dismiss','modal');
    cancelButton.setAttribute('aria-label','Close');
}

/*** Adobe Analytics dataLayer events ***/

//Updating adobeDataLayer once the form is loaded
export const formLoadEvent = (formName) => {
    const payload = { web: {} };
    payload.event = "formStart";
    payload.web.form = {
        formName,
    };
    window.adobeDataLayer.push(payload);
}

//Updating adobeDataLayer once form is completed
export const formCompletedEvent = (formName) => {
    const payload = { web: {} };
    payload.event = "formComplete";
    payload.web.form = {
        formName,
    };
    window.adobeDataLayer.push(payload);
}

/**
 * Sets personal details used in DSSE Services into Session Storage.
 *
 * @param data profileAPI response data object.
 */
export const setContactDetails = (data) => {
    // Validate data tree.
    const profile = data.profile;
    if (!profile) return;

    const contactDetails = profile.contactDetails;
    if (!contactDetails) return;

    const personalDetails = profile.personalDetails;
    if(!personalDetails) return;

    // Setting email & last name in the session storage
    sessionStorage.setItem('am_userEmail', contactDetails.email);
    sessionStorage.setItem('am_lastName', data.profile.personalDetails.lastName);
    sessionStorage.setItem('am_homePhone', contactDetails.homePhone);
    sessionStorage.setItem('am_mobilePhone', contactDetails.mobilePrimary);
    sessionStorage.setItem('am_emailStatus', contactDetails.emailStatus);
}

// Retrieves the first not null/undefined object of a list of arguments.
function firstNotNull(... params) {
    for (const element of params) {
        if (element != null && element !== 'undefined' && element !== 'null'){
            return element;
        }
    }
}