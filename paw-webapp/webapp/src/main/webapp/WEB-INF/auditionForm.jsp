<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: ldagostino
  Date: 12/4/22
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--Formulario--%>
<div class="post-form-container" id="form-post">
    <h1 class="home-h1-0"><spring:message code="home.formSectionh1"/></h1>
    <h2 class="home-h1-0"><spring:message code="home.formSectionh2"/></h2>
    <c:url value="/create" var="postPath" />
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
                <label class="home-form-label" for="musicGenres"> <spring:message code="home.form.musicGenres"/> </label>
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
                <label class="home-form-label" for="lookingFor"> <spring:message code="home.form.lookingFor"/> </label>
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
