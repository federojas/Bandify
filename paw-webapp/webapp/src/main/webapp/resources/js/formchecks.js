function applicationCheck(){
    name=document.getElementById("name").value;
    email=document.getElementById("email").value;
    message=document.getElementById("message").value;;
    validForm=true;
    sendForm=true;
    if(name.length<=0 || name.length>50 || email.length<=0 || email.length>250||message.length<=0||message.length>250){
        valid=false;
    }
    if(typeof email!='string'||typeof message != 'string'||typeof name != 'string'){
        sendForm=false;
    }
    if(typeof message != 'string'){
        valid=false;
    }
    if(!valid){
        snackbarMessage()
    }
    return sendForm;
}
function snackbarMessage() {
    var x = document.getElementById("snackbar");
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}