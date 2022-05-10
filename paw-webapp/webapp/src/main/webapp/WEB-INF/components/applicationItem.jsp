<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.applicationItem"/> </title>
    <link rel="stylesheet" href="<c:url value="/resources/css/applicationItem.css" />" />
</head>
<body>
<div class="application-box">

    <a href="<c:url value="/user/${param.userId}"/>">
        <div class="image-and-name">
            <spring:message code="profile.img.alt" var="img"/>
            <img class="postcard-profile-image"
                 src="<c:url value="/user/${param.userId}/profile-image"/>"
                 alt="${img}">

            <span class="user-name">
                    <c:out value="${param.applicantName}"/> <c:out value="${param.applicantSurname}"/>
            </span>
        </div>
    </a>

    <div>
        <spring:message code="application.accept" var="accept"/>
        <spring:message code="application.reject" var="reject"/>
        <c:if test="${param.actionable}">
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
        </c:if>

    </div>
</div>

</body>
</html>
