(()=>{
    const sherpaElement = document.getElementById('sherpa')
    if(!sherpaElement) return

    const elementConfig = {
        language: sherpaElement.dataset.lang
    }

    $sherpa.V2.createElement('trip', elementConfig).mount('#sherpa')
})()

