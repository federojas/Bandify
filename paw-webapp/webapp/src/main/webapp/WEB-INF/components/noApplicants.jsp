<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.noApplicants"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/applicationItem.css" />" />
</head>
<body>
<div class="application-box">
    <div class="no-applicants">
        <spring:message code="noapplicants.title" var="title1"/>
        <c:out value="${title1}"/>
    </div>
</div>

</body>
</html>
