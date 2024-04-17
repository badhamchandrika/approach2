$(document).ready(function () {

	let clickHandler = ("ontouchstart" in window ? "touchend" : "click");

	let formajax = $("[id^=email-form-registration]");

	let buttonmail = formajax.find("#mail-registration-button");

    buttonmail.on(clickHandler,function(){
		cleanErrorMessages(formajax);

		let errors = validFields(formajax);

	    if(errors.length != 0 || buttonmail.hasClass("disabled-ai-button")){
	      return false;
	    }

		buttonmail.addClass("disabled-ai-button", true);

	    $.post(formajax.attr("action"), formajax.serialize(),
	        function() {
	           messageResult("success", formajax);
	        },
	        formajax.attr("enctype"))
	        .fail(function () {
	            messageResult("error", formajax);
	        })
	        .always(function(){
				buttonmail.prop("disabled", false);
	        })
	        
        return true;
    });

    
});

function messageResult(type, formajax, email){
    let container = formajax.parent(".servletcall").parent(".cmp-container");
    let emailField = formajax.find("#email-registration-field");
    let emailVal = emailField.val();

    if(type == "success"){
		container.html(`<div class='mail-registration-resultmessage'><img src='/content/dam/airmiles-ai/success-mail-icon.png'><p class='error_title'>Thank you for trusting us!</p><p>Your data was sent correctly. In 72 working hours we will contact you at ${emailVal}</p></div>`);
    }
    if(type == "error"){
		container.html("<div class='mail-registration-resultmessage'><img src='/content/dam/airmiles-ai/error-mail-icon.svg'><p class='error_title'>Something went wrong and we haven't received the information.</p><div class='body_message'><p>Please <a href='javascript:window.location.reload(true)'>try again. </a></p><p> If the problem persists, check again in a few minutes.</p></div></div>");
    }	    
}

function cleanErrorMessages(formajax){
	$(".emailform-registration-error").remove();
	formajax.find("#name-registration-field").removeClass("red");
    formajax.find("#email-registration-field").removeClass("red");
}

function validFields(formajax){
    let errors = [];
    
    let nameField = formajax.find("#name-registration-field");
    let nameVal = nameField.val();
    let nameDivParent = nameField.parent("div .cmp-form-text");
    let nameErrorMsg = nameDivParent.attr("data-cmp-required-message");
    let emailField = formajax.find("#email-registration-field");
    let emailVal = emailField.val();
    
    let emailDivParent = emailField.parent("div .cmp-form-text");
    let emailErrorReqMsg = emailDivParent.attr("data-cmp-required-message");
    let emailErrorValidMsg = emailDivParent.attr("data-cmp-constraint-message");        
    
    if(nameVal == ''){
        errors.push(nameErrorMsg);
        nameField.attr("aria-invalid", "true");
        nameField.attr("aria-describedby", "name-registration-error");
        nameField.addClass("red");
        nameDivParent.append("<div class='emailform-registration-error'><strong id='name-registration-error'>"+nameErrorMsg+"</strong></div>");
    }
    
    if(emailVal == ''){
        errors.push(emailErrorReqMsg);
        emailField.attr("aria-invalid", "true");
        emailField.addClass("red");
        emailField.attr("aria-describedby", "email-registration-error");
        emailDivParent.append("<div class='emailform-registration-error'><strong id='email-registration-error'>"+emailErrorReqMsg+"</strong></div>");
    }else{
        if (!IsEmail(emailVal)) {
            errors.push(emailErrorValidMsg);
            emailField.addClass("red");
            emailField.attr("aria-invalid", "true");
            emailField.attr("aria-describedby", "email-registration-error");
            emailDivParent.append("<div class='emailform-registration-error'><strong id='email-registration-error'>"+emailErrorValidMsg+"</strong></div>");
        }
    }

    return errors;
}

function IsEmail(email) {
    let regex =
        /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return !(!regex.test(email));
}