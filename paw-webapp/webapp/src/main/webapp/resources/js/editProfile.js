function editArtistFormCheck() {
    let form = document.getElementById("artistEditForm");
    let artistName = document.getElementById("artistName");
    let artistSurname = document.getElementById("artistSurname");
    let artistDescription = document.getElementById("artistDescription");
    form.addEventListener('submit', function (e) {

        let wrongArtistName=document.getElementById("wrongArtistName");
        let wrongArtistDescription=document.getElementById("wrongArtistDescription");
        let wrongArtistSurname=document.getElementById("wrongArtistSurname");
        wrongArtistName.style.display='none'
        wrongArtistSurname.style.display='none'
        wrongArtistDescription.style.display='none'

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
        if(artistDescription.value.length<=0||artistDescription.value.length>500 ){
            e.preventDefault();
            wrongArtistDescription.style.display='block'
            snackbarMessage()

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

