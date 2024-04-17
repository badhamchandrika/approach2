$(document).ready(function () {
	let clickHandler = ("ontouchstart" in window ? "touchend" : "click");
	
	let faqs = $("[id^=faqs]");

    let faqsdiv = faqs.parent();

    let separatoraifaqs = faqsdiv.prev(".separatorai");

    let faqslink = $("#faq-section-portal");

    if(faqslink && separatoraifaqs){
		faqslink.on(clickHandler,function(){            
			gosection(separatoraifaqs);
    	});
    }    
});

function gosection(el){
	$('html, body').animate({
    	scrollTop: el.offset().top
    }, 1);

	let head = $("head");
    let currurl = DOMPurify.sanitize(window.location.href);
    let splitarr = currurl.split("/");
    let path = splitarr.pop();
    let newurl;
    let lang = $("html").attr("lang");
    
    $("meta[name='description']").attr("content","Check AIR MILES® FAQs and find answers to common questions about our loyalty and analytics solutions.");      
    $("title").html("Frequently Asked Questions | AIR MILES®");

    if(path.includes(`${lang}.html`))
    {
        newurl = currurl.replace(path,`${lang}/faqs`);
    }else{
		newurl = currurl.replace(path,"faqs");
	}

	newurl += ".html";

    let defaulthreflang = newurl.replace(`/${lang}/`,"/");

	let hrefLangs = $.makeArray($("link[rel='alternate']").map(
        function(){
            let hrefValue = escapeHtml($(this).attr("hreflang"));
            if(hrefValue !== "x-default"){
                return hrefValue;
            }
        }
    ));

	let linksAlt = $("link[rel='alternate']");
    linksAlt.remove();

    let linkCanon = $("link[rel='canonical']");
	linkCanon.remove();

	head.append(`<link rel="canonical" href="${escapeHtml(newurl)}"><link rel="alternate" hreflang="x-default" href=${escapeHtml(defaulthreflang)}/>`);

	$.each( hrefLangs, function( index, value ) {
        let hrefURL = escapeHtml(newurl.replace(`/${lang}/`,`/${value}/`))
        head.append(`<link rel="alternate" hreflang=${value} href=${newurl}/>`);      
    });

    window.history.pushState("", "FAQs", newurl);    

    
}

function escapeHtml(unsafe) {
    return unsafe.replace(/[&<"']/g, function (m) {
        switch (m) {
            case '&':
                return '&amp;';
            case '<':
                return '&lt;';
            case '"':
                return '&quot;';
            case "'":
                return '&#039;';
            default:
                return m;
        }
    });
}