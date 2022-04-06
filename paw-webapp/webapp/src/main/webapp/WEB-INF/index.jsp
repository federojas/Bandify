<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var titulo = "hholas";

</script>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="style.css"/>
    <style>
        .h1 {
            font-size: 6.5em;
            margin: 200px;
        }
    </style>
</head>
<body class="bg-gray-100">

<%--Navbar--%>
<jsp:include page="navbar.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<!-- CONTENT -->
<div class="bg-gray-100 p-6 flex flex-row justify-center">
    <%--FORM DE LA BANDA      --%>
    <jsp:include page="bandForm.jsp">
        <jsp:param name="bandForm" value="${1}"/>
    </jsp:include>
    <!-- SCROLL de PUBLICACIONES -->
    <div class="flex flex-col">
        <c:forEach var="audition" items="${auditionList}" varStatus="loop">
            <jsp:include page="postCard.jsp">
                <jsp:param name="Id" value="${audition.id}"/>
                <jsp:param name="postCard" value="${1}"/>
                <jsp:param name="bandName" value="${audition.title}"/>
                <jsp:param name="auditionDate" value="${audition.creationDate}"/>
                <jsp:param name="auditionTitle" value="${audition.lookingFor}"/>
                <jsp:param name="auditionDescription" value="<b>hola</b>"/>
            </jsp:include>
        </c:forEach>


        <jsp:include page="postCard.jsp">
            <jsp:param name="Id" value="$asd"/>
            <jsp:param name="postCard" value="${1}"/>
            <jsp:param name="bandName" value="$asdsda}"/>
            <jsp:param name="auditionDate" value="$asdasd}"/>
            <jsp:param name="auditionTitle" value="$aasdada"/>
            <jsp:param name="auditionDescription" value="<b>hola</b>"/>
        </jsp:include>
        <jsp:include page="postCard.jsp">
            <jsp:param name="Id" value="$asd"/>
            <jsp:param name="postCard" value="${1}"/>
            <jsp:param name="bandName" value="$asdsda}"/>
            <jsp:param name="auditionDate" value="$asdasd}"/>
            <jsp:param name="auditionTitle" value="$aasdada"/>
            <jsp:param name="auditionDescription" value="<b>hola</b>"/>
        </jsp:include>
        <jsp:include page="postCard.jsp">
            <jsp:param name="Id" value="$asd"/>
            <jsp:param name="postCard" value="${1}"/>
            <jsp:param name="bandName" value="$asdsda}"/>
            <jsp:param name="auditionDate" value="$asdasd}"/>
            <jsp:param name="auditionTitle" value="$aasdada"/>
            <jsp:param name="auditionDescription" value="<b>hola</b>"/>
        </jsp:include>
        <jsp:include page="postCard.jsp">
            <jsp:param name="Id" value="$asd"/>
            <jsp:param name="postCard" value="${1}"/>
            <jsp:param name="bandName" value="$asdsda}"/>
            <jsp:param name="auditionDate" value="$asdasd}"/>
            <jsp:param name="auditionTitle" value="$aasdada"/>
            <jsp:param name="auditionDescription" value="<b>hola</b>"/>
        </jsp:include>
    </div>


</div>

</body>
</html>
