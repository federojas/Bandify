function snackbarMessage() {
    var x = document.getElementById("snackbar");
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
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