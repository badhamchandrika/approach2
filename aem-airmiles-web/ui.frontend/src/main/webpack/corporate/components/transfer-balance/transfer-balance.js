import {isAuthed} from "../../../dependencies/js/utils";
import {apiURL, getApiHost, getDomBFFApiHost, useFetch, useFetchPost} from "../../../dependencies/js/api";

(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const transferBalanceDetail = document.querySelector('.transfer-balance-detail');
        if(!transferBalanceDetail){return;}
        let availableCashMiles,availableDreamMiles, updatedCashMiles, updatedDreamMiles,cardNumber, milesEntered, selectedTransferType;
        const transferBalanceConfirmation = document.querySelector('.transfer-balance-confirmation');
        const genericError = document.querySelector('#genericError');
        const insufficientMilesError = document.querySelector("#insufficientBalanceError");
        const timeOutError = document.querySelector("#timeOutError");
        const eligibilityError = document.querySelector("#eligibilityError");
        const transferBtn = document.getElementById("transferBtn");
        const customRadioTiles = document.querySelectorAll('.transfer-miles .col-6');
        const numericInput = document.getElementById('amountOfMiles');
        const dataTrackId = transferBtn.getAttribute("data-track-id");
        const loadingPage = document.getElementById('loading-page');
        const checkMarkParentElem = document.querySelector('.transfer-balance-confirmation .cmp-title');
        const inputElement = transferBalanceDetail.querySelector('.cmp-form-text__text');
        const inputErrorMsg = document.createElement('span');
        let isEligible = sessionStorage.getItem('am_tb');
        inputErrorMsg.classList.add('input__error__message', 'am-icon2', 'am-icon2-critical-empty', 'd-none');
        document.querySelector('.transfer-balance-detail .cmp-form-text').appendChild(inputErrorMsg);
        const milesInputError = document.querySelector('.transfer-balance-detail .cmp-form-text .input__error__message');
        inputElement.setAttribute("placeholder"," ");
        const checkMarkElem = document.createElement('div');
        checkMarkElem.classList.add("am-icon2","am-icon2-check-small", "text-center", "my-auto", "fs-4");
        checkMarkParentElem.insertBefore(checkMarkElem, checkMarkParentElem.firstChild);

        //Create div element for transfer type error message
        const transferTypeErrorMsg = document.createElement('div');
        transferTypeErrorMsg.classList.add('transfer-miles__type__error', 'am-icon2', 'am-icon2-critical-empty','d-none');
        transferTypeErrorMsg.innerText = Granite.I18n.get('Please select Cash to Dream or Dream to Cash.');
        document.querySelector('.transfer-balance-detail .transfer-miles').appendChild(transferTypeErrorMsg);

        //Create confetti element
        const confettiImg = document.createElement('img');
        confettiImg.classList.add('confetti', 'd-none');
        confettiImg.src = "/etc.clientlibs/aem-airmiles-web/clientlibs/clientlib-corporate-site/resources/images/confetti.svg";
        checkMarkParentElem.append(confettiImg);

        function pushErrorToAdobeLayer(id,message){
            window.adobeDataLayer.push ({
                event: "error_shown",
                web: {
                    error: {
                        type: "stand alone error page",
                        id: id,
                        message: message,
                        form: "transfer-balance"
                    }
                }
            });
        }

        function displayErrorData(error){
            const errorIcon = document.createElement('div');
            errorIcon.classList.add('am-icon2', 'am-icon2-error', 'my-auto','text-center');
            transferBalanceDetail.classList.add('hide-element');
            if(document.querySelector('.decorative-img') != null){
                document.querySelector('.decorative-img').classList.add('d-none');
            }
            if(error['code'] == "InsufficientMiles"){
                insufficientMilesError.querySelector('.cmp-title').prepend(errorIcon);
                insufficientMilesError.parentElement.classList.remove('hide-element');
                setTimeout(()=>{
                    pushErrorToAdobeLayer('insufficient-balance','you-have-an-insufficient-balance');
                }, 1000);
            }
            else if(error['code'] == "TimedOut"){
                timeOutError.querySelector('.cmp-title').prepend(errorIcon);
                timeOutError.parentElement.classList.remove('hide-element');
                setTimeout(()=>{
                    pushErrorToAdobeLayer('time-out','oops-something-went-wrong');
                }, 1000);
            }
            else if(error['code'] == "CollectorNotEmployee" || error['code'] == "CollectorNotEligible"){
                eligibilityError.querySelector('.cmp-title').prepend(errorIcon);
                eligibilityError.parentElement.classList.remove('hide-element');
                setTimeout(()=>{
                    pushErrorToAdobeLayer('not-eligible','looks-like-you-arent-eligible');
                }, 1000);
            }
            else{
                genericError.querySelector('.cmp-title').prepend(errorIcon);
                genericError.parentElement.classList.remove('hide-element');
                setTimeout(()=>{
                    pushErrorToAdobeLayer('generic','oops-something-went-wrong');
                }, 1000);
            }
        }
        async function checkCollectorEligibility(_myAPI, _header){
            try {
                if(isEligible == null || isEligible == "undefined"){
                    const data = await useFetch(_myAPI, _header);
                    sessionStorage.setItem('am_tb',data['eligible']);
                    isEligible = sessionStorage.getItem('am_tb');
                }
                if (isEligible == "false" || isEligible == "undefined") {
                    loadingPage.classList.add('d-none');
                    document.getElementById('transfer-balance-container').parentElement.classList.remove('hide-element');
                    if(transferBalanceDetail){
                        transferBalanceDetail.remove();
                    }
                    if(transferBalanceConfirmation){
                        transferBalanceConfirmation.remove();
                    }
                    isEligible == "false"?displayErrorData({ code: "CollectorNotEmployee" }):displayErrorData({ code: "GenericError" });
                }
            } catch (error) {
                console.error("error", error);
            }
        }

        async function getMemberBanner(_myAPI, _header) {
            try {
                const data = await useFetch(_myAPI, _header);
                return data;
            } catch (error) {
                console.error("error", error);
            }
        }

        customRadioTiles.forEach(tile => {
            if(tile.querySelector('#cash-to-dream-img') || tile.querySelector('#cash-to-dream-text')){
                tile.setAttribute('data-option','cash-to-dream');
            }
            if(tile.querySelector('#dream-to-cash-img') || tile.querySelector('#dream-to-cash-text')){
                tile.setAttribute('data-option','dream-to-cash');
            }
            tile.addEventListener('click', () => {
                customRadioTiles.forEach(mileTile => {
                    mileTile.classList.remove('selected');
                    mileTile.classList.remove('disabled');
                });
                tile.classList.add('selected');
                if(tile.classList.contains('selected')){
                    customRadioTiles.forEach(mileTile => {
                        if(!mileTile.classList.contains("selected")){
                            mileTile.classList.add('disabled');
                        }
                    });
                }
                selectedTransferType = tile.getAttribute('data-option');
                if(dataTrackId != null){
                    transferBtn.setAttribute("data-track-id",dataTrackId+':'+selectedTransferType);
                }
                if(selectedTransferType!= null && selectedTransferType != ''){
                    transferTypeErrorMsg.classList.add('d-none');
                }
                if(milesInputError.classList.contains('d-none')){
                    transferBtn.classList.remove('disabled');
                }
                else{
                    numericInput.value = '';
                    document.querySelector('.transfer-balance-detail .cmp-form-text .cmp-form-text__text').style.borderColor = '';
                    document.querySelector('.transfer-balance-detail .cmp-form-text label').style.color = '';
                    milesInputError.classList.add('d-none');
                    transferBtn.classList.remove('disabled');
                }
            });
        });

        numericInput.addEventListener('input', function(event) {
            document.querySelector('.transfer-balance-detail .cmp-form-text .cmp-form-text__text').style.borderColor = '';
            document.querySelector('.transfer-balance-detail .cmp-form-text label').style.color = '';
            milesInputError.classList.add('d-none');
            const inputValue = event.target.value;
            const numericValue = inputValue.replace(/\D/g, '');
            event.target.value = numericValue;
            if(transferTypeErrorMsg.classList.contains('d-none')){
                transferBtn.classList.remove('disabled');
            }
            else{
                transferBtn.classList.add('disabled');
            }
        });

        function easyNumberAnimate(updatedCashVal, updatedDreamVal, duration, intervalMs) {
            const cashElement = transferBalanceConfirmation.querySelector('.latest-cash-miles');
            const dreamElement = transferBalanceConfirmation.querySelector('.latest-dream-miles');
            const startCash = parseFloat(cashElement.innerText) || 0;
            const startDream = parseFloat(dreamElement.innerText) || 0;
            const totalSteps = Math.ceil(duration / intervalMs);
            const cashStepValue = (updatedCashVal - startCash) / totalSteps;
            const dreamStepValue = (updatedDreamVal - startDream) / totalSteps;
            let currentCash = startCash;
            let currentDream = startDream;
            let stepCount = 0;
            const intervalId = setInterval(() => {
                currentCash += cashStepValue;
                currentDream += dreamStepValue;
                cashElement.innerText = Math.round(currentCash).toLocaleString("en-US");
                dreamElement.innerText = Math.round(currentDream).toLocaleString("en-US");
                stepCount++;
                if (stepCount >= totalSteps) {
                    clearInterval(intervalId);
                    cashElement.innerText = parseInt(updatedCashVal).toLocaleString("en-US");
                    dreamElement.innerText = parseInt(updatedDreamVal).toLocaleString("en-US");
                    document.querySelector('.confetti').classList.add('show');
                    document.querySelector('.confetti').classList.remove('d-none');
                }
            }, intervalMs);
        }

        async function displayConfirmationData(){
            try {
                getMemberBanner(getApiHost()+apiURL.memberBanner, false).then((data) =>{
                    updatedCashMiles = data['cashBalance'];
                    updatedDreamMiles = data['dreamBalance'];
                    if(selectedTransferType =="cash-to-dream") {
                        document.querySelector('.transfer-balance-confirmation #cash-to-dream-selected p').innerHTML = '<b>'+milesEntered + '</b> '+document.querySelector('.transfer-balance-confirmation #cash-to-dream-selected p').innerHTML;
                        document.querySelector('.transfer-balance-confirmation #dream-to-cash-selected').classList.add('d-none');
                    }
                    if(selectedTransferType =="dream-to-cash") {
                        document.querySelector('.transfer-balance-confirmation #dream-to-cash-selected p').innerHTML = '<b>'+milesEntered + '</b> '+document.querySelector('.transfer-balance-confirmation #dream-to-cash-selected p').innerHTML;
                        document.querySelector('.transfer-balance-confirmation #cash-to-dream-selected').classList.add('d-none');
                    }
                    transferBalanceDetail.classList.add('hide-element');
                    document.querySelector('.airmiles_web_simple_header__cancel__link').classList.add('d-none');
                    transferBalanceConfirmation.classList.remove('hide-element');
                    easyNumberAnimate(updatedCashMiles, updatedDreamMiles, 2000, 100);
                    //Updating adobe data layer after completing transfer successfully
                    const payload = { web: {} };
                    payload.event = "formComplete";
                    payload.web.form = {
                        formName: "transfer-balance",
                    };
                    adobeDataLayer.push(payload);
                    document.querySelector('.confetti').classList.add('d-none');
                });
            } catch (error) {
                console.error('Error:', error);
            }
        }
        async function transferBalance(_myAPI, _header) {
            transferBtn.classList.add('disabled');
            let transferType;
            let cashMiles;
            let dreamMiles;
            if(selectedTransferType =="cash-to-dream"){
                transferType = "C2D_WITHOUT_FEE";
                cashMiles = milesEntered;
                dreamMiles = 0;
            }
            if(selectedTransferType =="dream-to-cash"){
                transferType = "D2C_WITHOUT_FEE";
                cashMiles = 0;
                dreamMiles = milesEntered;
            }
            const payload ={
                "sourceCardNumber": cardNumber,
                "targetCardNumber": cardNumber,
                "cashMiles": cashMiles,
                "dreamMiles": dreamMiles,
                "transferType": transferType,
                "reasonCode": "No Fee",
                "clientType": "WEB",
                "clientId": ""
            };
            const apiRequestPromise = useFetchPost(_myAPI, false, payload, 'POST', 'transfer-balance');
            const timeoutPromise = new Promise((resolve) => {
                setTimeout(() => {
                    resolve({ timeout: true });
                }, 15000); // 10 seconds, need to be changed
            });

            try {
                const data = await Promise.race([apiRequestPromise, timeoutPromise]);
                if(data && data['transactionId'] != undefined) {
                    displayConfirmationData();
                }
                else{
                    if(data.timeout){
                        console.error('Error: API response timed out');
                        displayErrorData({code: 'TimedOut'});
                    }
                    else {
                        displayErrorData(data);
                    }
                }
            } catch (error) {
                console.error('Continues fetch Error:', error);
            }
        }

        transferBtn.addEventListener('click',(evt)=>{
            if(isAuthed()) {
                evt.preventDefault();
                const isSelected = document.querySelector('.transfer-miles .col-6.selected');
                milesEntered = parseInt(document.getElementById('amountOfMiles').value);
                let step1ErrorsCount = 0;
                if(isSelected == null){
                    transferTypeErrorMsg.classList.remove('d-none');
                    transferBtn.classList.add('disabled');
                    step1ErrorsCount = 1;
                }
                else{
                    transferBtn.classList.remove('disabled');
                    transferTypeErrorMsg.classList.add('d-none');
                }

                if(milesEntered == null || milesEntered == 0 || isNaN(milesEntered) || (selectedTransferType =="cash-to-dream" && milesEntered > availableCashMiles) || (selectedTransferType =="dream-to-cash" && milesEntered > availableDreamMiles)){
                    step1ErrorsCount = 1;
                    milesInputError.classList.remove('d-none');
                    document.getElementById('amountOfMiles').style.borderColor = '#E71823';
                    document.querySelector('.transfer-balance-detail .cmp-form-text label').style.color = '#E71823';
                    transferBtn.classList.add('disabled');

                    if(milesEntered == null || milesEntered == undefined || isNaN(milesEntered)){
                        inputErrorMsg.innerText = Granite.I18n.get('Please enter the amount of Miles you want to transfer.');
                    }
                    else if(milesEntered == '' || milesEntered <= 0){
                        inputErrorMsg.innerText = Granite.I18n.get('The amount of Miles must be greater than 0.');
                    }
                    else if(selectedTransferType =="cash-to-dream" && milesEntered > availableCashMiles){
                        inputErrorMsg.innerText = Granite.I18n.get('You do not have enough Cash Miles');
                    }
                    else if(selectedTransferType =="dream-to-cash" && milesEntered > availableDreamMiles){
                        inputErrorMsg.innerText = Granite.I18n.get('You do not have enough Dream Miles');
                    }
                    milesInputError.classList.remove('d-none');
                    numericInput.focus();
                }

                if(step1ErrorsCount == 0){
                    document.querySelector('.input__error__message').classList.add('d-none')
                    inputErrorMsg.innerText = '';
                    transferBtn.classList.remove('disabled');
                    document.querySelector('.transfer-balance-detail .cmp-form-text .cmp-form-text__text').style.borderColor = '';
                    document.querySelector('.transfer-balance-detail .cmp-form-text label').style.color = '';
                    transferBalance(getDomBFFApiHost()+ apiURL.transferBalance, true);
                }
            }
        });

        function transferInit(){
            if (isAuthed()) {
                checkCollectorEligibility(getDomBFFApiHost()+apiURL.eligibility,false).then((data)=>{
                    if(isEligible == "true") {
                        loadingPage.classList.add('d-none');
                        document.getElementById('transfer-balance-container').parentElement.classList.remove('hide-element');
                        transferBalanceDetail.classList.remove('hide-element');
                        getMemberBanner(getApiHost() + apiURL.memberBanner, false).then((_mbdata) =>{
                            availableCashMiles = parseInt(_mbdata['cashBalance']).toLocaleString("en-US");
                            availableDreamMiles = parseInt(_mbdata['dreamBalance']).toLocaleString("en-US");;
                            cardNumber = _mbdata['cardNumber'];
                            const cashMiles = document.querySelectorAll('.transfer-miles .cash-miles b');
                            const dreamMiles = document.querySelectorAll('.transfer-miles .dream-miles b');
                            cashMiles.forEach(elem =>{
                                elem.innerHTML = availableCashMiles+' '+elem.innerHTML+' ';
                            });
                            dreamMiles.forEach(elem =>{
                                elem.innerHTML = availableDreamMiles+' '+elem.innerHTML+' ';
                            });
                            availableCashMiles = parseInt(availableCashMiles.replace(',',''));
                            availableDreamMiles = parseInt(availableDreamMiles.replace(',',''));
                        });
                        //Updating adobe data layer once the form is loaded
                        const payload = { web: {} };
                        payload.event = "formStart";
                        payload.web.form = {
                            formName: "transfer-balance",
                        };
                        adobeDataLayer.push(payload);
                    }
                });
            }
        }
        transferInit();
    });
})();