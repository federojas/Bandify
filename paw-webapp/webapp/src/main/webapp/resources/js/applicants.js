const queryString = window.location.search;
const parameters = new URLSearchParams(queryString);
let state = parameters.get('state');

$(document).ready(function () {

    $(".user-data-tabs").each(function () {
        if (state === "REJECTED") {
            $(this).find("#rejected").addClass("selected-tab");
        } else if (state === "ACCEPTED") {
            $(this).find("#accepted").addClass("selected-tab");
        } else if (state === "PENDING") {
            $(this).find("#pending").addClass("selected-tab");
        } else {
            $(this).find("#pending").addClass("selected-tab");
        }
    })
});