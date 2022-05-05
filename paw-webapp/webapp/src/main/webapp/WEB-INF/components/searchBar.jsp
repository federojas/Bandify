<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>SearchBar</title>
    <c:import url="../config/materializeHead.jsp"/>
    <c:import url="../config/generalHead.jsp" />
</head>
<body>
<div class="search-general-div">
    <c:url value="/search" var="searchUrl"/>
    <spring:message code="search.placeholder" var="searchPlaceholder"/>
    <div class="searchBarAndOrderBy">
        <form action="${searchUrl}" method="get" class="searchForm">
            <div class="search">
                <input type="text" maxlength="80" size="43" placeholder="${searchPlaceholder}" name="query">
                <button type="submit" aria-hidden="true"></button>
            </div>
        </form>
        <div class="orderBy">
            <select name="order">
                <option value="desc" selected>
                    <spring:message code="filters.order.desc"/>
                </option>
                <option value="asc">
                    <spring:message code="filters.order.asc"/>
                </option>
            </select>
        </div>
    </div>
    <div class="filters">
        <p><spring:message code="filters.title"/></p>
        <div>
            <select  multiple name="locations">
                <option disabled selected><spring:message code="filters.location"/></option>
                <c:forEach var="location" items="${requestScope.locationList}" varStatus="loop">
                    <option value="${location}">
                            ${location}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div>
            <select  multiple name="genres">
                <option disabled selected><spring:message code="filters.genres"/></option>
                <c:forEach var="genre" items="${requestScope.genreList}" varStatus="loop">
                    <option value="${genre}">
                            ${genre}
                    </option>
                </c:forEach>
        </select>
        </div>
        <div>
            <select  multiple name="roles">
                <option disabled selected><spring:message code="filters.roles"/></option>
                <c:forEach var="role" items="${requestScope.roleList}" varStatus="loop">
                    <option value="${role}">
                            ${role}
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>
</div>
</body>
</html>
