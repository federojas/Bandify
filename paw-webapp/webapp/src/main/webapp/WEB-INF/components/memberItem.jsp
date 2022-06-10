<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
  <title><spring:message code="title.applicationItem"/> </title>
  <link rel="stylesheet" href="<c:url value="/resources/css/applicationItem.css" />" />
  <link rel="stylesheet" href="<c:url value="/resources/css/memberItem.css" />" />
</head>
<body>
  <div class="main-div">
    <div class="info-div">
      <a href="<c:url value="/user/${param.userId}"/>" class="usr-url">
        <div class="image-and-name">
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
        </div>
      </a>
      <div class="message-div">
        <span class="message-span"><c:out value="${param.description}"/></span>
      </div>
      <div class="roles-div my-5">
        <c:forEach var="role"
                   items="${requestScope.memberRoles}" varStatus="loop">
          <c:if test="${loop.index < 2}">
              <span class="roles-span"><c:out value="${role.name}"/></span>
          </c:if>
        </c:forEach>
        <c:if test="${requestScope.memberRoles.size() > 2}">
          <span class="roles-span">+<c:out value="${requestScope.memberRoles.size() - 2}" /></span>
        </c:if>
      </div>
    </div>
  </div>
</body>
</html>
