<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="title.discoverusers"/></title>
    <c:import url="../config/generalHead.jsp" />
    <c:import url="../config/materializeHead.jsp" />
    <link rel="stylesheet" href="<c:url value="/resources/css/usersDiscover.css" />" />
</head>
<body>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${7}" />
    <jsp:param name="name" value="Bandify" />
</jsp:include>

<main class="users-discover">
    <div class="abs-container-users">
        <h2><spring:message code="usersdiscover.findUsers" /></h2>
        <jsp:include page="../components/userSearchBar.jsp">
            <jsp:param name="name" value="Bandify"/>
        </jsp:include>
    </div>
</main>

<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>
