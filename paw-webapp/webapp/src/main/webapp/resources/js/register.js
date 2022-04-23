function toggleForm() {
    let artistForm = document.getElementById("artist-form");
    let bandForm = document.getElementById("band-form");
    let artistButton = document.getElementById("artist-button");
    let bandButton = document.getElementById("band-button");

    if (artistForm.style.display === "none") {
        artistForm.style.display = "block";
        bandForm.style.display = "none";
        artistButton.style.backgroundColor = "#6c0c84";
        bandButton.style.backgroundColor = "rgba(108, 12, 132, 0.6)";
    } else {
        artistForm.style.display = "none";
        bandForm.style.display = "block";
        artistButton.style.backgroundColor = "rgba(108, 12, 132, 0.6)";
        bandButton.style.backgroundColor = "#6c0c84";
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

    if(email.length<=0 || email.length >250){
        wrongMail.style.display='block'
        validForm=false;
    }
    if(password.length<8 || password.length>50){
        emptypass.style.display='block'
        validForm=false;

    }else{
        if(!checkPasswordBand()){
            validForm=false;
        }

    }
    if(password.length>50){
        longPass.style.display='block'
        validForm=false;

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
function checkPasswordBand() {
    if (document.getElementById("password_band").value ===
        document.getElementById("confirm_password_band").value) {
        document.getElementById("match_message").style.display = "block";
        document.getElementById("nonmatch_message").style.display = "none";
        return true;
    } else {
        document.getElementById("match_message").style.display = "none";
        document.getElementById("nonmatch_message").style.display = "block";
        return false;
    }
}
function snackbarMessage() {
    var x = document.getElementById("snackbar");
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}