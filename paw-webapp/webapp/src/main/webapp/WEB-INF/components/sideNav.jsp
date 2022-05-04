<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="sideNav.title"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/sideNav.css" />"/>

</head>
<body>
<spring:message code="search.alt.filter" var="filter"/>
    <img onclick="openNav()" class="filter-button" src="<c:url value="/resources/icons/filter.svg" />" alt="${filter}">
    <div id="mySidenav" class="sidenav">
        <h1><b><spring:message code="sideNav.filter"/></b></h1>
        <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
        <c:url value="/search" var="searchUrl"/>
        <%--@elvariable id="filterForm" type="ar.edu.itba.paw.webapp.form.FilterForm"--%>
        <form
                method="get"
                action="${searchUrl}"
                class="box"
        >
            <div>
                <hr/>
                <p><spring:message code="sideNav.genres"/></p>
                <c:forEach var="genre" items="${genreList}" varStatus="loop">
                    <input type="checkbox" name="genre" id="${genre}" value="${genre}">
                    <label  class="sidenav-input-label" for="${genre}">${genre}</label>
                </c:forEach>
            </div>

            <div>
                <hr/>
                <p><spring:message code="sideNav.roles"/></p>
                <c:forEach var="role" items="${roleList}" varStatus="loop">
                    <input type="checkbox" name="role" id="${role}" value="${role}">
                    <label  class="sidenav-input-label" for="${role}">${role}</label>
                </c:forEach>
            </div>

            <div class="filter-button-1">
                <button  class=" postCard-button-0 hover:bg-sky-700"
                         type="submit"
                >
                    Filtrar
                </button>
            </div>
        </form>
    <hr>
    <div class="filter-button-1">
        <button  class=" postCard-button-0">Filtrar</button>
    </div>
</body>
</html>
