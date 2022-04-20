<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/welcome.css" />" />
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/forms.css" />">
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/alerts.css" />"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formchecks.js"></script>
    <style>
        /*body {*/
        /*    display: flex;*/
        /*    flex-direction: column;*/
        /*    background-color: #f3f4f6;*/
        /*}*/
        /*.card-content {*/
        /*    background-color: #ffffff;*/
        /*    box-shadow: 0 10px 15px -3px #1c041c1a, 0 4px 6px -2px #1c041c0d;*/
        /*    padding: 2.5rem;*/
        /*    border-radius: 1rem;*/
        /*    width: 60%;*/
        /*    margin: 1.5rem auto;*/
        /*}*/
        /*.card-content > h1 {*/
        /*    font-size: 2rem;*/
        /*    line-height: 1.75rem;*/
        /*    font-weight: 700;*/
        /*    text-align: center;*/
        /*}*/
        /*.inner-box-form {*/
        /*    padding: 0.75rem 1.5rem;*/
        /*    margin-top: 1.5rem;*/
        /*    border-color: #6c0c8436;*/
        /*    border-width: 2px;*/
        /*    border-style: dotted;*/
        /*    border-radius: 0.75rem;*/
        /*    font-size: 1.25rem;*/
        /*    line-height: 1.5rem;*/
        /*}*/
        /*.multiple-select {*/
        /*    height: fit-content !important;*/
        /*}*/
        /*#auditionForm > div {*/
        /*    margin: 1.5rem 0;*/
        /*}*/
    </style>
    <script>

    </script>
</head>
<body>
    <!-- Navbar -->
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="navItem" value="${3}" />
        <jsp:param name="name" value="Bandify" />
    </jsp:include>

    <!-- Formulario -->
    <div class="card-content" id="form-post">
        <h1><spring:message code="welcome.formSectionh1"/></h1>
        <br/>
        <h1><spring:message code="welcome.formSectionh2"/></h1>
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
                        <spring:message code="welcome.form.title"/>
                    </form:label>
                    <spring:message code="audition.form.title.placeholder" var="titleplaceholder" />
                    <form:input type="text" id="title"  maxlength="50" placeholder="${titleplaceholder}" class="form-input" path="title" />

                    <form:errors path="title" element="p" cssClass="error"> </form:errors>
                </div>
                <div>
                    <form:label class="form-label" path="description">
                        <spring:message code="welcome.form.description"/>
                    </form:label>
                    <spring:message code="audition.form.description.placeholder" var="descriptionplaceholder" />
                    <form:textarea
                            maxlength="300" placeholder="${descriptionplaceholder}"
                            class="form-input"
                            type="text"
                            id="description"
                            path="description"
                    />

                    <form:errors path="description" element="p" cssClass="error"> </form:errors>
                </div>
                <div>
                    <form:label class="form-label" path="location">
                        <spring:message code="welcome.form.location"/>
                    </form:label>
                    <form:select
                            class="form-input"
                            path="location"
                            multiple="false"
                            id="location"
                    >
                        <form:option value="0" selected="true" disabled="disabled" hidden="true"><spring:message code="welcome.form.location.default"/></form:option>
                        <c:forEach
                                var="location"
                                items="${locationList}"
                                varStatus="loop"
                        >
                            <form:option value="${location.name}">
                                ${location.name}
                            </form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="location" element="p" cssClass="error">
                    </form:errors>
                </div>
                <div>
                    <form:label class="form-label" for="musicGenres" path="musicGenres"> <spring:message code="welcome.form.musicGenres"/> </form:label>
                    <form:label class="form-label-legend" for="musicGenres" path="musicGenres"> <spring:message code="audition.form.musicGenres.maxSelect"/> </form:label>
                    <form:select
                            class="multiple-select form-input"
                            path="musicGenres"
                            multiple="true"
                    >
                        <c:forEach var="genre" items="${genreList}" varStatus="loop">
                            <form:option value="${genre.name}"> ${genre.name} </form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="musicGenres" element="p" cssClass="error">
                    </form:errors>
                </div>
                <div>
                    <form:label class="form-label" for="lookingFor" path="lookingFor"> <spring:message code="welcome.form.lookingFor"/> </form:label>
                    <form:label class="form-label-legend" for="lookingFor" path="lookingFor"> <spring:message code="audition.form.lookingFor.maxSelect"/> </form:label>
                    <form:select
                            class="multiple-select form-input"
                            path="lookingFor"
                            multiple="true"
                    >
                        <c:forEach var="role" items="${roleList}" varStatus="loop">
                            <form:option value="${role.name}"> ${role.name} </form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="lookingFor" element="p" cssClass="error">
                    </form:errors>
                </div>
                <div class="end-button-div">
                    <button
                            type="submit"
                            value="submit"
                            onclick="return auditionFormCheck()"
                            class="purple-button"
                    >
                        <spring:message code="welcome.postButton"/>
                    </button>
                </div>
            </form:form>
        </div>
    </div>
    <div id="snackbar"><spring:message code="snackbar.message"/></div>

</body>
</html>
