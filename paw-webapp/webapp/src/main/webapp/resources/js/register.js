function toggleForm() {
    let artistForm = document.getElementById("artist-form");
    let bandForm = document.getElementById("band-form");
    let artistButton = document.getElementById("artist-button");
    let bandButton = document.getElementById("band-button");

    if (artistForm.style.display === "none") {
        artistForm.style.display = "block";
        bandForm.style.display = "none";
        artistButton.style.backgroundColor = "#6c0c84";
        bandButton.style.backgroundColor = "rgba(108, 12, 132, 0.6)";
    } else {
        artistForm.style.display = "none";
        bandForm.style.display = "block";
        artistButton.style.backgroundColor = "rgba(108, 12, 132, 0.6)";
        bandButton.style.backgroundColor = "#6c0c84";
    }

}