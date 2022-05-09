<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page
        contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.profileapplications"/></title>
    <c:import url="../config/generalHead.jsp"/>
    <c:import url="../config/materializeHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/auditions.css" />"/>
    <script src="<c:url value="/resources/js/pagination.js" />"></script>
</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${2}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<!-- Auditions content -->
<div class="auditions-content">
    <h2 id="posts">
        <spring:message code="profile.myApplications"/>
    </h2>
    <div class="user-data">
        <form action="<c:url value="/profile/applications" />" method="get" class="filter-applications-form">
            <div class="filter-applications">

                    <div>
                        <label for="postulation"><spring:message code="applications.see"/></label>
                        <select id="postulation" name="state">
                            <option value=""><spring:message code="applications.all"/></option>
                            <option value="Pending"><spring:message code="applications.pending"/></option>
                            <option value="Accepted"><spring:message code="applications.accepted"/></option>
                            <option value="Rejected"><spring:message code="applications.rejected"/></option>
                        </select>
                    </div>
                <button type="submit" class="filter-applications-button"><spring:message code="applications.see"/></button>
            </div>
        </form>
        <c:if test="${artistApplications.size() > 0}">
            <c:forEach var="artistApplication" items="${artistApplications}">
                <jsp:include page="../components/artistApplicationItem.jsp">
                    <jsp:param name="artistApplicationState" value="${artistApplication.state}"/>
                    <jsp:param name="auditionTitle" value="${artistApplication.auditionTitle}"/>
                    <jsp:param name="auditionId" value="${artistApplication.auditionId}"/>
                </jsp:include>
            </c:forEach>
        </c:if>
        <c:if test="${artistApplications.size() == 0}">
            <p class="no-applications">
                <spring:message code="profile.noApplications"/>
            </p>
        </c:if>
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
</body>
</html>