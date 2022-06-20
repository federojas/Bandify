<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ldagostino
  Date: 13/6/22
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="title.managerTabs"/></title>
</head>

<body>
<div class="manager-tabs">
  <a href="<c:url value="/profile/applications"/>">
      <div class="${param.tabItem == 1 ? "manager-tab-selected" : "manager-tab"}">
          <div class="manager-tab-title-icon">

        <spring:message code="manager.myApplicationsIconAlt" var="myApplicationsIconAlt"/>
        <img src="<c:url value="/resources/icons/music-icon-1.svg"/>"
             class="audition-icon" alt="${myApplicationsIconAlt}" />
        <span class="manager-items-title">
                        <spring:message code="profile.myApplications"/>
                    </span>
          </div>
      </div>
  </a>
  <a href="<c:url value="/invites"/>">
    <div class="${param.tabItem == 2 ? "manager-tab-selected" : "manager-tab"}">
      <div class="manager-tab-title-icon">
        <spring:message code="invites.invitesIconAlt" var="invitesIconAlt"/>
        <img src="<c:url value="/resources/icons/users-network.svg"/>"
             class="audition-icon" alt="${invitesIconAlt}" />
        <span class="manager-items-title">
                        <spring:message code="manager.invites"/>
                    </span>
      </div>
      <c:if test="${param.pendingMembershipsCount > 0}">
        <span class="icon-button__badge"><c:out value="${param.pendingMembershipsCount}"/></span>
      </c:if>
    </div>
  </a>
</div>
</body>
</html>