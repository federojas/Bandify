function snackbarMessage() {
    let x = document.getElementById("snackbar");
    x.className = "show";
    setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
function confirmationModalLoad(){
    // Get the modal
    let confirmationModal = document.getElementById('confirmationModal');

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target === confirmationModal) {
            confirmationModal.style.display = "none";
        }
    }
}
function closeConfirmationModal(){
    let confirmationModal = document.getElementById('confirmationModal');
    confirmationModal.style.display = "none";
}
function openConfirmation(){
    let confirmationModal = document.getElementById('confirmationModal');
    confirmationModal.style.display = "block";
}