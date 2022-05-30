<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <c:import url="../config/materializeHead.jsp"/>
  <script>
    $(document).ready(function(){
      $('.tabs').tabs();
    });
  </script>
  <link rel="stylesheet" href="<c:url value="/resources/css/applicants.css" />" />

  <title><spring:message code="title.applicantsByState"/></title>
</head>
<body>
<div class="applicants">


  <div class="row applicants-by-state">
    <div class="col s12">
      <ul class="tabs">
        <li class="tab col s3"><a class="active" href="#pendingTab"><spring:message code="applicants.tabs.pending" /></a></li>
        <li class="tab col s3"><a href="#approvedTab"><spring:message code="applicants.tabs.approved" /></a></li>
        <li class="tab col s3 "><a href="#rejectedTab"><spring:message code="applicants.tabs.rejected" /></a></li>
      </ul>
    </div>
    <div id="pendingTab" class="col s12">
      <c:if test="${pendingApps.size() > 0}">
        <c:forEach var="app" items="${pendingApps}" varStatus="loop">
          <jsp:include page="applicationItem.jsp">
            <jsp:param name="applicantName" value="${app.applicant.name}" />
            <jsp:param name="applicantSurname" value="${app.applicant.surname}" />
            <jsp:param name="auditionId" value="${param.auditionId}" />
            <jsp:param name="userId" value="${app.applicantId}" />
            <jsp:param name="email" value="${app.applicant.email}" />
            <jsp:param name="actionable" value="true" />
          </jsp:include>
        </c:forEach>
      </c:if>
      <c:if test="${pendingApps.size() == 0}">
        <jsp:include page="noApplicants.jsp">
          <jsp:param name="section" value="pending" />
        </jsp:include>
      </c:if>
    </div>
    <div id="approvedTab" class="col s12">
      <c:if test="${acceptedApps.size() > 0}">
        <c:forEach var="app" items="${acceptedApps}" varStatus="loop">
          <jsp:include page="applicationItem.jsp">
            <jsp:param name="applicantName" value="${app.applicant.name}" />
            <jsp:param name="applicantSurname" value="${app.applicant.surname}" />
            <jsp:param name="auditionId" value="${param.auditionId}" />
            <jsp:param name="userId" value="${app.applicant.id}" />
            <jsp:param name="email" value="${app.applicant.email}" />
            <jsp:param name="actionable" value="false" />
          </jsp:include>
        </c:forEach>
      </c:if>
      <c:if test="${acceptedApps.size() == 0}">
        <jsp:include page="noApplicants.jsp">
          <jsp:param name="section" value="approved" />
        </jsp:include>
      </c:if>
    </div>
    <div id="rejectedTab" class="col s12">
      <c:if test="${rejectedApps.size() > 0}">
        <c:forEach var="app" items="${rejectedApps}" varStatus="loop">
          <jsp:include page="applicationItem.jsp">
            <jsp:param name="applicantName" value="${app.applicant.name}" />
            <jsp:param name="applicantSurname" value="${app.applicant.surname}" />
            <jsp:param name="auditionId" value="${param.auditionId}" />
            <jsp:param name="userId" value="${app.applicant.id}" />
            <jsp:param name="email" value="${app.applicant.email}" />
            <jsp:param name="actionable" value="false" />
          </jsp:include>
        </c:forEach>
      </c:if>
      <c:if test="${rejectedApps.size() == 0}">
        <jsp:include page="noApplicants.jsp">
          <jsp:param name="section" value="rejected" />
        </jsp:include>
      </c:if>
    </div>
  </div>


</div>
</body>
</html>
