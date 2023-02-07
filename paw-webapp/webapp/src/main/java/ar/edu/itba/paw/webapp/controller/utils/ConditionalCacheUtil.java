package ar.edu.itba.paw.webapp.controller.utils;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

public class ConditionalCacheUtil {

    private ConditionalCacheUtil() {

    }

    public static final int MAX_AGE = 604800; // 1 week

    public static Response.ResponseBuilder getConditionalCache(Request request, EntityTag eTag) {
        Response.ResponseBuilder response = request.evaluatePreconditions(eTag);
        if (response != null) {
            final CacheControl cacheControl = new CacheControl();
            cacheControl.setNoCache(true);
        }
        return response;
    }

    public static void setUnconditionalCache(Response.ResponseBuilder responseBuilder) {
        final CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(MAX_AGE);
        responseBuilder.cacheControl(cacheControl);
    }
}
