function applicationCheck(){
    name=document.getElementById("name").value;
    email=document.getElementById("email").value;
    message=document.getElementById("message").value;;
    validForm=true;
    sendForm=true;
    if(name.length<=0 || name.length>50 || email.length<=0 || email.length>250||message.length<=0||message.length>300){
        valid=false;
    }
    if(typeof email!='string'||typeof message != 'string'||typeof name != 'string'){
        sendForm=false;
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
function auditionFormCheck(){
    title=document.getElementById("title").value;
    description=document.getElementById("description").value;
    email=document.getElementById("email").value;
    validForm=true;
    sendForm=true;
    if(title.length<=0 || title.length>50 || description.length<=0 || description.length>300||email.length<=0||email.length>250){
        valid=false;
    }
    if(typeof title!='string'||typeof description != 'string'||typeof email != 'string'){
        sendForm=false;
    }
    if(!valid){
        snackbarMessage()
    }
    return sendForm;
}