function editArtistFormCheck() {
    let form = document.getElementById("artistEditForm");
    let artistName = document.getElementById("artistName");
    let artistSurname = document.getElementById("artistSurname");
    let artistDescription = document.getElementById("artistDescription");
    form.addEventListener('submit', function (e) {
        if(artistName.value.length<0||artistName.value.length>50 ){
            e.preventDefault();
            // snackbarMessage()
            alert("hola");

        }
        if(artistSurname.value.length<0||artistSurname.value.length>50 ){
            e.preventDefault();
            // snackbarMessage()
            alert("hola");

        }
        if(artistDescription.value.length<0||artistDescription.value.length>500 ){
            e.preventDefault();
            // snackbarMessage()
            alert("hola");

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

