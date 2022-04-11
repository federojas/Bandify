<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <link rel="stylesheet" href="public/styles/oldAuditionForm.css">
    <link rel="stylesheet" href="public/styles/home.css">
</head>
<div class="oldAuditionForm-div-0">
    <c:url value="/apply" var="postularmeUrl" />
    <%--@elvariable id="applicationForm" type="ar.edu.itba.paw.webapp.form.ApplicationForm"--%>
    <form:form id="${param.auditionFormId}" modelAttribute="applicationForm" action="${postularmeUrl}" method="post">
        <div>
            <form:label class="home-form-label" path="name">
                Nombre completo *
            </form:label>
            <form:input type="text" maxlength="25" class="home-form-input" placeholder="(max 25 caracteres)" path="name"/>

            <form:errors path="name" element="p" cssClass="error">
            </form:errors>
        </div>
        <div>
            <form:label class="home-form-label" path="email">
                Email *
            </form:label>
            <form:input type="email" maxlength="30" placeholder="(max 30 caracteres)" class="home-form-input" path="email"/>

            <form:errors path="email" element="p" cssClass="error">
            </form:errors>
        </div>
        <div>
            <form:label class="home-form-label mb-3" path="message">
                Mensaje
            </form:label>
            <form:input type="textarea" maxlength="50" placeholder="(max 50 caracteres)" class="home-form-input" path="message"/>

            <form:errors path="message" element="p" cssClass="error">
            </form:errors>
        </div>
        <input type="hidden" name="auditionEmail" value="${param.auditionEmail}"/>
        <div class="post-button-div">
            <button id="formSubmit_${param.auditionFormId}" type="submit"
                    class="post-button bg-sky-600">
                Aplicar
            </button>
        </div>
    </form:form>
</div>
</html>
