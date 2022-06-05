<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        .languages-buttons{
            color: white; font-size: large;
            margin: 0 10px;
        }
        footer.page-footer {
            padding: 20px;
            background-color: #1c041c;
        }
        footer.page-footer .footer-copyright {
            color: rgba(255, 255, 255, 0.8);
        }
        a {
            color: #039be5;
        }
    </style>
</head>
<body>
<footer class="page-footer">
    <div class="footer-copyright">
        <div class="flex flex-row justify-between px-10">
            <spring:message code="footer.copyright" />
            <div>
                <b><spring:message code="footer.contactus"/></b>
                <spring:eval expression="@environment.getProperty('mail.username')" var="mail"/>
                <a href="mailto: ${mail}">${mail}</a>
            </div>
            <div >
                <a class="languages-buttons right" href="<c:url value="?lang=es"/>"><spring:message code="footer.es"/></a>
                <a class="languages-buttons right" href="<c:url value="?lang=en"/>"><spring:message code="footer.en"/></a>
            </div>
        </div>
    </div>
</footer>
</body>
</html>
