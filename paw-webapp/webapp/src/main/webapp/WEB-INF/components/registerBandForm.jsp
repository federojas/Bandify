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
    <script>
        function checkPasswordBand() {
            if (document.getElementById("password_band").value ===
            document.getElementById("confirm_password_band").value) {
                document.getElementById("match_message").style.display = "block";
                document.getElementById("nonmatch_message").style.display = "none";
            } else {
                document.getElementById("match_message").style.display = "none";
                document.getElementById("nonmatch_message").style.display = "block";
            }
        }
    </script>
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
            <form:input type="text" maxlength="50" placeholder="${emailplaceholder}" class="form-input" path="email"/>
            <form:errors path="email" element="p" cssClass="error"> </form:errors>
        </div>

        <div>
            <form:label class="form-label" path="password">
                <spring:message code="register.form.password"/>
            </form:label>
            <spring:message code="register.form.passwordplaceholder" var="passwordplaceholder"/>
            <form:input id="password_band" type="password" maxlength="50" placeholder="${passwordplaceholder}" class="form-input"
                        path="password" onkeyup="checkPasswordBand()"/>
            <form:errors path="password" element="p" cssClass="error"> </form:errors>
        </div>

        <div>
            <label class="form-label">
                <spring:message code="register.form.confirm_password" />
            </label>
            <spring:message code="register.form.passwordplaceholder" var="confirm_passwordplaceholder"/>
            <input type="password" name="confirm_password" class="form-input" id="confirm_password_band" onkeyup="checkPasswordBand()" placeholder="${confirm_passwordplaceholder}"/>
            <span id="match_message" style="color: green; display: none;">
                <spring:message code="register.form.passwordmatch"/>
            </span>
            <span id="nonmatch_message" style="color: red; display: none;">
                <spring:message code="register.form.passwordnomatch"/>
            </span>
        </div>

        <div>
            <form:label class="form-label" path="name">
                <spring:message code="register.form.band_name"/>
            </form:label>
            <spring:message code="register.form.nameplaceholder" var="nameplaceholder"/>
            <form:input type="text" maxlength="50" placeholder="${nameplaceholder}" class="form-input" path="name"/>
            <form:errors path="name" element="p" cssClass="error"> </form:errors>
        </div>

        <div class="end-button-div">
            <button
                    type="submit"
                    class="purple-button"
            >
                <spring:message code="register.postButton"/>
            </button>
        </div>
    </form:form>
</div>

</body>
</html>
