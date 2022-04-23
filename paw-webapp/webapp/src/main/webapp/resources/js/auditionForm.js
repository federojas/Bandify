function auditionFormCheck(){
    emptyTitle=document.getElementById("emptyTitle")
    longTitle=document.getElementById("longTitle")
    emptyDescription=document.getElementById("emptyDescription")
    longDescription=document.getElementById("longDescription")

    emptyTitle.style.display='none'
    longTitle.style.display='none'
    emptyDescription.style.display='none'
    longDescription.style.display='none'


    title=document.getElementById("title").value;
    description=document.getElementById("description").value;
    validForm=true;
    if(title.length<=0){
        emptyTitle.style.display='block'

        validForm=false;
    }
    if(title.length>25){
        longTitle.style.display='block'

        validForm=false;
    }
    if(description.length<=0){
        emptyDescription.style.display='block'
        validForm=false;
    }
    if(description.length>=25){
        longDescription.style.display='block'
        validForm=false;
    }
    if(typeof title!='string'||typeof description != 'string'){
        validForm=false;

    }
    if(!validForm){
        snackbarMessage()
    }
    return validForm;
}