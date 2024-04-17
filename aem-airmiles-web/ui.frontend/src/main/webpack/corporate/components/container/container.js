import {getMemdeets, isAuthed} from "../../../../../main/webpack/dependencies/js/utils";


(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const _profileAlerts = document.querySelector('#alert-profile-conversion');
        function profileAlertDisplay(_profileAlerts){
            const currentPage = window.location.href;
            if(document.cookie.indexOf('wcmmode=edit') >-1 || currentPage.includes("flow=profile_conversion")){
                _profileAlerts.classList.add('d-block');
            }
        }
        if(_profileAlerts){
            profileAlertDisplay(_profileAlerts);
        }

        // Dynamic variables list variableName - cookie Name
        const dynamicValues = {
            'first_name' : 'firstName',
            'card_number': 'cardNumber',
            'cash_balance': 'cashBalance',
            'dream_balance': 'dreamBalance'
        }
        const textElements = document.querySelectorAll('.cmp-text');
        function replaceDynamicVariables(replaceValue){
            Object.keys(dynamicValues).forEach(function(key){
                const cookieVal = getMemdeets()[dynamicValues[key]];
                if (typeof replaceValue === 'string' && replaceValue.includes('${' + key + '}')) {
                    replaceValue = replaceValue.replace(new RegExp('\\$\\{' + key + '\\}', 'g'), cookieVal);
                }
            })
            return replaceValue;
        }
        if(isAuthed() && textElements.length > -1){
            var textElArray = Array.from(textElements);
            textElArray.forEach(function(elem){
                const textContent = elem.innerHTML;
                elem.innerHTML = replaceDynamicVariables(textContent);
            })
        }
    });
})();
