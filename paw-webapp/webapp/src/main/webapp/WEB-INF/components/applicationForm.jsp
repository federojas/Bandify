<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css?family=Questrial"
    />
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/alerts.css" />"/>
    <link rel="stylesheet" href="<c:url value="${pageContext.request.contextPath}/resources/css/forms.css" />">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/formchecks.js"></script>
</head>

<body>
<div class="applicationForm">
    <c:url value="/apply" var="postularmeUrl">
        <c:param name="id" value="${param.auditionFormId}"/>
    </c:url>

    <%--@elvariable id="applicationForm" type="ar.edu.itba.paw.webapp.form.ApplicationForm"--%>
    <form:form acceptCharset="utf-8" modelAttribute="applicationForm"
               action="${postularmeUrl}" method="post" id="form">
        <div>
            <form:label class="form-label"  path="name">
                <spring:message code="application.form.name"/>
            </form:label>
            <spring:message code="application.form.name.placeholder" var="nameplaceholder" />
            <form:input type="text" maxlength="50" id="name" class="form-input" placeholder="${nameplaceholder}"
                        path="name"/>

            <form:errors path="name" element="p" cssClass="error">
            </form:errors>
        </div>
        <div>
            <form:label class="form-label" path="email">
                <spring:message code="application.form.email"/>
            </form:label>
            <spring:message code="application.form.email.placeholder" var="emailplaceholder" />
            <form:input type="email" id="email" maxlength="254" placeholder="${emailplaceholder}" class="form-input"
                        path="email"/>

            <form:errors path="email" element="p" cssClass="error">
            </form:errors>
        </div>
        <div>
            <form:label class="form-label" path="message">
                <spring:message code="application.form.message"/>
            </form:label>
            <spring:message code="application.form.message.placeholder" var="messageplaceholder" />
            <form:textarea type="text" id="message" maxlength="300" placeholder="${messageplaceholder}" class="form-input"
                           path="message"/>

            <form:errors path="message" element="p" cssClass="error">
            </form:errors>
        </div>
        <div class="end-button-div">
            <button
                    type="submit"
                    value="submit"
                    onclick="return applicationCheck()"
                    class="purple-button">
                <spring:message code="application.form.apply"/>
            </button>
        </div>
    </form:form>
    <div id="snackbar"><spring:message code="snackbar.message"/></div>
</div>
</body>

</html>
