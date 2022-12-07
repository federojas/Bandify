function getPaginationURL(newPage) {
    let newUrl = new URL(window.location.href);
    newUrl.searchParams.set('page', newPage);
    location.href = newUrl.toString();
}