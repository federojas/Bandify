function auditionFormCheck(){
    let emptyTitle=document.getElementById("emptyTitle")
    let longTitle=document.getElementById("longTitle")
    let emptyDescription=document.getElementById("emptyDescription")
    let longDescription=document.getElementById("longDescription")
    let auditionForm=document.getElementById("auditionForm")

    auditionForm.addEventListener('submit', function (e) {
        emptyTitle.style.display='none'
        longTitle.style.display='none'
        emptyDescription.style.display='none'
        longDescription.style.display='none'

        let description=document.getElementById("description").value;


        let title=document.getElementById("titleForm").value;

        let validForm=true;
        if(title.length<=0){
            emptyTitle.style.display='block'
            e.preventDefault();

            validForm=false;
        }
        if(title.length>50){
            longTitle.style.display='block'
            e.preventDefault();

            validForm=false;
        }
        if(description.length<=0){
            emptyDescription.style.display='block'
            e.preventDefault();

            validForm=false;
        }
        if(description.length>300){
            longDescription.style.display='block'
            e.preventDefault();

            validForm=false;
        }
        if(typeof title!='string'||typeof description != 'string'){
            e.preventDefault();

            validForm=false;

        }
        if(!validForm){
            e.preventDefault();
            snackbarMessage();
        }

    });

}