
function checkAlert() {
    const alertHeader = document.querySelector(".alertheader");
    if(!alertHeader){return;}
    const alertContainer = alertHeader.querySelector(".alertheader-container");
    if(!alertContainer){return;}

    if(window.scrollY > alertContainer.offsetHeight){
        alertContainer.classList.add('alertheader-hide');
    }else{
        alertContainer.classList.remove('alertheader-hide');
    }
}
document.onscroll = () => {checkAlert();};
