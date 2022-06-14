<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
  <title><spring:message code="title.applicationItem"/> </title>
  <link rel="stylesheet" href="<c:url value="/resources/css/applicationItem.css" />" />
  <link rel="stylesheet" href="<c:url value="/resources/css/memberCard.css" />" />
  <link rel="stylesheet" href="<c:url value="/resources/css/alerts.css" />" />
  <link rel="stylesheet" href="<c:url value="/resources/css/modals.css" />" />
</head>
<body>
<div class="main-div">
  <div class="info-div">
    <div class="image-name-buttons">
        <a href="<c:url value="/user/${param.userId}"/>" class="usr-url">
          <spring:message code="profile.img.alt" var="img"/>
          <img class="postcard-profile-image"
               src="<c:url value="/user/${param.userId}/profile-image"/>"
               alt="${img}">
          <c:if test="${param.available}">
            <spring:message code="available.img.alt" var="available"/>
            <img class="top-image" src="<c:url value="/resources/images/available.png"/>" alt="${available}"/>
          </c:if>
          <h1 class="member-band-name">
            <c:out value="${param.memberName}"/> <c:out value="${param.memberSurname}"/>
          </h1>
        </a>
        <c:if test="${param.isPropietary and !param.isBand}">
          <div class="member-buttonry">
            <a class="member-edit-btn hover: shadow-sm" href="<c:url value="/profile/editMembership/${param.id}"/>">
              <button class="member-btn" type="submit">
                <spring:message code="audition.alt.edit" var="edit"/>
                <spring:message code="audition.edit" />
                <img src="<c:url value="/resources/icons/edit-white-icon.svg"/>" class="member-button-icon" alt="${edit}"/>
              </button>
            </a>
            <a class="member-remove-btn">
              <button class="member-btn" onclick="openConfirmation()" type="submit">
                <spring:message code="audition.alt.delete" var="delete"/>
                <spring:message code="member.remove" />
                <img src="<c:url value="/resources/icons/reject.svg"/>" class="member-button-icon invert" alt="${delete}"/>
              </button>
            </a>
            <spring:message code="removeConfirmationModal.title" var="modalTitle"/>
            <spring:message code="removeConfirmationModal.removeMember" var="modalHeading"/>
            <spring:message code="removeConfirmationModal.confirmationQuestion" var="confirmationQuestion"/>
            <c:url value="/profile/deleteMembership/${param.id}" var="postPath"/>
            <jsp:include page="confirmationModal.jsp">
              <jsp:param name="modalTitle" value="${modalTitle}" />
              <jsp:param name="isDelete" value="${true}"/>
              <jsp:param name="modalHeading" value="${modalHeading}" />
              <jsp:param name="confirmationQuestion" value="${confirmationQuestion}" />
              <jsp:param name="action" value="${postPath}" />
            </jsp:include>
          </div>
        </c:if>
      <c:if test="${param.isPropietary and param.isBand}">
        <div class="member-buttonry">
          <a class="member-remove-btn">
            <button class="member-btn" onclick="openConfirmation()" type="submit">
              <spring:message code="audition.alt.delete" var="delete"/>
              <spring:message code="member.leave" />
              <img src="<c:url value="/resources/icons/logout.svg"/>" class="member-button-icon" alt="${delete}"/>
            </button>
          </a>
          <spring:message code="leaveConfirmationModal.title" var="modalTitle"/>
          <spring:message code="leaveConfirmationModal.removeMember" var="modalHeading"/>
          <spring:message code="leaveConfirmationModal.confirmationQuestion" var="confirmationQuestion"/>
          <c:url value="/profile/deleteMembership/${param.id}" var="postPath"/>
          <jsp:include page="confirmationModal.jsp">
            <jsp:param name="modalTitle" value="${modalTitle}" />
            <jsp:param name="isDelete" value="${true}"/>
            <jsp:param name="modalHeading" value="${modalHeading}" />
            <jsp:param name="confirmationQuestion" value="${confirmationQuestion}" />
            <jsp:param name="action" value="${postPath}" />
          </jsp:include>
        </div>
      </c:if>
    </div>
    <c:if test="${param.isBand}">
      <div>
        <p class="legend"><spring:message code="artist.member.legend"/></p>
      </div>
    </c:if>
    <div class="message-div">
      <span class="message-span"><c:out value="${param.description}"/></span>
    </div>
    <div class="roles-div my-5">
      <c:forEach var="role"
                 items="${requestScope.memberRoles}">
          <span class="roles-span"><c:out value="${role.name}"/></span>
      </c:forEach>
    </div>
  </div>
</div>
</body>
</html>

