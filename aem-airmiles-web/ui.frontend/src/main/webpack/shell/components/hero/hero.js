(() => {
 document.addEventListener("DOMContentLoaded", function () {

    function pushErrorToAdobeLayer(id,message){
        const errorShownExists = window.adobeDataLayer.some(item => item.event === "error_shown");
        if (!errorShownExists) {
            window.adobeDataLayer.push ({
                event: 'error_shown',
                web: {
                    error: {
                        type: 'form field level',
                        id: id,
                        message: message,
                        form: 'shell-contest'
                    }
                }
            });
        }
    }   
    const submitButton = document.getElementById('submitSignup');
    if (submitButton) {
        submitButton.addEventListener('click', function (e) {
            const checkBox = document.getElementById('agreeTerms');
            if (!checkBox.checked) {
                setTimeout(()=>{
                    pushErrorToAdobeLayer('agree-to-official-rules','you must agree to our official rules to continue');
                }, 1000);
            }
        })
    }

    if (window.location.href.indexOf('shell-contest-participant-data') > -1) {
        function catchSubmitEvent (){
                const email = document.getElementById('email').value;
                const phone = document.getElementById('phone').value;
                if (phone === "" || email === "" || !(/^\S+@\S+\.\S+$/.test(email))) {
                    // Form fields validation
                    const formError = document.querySelector('.error'); 
                    if (formError) {
                        setTimeout(()=>{
                            pushErrorToAdobeLayer('information-incomplete','we re sorry some of your information appears to be incomplete please complete the required fields and click on enter now to complete your submission');
                        }, 1000);
                        
                        return;
                    } else {
                        return; // Return without further processing if there's an error message already present
                    }
                }
        }
        const enterButton = document.getElementById('submitContest');
        enterButton.addEventListener('click', catchSubmitEvent);
    } 
})
})()
