function load(){
    let form = document.getElementById("form");
    let email = document.getElementById("email");
    form.addEventListener('submit',function(e){

        let invalidEmail = document.getElementById("invalidEmail")

        invalidEmail.style.display='none'

        let validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
        if(!validRegex.test(email.value)){
            e.preventDefault();
            invalidEmail.style.display='block'
        }
    });
}

