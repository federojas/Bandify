<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page
        contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="public/styles/home.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

</head>
<body class="bg-gray-100 ">
<%--Navbar--%>
<jsp:include page="navbar2.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<!-- Content -->
<div class="home-div-0">
    <%--Formulario--%>
    <div class="home-div-1">
        <div class="home-div-2">
            <div>
                <h1 class="home-h1-0">
                    ¿En busca de músicos para tu banda?
                </h1>
                <h2 class="home-h2-0">
                    ¡Crea una audición!
                </h2>
            </div>
            <c:url value="/create" var="postPath"/>
            <!-- Form box -->
            <div class="home-div-4">
                <form:form modelAttribute="auditionForm" action="${postPath}" method="post">
                    <div>
                        <form:label class="home-form-label-0" path="title">
                            Titulo
                        </form:label>
                        <form:input type="text" class="home-form-input-0" path="title"/>

                        <form:errors path="title" element="p">
                        </form:errors>
                    </div>
                    <div>
                        <form:label class="home-form-label-1" path="description">
                            Descripción
                        </form:label>
                        <form:input class="home-form-input-1" type="description" path="description"/>

                        <form:errors path="description" element="p">
                        </form:errors>
                    </div>
                    <div>
                        <form:select class="home-form-select-0" path="location" multiple="false">
                            <c:forEach var="location" items="${locationList}" varStatus="loop">
                                <form:option value="${location.id}">
                                    ${location.name}
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div
                    <div>
                        <form:label class="home-form-label-3" path="email">
                            Email de la banda
                        </form:label>
                        <form:input type="text" class="home-form-input-0" path="email"/>

                        <form:errors path="email" element="p">
                        </form:errors>
                    </div>
                    <div>
                        <label class="home-label-0" for="musicGenres">
                            Géneros
                        </label>
                        <form:select class="home-form-select-1" path="musicGenres" multiple="true">
                            <c:forEach var="genre" items="${genreList}" varStatus="loop">
                                <form:option value="${genre.id}">
                                    ${genre.name}
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div>
                        <label class="home-label-1" for="lookingFor">
                            Buscando
                        </label>
                        <form:select class="home-form-select-2" path="lookingFor" multiple="true">
                            <c:forEach var="role" items="${roleList}" varStatus="loop">
                                <form:option value="${role.id}">
                                    ${role.name}
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="home-div-10">
                        <button type="submit" class="home-button-0 bg-sky-600 hover:bg-sky-700">
                            Publicar
                        </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>

    <%--Publicaciones de audiciones--%>
    <div class="home-div-11">
        <c:forEach var="audition" items="${auditionList}" varStatus="loop">
            <jsp:include page="postCard.jsp">
                <jsp:param name="Id" value="${audition.id}"/>
                <jsp:param name="postCard" value="${1}"/>
                <jsp:param name="bandName" value="${audition.bandId}"/>
                <jsp:param name="auditionDate" value="${audition.creationDate}"/>
                <jsp:param name="auditionTitle" value="${audition.title}"/>
                <jsp:param name="auditionLookingFor" value="${audition.lookingFor}"/>
                <jsp:param name="auditionMusicGenres" value="${audition.musicGenres}"/>
                <jsp:param name="auditionLocation" value="${audition.location.name}"/>
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