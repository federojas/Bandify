function loadApplication() {
    let form= document.getElementById("form");
    let bigMessage=document.getElementById("bigMessage");
    let emptyMessage=document.getElementById("emptyMessage");

    form.addEventListener('submit',function(e) {
        bigMessage.style.display = 'none'
        emptyMessage.style.display = 'none'
        let message=document.getElementById("message").value;
        let validForm = true;
        if (message.length <= 0) {
            validForm = false;
            emptyMessage.style.display = 'block'
        } else if (message.length > 300) {
            validForm = false;
            bigMessage.style.display = 'block'
        }
        if (typeof message != 'string') {
            validForm = false;
        }
        if (!validForm) {
            snackbarMessage()
            e.preventDefault();
        }
    });
}