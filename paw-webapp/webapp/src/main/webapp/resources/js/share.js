function share() {
    navigator.clipboard.writeText(window.location.href);
    let x = document.getElementById("snackbar-copy");
    x.style.visibility = "visible";
    setTimeout(function(){ x.style.visibility = x.style.visibility.replace("visible", "hidden"); }, 3000);
}