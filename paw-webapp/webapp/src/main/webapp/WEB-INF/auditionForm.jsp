<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <link rel="icon" type="image/png" href="public/images/logo.png" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Questrial">
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title><spring:message code="home.title"/></title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="public/styles/home.css" />
</head>
<body>
    <!-- Navbar -->
    <jsp:include page="navbar2.jsp">
        <jsp:param name="navItem" value="${3}" />
        <jsp:param name="name" value="Bandify" />
    </jsp:include>

    <%--Formulario--%>
    <div class="post-form-container" id="form-post">
        <h1 class="home-h1-0"><spring:message code="home.formSectionh1"/></h1>
        <h2 class="home-h1-0"><spring:message code="home.formSectionh2"/></h2>
        <c:url value="/postAudition" var="postPath" />
        <!-- Form box -->
        <div class="inner-box-form" id="form-post-title">
            <form:form
                    modelAttribute="auditionForm"
                    action="${postPath}"
                    method="post"
                    acceptCharset="utf-8"
            >
                <div>
                    <form:label class="home-form-label" path="title">
                        <spring:message code="home.form.title"/>
                    </form:label>
                    <form:input type="text"  maxlength="25" placeholder="(max 25 caracteres)" class="home-form-input" path="title" />

                    <form:errors path="title" element="p" cssClass="error"> </form:errors>
                </div>
                <div>
                    <form:label class="home-form-label" path="description">
                        <spring:message code="home.form.description"/>
                    </form:label>
                    <form:textarea
                            maxlength="300" placeholder="(max 300 caracteres)"
                            class="home-form-input"
                            type="text"
                            path="description"
                    />

                    <form:errors path="description" element="p" cssClass="error"> </form:errors>
                </div>
                <div>
                    <form:label class="home-form-label" path="location">
                        <spring:message code="home.form.location"/>
                    </form:label>
                    <form:select
                            class="home-form-input"
                            path="location"
                            multiple="false"

                    >
                        <form:option value="0" selected="true" disabled="disabled" hidden="true"><spring:message code="home.form.location.default"/></form:option>
                        <c:forEach
                                var="location"
                                items="${locationList}"
                                varStatus="loop"
                        >
                            <form:option value="${location.id}">
                                ${location.name}
                            </form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div>
                    <form:label class="home-form-label" path="email">
                        <spring:message code="home.form.email"/>
                    </form:label>
                    <form:input type="text"  maxlength="50" placeholder="ejemplo@email.com" class="home-form-input" path="email" />

                    <form:errors path="email" element="p" cssClass="error"> </form:errors>
                </div>
                <div>
                    <form:label class="home-form-label" for="musicGenres" path="musicGenres"> <spring:message code="home.form.musicGenres"/> </form:label>
                    <form:select
                            class="multiple-select home-form-input"
                            path="musicGenres"
                            multiple="true"
                    >
                        <c:forEach var="genre" items="${genreList}" varStatus="loop">
                            <form:option value="${genre.id}"> ${genre.name} </form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div>
                    <form:label class="home-form-label" for="lookingFor" path="lookingFor"> <spring:message code="home.form.lookingFor"/> </form:label>
                    <form:select
                            class="multiple-select home-form-input"
                            path="lookingFor"
                            multiple="true"
                    >
                        <c:forEach var="role" items="${roleList}" varStatus="loop">
                            <form:option value="${role.id}"> ${role.name} </form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="post-button-div mt-8">
                    <button
                            type="submit"
                            class="post-button bg-sky-600 hover:bg-sky-700"
                    >
                        <spring:message code="home.postButton"/>
                    </button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
