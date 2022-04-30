function load(){
    let form=document.getElementById("form");
    let password=document.getElementById("password");
    form.addEventListener('submit',function(e){

        let invalidPassword =document.getElementById("invalidPassword")
            invalidPassword.style.display='none'

        if(password.value.length<8||password.value.length>25 ){
            invalidPassword.style.display='block'
            e.preventDefault();
            snackbarMessage()

        }
        if(!checkPasswordsReset()){
            e.preventDefault();
            snackbarMessage()
        }
    });
}

function checkPasswordsReset() {
    let pass1 = document.getElementById("password").value
    let pass2 = document.getElementById("passwordConfirmation").value
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

// function validateNewPasswordForm() {
//     if(!checkPasswordsReset() ){
//         snackbarMessage();
//     }
//     return checkPasswordsReset();
// }
//
// function snackbarMessage() {
//     let x = document.getElementById("snackbar");
//     x.className = "show";
//     setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
// }