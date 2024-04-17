import {getApiHost, useFetch, apiURL} from "../../../dependencies/js/api";
import {getMemdeets, isAuthed, shortDate} from "../../../dependencies/js/utils";

(() => {
    document.addEventListener("DOMContentLoaded", function () {

        const _transTable = document.querySelector(".transaction-table-wrapper");
        if(!_transTable){return;}
        const _amTable = document.querySelector(".amtable-main");
        if(!_amTable){return;}
        const noData = _amTable.querySelector(".amtable-footer");

        const lang = _transTable.getAttribute('data-lang');
        const language = lang === 'en'? 'E':'F';
        const resourcePath = _transTable.getAttribute('data-contentPath');
        const mockup = document.getElementById('js-trans-table-json');
        const _nowDate = new Date();
        const _pastDate = new Date();
        _pastDate.setFullYear(_pastDate.getFullYear() - 1);
        const _nowStamp = _nowDate.toISOString();
        const _pastStamp = _pastDate.toISOString();
        let _pathParam = apiURL.transaction;
        let amTableDate = "";
        let transactionAuthoredValues = "";

        function findChannelLabelByChannelType(channelType){
            const result = transactionAuthoredValues.filter(function (obj) {
                return obj['channelType'] === channelType;
            });
            return result[0];
        }

        function findChannelLabelByActivityType(activityType){
            const result = transactionAuthoredValues.filter(function (obj) {
                return obj['activityType'] === activityType;
            });
            return result[0];
        }

        function findTransactionDescriptionByCharityDetails(activityType, tDesc) {
            const result = findChannelLabelByActivityType(activityType);

            const charityDetails = result['charityDetails'].filter(function(obj){
                if(obj != null){
                    if(obj.charityDescription === tDesc.toString()){
                        return obj;
                    }
                }
            });
            return charityDetails[0];
        }

        function conditionA(_data){
            let description = '';
            if(_data['sponsorCode'].toLowerCase()==='shop'){
                if(_data['locationDescription']){
                    description = _data['locationDescription'];
                }else{
                    description = language==='E' ? _data['offerDescriptionEnglish'] : _data['offerDescriptionFrench'];
                }
            }else{
                description = language==='E' ? _data['offerDescriptionEnglish'] : _data['offerDescriptionFrench'];
            }
            return description;
        }

        function conditionB(_data){
            let description = '';
            if(_data['redemptionStatus']==="MILES_CREDITED"){
                const result = transactionAuthoredValues.filter(function (obj) {
                    return obj['redemptionStatus'] === "MILES_CREDITED";
                });
                description = result[0];
            }
            if(_data['redemptionStatus']==="MILES_VOIDED"){
                const result = transactionAuthoredValues.filter(function (obj) {
                    return obj['redemptionStatus'] === "MILES_VOIDED";
                });
                description = result[0];
            }
            if(_data['redemptionStatus'] !== "MILES_CREDITED" && _data['redemptionStatus'] !== "MILES_VOIDED"){
                const channelType = _data['channel'];
                if(channelType === "EVR" || channelType === "POS" || channelType === "AMC" || channelType === "MER" || channelType === "PSH" || channelType === "PTS" || channelType === "RED" || channelType === "DON"){
                    description= findChannelLabelByChannelType(channelType);
                }
                else {
                    if(_data['transactionDescription'].toUpperCase() === "TRAVEL BOOKING"){
                        description=findChannelLabelByChannelType('DEFAULT-1');
                    }else{
                        description=findChannelLabelByChannelType('DEFAULT-2');
                    }
                }
            }
            return description;
        }

        function conditionC(_data){
            let description = '';
            if(_data['activityType'].toUpperCase()==='TRANSFER_OUT' || _data['activityType'].toUpperCase()==='TRANSFER_IN' || _data['activityType'].toUpperCase()==='RETIREMENT'){
                description = findChannelLabelByActivityType(_data['activityType']);
            }else{
                description = findChannelLabelByActivityType('DEFAULT');
            }
            return description;
        }

        function generateTable(_data, _lang=''){
            if(_data.length > 0){
                for (const element of _data) {
                    const transElm = document.createElement("div");
                    const _eDate = shortDate(element['transactionDate'],_lang);
                    const _partnerName = element['partnerName'];
                    let tempData = {};
                    let charityDetails = '';
                    let issuanceChannelLabel='';

                    //Check each activity type
                    if(element['activityType'] === "ISSUANCE" || element['activityType'] === "NEGATIVE_ISSUANCE"){
                        issuanceChannelLabel = conditionA(element);
                    }else if(element['activityType'] === "REDEMPTION"){
                        tempData = conditionB(element);
                    }else if(element['activityType'] === "TRANSFER_OUT"){
                        charityDetails = findTransactionDescriptionByCharityDetails('TRANSFER_OUT',element['transactionDescription']);
                        tempData = conditionC(element);
                    }else{
                        tempData = conditionC(element);
                    }


                    //Grouping transactions based on date in mobile view
                    if (amTableDate !== _eDate) {
                        amTableDate = _eDate;
                        const dateBlock = document.createElement("div");
                        dateBlock.classList.add("amtable-cell", "amtable-mobile__date", "col-12", "d-md-none");
                        dateBlock.textContent = _eDate;
                        transElm.appendChild(dateBlock);
                    }
                    transElm.classList.add("row", "amtable-transaction-row");

                    //Transaction Date
                    const cell3 = document.createElement("div");
                    cell3.classList.add("amtable-cell", "d-none", "d-md-block", "col-md-3", "my-auto");
                    cell3.textContent = _eDate;
                    cell3.style.paddingRight = 0+'px';
                    transElm.appendChild(cell3);

                    const cell1 = document.createElement("div");
                    cell1.classList.add("amtable-cell", "col-3", "col-md-2", "my-auto", "text-center", "partner_img");
                    const img = document.createElement("img");

                    // logo preference --> authored logo -> logo from api response --> default logo
                    if(element['activityType'] === "ISSUANCE" || element['activityType'] === "NEGATIVE_ISSUANCE" ){
                        img.setAttribute("src", element['logo']?element['logo']:_amTable.dataset.defaultLogo);
                        img.setAttribute("alt", _partnerName);
                    }
                    else{
                        const _logo = element['logo']?element['logo']:_amTable.dataset.defaultLogo;
                        img.setAttribute("src", tempData['logoSrc'] ? tempData['logoSrc'] : _logo);
                        img.setAttribute("alt", tempData['logoAltText'] ? tempData['logoAltText'] : _partnerName);
                    }

                    cell1.appendChild(img);
                    transElm.appendChild(cell1);

                    //Transaction description
                    const cell2 = document.createElement("div");
                    cell2.classList.add("amtable-cell", "col-6", "col-md-4", "my-auto");
                    const descDiv = document.createElement("div");
                    descDiv.classList.add("amtable-mobile__desc");
                    if((tempData=== null || tempData===undefined || tempData==='') && (element['activityType'] !== 'ISSUANCE' || element['activityType'] !== 'NEGATIVE_ISSUANCE' || element['activityType'] !== 'TRANSFER_OUT')){
                        if(element['offerDescriptionEnglish'] != null && element['offerDescriptionFrench'] != null){
                            descDiv.textContent = _lang === 'E' ? element['offerDescriptionEnglish'] : element['offerDescriptionFrench'];
                        }
                    } else{
                        if(element['activityType'] === 'TRANSFER_OUT'){
                            if(charityDetails != null && charityDetails !== '' ){
                                descDiv.textContent = charityDetails['charityName'];
                            } else{
                                descDiv.textContent = tempData['channelLabel'];
                            }
                        } else if(element['activityType'] === 'ISSUANCE' || element['activityType'] === 'NEGATIVE_ISSUANCE'){
                            descDiv.textContent = issuanceChannelLabel;
                        } else{
                            descDiv.textContent = tempData['channelLabel'];
                        }
                    }

                    cell2.appendChild(descDiv);
                    transElm.appendChild(cell2);



                    //Transaction Value
                    const cell4 = document.createElement("div");
                    cell4.classList.add("amtable-cell", "col-3", "col-md-3", "my-auto", "amtable-cell__miles");
                    if(element['transactionValue'] > 0){
                        cell4.classList.add("--am-base-font-color-cash");
                    } else{
                        element['transactionValue'] === element['cashMiles'] ? cell4.classList.add("--am-base-font-color-cash-spent") : cell4.classList.add("--am-base-font-color-dream");
                    }
                    cell4.textContent = element['transactionValue'] > 0 ? `+${element['transactionValue']}` : element['transactionValue'];
                    transElm.appendChild(cell4);
                    _amTable.appendChild(transElm);
                }
                noData.remove();
            }
        }

        function getDataError(){
            const _errorMsg = noData.querySelector('.amtable-mobile__msg');
            _errorMsg.textContent = _errorMsg.dataset.apiError;
        }

        function getComponentValues(){
            useFetch(resourcePath+'.model.json', false)
                .then(data => {
                    transactionAuthoredValues = data['transactionValues'];
                    if(mockup){
                        const _att = JSON.parse(mockup.innerText);
                        generateTable(_att['_embedded']['genericActivityDtoList'],'E');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }

        function getTableData(_api) {
            _pathParam += `?page=1&size=5&from=${_pastStamp}&to=${_nowStamp}&sort=transactionDate,desc&locale=${lang}`;

            useFetch(_api+_pathParam, false)
                .then(data => {
                    if(data != null && data['_embedded'] != null && data['_embedded']['genericActivityDtoList'] != null){
                        generateTable(data['_embedded']['genericActivityDtoList'], language);
                    }
                })
                .catch(error => {
                    getDataError();
                    console.error('Error:', error);
                });
        }


        if(mockup){
            getComponentValues();
        }else{
            if (isAuthed()) {
                const _api = getApiHost();
                if(_api){getComponentValues();getTableData(_api);}
            }
        }
    });
})();