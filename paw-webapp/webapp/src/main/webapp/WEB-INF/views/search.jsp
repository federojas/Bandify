<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
        contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
        prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <c:import url="../config/generalHead.jsp" />
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/auditions.css" />" />
    <script src="<c:url value="/resources/js/pagination.js" />"></script>
</head>
<body>
    <!-- Navbar -->
    <jsp:include page="../components/navbar.jsp">
        <jsp:param name="navItem" value="${2}" />
        <jsp:param name="name" value="Bandify" />
    </jsp:include>



    <!-- Auditions content -->
    <div class="auditions-content">
        <h2 id="posts">
            <spring:message code="search.title" />
        </h2>

        <jsp:include page="../components/searchBar.jsp">
            <jsp:param name="name" value="Bandify" />
        </jsp:include>

        <%--Publicaciones de audiciones--%>
            <div class="posts">
                <c:if test="${auditionList.size() == 0}">
                    <b><p  style="width: 100%; text-align: center">
                        <spring:message code="search.noresults"/>
                    </p>
                    </b>
                </c:if>
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
                    <jsp:param name="userName" value="${userMap[audition.id]}"/>
                    <jsp:param name="userId" value="${audition.bandId}"/>
                    <jsp:param name="id" value="${audition.id}" />
                    <jsp:param name="postCard" value="${1}" />
                    <jsp:param name="auditionDate" value="${audition.timeElapsed}" />
                    <jsp:param name="auditionTitle" value="${audition.title}" />
                    <jsp:param
                            name="auditionLocation"
                            value="${audition.location.name}"
                    />
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
    </div>
</body>
</html>