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
    <style>
        .applicationForm {
            padding: 0.5rem 1.5rem;
            border-color: #6c0c8436;
            border-radius: 0.75rem;
            border-width: 1px;
            border-style: dotted;
            font-size: 1.25rem;
            line-height: 1.5rem;
            font-weight: 600;
        }
        .form-input {
            display: block;
            padding: 0.5rem 0.75rem;
            margin-top: 0.25rem;
            background-color: #ffffff;
            width: 100%;
            border-radius: 0.375rem;
            border-width: 1px;
            box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
        }
        
        .form-label {
            display: block;
            margin-top: 1.25rem;
            font-weight: 500;
        }

        .end-button-div {
            display: flex;
            flex-direction: row-reverse;
            margin-top: 2rem;
        }

        .purple-button {
            padding: 0.5rem 1.25rem;
            color: #ffffff;
            font-weight: 600;
            border-radius: 9999px;
            background-color: #6c0c84;
        }
    </style>
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
