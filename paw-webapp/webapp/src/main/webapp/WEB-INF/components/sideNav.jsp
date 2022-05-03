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
    <b><h1><spring:message code="sideNav.filter"/></h1></b>
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
    <hr>
        <p><spring:message code="sideNav.location"/> </p>
        <c:forEach
                var="location"
                items="${locationList}"
                varStatus="loop"
        >
            <input type="checkbox" id="${location.id}">
            <label class="sidenav-input-label" for="${location.id}">${location.name}</label>

            </input>
        </c:forEach>
    <hr>
        <p><spring:message code="sideNav.genres"/> </p>
        <c:forEach var="genre" items="${genreList}" varStatus="loop">
            <input type="checkbox" id="${genre.id}">
            <label  class="sidenav-input-label" for="${genre.id}">${genre.name}</label>

        </c:forEach>
    <hr>
    <p><spring:message code="sideNav.roles"/></p>

        <c:forEach var="role" items="${roleList}" varStatus="loop">
            <input type="checkbox" id="${role.id}">
            <label  class="sidenav-input-label" for="${role.id}">${role.name}</label>
            <br>
        </c:forEach>
    <hr>
    <div class="filter-button-1">
        <button  class=" postCard-button-0 hover:bg-sky-700">Filtrar</button>
    </div>
</div>
</body>
</html>
