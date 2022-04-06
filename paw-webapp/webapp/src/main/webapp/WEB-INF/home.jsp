<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">
<%--Navbar--%>
<jsp:include page="navbar.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<%--Formulario--%>
<div>
    <h2>¿En busca de músicos para tu banda?</h2>
    <h3>Crea una audición</h3>
    <c:url value="/create" var="postPath"/>
    <form:form modelAttribute="auditionForm" action="${postPath}" method="post">

        <div>
            <form:label path="title">Title: </form:label>
            <form:input type="text" path="title"/>
            <form:errors path="title" element="p"/>
        </div>
        <div>
            <form:label path="description">Description: </form:label>
            <form:input type="description" path="description"/>
            <form:errors path="description" element="p"/>
        </div>
        <div>
            <form:label path="location">Location: </form:label>
            <form:input type="location" path="location"/>
            <form:errors path="location" element="p"/>
        </div>
        <div>
            <label for="musicGenres">Music Genres: </label>
            <form:select path="musicGenres" multiple="true">
                <form:option value="rock">Rock</form:option>
                <form:option value="cumbia">Cumbia</form:option>
            </form:select>
        </div>
        <div>
            <label for="lookingFor">Looking for: </label>
            <form:select path="lookingFor" multiple="true">
                <form:option value="guitarrista">Guitarrista</form:option>
                <form:option value="baterista">Baterista</form:option>
            </form:select>
        </div>
        <div>
            <input type="submit" value="Register!"/>
        </div>
    </form:form>
</div>

<%--Publicaciones de audiciones--%>
<div class="flex flex-col">
    <c:forEach var="audition" items="${auditionList}" varStatus="loop">
        <jsp:include page="postCard.jsp">
            <jsp:param name="Id" value="${audition.id}" />
            <jsp:param name="postCard" value="${1}" />
            <jsp:param name="bandName" value="${audition.title}" />
            <jsp:param name="auditionDate" value="${audition.creationDate}" />
            <jsp:param name="auditionTitle" value="${audition.lookingFor}" />
            <jsp:param name="auditionDescription" value="${audition.description}" />
        </jsp:include>
    </c:forEach>

</div>

</body>
</html>