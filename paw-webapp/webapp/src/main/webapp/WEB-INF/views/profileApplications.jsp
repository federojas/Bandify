<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
        contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
        prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title><spring:message code="title.profileapplications"/></title>
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
    <jsp:param name="navItem" value="${11}" />
    <jsp:param name="name" value="Bandify" />
</jsp:include>

<main class="manager-page">
    <jsp:include page="../components/managerTabs.jsp">
        <jsp:param name="tabItem" value="${1}"/>
        <jsp:param name="pendingMembershipsCount" value="${pendingMembershipsCount}"/>
    </jsp:include>

    <div class="manager-items-container">

        <span class="manager-items-title">
            <spring:message code="manager.myApplicationsTitle"/>
        </span>

        <hr class="rounded">

        <div class="auditions-content">
            <div class="user-data">
                <div class="user-data-tabs">
                    <c:url value="/profile/applications" var="pendingUrl">
                        <c:param name="state" value="PENDING"/>
                    </c:url>
                    <c:url value="/profile/applications" var="acceptedUrl">
                        <c:param name="state" value="ACCEPTED"/>
                    </c:url>
                    <c:url value="/profile/applications" var="rejectedUrl">
                        <c:param name="state" value="REJECTED"/>
                    </c:url>
                    <a href="${pendingUrl}" id="pending"><spring:message code="applications.pending"/></a>
                    <a href="${acceptedUrl}" id="accepted"><spring:message code="applications.accepted"/></a>
                    <a href="${rejectedUrl}" id="rejected"><spring:message code="applications.rejected"/></a>
                </div>
                <hr class="rounded">
                <div class="user-data-applicants">
                    <c:if test="${artistApplications.size() > 0}">
                        <c:forEach var="artistApplication" items="${artistApplications}">
                            <jsp:include page="../components/artistApplicationItem.jsp">
                                <jsp:param name="artistApplicationState" value="${artistApplication.state}"/>
                                <jsp:param name="auditionTitle" value="${artistApplication.audition.title}"/>
                                <jsp:param name="isOpen" value="${artistApplication.audition.isOpen}"/>
                                <jsp:param name="auditionId" value="${artistApplication.audition.id}"/>
                            </jsp:include>
                        </c:forEach>
                    </c:if>
                    <c:if test="${artistApplications.size() == 0}">
                        <p class="no-applications">
                            <spring:message code="profile.noApplications"/>
                        </p>
                    </c:if>
                </div>
            </div>

        </div>

        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <spring:message code="pagination.previous.page.alt" var="previous"/>
                <a onclick="getPaginationURL(${currentPage-1})">
                    <img src="<c:url value="/resources/images/page-next.png"/>"
                         alt="${previous}" class="pagination-next rotate">
                </a>
            </c:if>
            <b><spring:message code="page.current" arguments="${currentPage},${lastPage}"/></b>
            <c:if test="${currentPage < lastPage}">
                <spring:message code="pagination.next.page.alt" var="next"/>
                <a onclick="getPaginationURL(${currentPage+1})">
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
