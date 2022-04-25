function load(){
    var form=document.getElementById("form");
    email=document.getElementById("email");
    form.addEventListener('submit',function(e){

        email=document.getElementById("email");
        invalidEmail =document.getElementById("invalidEmail")

        invalidEmail.style.display='none'


        var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
        if(!validRegex.test(email.value)){
            e.preventDefault();
            invalidEmail.style.display='block'
        }
    });
}