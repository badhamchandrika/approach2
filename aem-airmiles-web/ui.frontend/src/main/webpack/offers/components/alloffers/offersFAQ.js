
(() => {
    function removeActiveFAQ(){
        const _allFAQ = document.querySelectorAll('.faq-box');
        for(const _faq of _allFAQ){
            _faq.classList.remove('active');
        }
    }
    document.addEventListener("DOMContentLoaded", () => {
        const faqMain = document.querySelector('section.faq');
        if(faqMain) {
            const articles = faqMain.querySelectorAll('article');
            const isMultiple = faqMain.hasAttribute("data-is-multiple");
            for(const article of articles){
                const faqClickBar = article.querySelector('.faq-box__question')
                faqClickBar.addEventListener('click',()=> {
                    if(isMultiple){
                        article.classList.toggle("active");
                    }else{
                        removeActiveFAQ();
                        article.classList.add("active");
                    }
                });
            }
        }else{
            console.log("NO CLO FAQ LIST FOUND");
        }
    });
})();
