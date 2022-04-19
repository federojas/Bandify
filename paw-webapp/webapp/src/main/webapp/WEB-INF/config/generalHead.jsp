<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<meta charset="UTF-8"/>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><spring:message code="welcome.title"/></title>
<script src="https://cdn.tailwindcss.com"></script>
<link rel="icon" type="image/x-icon"
      href="<c:url value="${pageContext.request.contextPath}/resources/images/favi.ico"/>"/>
<link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Questrial"
/>
<style>
    body {
        font-family: "Questrial", sans-serif;
    }
</style>
