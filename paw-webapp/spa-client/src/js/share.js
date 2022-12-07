function share() {
    //Page is not HTTPS, LIKE PAW SERVER!
    if(!navigator.clipboard) {
        let inputDump = document.createElement('input'),
            hrefText = window.location.href;
        document.body.appendChild(inputDump);
        inputDump.value = hrefText;
        inputDump.select();
        document.execCommand('copy');
        document.body.removeChild(inputDump);
        let x = document.getElementById("snackbar-copy");
        x.style.visibility = "visible";
        setTimeout(function(){ x.style.visibility = x.style.visibility.replace("visible", "hidden"); }, 3000);
    } else {
        navigator.clipboard.writeText(window.location.href).then(function() {
            let x = document.getElementById("snackbar-copy");
            x.style.visibility = "visible";
            setTimeout(function(){ x.style.visibility = x.style.visibility.replace("visible", "hidden"); }, 3000);
        }, function(err) {
            alert('Failed to copy');
        });
    }
}