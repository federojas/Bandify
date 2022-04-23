function applicationCheck(){
    message=document.getElementById("message").value;;
    document.getElementById("bigMessage").style.display='none'
    document.getElementById("emptyMessage").style.display='none'
    validForm=true;
    if(message.length<=0){
        validForm=false;
        document.getElementById("emptyMessage").style.display='block'

    }
    if(message.length>300){
        validForm=false;
        document.getElementById("bigMessage").style.display='block'
    }
    if(typeof message != 'string'){
        validForm=false;

    }
    if(!validForm){
        snackbarMessage()
    }
    return validForm;
}