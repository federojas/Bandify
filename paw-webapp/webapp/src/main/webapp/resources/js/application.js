// function applicationCheck(){
//
//     let message=document.getElementById("message").value;;
//
//     let bigMessage=document.getElementById("bigMessage")
//     let emptyMessage=document.getElementById("emptyMessage")
//
//     bigMessage.style.display='none'
//     emptyMessage.style.display='none'
//
//     validForm=true;
//     if(message.length<=0){
//         validForm=false;
//         emptyMessage.style.display='block'
//
//     }
//     if(message.length>300){
//         validForm=false;
//         bigMessage.style.display='block'
//     }
//     if(typeof message != 'string'){
//         validForm=false;
//     }
//     if(!validForm){
//         snackbarMessage()
//     }
//     return validForm;
// }
function loadApplication(){
    let form= document.getElementById("form");
    let message=document.getElementById("message").value;
    let bigMessage=document.getElementById("bigMessage");
    let emptyMessage=document.getElementById("emptyMessage")

    form.addEventListener('submit',function(e) {
        bigMessage.style.display = 'none'
        emptyMessage.style.display = 'none'
        let validForm = true;
        if (message.length <= 0) {
            validForm = false;
            emptyMessage.style.display = 'block'

        }
        if (message.length > 300) {
            validForm = false;
            bigMessage.style.display = 'block'
        }
        if (typeof message != 'string') {
            validForm = false;
        }
        if (!validForm) {
            snackbarMessage()
            e.preventDefault();

        }
    });
}