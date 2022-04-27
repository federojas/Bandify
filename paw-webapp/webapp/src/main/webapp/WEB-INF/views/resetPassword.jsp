<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <c:import url="../config/generalHead.jsp"/>
    <script type="text/javascript" src="/resources/js/resetPassword.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />"/>
</head>
<body onload="load()">
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<h1 class="reset-pwd-header"><spring:message code="resetPassword.instructions" /></h1>
<c:url value="/resetPassword" var="resetUrl"/>
<%--@elvariable id="resetPasswordForm" type="ar.edu.itba.paw.webapp.form.ResetPasswordForm"--%>
<div class="email-reset-password-box">
    <form:form
            modelAttribute="resetPasswordForm"
            action="${resetUrl}"
            method="post"
            acceptCharset="utf-8"
            class="box"
            id="form"
    >
        <div>
            <form:label class="form-label" path="email">
                <spring:message code="resetPassword.form.email"/>
            </form:label>
            <spring:message code="resetPassword.form.emailplaceholder" var="placeholder"/>
            <form:input type="text" id="email" maxlength="50" placeholder="${placeholder}" class="form-input recovery-input" path="email"/>
            <form:errors path="email" cssClass="error" element="p"> </form:errors>
            <p class="error" id="invalidEmail" style="display: none"><spring:message code="Email.resetPasswordForm.email"/></p>
        </div>
        <c:if test="${emailNotFound}">
            <div class="error">
                <spring:message code="resetPassword.invalidEmailSnackbar.message"/>
            </div>
        </c:if>
        <c:if test="${emailSent}">
            <div class="email-sent">
                <spring:message code="resetPassword.validEmailSnackbar.message"/>
            </div>
        </c:if>
        <div class="mt-4">
            <button
                    type="submit"
                    value="submit"
                    class="purple-button"
                    id="sumbitButton"
            >
                <spring:message code="resetPassword.form.postButton"/>
            </button>
        </div>
    </form:form>
</div>

</body>
</html>
