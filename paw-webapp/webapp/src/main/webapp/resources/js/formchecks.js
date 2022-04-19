function applicationCheck(){
    message=document.getElementById("message").value;;
    validForm=true;
    sendForm=true;
    if(message.length<=0||message.length>300){
        valid=false;
    }
    if(typeof message != 'string'){
        valid=false;
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
    validForm=true;
    sendForm=true;
    if(title.length<=0 || title.length>50 || description.length<=0 || description.length>300){
        valid=false;
    }
    if(typeof title!='string'||typeof description != 'string'){
        valid=false;
        sendForm=false;
    }
    if(!valid){
        snackbarMessage()
    }
    return sendForm;
}
function loginFormCheck(){
    password=document.getElementById("password").value;
    email=document.getElementById("email").value;
    validForm=true;
    sendForm=true;
    var x = document.getElementById("wrongEmail");
    var y = document.getElementById("wrongPassword");
    if(password.length<=0 || password.length>50 || email.length<=0||email.length>250){
        valid=false;
    }
    if(typeof password!='string'||typeof email != 'string'){
        valid=false;
        sendForm=false;
        y.className = "show";
        x.className = "show";
    }
    if(!valid){
        snackbarMessage()
    }
    return sendForm;
}

function registerArtistCheck(){
    password=document.getElementById("password").value;
    email=document.getElementById("email").value;
    name=document.getElementById("name").value;
    surname=document.getElementById("surname").value;

    validForm=true;
    sendForm=true;
    if(password.length<=0 || password.length>50 || email.length<=0||email.length>250||name.length<=0||name.length>50||surname.length<=0||surname.length>50){
        valid=false;
    }
    if(typeof password!='string'||typeof email != 'string'||typeof name != 'string'||typeof surname != 'string'){
        valid=false;
        sendForm=false;
    }
    if(!valid){
        snackbarMessage()
    }
    return sendForm;
}

function registerbandCheck(){
    password=document.getElementById("password").value;
    email=document.getElementById("email").value;
    name=document.getElementById("name").value;
    validForm=true;
    sendForm=true;
    if(password.length<=0 || password.length>50 || email.length<=0||email.length>250||name.length<=0||name.length>50){
        valid=false;
    }
    if(typeof password!='string'||typeof email != 'string'||typeof name != 'string'){
        valid=false;
        sendForm=false;
    }
    if(!valid){
        // customAlert.alert('This is a custom alert without heading.');
        snackbarMessage()
    }
    return sendForm;
}
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
function CustomAlert(){
    this.alert = function(message,title){
        document.body.innerHTML = document.body.innerHTML + '<div id="dialogoverlay"></div><div id="dialogbox" class="slit-in-vertical"><div><div id="dialogboxhead"></div><div id="dialogboxbody"></div><div id="dialogboxfoot"></div></div></div>';

        let dialogoverlay = document.getElementById('dialogoverlay');
        let dialogbox = document.getElementById('dialogbox');

        let winH = window.innerHeight;
        dialogoverlay.style.height = winH+"px";

        dialogbox.style.top = "100px";

        dialogoverlay.style.display = "block";
        dialogbox.style.display = "block";

        document.getElementById('dialogboxhead').style.display = 'block';

        if(typeof title === 'undefined') {
            document.getElementById('dialogboxhead').style.display = 'none';
        } else {
            document.getElementById('dialogboxhead').innerHTML = '<i class="fa fa-exclamation-circle" aria-hidden="true"></i> '+ title;
        }
        document.getElementById('dialogboxbody').innerHTML = message;
        document.getElementById('dialogboxfoot').innerHTML = '<button class="pure-material-button-contained active" onclick="customAlert.ok()">OK</button>';
    }

    this.ok = function(){
        document.getElementById('dialogbox').style.display = "none";
        document.getElementById('dialogoverlay').style.display = "none";
    }
    return;
}

let customAlert = new CustomAlert();