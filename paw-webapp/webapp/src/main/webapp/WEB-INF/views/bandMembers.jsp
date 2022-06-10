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
  <link rel="stylesheet" href="<c:url value="/resources/css/bandMembers.css" />"/>
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
  <h2 class="bandMembers-title">
    <spring:message code="band.members.title" arguments="${bandName}"/>
  </h2>
  <!-- Auditions content -->
  <div class="band-members-container ">
    <div class="left-panel-abs-bandMembers">
      <a class="back-anchor" onclick="window.history.back();" style="cursor: pointer;">
        <div class="back-div">
          <spring:message code="audition.alt.back" var="backAlt"/>
          <img src="<c:url value="/resources/icons/back.svg" />" alt="${backAlt}" class="back-icon"/>
        </div>
      </a>
    </div>
    <div class="applicants-content">
      <div class="user-data">
        <div class="user-data-applicants">
          <c:if test="${members.size() > 0}">
            <ul class="applicants-ul">
              <c:forEach var="member" items="${members}" varStatus="loop">
                <c:set
                        var="memberRoles"
                        value="${member.roles}"
                        scope="request"
                />
                <jsp:include page="../components/memberCard.jsp">
                  <jsp:param name="id" value="${member.id}" />
                  <jsp:param name="isPropietary" value="${isPropietary}" />
                  <jsp:param name="memberName" value="${member.artist.name}" />
                  <jsp:param name="memberSurname" value="${member.artist.surname}" />
                  <jsp:param name="userId" value="${member.artist.id}" />
                  <jsp:param name="description" value="${member.description}"/>
                  <jsp:param name="available" value="${member.artist.available}" />
                </jsp:include>
                <c:if test="${loop.count < members.size()}">
                  <hr/><div class="horizontal-line"></div>
                </c:if>
              </c:forEach>
            </ul>
          </c:if>
          <c:if test="${members.size() == 0}">
            <p class="no-applications">
              <spring:message code="profile.noMembers"/>
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
