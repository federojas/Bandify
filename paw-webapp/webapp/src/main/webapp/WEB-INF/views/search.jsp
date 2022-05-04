<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
        contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
        prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <c:import url="../config/generalHead.jsp" />
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />" />
    <style>
        body {
            /* gray-100 */
            --tw-bg-opacity: 1;
            background-color: rgb(243 244 246 / var(--tw-bg-opacity));
        }
        .auditions-content {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 2rem 1rem;
        }
        .auditions-content > h2 {
            font-size: 3rem;
            line-height: 1;
            font-weight: 700;
            padding: 1rem;
            margin: 1rem;
        }
        .posts {
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
            margin: 0.75rem;
            justify-content: space-around;
        }

        .pagination {
            display: flex;
            flex-direction: row;
            flex-wrap: wrap;
            margin: 0.75rem;
            justify-content: space-around;
        }

        .pagination-next{
            margin: 0 10px;
            height: 40px;
            cursor: pointer;
        }

        .rotate{
            transform: rotate(180deg);
        }
    </style>
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



    <%--Publicaciones de audiciones--%>
    <c:if test="${auditionList.size() != 0}">
    <div>
        <div class="posts">
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
                    <jsp:param name="id" value="${audition.id}" />
                    <jsp:param name="postCard" value="${1}" />
                    <jsp:param name="auditionDate" value="${audition.timeElapsed}" />
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
        </div>
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <spring:message code="pagination.previous.page.alt" var="previous"/>
                <a href="<c:url value="/search?query=${query}?page=${currentPage-1}"/>">
                    <img src="<c:url value="/resources/images/page-next.png"/>"
                         alt="previous" class="pagination-next rotate">
                </a>
            </c:if>
            <spring:message code="page.current" arguments="${currentPage},${lastPage}" />
            <c:if test="${currentPage < lastPage}">
                <spring:message code="pagination.next.page.alt" var="next"/>
                <a href="<c:url value="/search?query=${query}?page=${currentPage+1}"/>">
                    <img src="<c:url value="/resources/images/page-next.png"/>"
                         alt="next" class="pagination-next">
                </a>
            </c:if>
        </div>
    </div>
    </c:if>
    <c:if test="${auditionList.size() == 0}">
        <div>
            <h1><spring:message code="search.noresults"/></h1>
        </div>
    </c:if>
</div>
</body>
</html>