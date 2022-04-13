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
    <link rel="stylesheet" href="public/styles/forms.css">
    <style>
        body {
            display: flex;
            flex-direction: column;
            background-color: #f3f4f6;
        }
        .card-content {
            background-color: #ffffff;
            box-shadow: 0 10px 15px -3px #1c041c1a, 0 4px 6px -2px #1c041c0d;
            padding: 2.5rem;
            border-radius: 1rem;
            width: 60%;
            margin: 1.5rem auto;
        }
        .card-content > h1 {
            font-size: 2rem;
            line-height: 1.75rem;
            font-weight: 700;
            text-align: center;
        }
        .inner-box-form {
            padding: 0.75rem 1.5rem;
            margin-top: 1.5rem;
            border-color: #6c0c8436;
            border-width: 2px;
            border-style: dotted;
            border-radius: 0.75rem;
            font-size: 1.25rem;
            line-height: 1.5rem;
        }
        .multiple-select {
            height: fit-content !important;
        }
        #auditionForm > div {
            margin: 1.5rem 0;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <jsp:include page="navbar2.jsp">
        <jsp:param name="navItem" value="${3}" />
        <jsp:param name="name" value="Bandify" />
    </jsp:include>

    <!-- Formulario -->
    <div class="card-content" id="form-post">
        <h1><spring:message code="home.formSectionh1"/></h1>
        <h1><spring:message code="home.formSectionh2"/></h2>
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
                    <form:label class="form-label" path="title">
                        <spring:message code="home.form.title"/>
                    </form:label>
                    <form:input type="text"  maxlength="25" placeholder="(max 25 caracteres)" class="form-input" path="title" />

                    <form:errors path="title" element="p" cssClass="error"> </form:errors>
                </div>
                <div>
                    <form:label class="form-label" path="description">
                        <spring:message code="home.form.description"/>
                    </form:label>
                    <form:textarea
                            maxlength="300" placeholder="(max 300 caracteres)"
                            class="form-input"
                            type="text"
                            path="description"
                    />

                    <form:errors path="description" element="p" cssClass="error"> </form:errors>
                </div>
                <div>
                    <form:label class="form-label" path="location">
                        <spring:message code="home.form.location"/>
                    </form:label>
                    <form:select
                            class="form-input"
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
                    <form:label class="form-label" path="email">
                        <spring:message code="home.form.email"/>
                    </form:label>
                    <form:input type="text"  maxlength="50" placeholder="ejemplo@email.com" class="form-input" path="email" />

                    <form:errors path="email" element="p" cssClass="error"> </form:errors>
                </div>
                <div>
                    <form:label class="form-label" for="musicGenres" path="musicGenres"> <spring:message code="home.form.musicGenres"/> </form:label>
                    <form:select
                            class="multiple-select form-input"
                            path="musicGenres"
                            multiple="true"
                    >
                        <c:forEach var="genre" items="${genreList}" varStatus="loop">
                            <form:option value="${genre.id}"> ${genre.name} </form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div>
                    <form:label class="form-label" for="lookingFor" path="lookingFor"> <spring:message code="home.form.lookingFor"/> </form:label>
                    <form:select
                            class="multiple-select form-input"
                            path="lookingFor"
                            multiple="true"
                    >
                        <c:forEach var="role" items="${roleList}" varStatus="loop">
                            <form:option value="${role.id}"> ${role.name} </form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="end-button-div">
                    <button
                            type="submit"
                            class="purple-button"
                    >
                        <spring:message code="home.postButton"/>
                    </button>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>
