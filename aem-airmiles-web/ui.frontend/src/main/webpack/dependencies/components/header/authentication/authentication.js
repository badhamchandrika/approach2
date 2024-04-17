import {isAuthed, getMemdeets, getLocale, setWebCookie} from "../../../js/utils";
import {apiURL, getApiHost, useFetch} from "../../../js/api";

(() => {
    // Init authenticated status, following element MUST be in header components(topnav, memberbanner) with Exact ID(sign_in, sign_out, join_now)
    const _memberProfile = document.getElementById("member-profile");
    const _signInBtn = document.getElementById('sign_in');
    const _signOutBtn = document.getElementById('sign_out');
    const _joinNowBtn = document.getElementById('join_now');
    const _unAuthTopMenu = document.querySelectorAll('.headnav__button-unauthed');

    let initExecuted = false;

    // Check for member banner visibility in authoring mode
    function getAuthSession(_cash, _dream) {
        const _memberBanner = document.querySelectorAll('.memberbanner');
        const _mbData = getMemdeets();
        for (const element of _memberBanner) {
            // Update all user names under class 'memberbanner-item__name'.
            const _names = element.querySelectorAll('.memberbanner-item__name');
            for (const _name of _names) {
                _name.innerText = _mbData['firstName'].replace(/\+/g, " ");
            }

            // Update both balances numbers.
            element.querySelector('.memberbanner-item__dreamNum').innerText = parseInt(_dream).toLocaleString("en-US");
            element.querySelector('.memberbanner-item__cashNum').innerText = parseInt(_cash).toLocaleString("en-US");

            //Below code updates the cash and dream miles in Welcome banner component and transfer miles component
            if(document.querySelector('.mile-points.cash-miles') != null){
                document.querySelector('.mile-points.cash-miles').innerText = parseInt(_cash).toLocaleString("en-US");
            }
            if(document.querySelector('.mile-points.dream-miles') != null){
                document.querySelector('.mile-points.dream-miles').innerText = parseInt(_dream).toLocaleString("en-US");
            }

            // Update user tier.
            const tierLevel = getLocale() !== 'en-CA' ? {"B": "Bleu", "G": "Or", "O": "Onyx"} : {"B": "Blue", "G": "Gold", "O": "Onyx"};
            const _tag = element.querySelector('.memberbanner-item__tag');
            const _profileIcon = element.querySelector('.am-icon-tier-oynx');
            _tag.innerText = tierLevel[_mbData['tier']];
            _tag.classList.add(tierLevel[_mbData['tier']].toLowerCase());
            _profileIcon.classList.add(tierLevel[_mbData['tier']].toLowerCase());
        }
    }

    // Get updated Cash/Dream miles and push back to memdeets
    async function getMemberBanner(_myAPI, _header) {
        const _memdeets = getMemdeets();
        try {
            const data = await useFetch(_myAPI, _header);
            _memdeets['cashBalance'] = data['cashBalance'];
            _memdeets['dreamBalance'] = data['dreamBalance'];
            const memdeetsObjStr = JSON.stringify(_memdeets);
            const encodedString = encodeURIComponent(memdeetsObjStr);
            setWebCookie('memdeets', encodedString, 7);
            getAuthSession(data['cashBalance'],data['dreamBalance']);
        } catch (error) {
            console.error('Error:', error);
        }
    }

    function init() {
        let memberProfileEdit = false;
        // Not hide/delete memberProfile in Edit mode
        if(_memberProfile){
            memberProfileEdit = !!_memberProfile.hasAttribute('data-editMode');
        }
        if (_memberProfile && !memberProfileEdit) {
            if (isAuthed()) {
                _memberProfile.classList.remove('d-none');
                _unAuthTopMenu[0].remove();
                _signInBtn.remove();
                _joinNowBtn.remove();
                getMemberBanner(getApiHost()+apiURL.memberBanner, false);
            } else {
                _memberProfile.remove();
                _signOutBtn.remove();
            }
        }
        initExecuted = true;
    }
    document.addEventListener("DOMContentLoaded", function () {
        if(!initExecuted){
            init();
        }
    });

})();
