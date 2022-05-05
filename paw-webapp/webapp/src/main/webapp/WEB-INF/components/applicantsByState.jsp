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
</head>
<body>
<div class="applicants">


  <div class="row">
    <div class="col s12">
      <ul class="tabs">
        <li class="tab col s3"><a class="active"href="#pendingTab"><spring:message code="applicants.tabs.pending" /></a></li>
        <li class="tab col s3"><a href="#approvedTab"><spring:message code="applicants.tabs.approved" /></a></li>
        <li class="tab col s3 "><a href="#rejectedTab"><spring:message code="applicants.tabs.rejected" /></a></li>
      </ul>
    </div>
    <div id="pendingTab" class="col s12">
      <c:forEach var="app" items="${pendingApps}" varStatus="loop">
        <jsp:include page="applicationItem.jsp">
          <jsp:param name="applicantName" value="${app.applicantName}" />
          <jsp:param name="applicantSurname" value="${app.applicantSurname}" />
          <jsp:param name="auditionId" value="${param.auditionId}" />
          <jsp:param name="userId" value="${app.applicantId}" />
        </jsp:include>
      </c:forEach>
    </div>
    <div id="approvedTab" class="col s12">
      <c:forEach var="app" items="${acceptedApps}" varStatus="loop">
        <jsp:include page="applicationItem.jsp">
          <jsp:param name="applicantName" value="${app.applicantName}" />
          <jsp:param name="applicantSurname" value="${app.applicantSurname}" />
          <jsp:param name="auditionId" value="${param.auditionId}" />
          <jsp:param name="userId" value="${app.applicantId}" />
        </jsp:include>
      </c:forEach>
    </div>
    <div id="rejectedTab" class="col s12">
      <c:forEach var="app" items="${rejectedApps}" varStatus="loop">
        <jsp:include page="applicationItem.jsp">
          <jsp:param name="applicantName" value="${app.applicantName}" />
          <jsp:param name="applicantSurname" value="${app.applicantSurname}" />
          <jsp:param name="auditionId" value="${param.auditionId}" />
          <jsp:param name="userId" value="${app.applicantId}" />
        </jsp:include>
      </c:forEach>
    </div>
  </div>


</div>
</body>
</html>
