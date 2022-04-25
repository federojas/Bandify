function toggleForm(isBand) {
    let artistForm = document.getElementById("artist-form");
    let bandForm = document.getElementById("band-form");
    let artistButton = document.getElementById("artist-button");
    let bandButton = document.getElementById("band-button");

    if (isBand) {
        artistForm.style.display = "none";
        bandForm.style.display = "block";
        artistButton.style.backgroundColor = "rgba(108, 12, 132, 0.6)";
        bandButton.style.backgroundColor = "#6c0c84";
    } else {
        artistForm.style.display = "block";
        bandForm.style.display = "none";
        artistButton.style.backgroundColor = "#6c0c84";
        bandButton.style.backgroundColor = "rgba(108, 12, 132, 0.6)";
    }

}

function checkPasswordBand() {
    let pass1 = document.getElementById("password_band").value
    let pass2 = document.getElementById("confirm_password_band").value
    if (pass1 === pass2 && pass1.length > 0) {
        document.getElementById("match_message").style.display = "block";
        document.getElementById("nonmatch_message").style.display = "none";
        return true;
    } else {
        document.getElementById("match_message").style.display = "none";
        document.getElementById("nonmatch_message").style.display = "block";
        return false;
    }
}

function checkPasswordArtist() {
    let pass1 = document.getElementById("password_artist").value
    let pass2 = document.getElementById("confirm_password_artist").value
    if (pass1 === pass2 && pass1.length > 0) {
        document.getElementById("match_message_artist").style.display = "block";
        document.getElementById("nonmatch_message_artist").style.display = "none";
        return true;
    } else {
        document.getElementById("match_message_artist").style.display = "none";
        document.getElementById("nonmatch_message_artist").style.display = "block";
        return false;
    }
}

function registerbandCheck(){
    password=document.getElementById("password_band").value;
    email=document.getElementById("bandEmaill").value;
    bandName=document.getElementById("bandName").value;

    emptypass= document.getElementById("emptyPass")
    wrongMail= document.getElementById("wrongMail")
    wrongName= document.getElementById("wrongName")


    wrongMail.style.display='none'
    emptypass.style.display='none'
    wrongName.style.display='none'

    var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;



    validForm=true;

    if(!validRegex.test(email)){
        validForm=false;

        wrongMail.style.display='block'

    }

    if(email.length<=0 || email.length >300){
        wrongMail.style.display='block'
        validForm=false;
    }
    if(password.length<8 || password.length>25){
        emptypass.style.display='block'
        validForm=false;

    }else{
        if(!checkPasswordBand()){
            validForm=false;
        }

    }
    if(bandName<=0||bandName>50){
        wrongName.style.display='block'
        validForm=false;

    }
    if(typeof password!='string'||typeof email != 'string'||typeof name != 'string'){

        validForm=false;

    }
    if(!validForm){
       snackbarMessage()
    }

    return validForm;
}

function registerArtistCheck(){
    password=document.getElementById("password_artist").value;
    email=document.getElementById("artistEmail").value;
    name=document.getElementById("artistName").value;
    surname=document.getElementById("artistSurname").value;

    validForm=true;

    wrongArtistMail=document.getElementById("wrongArtistMail")
    wrongArtistPass=document.getElementById("wrongArtistPass")
    wrongArtistName=document.getElementById("wrongArtistName")
    wrongArtistSurname=document.getElementById("wrongArtistSurname")

    wrongArtistMail.style.display='none'
    wrongArtistPass.style.display='none'
    wrongArtistName.style.display='none'
    wrongArtistSurname.style.display='none'

    var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    if(!validRegex.test(email)){
        validForm=false;

        wrongArtistMail.style.display='block'

    }
    if(email.length<=0|| email.length >300){
        validForm=false;
        wrongArtistMail.style.display='block'
    }
    if(password.length<8 || password.length>25){
        wrongArtistPass.style.display='block'
        validForm=false;

    }else{
        if(!checkPasswordArtist()){
            validForm=false;
        }

    }
    if(name<=0||name>50){
        wrongArtistName.style.display='block'
        validForm=false;

    }
    if(surname<=0||surname>50){
        wrongArtistSurname.style.display='block'
        validForm=false;

    }
    if(typeof password!='string'||typeof email != 'string'||typeof name != 'string'||typeof surname != 'string'){
        validForm=false;
    }
    if(!validForm){
        snackbarMessage()
    }
    return validForm;
}


function snackbarMessage() {
    var x = document.getElementById("snackbar");
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}