<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link
            rel="stylesheet"
            href="https://fonts.googleapis.com/css?family=Questrial"
    />
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />">
    <script src="<c:url value="/resources/js/register.js" />"></script>

</head>
<body>
<div class="register-content">
    <c:url value="/registerArtist" var="registerUrl"/>
    <%--@elvariable id="userArtistForm" type="ar.edu.itba.paw.webapp.form.UserArtistForm"--%>
    <form:form
            modelAttribute="userArtistForm"
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
            <form:input type="text" id="artistEmail" maxlength="50" placeholder="${emailplaceholder}" class="form-input" path="email"/>
            <form:errors path="email" element="p" cssClass="error"> </form:errors>
            <p class="error" id="wrongArtistMail" style="display: none"><spring:message code="Email.userArtistForm.email"/> </p>

        </div>

        <div>
            <form:label class="form-label" path="password">
                <spring:message code="register.form.password"/>
            </form:label>
            <spring:message code="register.form.passwordplaceholder" var="passwordplaceholder"/>
            <form:input id="password_artist" type="password" maxlength="50" placeholder="${passwordplaceholder}" class="form-input"
                        path="password" onkeyup="checkPasswordArtist()"/>
            <form:errors path="password" element="p" cssClass="error"> </form:errors>
            <p class="error" id="wrongArtistPass" style="display: none"><spring:message code="register.form.emptyPassword"/></p>

        </div>

        <div>
            <label class="form-label">
                <spring:message code="register.form.confirm_password" />
            </label>
            <spring:message code="register.form.passwordplaceholder" var="confirm_passwordplaceholder"/>
            <input type="password" name="confirm_password" class="form-input" id="confirm_password_artist" onkeyup="checkPasswordArtist()" placeholder="${confirm_passwordplaceholder}"/>
            <span id="match_message_artist" style="color: green; display: none;">
                <spring:message code="register.form.passwordmatch"/>
            </span>
            <span id="nonmatch_message_artist" style="color: red; display: none;">
                <spring:message code="register.form.passwordnomatch"/>
            </span>
        </div>

        <div>
            <form:label class="form-label" path="name">
                <spring:message code="register.form.name"/>
            </form:label>
            <spring:message code="register.form.nameplaceholder" var="nameplaceholder"/>
            <form:input type="text" id="artistName" maxlength="50" placeholder="${nameplaceholder}" class="form-input" path="name"/>
            <form:errors path="name" element="p" cssClass="error"> </form:errors>
            <p class="error" id="wrongArtistName" style="display: none"><spring:message code="register.form.invalidName"/></p>

        </div>

        <div id="surname-div">
            <form:label class="form-label" path="surname">
                <spring:message code="register.form.surname"/>
            </form:label>
            <spring:message code="register.form.surnameplaceholder" var="surnameplaceholder"/>
            <form:input type="text" id="artistSurname" maxlength="50" placeholder="${surnameplaceholder}" class="form-input"
                        path="surname"/>
            <form:errors path="surname" element="p" cssClass="error"> </form:errors>
            <p class="error" id="wrongArtistSurname" style="display: none"><spring:message code="register.form.invalidSurname"/> </p>

        </div>

        <div class="end-button-div">
            <button
                    type="submit"
                    value="submit"
                    class="purple-button"
                    onclick="return registerArtistCheck()"
            >
                <spring:message code="register.postButton"/>
            </button>
        </div>
    </form:form>
</div>
<%--<div id="snackbar"><spring:message code="snackbar.message"/></div>--%>

</body>
</html>
