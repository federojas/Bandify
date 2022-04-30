function load() {
    let sendButton = document.getElementById("resendButton");
    sendButton.disabled = true;
    setTimeout(function() {sendButton.disabled = false;}, 3000);
}