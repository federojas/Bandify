package ar.edu.itba.paw.webapp.controller.utils;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class PaginationLinkBuilder {

    private PaginationLinkBuilder() {

    }

    public static void getResponsePaginationLinks(Response.ResponseBuilder response, UriInfo uriInfo, int currentPage, int lastPage) {
        if(currentPage != 1)
            response.link(uriInfo.getRequestUriBuilder().queryParam("page", currentPage - 1).build(), "prev");
        if(currentPage != lastPage)
            response.link(uriInfo.getRequestUriBuilder().queryParam("page", currentPage + 1).build(), "next");
        response.link(uriInfo.getRequestUriBuilder().queryParam("page", 1).build(), "first");
        response.link(uriInfo.getRequestUriBuilder().queryParam("page", lastPage).build(), "last");
    }

}
