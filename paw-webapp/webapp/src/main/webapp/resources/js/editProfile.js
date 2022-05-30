function editArtistFormCheck() {
    let form = document.getElementById("artistEditForm");
    let artistName = document.getElementById("artistName");
    let artistSurname = document.getElementById("artistSurname");
    let artistDescription = document.getElementById("artistDescription");
    form.addEventListener('submit', function (e) {


        hideErrorMessages(true);

        if(artistName.value.length<=0||artistName.value.length>50 ){
            e.preventDefault();
            wrongArtistName.style.display='block'
            snackbarMessage()

        }
        if(artistSurname.value.length<=0||artistSurname.value.length>50 ){
            e.preventDefault();
            wrongArtistSurname.style.display='block'
            snackbarMessage()

        }
        if(artistDescription.value.length>500 ){
            e.preventDefault();
            wrongArtistDescription.style.display='block'
            snackbarMessage()

        }
    });
    toggleSocialMedia();

}
function hideErrorMessages(isArtist){
    let wrongArtistName=document.getElementById("wrongArtistName");
    let wrongArtistDescription=document.getElementById("wrongArtistDescription");
    if(isArtist){
        let wrongArtistSurname=document.getElementById("wrongArtistSurname");
        wrongArtistSurname.style.display='none';
    }
    wrongArtistName.style.display='none';
    wrongArtistDescription.style.display='none';
}
function editBandFormCheck() {
    let form = document.getElementById("bandEditForm");
    let bandName = document.getElementById("bandName");
    let bandDescription = document.getElementById("bandDescription");
    form.addEventListener('submit', function (e) {
        hideErrorMessages(false);
        if(bandName.value.length<=0||bandName.value.length>50 ){
            e.preventDefault();
            wrongArtistName.style.display='block'
            snackbarMessage()

        }
        if(bandDescription.value.length>500 ){
            e.preventDefault();
            wrongArtistDescription.style.display='block'
            snackbarMessage()

        }
    });
    toggleSocialMedia();
}
function toggleSocialMedia(){
    socialMediacontainer=document.getElementById("socialMediaContainer");
    socialMediaContainerOpener=document.getElementById("socialMediaContainerOpener");
    arrowIcon=document.getElementById("arrowIcon");
    toggleSocialMediaContainer.addEventListener("click",function (e){
        if(socialMediacontainer.style.display==='block'){
            socialMediacontainer.style.display='none';
            document.querySelector("#arrowIcon").style.transform = "rotate(0deg)";


        }else {
            socialMediacontainer.style.display='block'
            document.querySelector("#arrowIcon").style.transform = "rotate(180deg)";

        }
    });
}
function previewImage() {
        let output = document.getElementById('imagePreview');
        output.src = URL.createObjectURL(event.target.files[0]);
        output.onload = function() {
            URL.revokeObjectURL(output.src)
        }
}



