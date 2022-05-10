<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> <%@ taglib
prefix="form" uri="http://www.springframework.org/tags/form" %> <%@ page
contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
  <head>
    <title><spring:message code="title.auditions"/></title>
    <c:import url="../config/generalHead.jsp" />
    <c:import url="../config/materializeHead.jsp" />
    <link rel="stylesheet" href="<c:url value="/resources/css/welcome.css" />" />
    <link rel="stylesheet" href="<c:url value="/resources/css/auditions.css" />" />
    <script src="<c:url value="/resources/js/pagination.js" />"></script>

    <script>
      $(document).ready(function(){
        $('.parallax').parallax();
      });
    </script>

  </head>
  <body>

    <!-- Navbar -->
    <jsp:include page="../components/navbar.jsp">
      <jsp:param name="navItem" value="${2}" />
      <jsp:param name="name" value="Bandify" />
    </jsp:include>
    <div>
      <div class="parallax-container">
        <div class="parallax"><img src="<c:url value="/resources/images/parallax5.png" />"/></div>
        <div class="flex flex-row justify-between">
          <div class="ml-10 mt-10 flex flex-col justify-between">
            <h2 id="posts">
              <spring:message code="welcome.auditionsSection" />
            </h2>
            <jsp:include page="../components/searchBar.jsp">
              <jsp:param name="name" value="Bandify" />
            </jsp:include>
          </div>
          <div class="mt-10 mr-10 languages">
            <a class="languages-buttons" href="?lang=es">ES</a>
            &nbsp;
            <a class="languages-buttons" href="?lang=en">EN</a>
          </div>
        </div>
      </div>
    <!-- Auditions content -->
      <div class="auditions-content">

        <h2 class="black-title">
          <spring:message code="auditions.latest"/>
        </h2>
      <div class="posts">
        <c:if test="${auditionList.size() == 0}">
          <b><p class="languages-buttons">
            <spring:message code="auditions.noAuditions"/>
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
            <jsp:param name="userName" value="${audition.bandName}"/>
            <jsp:param name="userId" value="${audition.bandId}"/>
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
      </div>
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
    </div>
  </body>
</html>
