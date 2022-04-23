function applicationCheck(){
    message=document.getElementById("message").value;;

    bigMessage=document.getElementById("bigMessage")
    emptyMessage=document.getElementById("emptyMessage")

    bigMessage.style.display='none'
    emptyMessage.style.display='none'

    validForm=true;
    if(message.length<=0){
        validForm=false;
        emptyMessage.style.display='block'

    }
    if(message.length>300){
        validForm=false;
        bigMessage.style.display='block'
    }
    if(typeof message != 'string'){
        validForm=false;

    }
    if(!validForm){
        snackbarMessage()
    }
    return validForm;
}