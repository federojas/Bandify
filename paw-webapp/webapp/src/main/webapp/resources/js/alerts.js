function snackbarMessage() {
    let x = document.getElementById("snackbar");
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
function confirmationModalLoad(){
    // Get the modal
    let confirmationModal = document.getElementById('confirmationModal');
    //TODO: SACAR ESTA WEA DESPUES DE TESTEAR
    openConfirmation()

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target === confirmationModal) {
            confirmationModal.style.display = "none";
        }
    }
}
function closeConfirmationModal(){
        confirmationModal.style.display = "none";
}
function openConfirmation(){
    confirmationModal.style.display = "block";

}