<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--@elvariable id="newPasswordForm" type="ar.edu.itba.paw.webapp.form.NewPasswordForm"--%>
<c:url value="/newPassword?token=${token}" var="newPasswordUrl"/>
<form:form
        modelAttribute="newPasswordForm"
        action="${newPasswordUrl}"
        method="post"
        acceptCharset="utf-8"
        class="box"
>
    <div>
        <form:label path="newPassword">
            <spring:message code="newPassword.form.newPassword"/>
        </form:label>
        <spring:message code="newPassword.form.placeholder" var="placeholder"/>
        <form:input type="password" id="password" maxlength="25" placeholder="${placeholder}" class="form-input" path="newPassword"/>
        <form:errors path="newPassword" element="p"> </form:errors>
    </div>
    <div>
        <button
                type="submit"
                value="submit"
        >
            <spring:message code="newPassword.form.postButton"/>
        </button>
    </div>
</form:form>
</body>
</html>
