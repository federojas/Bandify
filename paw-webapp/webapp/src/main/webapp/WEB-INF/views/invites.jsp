<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
        contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
        prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title><spring:message code="title.profileauditions"/></title>
    <c:import url="../config/generalHead.jsp"/>
    <c:import url="../config/materializeHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/auditions.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/applicants.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/invites.css" />"/>
    <script src="<c:url value="/resources/js/pagination.js" />"></script>
    <script src="<c:url value="/resources/js/applicants.js"/>"></script>

</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${12}" />
    <jsp:param name="name" value="Bandify" />
</jsp:include>

<main class="manager-page">
    <jsp:include page="../components/managerTabs.jsp">
        <jsp:param name="tabItem" value="${2}"/>
        <jsp:param name="pendingMembershipsCount" value="${pendingMembershipsCount}"/>
    </jsp:include>

    <div class="manager-items-container">

        <span class="manager-items-title">
            <c:if test="${invites.size() > 0}">
                        <spring:message code="invites.title" arguments="${pendingMembershipsCount}"/>
            </c:if>
            <c:if test="${invites.size() == 0}">
                <spring:message code="invites.title2" />
            </c:if>
    </span>
        <hr class="rounded">

        <c:if test="${invites.size() == 0}">
            <p class="no-invites"><spring:message code="invites.none"/></p>
        </c:if>
        <c:if test="${invites.size() > 0}">
            <div class="manager-items-list">
                <div>
                    <ul class="collapsible">
                        <c:forEach var="pendingMembership" items="${invites}">
                            <c:set
                                    var="memberRoles"
                                    value="${pendingMembership.roles}"
                                    scope="request"
                            />
                            <jsp:include page="../components/inviteItem.jsp">
                                <jsp:param name="bandId" value="${pendingMembership.band.id}"/>
                                <jsp:param name="bandName" value="${pendingMembership.band.name}"/>
                                <jsp:param name="userId" value="${user.id}"/>
                                <jsp:param name="membershipId" value="${pendingMembership.id}"/>
                                <jsp:param name="inviteDescription" value="${pendingMembership.description}"/>
                            </jsp:include>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </c:if>

        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <spring:message code="pagination.previous.page.alt" var="previous"/>
                <a onclick="getPaginationURL(${currentPage-1});">
                    <img src="<c:url value="/resources/images/page-next.png"/>"
                         alt="${previous}" class="pagination-next rotate">
                </a>
            </c:if>
            <b><spring:message code="page.current" arguments="${currentPage},${lastPage}" /></b>
            <c:if test="${currentPage < lastPage}">
                <spring:message code="pagination.next.page.alt" var="next"/>
                <a onclick="getPaginationURL(${currentPage+1});">
                    <img src="<c:url value="/resources/images/page-next.png"/>"
                         alt="${next}" class="pagination-next">
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
