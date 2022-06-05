<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.newpassword"/></title>
    <c:import url="../config/generalHead.jsp"/>
    <c:import url="../config/materializeHead.jsp"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/alerts.js"/>"></script>
    <script src="<c:url value="/resources/js/newPassword.js" />"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/alerts.css" />"/>
</head>
<body onload="load()">
<%--@elvariable id="newPasswordForm" type="ar.edu.itba.paw.webapp.form.NewPasswordForm"--%>
<c:url value="/newPassword?token=${token}" var="newPasswordUrl"/>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
<main class="flex flex-col justify-center">
    <h1 class="reset-pwd-header"><spring:message code="newPassword.instructions"/></h1>
    <div class="email-reset-password-box">
        <form:form
                modelAttribute="newPasswordForm"
                action="${newPasswordUrl}"
                method="post"
                acceptCharset="utf-8"
                id="form"
        ><spring:message code="newPassword.form.placeholder" var="placeholder"/>
            <div>
                <form:label path="newPassword" class="form-label">
                    <spring:message code="newPassword.form.newPassword"/>
                </form:label>

                <form:input type="password" id="password" maxlength="25" placeholder="${placeholder}"
                            class="form-input recovery-input" path="newPassword" onkeyup="checkPasswordsReset()"/>
                <form:errors cssClass="error" path="newPassword" element="p"> </form:errors>
                <p class="error" id="invalidPassword" style="display: none"><spring:message code="newPasswordForm.newPassword" arguments="8,25"/></p>
            </div>
            <div>
                <form:label path="newPasswordConfirmation" class="form-label">
                    <spring:message code="newPassword.form.newPasswordConfirmation"/>
                </form:label>

                <form:input type="password" id="passwordConfirmation" maxlength="25" placeholder="${placeholder}"
                            class="form-input recovery-input" path="newPasswordConfirmation" onkeyup="checkPasswordsReset()"/>
                <form:errors cssClass="error" path="newPasswordConfirmation" element="p"> </form:errors>
                <p class="error" id="invalidPasswordConfirmation" style="display: none"><spring:message
                        code="newPasswordForm.newPassword"/></p>
            </div>
            <span id="match_message" class="success" style="display: none;">
                <spring:message code="register.form.passwordmatch"/>
            </span>
            <span id="nonmatch_message" class="error" style="display: none;">
                <spring:message code="register.form.passwordnomatch"/>
        </span>
            <div class="mt-4">
                <button
                        type="submit"
                        value="submit"
                        class="purple-button"
                        onclick="return validateNewPasswordForm()"
                >
                    <spring:message code="newPassword.form.postButton"/>
                </button>
            </div>
        </form:form>
    </div>
    <div id="snackbar"><spring:message code="snackbar.message"/></div>
</main>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>
