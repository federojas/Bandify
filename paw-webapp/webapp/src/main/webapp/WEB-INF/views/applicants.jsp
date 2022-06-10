<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page
        contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.applicants"/></title>
    <c:import url="../config/generalHead.jsp"/>
    <c:import url="../config/materializeHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/auditions.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/applicants.css" />"/>
    <script src="<c:url value="/resources/js/pagination.js" />"></script>
    <script src="<c:url value="/resources/js/applicants.js"/>"></script>
</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${2}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<main>
    <!-- Auditions content -->
    <div class="applicants-container">
        <div class="left-panel-abs">
            <a class="back-anchor" onclick="window.history.back();" style="cursor: pointer;">
                <div class="back-div">
                    <spring:message code="audition.alt.back" var="backAlt"/>
                    <img src="<c:url value="/resources/icons/back.svg" />" alt="${backAlt}" class="back-icon"/>
                </div>
            </a>
        </div>
        <div class="applicants-content">
            <h2 class="applicants-title">
                <spring:message code="applicants.title" arguments="${auditionTitle}"/>
            </h2>

            <div class="user-data">
                <div class="user-data-tabs">
                    <c:url value="/auditions/${id}/applicants" var="pendingUrl">
                        <c:param name="state" value="PENDING"/>
                    </c:url>
                    <c:url value="/auditions/${id}/applicants" var="acceptedUrl">
                        <c:param name="state" value="ACCEPTED"/>
                    </c:url>
                    <c:url value="/auditions/${id}/applicants" var="rejectedUrl">
                        <c:param name="state" value="REJECTED"/>
                    </c:url>
                    <a href="${pendingUrl}" id="pending"><spring:message code="applications.pending"/></a>
                    <a href="${acceptedUrl}" id="accepted"><spring:message code="applications.accepted"/></a>
                    <a href="${rejectedUrl}" id="rejected"><spring:message code="applications.rejected"/></a>
                </div>
                <hr class="rounded">
                <div class="user-data-applicants">
                    <c:if test="${applications.size() > 0}">
                        <ul class="collapsible applicants-ul">
                            <c:forEach var="app" items="${applications}">
                                <jsp:include page="../components/applicationItem.jsp">
                                    <jsp:param name="applicantName" value="${app.applicant.name}" />
                                    <jsp:param name="applicantSurname" value="${app.applicant.surname}" />
                                    <jsp:param name="auditionId" value="${app.audition.id}" />
                                    <jsp:param name="userId" value="${app.applicant.id}" />
                                    <jsp:param name="actionable" value="${app.state.state=='PENDING'}" />
                                    <jsp:param name="accepted" value="${app.state.state == 'ACCEPTED'}"/>
                                    <jsp:param name="message" value="${app.message}"/>
                                    <jsp:param name="available" value="${app.applicant.available}" />
                                    <jsp:param name="email" value="${app.applicant.email}" />
                                </jsp:include>
                            </c:forEach>
                        </ul>
                    </c:if>
                    <c:if test="${applications.size() == 0}">
                        <p class="no-applications">
                            <spring:message code="profile.noApplications"/>
                        </p>
                    </c:if>
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

    </div>
</main>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>