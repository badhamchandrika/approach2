$(document).ready(function () {
    let contactlinks = $("a[href*='airmilesaimailregistrationform']");
    let amaplink = $("a[href='https://amap.airmiles.ai/login.html']");

    amaplink.attr("href","https://amap.airmiles.ai/login");

	contactlinks.removeAttr("href");

    contactlinks.attr("onclick","formportal();");
});

function formportal(el){
    let emailForm = $("#email-container-registration");
	$('html, body').animate({
        scrollTop: emailForm.offset().top
    }, 1);
}