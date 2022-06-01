<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="title.artists"/></title>
    <c:import url="../config/generalHead.jsp" />
    <c:import url="../config/materializeHead.jsp" />
    <link rel="stylesheet" href="<c:url value="/resources/css/users.css" />" />
    <script src="<c:url value="/resources/js/pagination.js" />"></script>
    <style>
        main {
            padding: 2rem;
        }
    </style>
</head>
<body>
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${2}" />
    <jsp:param name="name" value="Bandify" />
</jsp:include>

<main>

    <div class="users-content">
        <h2 class="black-title">
            <spring:message code="search.title" />
        </h2>
        <div class="meet-artists">
            <c:forEach
                var="user"
                items="${userList}"
                varStatus="loop"
            >
                <c:set
                        var="userRoles"
                        value="${user.userRoles}"
                        scope="request"
                />
                <c:set
                        var="userGenres"
                        value="${user.userGenres}"
                        scope="request"
                />
                <jsp:include page="../components/userCard.jsp">
                    <jsp:param name="userId" value="${user.id}"/>
                    <jsp:param name="userName" value="${user.name}" />
                    <jsp:param name="userSurname" value="${user.surname}"/>
                    <jsp:param name="userDescription" value="${user.description}" />
                </jsp:include>
            </c:forEach>
        </div>
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <spring:message code="pagination.previous.page.alt" var="previous"/>
                <a onclick="getPaginationURL(${currentPage-1});">
                    <img src="<c:url value="/resources/images/page-next.png"/>"
                         alt="previous" class="pagination-next rotate">
                </a>
            </c:if>
            <b><spring:message code="page.current" arguments="${currentPage},${lastPage}" /></b>
            <c:if test="${currentPage < lastPage}">
                <spring:message code="pagination.next.page.alt" var="next"/>
                <a onclick="getPaginationURL(${currentPage+1});">
                    <img src="<c:url value="/resources/images/page-next.png"/>"
                         alt="next" class="pagination-next">
                </a>
            </c:if>
        </div>
    </div>
</main>

<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>