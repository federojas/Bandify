<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
        contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
        prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title><spring:message code="title.profileauditions"/></title>
    <c:import url="../config/generalHead.jsp" />

    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/auditions.css" />" />

</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${11}" />
    <jsp:param name="name" value="Bandify" />
</jsp:include>

<main>
    <!-- Auditions content -->
    <div class="auditions-content">
        <c:if test="${isPropietary}">
            <h2 id="posts">
                <spring:message code="profile.auditions" />
            </h2>
        </c:if>
        <c:if test="${!isPropietary}">
            <h2 id="bandAuditions">
                <spring:message code="profile.bandAuditions" arguments="${userName}"/>
            </h2>
        </c:if>
        <div class="posts">
            <c:if test="${auditionList.size() == 0}">
                <b><p  class="no-auditions">
                    <spring:message code="auditions.noAuditions"/>
                </p>
                </b>
            </c:if>
            <c:if test="${!isPropietary}">
                <c:forEach var="audition" items="${auditionList}" varStatus="loop">
                    <c:set
                            var="lookingFor"
                            value="${audition.lookingFor}"
                            scope="request"
                    />
                    <c:set
                            var="musicGenres"
                            value="${audition.musicGenres}"
                            scope="request"
                    />

                    <jsp:include page="../components/postCard.jsp">
                        <jsp:param name="userName" value="${audition.band.name}"/>
                        <jsp:param name="userId" value="${audition.band.id}"/>
                        <jsp:param name="id" value="${audition.id}" />
                        <jsp:param name="postCard" value="${1}" />
                        <jsp:param name="auditionDate" value="${audition.creationDate}" />
                        <jsp:param name="month" value="${audition.creationDate.month.toString()}" />
                        <jsp:param name="dayOfMonth" value="${audition.creationDate.dayOfMonth}"/>
                        <jsp:param name="year" value="${audition.creationDate.year}" />
                        <jsp:param name="auditionTitle" value="${audition.title}" />
                        <jsp:param
                                name="auditionLocation"
                                value="${audition.location.name}"
                        />
                        <jsp:param
                                name="auditionDescription"
                                value="${audition.description}"
                        />
                    </jsp:include>
                </c:forEach>
            </c:if>
            <c:if test="${isPropietary}">
                <c:forEach var="audition" items="${auditionList}" varStatus="loop">
                    <c:set
                            var="lookingFor"
                            value="${audition.lookingFor}"
                            scope="request"
                    />
                    <c:set
                            var="musicGenres"
                            value="${audition.musicGenres}"
                            scope="request"
                    />

                    <jsp:include page="../components/bandPostCard.jsp">
                        <jsp:param name="userName" value="${audition.band.name}"/>
                        <jsp:param name="userId" value="${audition.band.id}"/>
                        <jsp:param name="id" value="${audition.id}" />
                        <jsp:param name="postCard" value="${1}" />
                        <jsp:param name="auditionDate" value="${audition.creationDate}" />
                        <jsp:param name="month" value="${audition.creationDate.month.toString()}" />
                        <jsp:param name="dayOfMonth" value="${audition.creationDate.dayOfMonth}"/>
                        <jsp:param name="year" value="${audition.creationDate.year}" />
                        <jsp:param name="auditionTitle" value="${audition.title}" />
                        <jsp:param
                                name="auditionLocation"
                                value="${audition.location.name}"
                        />
                        <jsp:param
                                name="auditionDescription"
                                value="${audition.description}"
                        />
                        <jsp:param name="pendingApplicantsCount" value="${audition.pendingCount}"/>
                    </jsp:include>
                </c:forEach>

            </c:if>
        </div>
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <spring:message code="pagination.previous.page.alt" var="previous"/>
                <c:if test="${isPropietary}">
                    <a href="<c:url value="/profile/auditions?page=${currentPage-1}"/>">
                        <img src="<c:url value="/resources/images/page-next.png"/>"
                             alt="${previous}" class="pagination-next rotate">
                    </a>
                </c:if>

                <c:if test="${!isPropietary}">
                    <a href="<c:url value="/bandAuditions/${bandId}?page=${currentPage-1}"/>">
                        <img src="<c:url value="/resources/images/page-next.png"/>"
                             alt="${previous}" class="pagination-next rotate">
                    </a>
                </c:if>
                </c:if>
            <b><spring:message code="page.current" arguments="${currentPage},${lastPage}" /></b>
            <c:if test="${currentPage < lastPage}">
                <spring:message code="pagination.next.page.alt" var="next"/>

                <c:if test="${isPropietary}">
                    <a href="<c:url value="/profile/auditions?page=${currentPage+1}"/>">
                        <img src="<c:url value="/resources/images/page-next.png"/>"
                             alt="${next}" class="pagination-next">
                    </a>
                </c:if>
                <c:if test="${!isPropietary}">
                    <a href="<c:url value="/bandAuditions/${bandId}?page=${currentPage+1}"/>">
                        <img src="<c:url value="/resources/images/page-next.png"/>"
                             alt="${next}" class="pagination-next">
                    </a>
                </c:if>
            </c:if>
        </div>
    </div>

</main>
<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
</body>
</html>
