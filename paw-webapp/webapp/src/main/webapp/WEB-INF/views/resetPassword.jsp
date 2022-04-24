<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Reset Password</title>
</head>
<body>
<c:url value="/resetPassword" var="resetUrl"/>
<%--@elvariable id="resetPasswordForm" type="ar.edu.itba.paw.webapp.form.ResetPasswordForm"--%>
<form:form
        modelAttribute="resetPasswordForm"
        action="${resetUrl}"
        method="post"
        acceptCharset="utf-8"
        class="box"
>
    <div>
        <form:label path="email">
            <spring:message code="resetPassword.form.email"/>
        </form:label>
        <spring:message code="resetPassword.form.emailplaceholder" var="placeholder"/>
        <form:input type="text" id="email" maxlength="50" placeholder="${placeholder}" class="form-input" path="email"/>
        <form:errors path="email" element="p"> </form:errors>
    </div>
    <div>
        <button
                type="submit"
                value="submit"
        >
            <spring:message code="resetPassword.form.postButton"/>
        </button>
    </div>
</form:form>
</body>
</html>
