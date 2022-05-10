<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.loginForm"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/login.css" />" />

</head>
<body>
<%--        Log in --%>
<div class="login-box">
    <div class="general-div" id="login">
        <form action="<c:url value="/login"/> " method="post">
            <div class="form-group">
                <label for="email" class="form-label">
                    <spring:message code="welcome.email"/>
                </label>
                <input type="text" required class="form-input" id="email" name="email"
                       placeholder="<spring:message code="welcome.email"/>"/>
                <p id="invalidMail" class="error" style="display: none"><spring:message
                        code="Welcome.error.invalidMail"/></p>

            </div>
            <div class="form-group">
                <label for="password" class="form-label">
                    <spring:message code="welcome.password"/>
                </label>
                <input type="password" required class="form-input" id="password" name="password"
                       placeholder="<spring:message code="welcome.password"/>"/>
            </div>
            <p id="invalidPassword" class="error" style="display: none"><spring:message
                    code="Welcome.error.invalidPassword"/></p>

            <div class="check-box">
                <input type="checkbox" id="rememberme" class="remember-me" value="false"/>
                <spring:message code="welcome.rememberme"/>
            </div>
            <a href="<c:url value="/resetPassword"/>"><u class="login-reset-button"><spring:message code="welcome.resetButton"/></u></a>

            <div class="errorDiv">
                <p class="error">
                    <c:if test="${param.error}"><c:out
                            value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></c:if>
                </p>
            </div>
            <div class="loginButton">
                <button type="submit" onclick="return loginFormCheck()" class="purple-hover-button">
                    <spring:message code="welcome.loginButton"/>
                </button>
            </div>
        </form>
        <div class="notMemberYet">
            <p><spring:message code="welcome.notMemberYet"/></p>
            &nbsp;&nbsp;
            <b><a href="<c:url value="/register"/>"><u class="login-register-button"><spring:message code="welcome.registerButton"/></u></a>
            </b>
        </div>
    </div>
</div>
</body>
</html>
