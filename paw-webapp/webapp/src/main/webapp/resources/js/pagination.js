function getPaginationURL(newPage) {
    let nweUrl = new URL(window.location.href);
    nweUrl.searchParams.set('page', newPage);
    location.href = nweUrl.toString();
}