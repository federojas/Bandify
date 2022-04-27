function load(){
    var form=document.getElementById("form");
    password=document.getElementById("password");
    form.addEventListener('submit',function(e){

        invalidPassword =document.getElementById("invalidPassword")

        if(password.value.length<8||password.value.length>25){
            e.preventDefault();
            invalidPassword.style.display='block'
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