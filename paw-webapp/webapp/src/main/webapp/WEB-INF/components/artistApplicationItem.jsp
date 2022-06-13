<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:import url="../config/materializeHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/artistApplication.css" />"/>
    <title><spring:message code="title.artistApplicationItem"/> </title>
</head>
<body>
<%-- ArtistApplicationItem --%>
<div class="collection">
    <c:choose>
        <c:when test="${param.isOpen}">
            <c:url value="/auditions/${param.auditionId}" var="auditionUrl"/>
            <a href="${auditionUrl}" class="collection-item">
                    <%-- Status --%>
                <c:choose>
                    <c:when test="${param.artistApplicationState == 'ACCEPTED'}">
                        <span class="badge green"><spring:message code="applicants.tabs.approved"/></span>
                    </c:when>
                    <c:when test="${param.artistApplicationState == 'REJECTED'}">
                        <span class="badge red"><spring:message code="applicants.tabs.rejected"/></span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge orange"><spring:message code="applicants.tabs.pending"/></span>
                    </c:otherwise>
                </c:choose>
                <div class="auditionName"><c:out value="${param.auditionTitle}"/></div>
            </a>
        </c:when>
        <c:otherwise><%-- Status --%>
            <div class="closed-app">
                <div class="auditionName"><c:out value="${param.auditionTitle}"/></div>
                <div class="states">
                    <c:choose>
                        <c:when test="${param.artistApplicationState == 'ACCEPTED'}">
                            <span class="badge green"><spring:message code="applicants.tabs.approved"/></span>
                        </c:when>
                        <c:when test="${param.artistApplicationState == 'REJECTED'}">
                            <span class="badge red"><spring:message code="applicants.tabs.rejected"/></span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge orange"><spring:message code="applicants.tabs.pending"/></span>
                        </c:otherwise>
                    </c:choose>
                    <span class="badge-bigger red"><spring:message code="applicants.tabs.closed"/></span>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
