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