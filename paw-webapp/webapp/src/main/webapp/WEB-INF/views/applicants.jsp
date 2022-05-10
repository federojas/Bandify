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
    <script>
        const queryString = window.location.search;
        const parameters = new URLSearchParams(queryString);
        console.log(parameters.get('state'))
        $(document).ready(function () {
            $(".select-wrapper").each(function () {
                var wrapper = this;
                $(this).find("ul>li").each(function () {
                    var li = this;
                    var option_text = $(this).text();
                    if (option_text == parameters.get('state')) {
                        $(li).click();
                    }
                });
            });
        });
    </script>
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
        <form action="<c:url value="/auditions/${id}/applicants" />" method="get" class="filter-applications-form">
            <div class="filter-applications">
                <div>
                    <label for="postulation"><spring:message code="applications.seeBy"/></label>
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
        <c:if test="${applications.size() > 0}">
            <c:forEach var="app" items="${applications}">
                <jsp:include page="../components/applicationItem.jsp">
                    <jsp:param name="applicantName" value="${app.applicantName}" />
                    <jsp:param name="applicantSurname" value="${app.applicantSurname}" />
                    <jsp:param name="auditionId" value="${param.auditionId}" />
                    <jsp:param name="userId" value="${app.applicantId}" />
                    <jsp:param name="actionable" value="true" />
                </jsp:include>
            </c:forEach>
        </c:if>
        <c:if test="${applications.size() == 0}">
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