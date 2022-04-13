<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Questrial">
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><spring:message code="home.title"/></title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="public/styles/home.css" />
    <style>
        body {
            font-family: 'Questrial', sans-serif;
        }
    </style>
</head>
<body class="bg-gray-100">
<%--Navbar--%>
<jsp:include page="navbar2.jsp">
    <jsp:param name="navItem" value="${2}" />
    <jsp:param name="name" value="Bandify" />
</jsp:include>

<div class="flex flex-col">
    <h2 class="text-5xl section-title mt-20 mb-5" id="posts">
        <spring:message code="home.auditionsSection" />
    </h2>
    <%--Publicaciones de audiciones--%>
    <div class="posts">
        <c:forEach var="audition" items="${auditionList}" varStatus="loop">
            <c:set
                    var="lookingFor"
                    value="${audition.lookingFor}"
                    scope="request"
            />
            <c:set
                    var="musicGenres"
                    value="${audition.musicGenres}"
                    scope="request"
            />
            <jsp:include page="postCard.jsp">
                <jsp:param name="id" value="${audition.id}" />
                <jsp:param name="postCard" value="${1}" />
                <jsp:param name="auditionDate" value="${audition.timeElapsed}" />
                <jsp:param name="auditionTitle" value="${audition.title}" />
                <jsp:param name="auditionEmail" value="${audition.email}" />
                <jsp:param
                        name="auditionLocation"
                        value="${audition.location.name}"
                />
                <jsp:param
                        name="auditionDescription"
                        value="${audition.description}"
                />
            </jsp:include>
        </c:forEach>
    </div>
</div>
</body>
</html>
