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
<%--<spring:message code="search.alt.filter" var="filter"/>--%>
<%--<img onclick="openNav()" class="filter-button" src="<c:url value="/resources/icons/filter.svg" />" alt="${filter}">--%>
<%--<div id="mySidenav" class="sidenav">--%>
<%--    <b><h1><spring:message code="sideNav.filter"/></h1></b>--%>
<%--    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>--%>
<%--    <select  id="selectLocation" multiple name="location" id="">--%>
<%--        <option disabled selected><spring:message code="sideNav.location"/> </option>--%>
<%--        <c:forEach--%>
<%--                var="location"--%>
<%--                items="${locationList}"--%>
<%--                varStatus="loop"--%>
<%--        >--%>
<%--            <option value="${location.name}">--%>
<%--                ${location.name}--%>
<%--            </option>--%>
<%--        </c:forEach>--%>
<%--    </select>--%>
<%--    <select id="selectGenres" multiple>--%>
<%--        <option disabled selected><spring:message code="sideNav.genres"/> </option>--%>

<%--        <c:forEach var="genre" items="${genreList}" varStatus="loop">--%>
<%--            <option value="${genre.name}"> ${genre.name} </option>--%>
<%--        </c:forEach>--%>
<%--    </select>--%>
<%--    <select id="selectRoles" multiple>--%>
<%--        <option disabled selected><spring:message code="sideNav.roles"/> </option>--%>

<%--        <c:forEach var="role" items="${roleList}" varStatus="loop">--%>
<%--            <option value="${role.name}"> ${role.name} </option>--%>
<%--        </c:forEach>--%>
<%--    </select>--%>
<%--    <div class="filter-button-1">--%>
<%--        <button  class=" postCard-button-0 hover:bg-sky-700">Filtrar</button>--%>
<%--    </div>--%>
<%--</div>--%>
</body>
</html>
