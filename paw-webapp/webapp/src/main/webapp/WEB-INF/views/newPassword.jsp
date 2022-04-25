<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>New Password</title>
    <c:import url="../config/generalHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />"/>

    <script type="text/javascript" src="/resources/js/resetPassword.js"></script>

</head>
<body>
<%--@elvariable id="newPasswordForm" type="ar.edu.itba.paw.webapp.form.NewPasswordForm"--%>
<c:url value="/newPassword?token=${token}" var="newPasswordUrl"/>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
<h1 class="reset-pwd-header"><spring:message code="newPassword.instructions"/></h1>
<div class="email-reset-password-box">
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
        <form:errors cssClass="error" path="newPassword" element="p"> </form:errors>
    </div>
    <div class="mt-4">
        <button
                type="submit"
                value="submit"
                class="purple-button"
        >
            <spring:message code="newPassword.form.postButton"/>
        </button>
    </div>
</form:form>
</div>
</body>
</html>
