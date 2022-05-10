<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link
            rel="stylesheet"
            href="<c:url value="https://fonts.googleapis.com/css?family=Questrial"/> "
    />
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />">
    <script src="<c:url value="/resources/js/register.js" />"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/alerts.css" />">
    <title><spring:message code="title.registerBandForm"/> </title>
</head>
<body>
<div class="register-content">
    <c:url value="/registerBand" var="registerUrl"/>
    <%--@elvariable id="userBandForm" type="ar.edu.itba.paw.webapp.form.UserBandForm"--%>
    <form:form
            modelAttribute="userBandForm"
            action="${registerUrl}"
            method="post"
            acceptCharset="utf-8"
            class="box"
    >
        <div>
            <form:label class="form-label" path="email">
                <spring:message code="register.form.email"/>
            </form:label>
            <spring:message code="register.form.emailplaceholder" var="emailplaceholder"/>
            <form:input type="text" maxlength="254" placeholder="${emailplaceholder}" id="bandEmaill" class="form-input" path="email"/>
            <form:errors path="email" element="p" cssClass="error"> </form:errors>
            <p class="error" id="wrongMail" style="display: none"><spring:message code="Email.userBandForm.email"/></p>

        </div>

        <div>
            <form:label class="form-label" path="password">
                <spring:message code="register.form.password"/>
            </form:label>
            <spring:message code="register.form.passwordplaceholder" var="passwordplaceholder"/>
            <form:input id="password_band" type="password" maxlength="25" placeholder="${passwordplaceholder}" class="form-input"
                        path="password" onkeyup="checkPasswordBand()" />
            <form:errors path="password" element="p" cssClass="error"> </form:errors>
            <p class="error" id="emptyPass" style="display: none"><spring:message code="register.form.emptyPassword"/></p>

        </div>

        <div>
            <form:label class="form-label" path="passwordConfirmation">
                <spring:message code="register.form.confirm_password"/>
            </form:label>
            <spring:message code="register.form.passwordplaceholder" var="passwordplaceholder"/>
            <form:input id="confirm_password_band" type="password" maxlength="25" placeholder="${passwordplaceholder}" class="form-input"
                        path="passwordConfirmation" onkeyup="checkPasswordArtist()"/>
            <form:errors path="passwordConfirmation" element="p" cssClass="error"> </form:errors>
            <span id="match_message" class="success" style="display: none;">
                <spring:message code="register.form.passwordmatch"/>
            </span>
            <span id="nonmatch_message" class="error" style="display: none;">
                <spring:message code="register.form.passwordnomatch"/>
            </span>
        </div>

        <div>
            <form:label class="form-label" path="name">
                <spring:message code="register.form.band_name"/>
            </form:label>
            <spring:message code="register.form.nameplaceholder" var="nameplaceholder"/>
            <form:input type="text" maxlength="50" placeholder="${nameplaceholder}" class="form-input" id="bandName" path="name"/>
            <form:errors path="name" element="p" cssClass="error"> </form:errors>
            <p class="error" id="wrongName" style="display: none"><spring:message code="register.form.invalidName"/></p>

        </div>

        <div class="end-button-div">
            <button
                    type="submit"
                    value="submit"
                    class="purple-button"
                    onclick="return registerbandCheck()"
            >
                <spring:message code="register.postButton"/>
            </button>
        </div>
    </form:form>
</div>

</body>
</html>
