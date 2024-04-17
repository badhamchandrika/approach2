import {useFetchPost, getApiHost, apiURL, getDomBFFDomainUrl, useFetch} from "../../../dependencies/js/api";
import {
  activeInputLabel,
  activeSelectLabel,
  buildCollectorObj,
  setDataOnDataList,
  validateInput,
  formLoadEvent,
  formCompletedEvent,
  clearDropdownValues,
  disableModal,
  cancelModal,
  setContactDetails
} from "./utils/utils";
import {imageUuids, setImagesUploadedOnDataList} from "../fileupload/fileupload";

(() => {
    const CONST = {
        classnames: {
          FORM_CONTAINER: ".form-container.merchandise-form",
          FORM_TEXT: ".cmp-form-text",
          FORM_OPTIONS: ".cmp-form-options",
          STEP_NAVIGATION: ".cmp-step-navigation"
        },
        ids: {
          STEP0: "step-0",
          STEP1: "step-1",
          STEP2: "step-2",
          STEP3_SUCCESS_SCREEN1: "step3-success-screen1",
          STEP3_SUCCESS_SCREEN2: "step3-success-screen2",
          STEP3_ERROR_SCREEN: "step3-error-screen",
          STEP1_ORDER_ISSUE: "step-1-order-management-issue",
          STEP0_DELIVERY_ISSUE: "step0-delivery-issue",
          STEP1_DELIVERY_ISSUE: 'step-1-delivery-issue',
          STEP2_DELIVERY_ISSUE: 'step-2-delivery-issue',
          FIRST_DROPDOWN: "first-merch-select",
          MERCH_REWARD: "#PRODUCT_ISSUE",
          DELIVERY_ISSUE: "#DELIVERY_ISSUE",
          ORDER_MANAGEMENT: "#order-management",
          BTN_CONTAINER_FIRST_SCREEN: "#btn-container-first-screen",
          BTN_CONTAINER_SECOND_SCREEN: "#btn-container-second-screen",
          BTN_CONTINUE_TO_DETAILS_SCREEN: "#btn-continue-to-details",
          BTN_CONTINUE_TO_REVIEW: "#btn-continue-to-review",
          BTN_CONTINUE_TO_STEP3: "btn-continue-to-step-3",
          BTN_BACK_TO_STEP0: "#btn-back-to-step0",
          MERCH_BTN_BACK_TO_STEP0: "#merch-btn-back-to-step0",
          BTN_BACK_TO_STEP1: "btn-back-to-step1",
          DI_BTN_BACK_TO_INITIAL_STEP: "di-btn-back-to-initial-step",
          DI_BTN_CONTINUE_TO_STEP1:"di-btn-continue-to-step1",
          DI_BTN_BACK_TO_STEP0: "di-btn-back-to-step0",
          OM_BTN_BACK_TO_STEP0: "om-btn-back-to-step0",
          DI_BTN_CONTINUE_TO_STEP2:"di-btn-continue-to-step2",
          DI_BTN_BACK_TO_STEP1: "di-btn-back-to-step1",
          DI_BTN_CONTINUE_TO_STEP3:"di-btn-continue-to-step3",
          INPUT_ORDER_NUMBER: "input-order-number",
          INPUT_ORDER_RECEIVE_DATE: "input-order-received-date",
          INPUT_TYPE_OF_MERCHANDISE: "input-type-of-merchandise",
          INPUT_DESCRIPTION_OF_HELP: "input-description-of-help",
          INPUT_SUPPORTING_IMAGES: "input-supporting-images",
          DATALIST_COLLECTOR_PROFILE1: "datalist-collector-profile1",
          DATALIST_COLLECTOR_PROFILE2: "datalist-collector-profile2",
          DATALIST_ORDER_DETAILS: "datalist-order-details",
          DATALIST_MERCHANDISE_REWARD_DETAILS: "datalist-merchandise-reward-details",
          DATALIST_ISSUE_TYPE: "datalist-issue-type",
          DATALIST_ORDER_ISSUE_TYPE: "datalist-order-issue-type",
          INPUT_ORDER_NUMBER_DI: "input-order-number-di",
          INPUT_DELIVERY_STATUS_DI: "input-delivery-status-di",
          INPUT_ISSUE_DESCRIPTION_DI: "input-issue-description-di",
          DATALIST_COLLECTOR_PROFILE_DI: "datalist-collector-profile-di",
          DATALIST_COLLECTOR_PROFILE_DI_REVIEW: "datalist-collector-profile-di-review",
          DATALIST_DELIVERY_ISSUE_DETAIL: 'datalist-delivery-issue-detail',
          DATALIST_MERCH_DELIVERY_DETAILS: 'datalist-merchandise-delivery-details',
          MERCH_INSTRUCTIONS: 'merch-instructions'
        },
        elementNames: {
          DROPDOWN_ITEM: "merch-item",
          INPUT_ORDER_NUMBER: "order-number",
        }
    };

    let merchElementSelected = null;
    let merchOptionValueSelected = null;
    let orderNumberIsValid = 0;
    let dateIsValid = 0;
    let typeOfMerchandiseIsValid = 0;
    let descriptionOfHelpIsValid = 0;
    let diOrderNumberIsValid = 0;
    let issueDescriptionIsValid = 0;
    let imagesUploaded = 0;
    let issueTypeValue = null;
    const orderDetailsObject = {};
    const merchRewardDetailsObject = {};
    const merchandiseDeliveryDetailObject = {};
    let firstDropdown = {};
    let collectorObject = {};

    function hideElement(element) {
        element.classList.add('hide-element');
    }
    function convertToNumberValue(event) {
      const inputValue = event.target.value.replace(/\D/g, '');
      event.target.value = inputValue;
    }

    function validateReceiveDate(inputParent, inputDate, inputValue) {
      const currentDate = new Date();
      const newDate = new Date(inputValue);
      const minDate = new Date((currentDate.getFullYear()-2)+'-'+ (currentDate.getMonth()+1)+'-'+ currentDate.getDate());
      return validateInput(inputParent, !(newDate < currentDate && newDate > minDate))
    }

    function validateAllInputs(formTextList) {
      Array.from(formTextList, element => {
        const input = element.querySelector('[class*="cmp-form-text"]')
        switch(input.id) {
          case CONST.ids.INPUT_ORDER_NUMBER:
            orderNumberIsValid = validateInput(element, input.value.length != 15)
            orderDetailsObject.orderNumber = input.value;
            break;
          case CONST.ids.INPUT_ORDER_RECEIVE_DATE:
            dateIsValid = validateReceiveDate(element, input, input.value);
            orderDetailsObject.orderReceiveDate = input.value;
            break;
          case CONST.ids.INPUT_TYPE_OF_MERCHANDISE:
            typeOfMerchandiseIsValid = validateInput(element, !input.value);
            orderDetailsObject.typeOfMerchandise = input.value;
            break;
          case CONST.ids.INPUT_DESCRIPTION_OF_HELP:
            descriptionOfHelpIsValid = validateInput(element, !input.value);
            merchRewardDetailsObject.descriptionOfHelp = input.value;
            break;
          case CONST.ids.INPUT_ORDER_NUMBER_DI:
            diOrderNumberIsValid = validateInput(element, input.value.length != 15)
            merchandiseDeliveryDetailObject.orderNumber = input.value;
            break;
          case CONST.ids.INPUT_DELIVERY_STATUS_DI:
            merchandiseDeliveryDetailObject.deliveryStatus = input.value;
            break;
          case CONST.ids.INPUT_ISSUE_DESCRIPTION_DI:
            issueDescriptionIsValid = validateInput(element, !input.value);
            merchandiseDeliveryDetailObject.issueDescription = input.value;
            break;
        }
      })
    }
    
    //Input events
    function updateNextDropdown(selectList){
      const firstSelect = document.getElementById("first-merch-select");
      merchOptionValueSelected = firstSelect.options[firstSelect.selectedIndex].value;

      Array.from(selectList, select => {
        if(merchOptionValueSelected == select.id) {
          select.parentElement.parentElement.classList.remove('hide-element');
          merchElementSelected = select;
        } else {
          select.parentElement.parentElement.classList.add('hide-element');
        }
      });
    }

    function runInputEvents(component) {
      const inputOrderNumber = component.querySelector(`[name="${CONST.elementNames.INPUT_ORDER_NUMBER}"]`);
      const inputDate = component.querySelector(`#${CONST.ids.INPUT_ORDER_RECEIVE_DATE}`)
      const inputTypeOfMerchandise = component.querySelector(`#${CONST.ids.INPUT_TYPE_OF_MERCHANDISE}`)
      const inputDescriptionOfHelp = component.querySelector(`#${CONST.ids.INPUT_DESCRIPTION_OF_HELP}`)
      const inputIssueDescription = component.querySelector(`#${CONST.ids.INPUT_ISSUE_DESCRIPTION_DI}`)

      inputOrderNumber && inputOrderNumber.addEventListener('input', (event) => {
        convertToNumberValue(event);
      });
      
      inputOrderNumber && inputOrderNumber.addEventListener('blur', (event) => {
        const inputValue = event.target.value;
        validateInput(inputOrderNumber.parentElement, inputValue.length != 15)
      })

      inputDate && inputDate.addEventListener('blur', (event) => {
        const inputValue = event.target.value;

        validateReceiveDate(inputDate.parentElement, inputDate, inputValue);
      })
      inputTypeOfMerchandise && inputTypeOfMerchandise.addEventListener('blur', (event) => {
        const inputValue = event.target.value;
        validateInput(inputTypeOfMerchandise.parentElement, !inputValue)
      })
      inputDescriptionOfHelp && inputDescriptionOfHelp.addEventListener('blur', (event) => {
        const inputValue = event.target.value;
        validateInput(inputDescriptionOfHelp.parentElement, !inputValue)
      })
      inputIssueDescription && inputIssueDescription.addEventListener('blur', (event) => {
        const inputValue = event.target.value;
        validateInput(inputIssueDescription.parentElement, !inputValue)
      })
    }

    //Button events
    function continueToDetails(component) {
      const btnContinue = component.querySelector(CONST.ids.BTN_CONTINUE_TO_DETAILS_SCREEN)
      const step0Container = component.querySelector(`#${CONST.ids.STEP0}`).parentElement
      const step1Container = component.querySelector(`#${CONST.ids.STEP1}`).parentElement
      const step1OrderIssueContainer = component.querySelector(`#${CONST.ids.STEP1_ORDER_ISSUE}`).parentElement
      const step0DeliveryIssueContainer = component.querySelector(`#${CONST.ids.STEP0_DELIVERY_ISSUE}`).parentElement
      const stepNavigation = component.querySelector(CONST.classnames.STEP_NAVIGATION).parentElement
      const dataListCollector = component.querySelectorAll(`#${CONST.ids.DATALIST_COLLECTOR_PROFILE1} .cmp-data-list__item span`)
      const dataListIssueType = component.querySelectorAll(`#${CONST.ids.DATALIST_ISSUE_TYPE} .cmp-data-list__item span`)
      const dataListOrderType = component.querySelectorAll(`#${CONST.ids.DATALIST_ORDER_ISSUE_TYPE} .cmp-data-list__item span`)
      const instructionsContainer = component.querySelector(`#${CONST.ids.MERCH_INSTRUCTIONS}`).parentElement
      const dateField = component.querySelector(`#${CONST.ids.INPUT_ORDER_RECEIVE_DATE}`)
      runInputEvents(step1Container);
      profileApiCall();

      async function profileApiCall() {
        await useFetch(getApiHost() + apiURL.profile, true)
            .then((data) => {
                if(data && data.profile['contactDetails']['emailStatus']) {
                  setContactDetails(data.profile.contactDetails);
                }
                collectorObject = buildCollectorObj();
                if(collectorObject && sessionStorage.getItem('am_emailStatus')=='VERIFIED') disableModal(btnContinue);
                btnContinue.addEventListener('click',() => {
                    if(!collectorObject || !validateInput(firstDropdown.parentElement, !merchOptionValueSelected)) return false;
                    const optionSelected = merchElementSelected.options[merchElementSelected.selectedIndex];
                    merchRewardDetailsObject.issueType = optionSelected.label;
                    merchandiseDeliveryDetailObject.issueType = optionSelected.label;
                    issueTypeValue = optionSelected.value;
                    if(!validateInput(merchElementSelected.parentElement, !issueTypeValue)) return false;
                    step0Container.classList.add('hide-element');
                    if(dateField){
                      const date = new Date();
                      dateField.setAttribute("min",(date.getFullYear()-2)+'-'+ (date.getMonth()+1)+'-'+ date.getDate());
                      dateField.setAttribute("max", date.getFullYear()+'-'+ (date.getMonth()+1)+'-'+ date.getDate());
                    }
                    switch(merchOptionValueSelected) {
                      case 'PRODUCT_ISSUE':
                        // Text which need to be shown only for Defective Item issue type
                        const defectiveItemTextIds = document.querySelectorAll('#defective-item-text');
                        if(defectiveItemTextIds){
                          if(issueTypeValue != 'DEFECTIVE_ITEM'){
                            defectiveItemTextIds.forEach((item) => {
                              item.classList.add('d-none');
                            });
                          }
                          else {
                            defectiveItemTextIds.forEach((item) => {
                              item.classList.remove('d-none');
                            });
                          }
                        }
                        if(component.querySelector('#image-upload-container')){
                          if(issueTypeValue == 'MISSING_ITEMS'){
                            component.querySelector('.cmp-form-archive-upload').classList.add('d-none');
                            component.querySelector('#image-upload-container').classList.add('d-none');
                          }
                          else{
                            component.querySelector('.cmp-form-archive-upload').classList.remove('d-none');
                            component.querySelector('#image-upload-container').classList.remove('d-none');
                          }
                        }
                        setDataOnDataList(collectorObject, dataListCollector)
                        step1Container.classList.remove('hide-element');
                        stepNavigation.classList.remove('hide-element');
                        formLoadEvent('product-issue');
                        break;
                      case 'DELIVERY_ISSUE':
                        step0DeliveryIssueContainer.classList.remove('hide-element');
                        if(optionSelected.value == 'DELIVERY_ADDRESS_CORRECTION'){
                          step0DeliveryIssueContainer.classList.add('option-address-change-selected');
                        } else {
                          step0DeliveryIssueContainer.classList.remove('option-address-change-selected');
                        }
                        setDataOnDataList(merchRewardDetailsObject, dataListIssueType)
                        formLoadEvent('delivery-issue');
                        break;
                      case 'ORDER_MANAGEMENT':
                        step1OrderIssueContainer.classList.remove('hide-element');
                        setDataOnDataList(merchRewardDetailsObject, dataListOrderType)
                        break;
                      default:
                        return false;
                    }
                  })
            })
            .catch((error) => {
              console.error('Error:', error);
            });
      }
    }
    function backToStep0(component) {
      const btnBack = component.querySelector(CONST.ids.BTN_BACK_TO_STEP0);
      const merchInstructionsBtnBack = component.querySelector(CONST.ids.MERCH_BTN_BACK_TO_STEP0);
      const diBtnBack = component.querySelector(`#${CONST.ids.DI_BTN_BACK_TO_INITIAL_STEP}`);
      const omBtnBack = component.querySelector(`#${CONST.ids.OM_BTN_BACK_TO_STEP0}`) 
      const step0Container = component.querySelector(`#${CONST.ids.STEP0}`).parentElement;
      const step1Container = component.querySelector(`#${CONST.ids.STEP1}`).parentElement;
      const step0DeliveryIssueContainer = component.querySelector(`#${CONST.ids.STEP0_DELIVERY_ISSUE}`).parentElement
      const step1OrderIssueContainer = component.querySelector(`#${CONST.ids.STEP1_ORDER_ISSUE}`).parentElement
      const stepNavigation = component.querySelector(CONST.classnames.STEP_NAVIGATION).parentElement;
      const instructionsContainer = component.querySelector(`#${CONST.ids.MERCH_INSTRUCTIONS}`).parentElement

      btnBack.onclick = () => {
        step0Container.classList.remove('hide-element');
        step1Container.classList.add('hide-element');
        stepNavigation.classList.add('hide-element');
      }

      merchInstructionsBtnBack.onclick = () => {
        step0Container.classList.remove('hide-element');
        instructionsContainer.classList.add('hide-element');
      }

      diBtnBack.onclick = () => {
        step0Container.classList.remove('hide-element');
        step0DeliveryIssueContainer.classList.add('hide-element');
      }
      omBtnBack.onclick = () => {
        step0Container.classList.remove('hide-element');
        step1OrderIssueContainer.classList.add('hide-element');
      }
    }

    //STEP 2
    function continueToReview(component) {
      const btnContinue = component.querySelector(CONST.ids.BTN_CONTINUE_TO_REVIEW)
      const step1Container = component.querySelector(`#${CONST.ids.STEP1}`).parentElement
      const step2Container = component.querySelector(`#${CONST.ids.STEP2}`).parentElement
      const formTextList = step1Container.querySelectorAll(CONST.classnames.FORM_TEXT);
      const stepNavigationList = component.querySelector(`${CONST.classnames.STEP_NAVIGATION} ul`)
      const stepNavigationItem1 = stepNavigationList.querySelector('[data-step-number="1"]')
      const stepNavigationItem2 = stepNavigationList.querySelector('[data-step-number="2"]')
      const dataListCollector = component.querySelectorAll(`#${CONST.ids.DATALIST_COLLECTOR_PROFILE2} .cmp-data-list__item span`)
      const dataListOrderDetails = component.querySelectorAll(`#${CONST.ids.DATALIST_ORDER_DETAILS} .cmp-data-list__item span`)
      const dataListMerchRewardDetails = component.querySelectorAll(`#${CONST.ids.DATALIST_MERCHANDISE_REWARD_DETAILS} .cmp-data-list__item span`)
      const imagesUploadedCount = component.querySelector('.cmp-form-archive-upload__uploaded--list');
      const fileInput = component.querySelector('.cmp-form-archive-upload .file-input');
      const isImageUploadRequired = fileInput ? fileInput.required: false;
      btnContinue.onclick = () => {
        validateAllInputs(formTextList);
        if(fileInput){
          if(imagesUploadedCount != null && isImageUploadRequired) {
            if(parseInt(imagesUploadedCount.getAttribute('data-uploadedFiles')) > 0 ){
              component.querySelector('.cmp-form-archive-upload .cmp-form-text__error').classList.add('hide-element');
              component.querySelector('.cmp-form-archive-upload').classList.remove('error');
              imagesUploaded = 1;
            }
            else{
              component.querySelector('.cmp-form-archive-upload').classList.add('error');
              component.querySelector('.cmp-form-archive-upload .cmp-form-text__error').classList.remove('hide-element');
              imagesUploaded = 0;
            }
          }
        }
        if(!isImageUploadRequired || issueTypeValue == 'MISSING_ITEMS') {
          const reviewImages = component.querySelector('#review-uploaded-images');
          if(reviewImages){
            reviewImages.classList.add('d-none');
          }
          imagesUploaded = 1;
        }
        if(orderNumberIsValid && dateIsValid && typeOfMerchandiseIsValid && descriptionOfHelpIsValid && imagesUploaded) {
          step1Container.classList.add('hide-element');
          step2Container.classList.remove('hide-element');
          stepNavigationList.classList.remove('step-1');
          stepNavigationList.classList.add('step-2');
          stepNavigationItem1.classList.remove('active');
          stepNavigationItem1.classList.add('done');
          stepNavigationItem2.classList.add('active');
          setDataOnDataList(collectorObject, dataListCollector);
          setDataOnDataList(orderDetailsObject, dataListOrderDetails);
          setDataOnDataList(merchRewardDetailsObject, dataListMerchRewardDetails);
          setImagesUploadedOnDataList('review-uploaded-images');
        }
      }
    }

    function backToStep1(component) {
      const btnBack = component.querySelector(`#${CONST.ids.BTN_BACK_TO_STEP1}`);
      const step1Container = component.querySelector(`#${CONST.ids.STEP1}`).parentElement;
      const step2Container = component.querySelector(`#${CONST.ids.STEP2}`).parentElement;
      const stepNavigationList = component.querySelector(`${CONST.classnames.STEP_NAVIGATION} ul`)
      const stepNavigationItem1 = stepNavigationList.querySelector('[data-step-number="1"]')
      const stepNavigationItem2 = stepNavigationList.querySelector('[data-step-number="2"]')

      btnBack.onclick = () => {
        step1Container.classList.remove('hide-element');
        step2Container.classList.add('hide-element');
        stepNavigationList.classList.add('step-1');
        stepNavigationList.classList.remove('step-2');
        stepNavigationItem1.classList.add('active');
        stepNavigationItem1.classList.remove('done');
        stepNavigationItem2.classList.remove('active');
      }
    }

    async function sendData(detailsObject){
      const payload = {
        ...detailsObject,
        name: collectorObject.collectorName,
        email: collectorObject.collectorEmail,
        contactPhone: collectorObject.collectorNumber,
        inquiryType: merchOptionValueSelected,
        issueType: issueTypeValue,
      }
      try {
        const response = await useFetchPost(getDomBFFDomainUrl()+"/dombff-dsse"+ apiURL.merchandiseIssue, true, payload, 'POST', '', null , false);
        return response;
      } catch (error) {
        console.error('Error:', error);
        return false;
      }
    }

    //STEP 3
    function continueToStep3(component) {
      const btnContinue = component.querySelector(`#${CONST.ids.BTN_CONTINUE_TO_STEP3}`)
      const step2Container = component.querySelector(`#${CONST.ids.STEP2}`).parentElement
      const step3SuccessContainer1 = component.querySelector(`#${CONST.ids.STEP3_SUCCESS_SCREEN1}`).parentElement
      const step3SuccessContainer2 = component.querySelector(`#${CONST.ids.STEP3_SUCCESS_SCREEN2}`).parentElement
      const step3ErrorContainer = component.querySelector(`#${CONST.ids.STEP3_ERROR_SCREEN}`).parentElement
      const stepNavigationList = component.querySelector(`${CONST.classnames.STEP_NAVIGATION} ul`)
      const stepNavigationItem2 = stepNavigationList.querySelector('[data-step-number="2"]')
      const stepNavigationItem3 = stepNavigationList.querySelector('[data-step-number="3"]')

      btnContinue.onclick = async () => {
        step2Container.classList.add('hide-element');
        const payload = {
          orderNumber: orderDetailsObject.orderNumber,
          issueDescription: merchRewardDetailsObject.descriptionOfHelp,
          productDescription: orderDetailsObject.typeOfMerchandise,
          receivedDate: orderDetailsObject.orderReceiveDate+'T00:00:00.000Z',
        }
        if(issueTypeValue != 'MISSING_ITEMS'){
          payload.imagesUuids = imageUuids;
        }
        const response = await sendData(payload);
        if(!response.error) {
          stepNavigationList.classList.remove('step-2');
          stepNavigationList.classList.add('step-3');
          stepNavigationItem2.classList.remove('active');
          stepNavigationItem2.classList.add('done');
          stepNavigationItem3.classList.add('active','done');
          step3SuccessContainer1.classList.remove('hide-element');
          formCompletedEvent('product-issue');
        } else {
          stepNavigationList.classList.add('hide-element');
          step3ErrorContainer.classList.remove('hide-element');
        }
      }
    }

    //Delivery Issue STEP1
    function continueToStep1Delivery(component) {
      const btnContinue = component.querySelector(`#${CONST.ids.DI_BTN_CONTINUE_TO_STEP1}`)
      const step1Container = component.querySelector(`#${CONST.ids.STEP1_DELIVERY_ISSUE}`).parentElement
      const stepNavigation = component.querySelector(CONST.classnames.STEP_NAVIGATION).parentElement
      const dataListCollector = component.querySelectorAll(`#${CONST.ids.DATALIST_COLLECTOR_PROFILE_DI} .cmp-data-list__item span`)
      const step0DeliveryIssueContainer = component.querySelector(`#${CONST.ids.STEP0_DELIVERY_ISSUE}`).parentElement
      const dataListDeliveryIssueDetail = component.querySelectorAll(`#${CONST.ids.DATALIST_DELIVERY_ISSUE_DETAIL} .cmp-data-list__item span`)
      runInputEvents(step1Container);
      btnContinue.addEventListener('click', () => {
        step0DeliveryIssueContainer.classList.add('hide-element');
        step1Container.classList.remove('hide-element');
        stepNavigation.classList.remove('hide-element');
        setDataOnDataList(collectorObject, dataListCollector)
        setDataOnDataList(merchRewardDetailsObject, dataListDeliveryIssueDetail)
      })
    }
    function backToStep0DeliveryIssue(component) {
      const btnBack = component.querySelector(`#${CONST.ids.DI_BTN_BACK_TO_STEP0}`);
      const step0DeliveryIssueContainer = component.querySelector(`#${CONST.ids.STEP0_DELIVERY_ISSUE}`).parentElement
      const step1DeliveryIssueContainer = component.querySelector(`#${CONST.ids.STEP1_DELIVERY_ISSUE}`).parentElement
      const stepNavigation = component.querySelector(CONST.classnames.STEP_NAVIGATION).parentElement;

      btnBack.onclick = () => {
        step0DeliveryIssueContainer.classList.remove('hide-element');
        step1DeliveryIssueContainer.classList.add('hide-element');
        stepNavigation.classList.add('hide-element');
      }
    }

    //Delivery Issue STEP2
    function continueToStep2Delivery(component) {
      const btnContinue = component.querySelector(`#${CONST.ids.DI_BTN_CONTINUE_TO_STEP2}`)
      const step1Container = component.querySelector(`#${CONST.ids.STEP1_DELIVERY_ISSUE}`).parentElement
      const step2Container = component.querySelector(`#${CONST.ids.STEP2_DELIVERY_ISSUE}`).parentElement
      const formTextList = step1Container.querySelectorAll(CONST.classnames.FORM_TEXT);
      const stepNavigationList = component.querySelector(`${CONST.classnames.STEP_NAVIGATION} ul`)
      const stepNavigationItem1 = stepNavigationList.querySelector('[data-step-number="1"]')
      const stepNavigationItem2 = stepNavigationList.querySelector('[data-step-number="2"]')
      const dataListCollector = component.querySelectorAll(`#${CONST.ids.DATALIST_COLLECTOR_PROFILE_DI_REVIEW} .cmp-data-list__item span`)
      const dataListMerchDeliveryDetails = component.querySelectorAll(`#${CONST.ids.DATALIST_MERCH_DELIVERY_DETAILS} .cmp-data-list__item span`)

      btnContinue.onclick = () => {
        validateAllInputs(formTextList);
        if(diOrderNumberIsValid && issueDescriptionIsValid) {
          step1Container.classList.add('hide-element');
          step2Container.classList.remove('hide-element');
          stepNavigationList.classList.remove('step-1');
          stepNavigationList.classList.add('step-2');
          stepNavigationItem1.classList.remove('active');
          stepNavigationItem1.classList.add('done');
          stepNavigationItem2.classList.add('active');
          setDataOnDataList(collectorObject, dataListCollector)
          setDataOnDataList(merchandiseDeliveryDetailObject, dataListMerchDeliveryDetails)
        }
      }
    }
    function backToStep1DeliveryIssue(component) {
      const btnBack = component.querySelector(`#${CONST.ids.DI_BTN_BACK_TO_STEP1}`);
      const step1Container = component.querySelector(`#${CONST.ids.STEP1_DELIVERY_ISSUE}`).parentElement;
      const step2Container = component.querySelector(`#${CONST.ids.STEP2_DELIVERY_ISSUE}`).parentElement;
      const stepNavigationList = component.querySelector(`${CONST.classnames.STEP_NAVIGATION} ul`)
      const stepNavigationItem1 = stepNavigationList.querySelector('[data-step-number="1"]')
      const stepNavigationItem2 = stepNavigationList.querySelector('[data-step-number="2"]')

      btnBack.onclick = () => {
        step1Container.classList.remove('hide-element');
        step2Container.classList.add('hide-element');
        stepNavigationList.classList.add('step-1');
        stepNavigationList.classList.remove('step-2');
        stepNavigationItem1.classList.add('active');
        stepNavigationItem1.classList.remove('done');
        stepNavigationItem2.classList.remove('active');
      }
    }

    //Delivery Issue Step3
    function continueToStep3Delivery(component) {
      const btnContinue = component.querySelector(`#${CONST.ids.DI_BTN_CONTINUE_TO_STEP3}`)
      const step2Container = component.querySelector(`#${CONST.ids.STEP2_DELIVERY_ISSUE}`).parentElement
      const step3SuccessContainer = component.querySelector(`#${CONST.ids.STEP3_SUCCESS_SCREEN1}`).parentElement
      const step3ErrorContainer = component.querySelector(`#${CONST.ids.STEP3_ERROR_SCREEN}`).parentElement
      const stepNavigationList = component.querySelector(`${CONST.classnames.STEP_NAVIGATION} ul`)
      const stepNavigationItem2 = stepNavigationList.querySelector('[data-step-number="2"]')
      const stepNavigationItem3 = stepNavigationList.querySelector('[data-step-number="3"]')

      btnContinue.onclick = async () => {
        step2Container.classList.add('hide-element');
        const payload = {
          orderNumber: merchandiseDeliveryDetailObject.orderNumber,
          issueDescription: merchandiseDeliveryDetailObject.issueDescription,
          dedliveryStatus: merchandiseDeliveryDetailObject.deliveryStatus,
        }
        const response = await sendData(payload)
        if(!response.errorCode) {
          stepNavigationList.classList.remove('step-2');
          stepNavigationList.classList.add('step-3');
          stepNavigationItem2.classList.remove('active');
          stepNavigationItem2.classList.add('done');
          stepNavigationItem3.classList.add('active','done');
          step3SuccessContainer.classList.remove('hide-element');
          formCompletedEvent('delivery-issue')
        } else {
          stepNavigationList.classList.add('hide-element');
          step3ErrorContainer.classList.remove('hide-element');
        }
      }
    }

    //Initialization
    function initComponent() {
      const component = document.querySelector(CONST.classnames.FORM_CONTAINER);
      if(component) {
        const selectList = component.querySelectorAll(`[name="${CONST.elementNames.DROPDOWN_ITEM}"]`);
        const formTextList = component.querySelectorAll(CONST.classnames.FORM_TEXT);
        const formOptionsList = component.querySelectorAll(CONST.classnames.FORM_OPTIONS);
        firstDropdown = document.getElementById(CONST.ids.FIRST_DROPDOWN);
        merchElementSelected = selectList[0];
        merchOptionValueSelected = firstDropdown.options[firstDropdown.selectedIndex].value;
        Array.from(selectList, select => {
          hideElement(select.parentElement.parentElement)
        })
        
        activeInputLabel(formTextList);
        activeSelectLabel(formOptionsList);
        //Add functionality on Cancel button of modal
        cancelModal(component)
        //Product Issue
        continueToDetails(component)
        continueToReview(component)
        backToStep0(component)
        backToStep1(component)
        continueToStep3(component)
        //Delivery Issue
        continueToStep1Delivery(component)
        backToStep0DeliveryIssue(component)
        continueToStep2Delivery(component)
        backToStep1DeliveryIssue(component)
        continueToStep3Delivery(component)

        //Attach event listener to the first dropdown
        firstDropdown.addEventListener('change', updateNextDropdown.bind(null, selectList));

        //Clear dropdown values when navigate to external browser
        clearDropdownValues(formOptionsList);
      }
    }
    
    function onDocumentReady() {
      initComponent();
    }
  
    if (document.readyState !== "loading") {
      onDocumentReady();
    } else {
      document.addEventListener("DOMContentLoaded", onDocumentReady);
    }
  })();