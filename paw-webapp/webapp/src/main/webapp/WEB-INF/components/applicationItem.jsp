<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <style>
        .application-box {
            display: flex;
            flex-direction: row;
            margin: 1rem;
            padding: 1rem;
            border: 1px solid #ccc;
            border-radius: 5px;
            justify-content: space-around;
            background-color: white;
            /*width: 0%;*/
            align-self: center;
        }

        .application-icon {
            width: 32px;
            margin: 0 0.25rem;
        }

        .application-icons {
            display: flex;
            flex-direction: row;
            justify-items: center;
            align-items: center;

        }
    </style>
</head>
<body>
<div class="application-box">
    <a><c:out value="${param.applicantName}"/> <c:out value="${param.applicantSurname}"/></a>
    <div>
        <spring:message code="application.accept" var="accept"/>
        <spring:message code="application.reject" var="reject"/>
        <div class="application-icons">
            <c:url value="/auditions/${param.auditionId}" var="acceptUrl">
                <c:param name="accept" value="true"/>
                <c:param name="userId" value="${param.userId}"/>
            </c:url>
            <c:url value="/auditions/${param.auditionId}" var="rejectUrl">
                <c:param name="accept" value="false"/>
                <c:param name="userId" value="${param.userId}"/>
            </c:url>
            <form action="${acceptUrl}" method="post">
                <button type="submit"><img src="<c:url value="/resources/icons/success.svg" />" alt="${accept}" class="application-icon"/></button>
            </form>
            <form action="${rejectUrl}" method="post">
                <button type="submit"><img src="<c:url value="/resources/icons/reject.svg" />" alt="${reject}" class="application-icon"/></button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
