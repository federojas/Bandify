<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css?family=Questrial"
    />
    <link rel="stylesheet" href="public/styles/forms.css">
</head>
<body>
<div class="applicationForm">
    <c:url value="/apply" var="postularmeUrl">
        <c:param name="id" value="${param.auditionFormId}"/>
    </c:url>

    <%--@elvariable id="applicationForm" type="ar.edu.itba.paw.webapp.form.ApplicationForm"--%>
    <form:form acceptCharset="utf-8" modelAttribute="applicationForm"
               action="${postularmeUrl}" method="post">
        <div>
            <form:label class="form-label" path="name">
                Nombre completo *
            </form:label>
            <form:input type="text" maxlength="25" class="form-input" placeholder="(max 25 caracteres)"
                        path="name"/>

            <form:errors path="name" element="p" cssClass="error">
            </form:errors>
        </div>
        <div>
            <form:label class="form-label" path="email">
                Email *
            </form:label>
            <form:input type="email" maxlength="30" placeholder="(max 30 caracteres)" class="form-input"
                        path="email"/>

            <form:errors path="email" element="p" cssClass="error">
            </form:errors>
        </div>
        <div>
            <form:label class="form-label" path="message">
                Mensaje *
            </form:label>
            <form:textarea type="text" maxlength="300" placeholder="(max 300 caracteres)" class="form-input"
                           path="message"/>

            <form:errors path="message" element="p" cssClass="error">
            </form:errors>
        </div>
        <div class="end-button-div">
            <button type="submit"
                    class="purple-button">
                Aplicar
            </button>
        </div>
    </form:form>
</div>
</body>

</html>
