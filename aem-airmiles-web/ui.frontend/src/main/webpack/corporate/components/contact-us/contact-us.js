import {isAuthed, setWebCookie, getCookie} from "../../../dependencies/js/utils";
import {disableModal} from "../forms/utils/utils";
import {openLiveChatWidget} from "../../../dependencies/js/chat";
import {constants} from "../../../dependencies/js/constants";

(() => {
    document.addEventListener("DOMContentLoaded", function () {
        const chatWithUsContainer = document.querySelector('#chat-with-us-container');
        const callUsContainer = document.querySelector('#call-us-container');
        const contactUsContainer = document.querySelector('#contact-us-section');
        if(!contactUsContainer || !chatWithUsContainer || !callUsContainer) return;
        
        const CONST = {
            ids: {
                btnChatWithUs: "#btn-chat-with-us",
                btnCallUs: "#btn-call-us",
                tableLiveChatHours: "#table-live-chat-hours",
                btnLiveChatHours: "#btn-live-chat-hours",
                tableAllCentreHours: "#table-all-centre-hours",
                btnCallCentreHours:"#btn-call-centre-hours",
                btnSignIn: "#btn-signin",
                btnSkipToChat: "#btn-skip-to-chat"
            }
        }
        
        const btnLiveChatHours = chatWithUsContainer.querySelector(CONST.ids.btnLiveChatHours);
        const tableLiveChatHours = chatWithUsContainer.querySelector(CONST.ids.tableLiveChatHours);
        const btnCallCentreHours = callUsContainer.querySelector(CONST.ids.btnCallCentreHours);
        const tableAllCentreHours = callUsContainer.querySelector(CONST.ids.tableAllCentreHours);
        const btnChatWithUs = chatWithUsContainer.querySelector(CONST.ids.btnChatWithUs);
        const btnSignIn = contactUsContainer.querySelector(CONST.ids.btnSignIn);
        const btnSkipToChat = contactUsContainer.querySelector(CONST.ids.btnSkipToChat);

        const closeModal = (btnClose) => {
            btnClose.setAttribute('data-bs-dismiss','modal');
            btnClose.setAttribute('aria-label','Close');
        }

        isAuthed() ? disableModal(btnChatWithUs) : closeModal(btnSkipToChat);
        
        if (isAuthed() && getCookie("openChatDefault") === 'true') {
            openLiveChatWidget();
            btnChatWithUs.classList.add('disable');
        }

        btnLiveChatHours.addEventListener("click", () => {
            tableLiveChatHours.parentElement.classList.toggle('hide-element');
        });

        btnCallCentreHours.addEventListener("click", () => {
            tableAllCentreHours.parentElement.classList.toggle('hide-element');
        });

        btnChatWithUs && btnChatWithUs.addEventListener('click', ()=> {
            if(isAuthed()) {
                openLiveChatWidget();
                btnChatWithUs.classList.add('disable');
            }
        })

        btnSignIn && btnSignIn.addEventListener('click', ()=> {
            setWebCookie('openChatDefault', 'true', 1);
        })

        btnSkipToChat && btnSkipToChat.addEventListener('click', ()=> {
            openLiveChatWidget();
            btnChatWithUs.classList.add('disable');
        })

        if (sessionStorage.getItem(constants.sessionGenesysChatStatus) === 'open') {
            if(btnChatWithUs) btnChatWithUs.classList.add('disable');
        }
      
        window.Genesys && window.Genesys('subscribe', 'Launcher.hidden', () => {
            sessionStorage.removeItem(constants.sessionGenesysChatStatus);
            if(btnChatWithUs) btnChatWithUs.classList.remove('disable');
            setWebCookie('openChatDefault', 'false', 1);
        });
    })
})();