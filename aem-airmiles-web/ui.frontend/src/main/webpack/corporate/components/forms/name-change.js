import {apiURL, getApiHost, getDomBFFDomainUrl, useFetch, useFetchPost} from "../../../dependencies/js/api";
import {
    activeInputLabel,
    activeSelectLabel,
    buildCollectorObj,
    setDataOnDataList,
    validateInput,
    clearDropdownValues,
    disableModal,
    cancelModal,
    formLoadEvent,
    formCompletedEvent,
    setContactDetails
} from "./utils/utils";

(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const _nameChangeForm = document.querySelector('.name-change-container');
        if(!_nameChangeForm){return;}
        let nameChangeTypeObj = {};
        let nameChangeTypeSelected = "";
        let typeOfChangeSelected = "";
        let collectorDataObj, isFirstNameValid, isLastNameValid, isAdditionalInfoValid, updatedFirstName, updatedLastName, additionalInformation;
        const CONST = {
            ids: {
                continueToDetails: "#btn-continue-to-details",
                continueToReview: "#btn-continue-to-review",
                continueToSubmit: "#btn-continue-to-submit",
                nameChangeTypeSelection: "#name-change-type-selection",
                nameChangeFormDetails: "#name-change-form-details",
                nameChangeFormReview: "#name-change-form-review",
                nameChangeFormComplete: "#name-change-form-complete",
                nameChangeFormError: "#name-change-form-error",
                backToSelection: "#back-to-name-change-type-selection",
                backToDetails: "#back-to-name-change-details",
                datalistCollectorDetails: "#datalist-collector-details",
                datalistCollectorDetailsReview: "#datalist-collector-details-review",
                datalistNameChangeTypeReview: "#datalist-name-change-type-review",
                datalistNameChangeType: "#datalist-name-change-type",
                nameChangeType: "#name-change-type",
                firstName: "first_name",
                lastName: "last_name",
                additionalInformation: "additional_information",
                nameChangeLegalInstructions: "#name-change-legal-instructions",
                nameChangeNonLegalInstructions: "#name-change-non-legal-instructions"
            }
        };
        const continueToDetails = _nameChangeForm.querySelector(CONST.ids.continueToDetails);
        const stepNav = _nameChangeForm.querySelector('.stepnav');
        const nameChangeTypeSelection = _nameChangeForm.querySelector(CONST.ids.nameChangeTypeSelection);
        const nameChangeFormDetailsStep = _nameChangeForm.querySelector(CONST.ids.nameChangeFormDetails);
        const nameChangeFormReviewStep = _nameChangeForm.querySelector(CONST.ids.nameChangeFormReview);
        const nameChangeFormComplete = _nameChangeForm.querySelector(CONST.ids.nameChangeFormComplete);
        const nameChangeFormError = _nameChangeForm.querySelector(CONST.ids.nameChangeFormError);
        const nameChangeLegalInstructions = _nameChangeForm.querySelector(CONST.ids.nameChangeLegalInstructions);
        const nameChangeNonLegalInstructions = _nameChangeForm.querySelector(CONST.ids.nameChangeNonLegalInstructions);
        const continueToReview = _nameChangeForm.querySelector(CONST.ids.continueToReview);
        const continueToSubmit = _nameChangeForm.querySelector(CONST.ids.continueToSubmit);
        const stepNavigationItem1 = stepNav.querySelector('[data-step-number="1"]');
        const stepNavigationItem2 = stepNav.querySelector('[data-step-number="2"]');
        const stepNavigationItem3 = stepNav.querySelector('[data-step-number="3"]')
        const backToSelection = _nameChangeForm.querySelector(CONST.ids.backToSelection);
        const backToDetails = _nameChangeForm.querySelector(CONST.ids.backToDetails);
        const nameChangeDropdown = _nameChangeForm.querySelector(CONST.ids.nameChangeType);
        const formTextList = _nameChangeForm.querySelectorAll(".cmp-form-text");
        const firstName = _nameChangeForm.querySelector(`#${CONST.ids.firstName}`);
        const lastName = _nameChangeForm.querySelector(`#${CONST.ids.lastName}`);
        const additionalInfo = _nameChangeForm.querySelector(`#${CONST.ids.additionalInformation}`);
        const btn1BackToNameChangeSelection = nameChangeLegalInstructions.querySelector('.cmp-link');
        const btn2BackToNameChangeSelection = nameChangeNonLegalInstructions.querySelector('.cmp-link');

        //Datalist elements
        const dataListCollector = _nameChangeForm.querySelectorAll(`${CONST.ids.datalistCollectorDetails} .cmp-data-list__item span`);
        const dataListCollectorReview = _nameChangeForm.querySelectorAll(`${CONST.ids.datalistCollectorDetailsReview} .cmp-data-list__item span`);
        const dataListNameChangeType = _nameChangeForm.querySelectorAll(`${CONST.ids.datalistNameChangeType} .cmp-data-list__item span`);
        const dataListNameChangeReview = _nameChangeForm.querySelectorAll(`${CONST.ids.datalistNameChangeTypeReview} .cmp-data-list__item span`);
        const formOptionsList = _nameChangeForm.querySelectorAll(".cmp-form-options");

        function validateInputFields(formField){
            Array.from(formField, element => {
                const input = element.querySelector('[class*="cmp-form-text"]')
                const regex = new RegExp(input.getAttribute('data-regex'));
                switch(input.id) {
                    case CONST.ids.firstName:
                        isFirstNameValid = validateInput(element, !input.value) && validateInput(element, !regex.test(input.value));
                        updatedFirstName = input.value;
                        break;
                    case CONST.ids.lastName:
                        isLastNameValid = validateInput(element, !input.value) && validateInput(element, !regex.test(input.value));
                        updatedLastName = input.value;
                        break;
                    case CONST.ids.additionalInformation:
                        if (!input.required){
                            isAdditionalInfoValid = true;
                            break;
                        }
                        isAdditionalInfoValid = validateInput(element, !input.value);
                        additionalInformation = input.value;
                        break;
                }
            })
        }

        async function submitNameChangeForm() {
            const payload = {
                oldName: collectorDataObj.collectorName,
                email: collectorDataObj.collectorEmail,
                contactPhone: collectorDataObj.collectorPhone,
                newName: nameChangeTypeObj.nameChangeValue,
                nameChangeReason: nameChangeTypeSelected,
                additionalInfo: additionalInfo.value
            };
            try {
                const response = await useFetchPost(getDomBFFDomainUrl()+'/dombff-dsse'+ apiURL.nameChange, true, payload, 'POST', '', null, false);
                return response;
            } catch (error) {
                console.error('Error:', error);
                return false;
            }
        }

        function backToNameChangeSelection (currentContainer) {
            currentContainer.parentElement.classList.add('hide-element');
            nameChangeTypeSelection.parentElement.classList.remove('hide-element');
        }

        firstName && firstName.addEventListener('blur', (event) => {
            const inputValue = event.target.value;
            const regex = new RegExp(firstName.getAttribute('data-regex'));
            validateInput(firstName.parentElement, !inputValue)
            validateInput(firstName.parentElement, !regex.test(inputValue))
        });

        lastName && lastName.addEventListener('blur', (event) => {
            const inputValue = event.target.value;
            const regex = new RegExp(lastName.getAttribute('data-regex'));
            validateInput(lastName.parentElement, !inputValue)
            validateInput(lastName.parentElement, !regex.test(inputValue))
        });

        additionalInfo && additionalInfo.addEventListener('blur', (event) => {
            const inputValue = event.target.value;
            validateInput(additionalInfo.parentElement, !inputValue)
        });

        nameChangeDropdown && nameChangeDropdown.addEventListener('change', () => {
            const optionSelected = nameChangeDropdown.options[nameChangeDropdown.selectedIndex]
            nameChangeTypeSelected = optionSelected.value;
            typeOfChangeSelected = optionSelected.label;
            nameChangeDropdown.parentElement.classList.remove('error');
        });

        async function profileApiCall() {
            await useFetch(getApiHost() + apiURL.profile, true)
                .then((data) => {
                    if(data && data.profile['contactDetails']['emailStatus']) {
                        setContactDetails(data.profile.contactDetails);
                    }
                    collectorDataObj = buildCollectorObj();
                    if(collectorDataObj && sessionStorage.getItem('am_emailStatus')=='VERIFIED') disableModal(continueToDetails);
                    continueToDetails.addEventListener('click', () => {
                        if(!collectorDataObj || !validateInput(nameChangeDropdown.parentElement, !nameChangeTypeSelected)) return false;
                        nameChangeTypeSelection.parentElement.classList.add('hide-element');
                        switch(nameChangeTypeSelected) {
                            case 'MISSPELL':
                                formLoadEvent('mispell-form');
                                setDataOnDataList(collectorDataObj, dataListCollector);
                                nameChangeTypeObj.typeOfChangeSelected = typeOfChangeSelected;
                                setDataOnDataList(nameChangeTypeObj, dataListNameChangeType);
                                stepNav.classList.remove('hide-element');
                                nameChangeFormDetailsStep.parentElement.classList.remove('hide-element');
                                nameChangeTypeObj = {};
                                break;
                            case 'LEGAL_CHANGE':
                                nameChangeLegalInstructions.parentElement.classList.remove('hide-element');
                                break;
                            case 'NON_LEGAL_CHANGE':
                                nameChangeNonLegalInstructions.parentElement.classList.remove('hide-element');
                                break;
                            default:
                                return false;
                        }
                    });
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        }

        btn1BackToNameChangeSelection && btn1BackToNameChangeSelection.addEventListener('click', () => {
            backToNameChangeSelection(nameChangeLegalInstructions);
        })

        btn2BackToNameChangeSelection && btn2BackToNameChangeSelection.addEventListener('click', () => {
            backToNameChangeSelection(nameChangeNonLegalInstructions);
        })

        continueToReview.addEventListener('click', (evt)=>{
            validateInputFields(formTextList);
            if(isFirstNameValid && isLastNameValid && isAdditionalInfoValid){
                collectorDataObj = buildCollectorObj();
                setDataOnDataList(collectorDataObj, dataListCollectorReview);
                nameChangeTypeObj.nameChangeValue = updatedFirstName+' '+updatedLastName;
                nameChangeTypeObj.typeOfChangeSelected = typeOfChangeSelected;
                setDataOnDataList(nameChangeTypeObj, dataListNameChangeReview);
                nameChangeFormDetailsStep.parentElement.classList.add('hide-element');
                stepNavigationItem1.classList.remove('active');
                stepNavigationItem1.classList.add('done');
                stepNavigationItem2.classList.add('active');
                nameChangeFormReviewStep.parentElement.classList.remove('hide-element');
            }
        });

        backToSelection.addEventListener('click',(evt)=>{
            stepNav.classList.add('hide-element');
            backToNameChangeSelection(nameChangeFormDetailsStep);
        });

        backToDetails.addEventListener('click', (evt)=>{
            nameChangeTypeSelection.classList.add('hide-element');
            stepNavigationItem2.classList.remove('active');
            stepNavigationItem1.classList.remove('done');
            stepNavigationItem1.classList.add('active');
            nameChangeFormReviewStep.parentElement.classList.add('hide-element');
            nameChangeFormDetailsStep.parentElement.classList.remove('hide-element');
        });

        continueToSubmit.addEventListener('click', async (evt)=>{
            const response = await submitNameChangeForm();
            nameChangeFormReviewStep.parentElement.classList.add('hide-element');
            stepNavigationItem2.classList.remove('active');
            stepNavigationItem2.classList.add('done');
            stepNavigationItem3.classList.add('done');
            if(!response.error){
                nameChangeFormComplete.parentElement.classList.remove('hide-element');
                formCompletedEvent('mispell-form');
            }
            else {
                stepNav.classList.add('hide-element');
                nameChangeFormError.parentElement.classList.remove('hide-element');
            }
        });

        cancelModal(_nameChangeForm);
        activeInputLabel(formTextList);
        activeSelectLabel(formOptionsList);
        clearDropdownValues(formOptionsList);
        profileApiCall();
    });
})();