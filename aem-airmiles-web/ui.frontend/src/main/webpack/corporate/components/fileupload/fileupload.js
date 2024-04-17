import {apiURL, getDomBFFDomainUrl, useFetchPost} from "../../../dependencies/js/api";
import {createElement} from "../../../dependencies/js/utils";

export const imageUuids = [];
let storedFileNames = [];
export const setImagesUploadedOnDataList = (id) => {
    const reviewUploadedImages = document.getElementById(id);
    const reviewImagesList = reviewUploadedImages.querySelector('.cmp-form-archive-upload__uploaded--list');
    if(reviewImagesList){
        reviewImagesList.remove();
    }
    const fileListWrapper = createElement('ul',['cmp-form-archive-upload__uploaded--list'],null,'','');
    for (const file of storedFileNames) {
        const liElement = createElement("li",["cmp-form-archive-upload__uploaded--list--item"],fileListWrapper,'','');
        const rowDiv = createElement("div",["row", "align-items-center"],liElement,'','');
        const col8Div = createElement("div",["col-10", "d-flex", "align-items-center", "py-2"], rowDiv,'','');
        createElement("span",["am-icon2", "am-icon2-icon-file", "pe-3"],col8Div,'','');
        const detailsDiv = createElement("div",[],col8Div,'','');
        createElement("span",["cmp-form-archive-upload__uploaded--list--item--name"], detailsDiv,'',file.name);
        createElement("span",["cmp-form-archive-upload__uploaded--list--item--size"], detailsDiv, '',file.size);
        createElement('div',['d-flex'], detailsDiv, '', '');
        reviewUploadedImages.appendChild(fileListWrapper);
    }
};
(() => {
    document.addEventListener('DOMContentLoaded', function () {
        const CLICK = 'click';
        const DRAGENTER = 'dragenter';
        const DRAGOVER = 'dragover';
        const DRAGLEAVE = 'dragleave';
        const DROP = 'drop';

        const _archiveUpload = document.querySelectorAll('.cmp-form-archive-upload');
        if (!_archiveUpload || _archiveUpload.length === 0) return;
        _archiveUpload.forEach((th1$) => {
            const _dropArea = th1$.querySelector('.cmp-form-archive-upload__choose--file');
            const _uploadWrapper = th1$.querySelector('.cmp-form-archive-upload__drop-area');
            const _fileInput = th1$.querySelector('.file-input');

            _dropArea.addEventListener(CLICK, function () {
                chooseFile(_uploadWrapper, _fileInput, th1$);
            });

            _fileInput.addEventListener('change',
                (e) => handleFiles(e.target.files, _uploadWrapper, _fileInput, th1$));

            // Prevent default drag behaviors
            [DRAGENTER, DRAGOVER, DRAGLEAVE, DROP].forEach(eventName => {
                document.body.addEventListener(eventName, (e) => {
                    e.preventDefault();
                    e.stopPropagation();
                }, false);
            });

            // Highlight drop area with a color transition
            [DRAGENTER, DRAGOVER].forEach(eventName => {
                _uploadWrapper.addEventListener(eventName, () => {
                    _uploadWrapper.classList.add('dragging');
                }, false);
            });

            // Unhighlight drop area with a color transition
            [DRAGLEAVE, DROP].forEach(eventName => {
                _uploadWrapper.addEventListener(eventName, () => {
                    _uploadWrapper.classList.remove('dragging');
                }, false);
            });

            // Handle dropped files
            _uploadWrapper.addEventListener(DROP, (e) => {
                const dataTransfer = e.dataTransfer;
                const files = dataTransfer.files;
                handleFiles(files, _uploadWrapper, _fileInput, th1$);
            }, false);
        });

        function chooseFile(_uploadWrapper, _fileInput, parent) {
            _fileInput.click();
        }

        function bytesToMB(bytes) {
            return (bytes / (1024 * 1024)).toFixed(2) + " mb";
        }

        function setUpModal(id, fileName, _fileInput){
            const cancelButton = document.getElementById(id).querySelector('#cancel-modal');
            cancelButton.setAttribute('data-bs-dismiss','modal');
            cancelButton.setAttribute('aria-label','Close');
            document.getElementById(id).querySelector('#image_name').innerText = fileName;
            const _modalJS = new bootstrap.Modal('#'+id);
            _fileInput.value = null;
            _modalJS.show();
        }

        function handleFiles(files, _uploadWrapper, _fileInput, parent) {
            const fileList = parent.querySelector('.cmp-form-archive-upload__uploaded--list');
            const maxFiles = parseInt(_uploadWrapper.getAttribute('data-max-files'));
            const uploadingText = _uploadWrapper.getAttribute('data-upload-wip');
            const uploadErrorText = _uploadWrapper.getAttribute('data-upload-error');
            const uploadingCompletedText = _uploadWrapper.getAttribute('data-upload-done');
            const maxSize = getAllowedFileSize(_uploadWrapper);
            parent.querySelector('.cmp-form-archive-upload .cmp-form-text__error').classList.add('hide-element');

            // Iterate through the selected files
            for (const file of files) {
                const formData = new FormData();
                formData.append('file', file);
                const fileSize = bytesToMB(file.size);
                const liElement = createElement("li", ['cmp-form-archive-upload__uploaded--list--item'],null,'','');
                const rowDiv = createElement("div",['row', 'align-items-center'],null,'','');
                const col8Div = createElement("div",["col-10", "d-flex", "align-items-center", "py-2"],rowDiv,'','');
                createElement("span",["am-icon2", "am-icon2-icon-file", "pe-3"],col8Div,'','');
                const detailsDiv = createElement("div",[],col8Div,'','');
                createElement("span",["cmp-form-archive-upload__uploaded--list--item--name"],detailsDiv,'',file.name);
                createElement("span",["cmp-form-archive-upload__uploaded--list--item--size"],detailsDiv,'',fileSize);
                const elem = createElement('div',['d-flex'],detailsDiv,'','');
                const spinnerSpan = createElement("span",['upload-spinner'],elem,'','');
                const statusP = createElement("span",["cmp-form-archive-upload__uploaded--list--item--status"],elem,'',uploadingText);
                const col4Div = createElement("div",["col-2", "text-end"], rowDiv,'','');
                const deleteIconSpan = createElement("span",["am-icon2", "am-icon2-icon-delete"], col4Div, '','');

                // Check if the number of selected files exceeds the limit
                if (fileList.children.length >= maxFiles) {
                    setUpModal('max-images-error', file.name, _fileInput);
                    return;
                }
                // Validate extension.
                if (!validateExt(file.name, _uploadWrapper)) {
                    setUpModal('image-extension-error', file.name, _fileInput);
                    elem.classList.add('d-none');
                    return;
                }
                else if (file.size > maxSize * 1048576) {
                    setUpModal('image-size-error', file.name, _fileInput);
                    return;
                }
                else{
                    liElement.appendChild(rowDiv);
                    fileList.appendChild(liElement);
                    fileList.setAttribute("data-uploadedFiles", fileList.children.length.toString());
                    document.querySelector('.cmp-form-archive-upload__uploaded--files').classList.remove('d-none');
                    document.querySelector('.cmp-form-archive-upload__uploaded--list').classList.remove('d-none');
                    useFetchPost(getDomBFFDomainUrl()+apiURL.imageUpload+"/upload", false, formData, 'POST', 'image-upload').then((data)=>{
                        if(data && data['fileUuid'] != null){
                            liElement.setAttribute('data-uuid', data['fileUuid']);
                            spinnerSpan.classList.remove('upload-spinner');
                            statusP.textContent = uploadingCompletedText;
                            storedFileNames.push( {
                                name: file.name,
                                size: fileSize,
                                fileuuid: data['fileUuid']
                            });
                            imageUuids.push(data['fileUuid']);
                        }
                        else{
                            spinnerSpan.classList.add('d-none');
                            statusP.textContent = uploadErrorText;
                            statusP.classList.add('am-icon2', 'am-icon2-critical-empty', 'error');
                        }
                         if(imageUuids.length > 0){
                             document.querySelector('.cmp-form-archive-upload__uploaded--files--number').innerHTML = ' ('+imageUuids.length+' of '+maxFiles+' allowed)';
                         }
                    });
                }

                deleteIconSpan.addEventListener(CLICK, () => {
                    const uuid = liElement.getAttribute('data-uuid');
                    useFetchPost(getDomBFFDomainUrl()+apiURL.imageUpload+"/"+uuid+"/remove", false, formData, 'DELETE', '').then((data)=>{
                        fileList.removeChild(liElement);
                        fileList.setAttribute("data-uploadedFiles", fileList.children.length.toString());
                        imageUuids.splice(imageUuids.indexOf(uuid),1);
                        storedFileNames = storedFileNames.filter(item => item.fileuuid != uuid);
                        if(fileList.children.length == 0){
                            document.querySelector('.cmp-form-archive-upload__uploaded--files').classList.add('d-none');
                            document.querySelector('.cmp-form-archive-upload__uploaded--list').classList.add('d-none');
                        }
                        if(imageUuids.length > 0){
                            document.querySelector('.cmp-form-archive-upload__uploaded--files--number').innerHTML = ' ('+imageUuids.length+' of '+maxFiles+' allowed)';
                        }
                    });
                });
            }
        }

        function validateExt(filename, _uploadWrapper) {
            const allowedExtensions = _uploadWrapper.getAttribute('data-allowed-extensions');
            if (!allowedExtensions) {
                return false;
            }
            return allowedExtensions.split(',').includes(filename.split('.').pop());
        }

        function getAllowedFileSize(_uploadWrapper) {
            if (!_uploadWrapper) return 0;
            const _size = +_uploadWrapper.getAttribute('data-allowed-size');
            if (!_size) return 0;
            return _size;
        }
    });
})();