function auditionFormCheck(){
    document.getElementById("emptyTitle").style.display='none'
    document.getElementById("longTitle").style.display='none'
    document.getElementById("emptyDescription").style.display='none'
    document.getElementById("longDescription").style.display='none'

    title=document.getElementById("title").value;
    description=document.getElementById("description").value;
    validForm=true;
    if(title.length<=0){
        document.getElementById("emptyTitle").style.display='block'

        validForm=false;
    }
    if(title.length>25){
        document.getElementById("longTitle").style.display='block'

        validForm=false;
    }
    if(description.length<=0){
        document.getElementById("emptyDescription").style.display='block'
        validForm=false;
    }
    if(description.length>=25){
        document.getElementById("longDescription").style.display='block'
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