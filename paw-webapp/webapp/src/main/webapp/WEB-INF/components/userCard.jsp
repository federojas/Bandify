<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="title.artistCard"/></title>
    <link rel="stylesheet" href="<c:url value="/resources/css/userCard.css" />" />
</head>
<body>
<div class="artist-card shadow">

    <div>
        <spring:message code="artists.img.alt" var="artistImgAlt"/>
        <img class="artist-img"
             src="<c:url value='/user/${param.userId}/profile-image'/>" alt="${artistImgAlt}"/>
    </div>


    <div class="artist-content">
        <div>
            <p class="artist-name">
                <c:out value="${param.userName}" />
                <c:out value="${param.userSurname}"/>
            </p>
            <p class="artist-description">
                <c:if test="${param.isBand}">
                    <spring:message code="register.band_word"/>
                </c:if>
                <c:if test="${!param.isBand}">
                    <spring:message code="register.artist_word"/>
                </c:if>
            </p>
            <%--  TODO:   Location--%>
            <div class="roles-div">
                <div class="loop-div">
                    <c:forEach var="role"
                               items="${requestScope.userRoles}"
                               varStatus="loop">
                        <c:if test="${loop.index < 2}">
                            <span class="roles-span"><c:out value="${role.name}" /></span>
                        </c:if>
                    </c:forEach>
                    <c:if test="${requestScope.userRoles.size() > 2}">
                        <span class="roles-span">+<c:out value="${requestScope.userRoles.size() - 2}" /></span>
                    </c:if>
                </div>
            </div>
            <div class="genres-div">
                <div class="loop-div">
                    <c:forEach var="genre"
                               items="${requestScope.userGenres}"
                               varStatus="loop">
                        <c:if test="${loop.index < 2}">
                            <span class="genre-span"><c:out value="${genre.name}" /></span>
                        </c:if>
                    </c:forEach>
                    <c:if test="${requestScope.userGenres.size() > 2}">
                        <span class="genre-span">+<c:out value="${requestScope.userGenres.size() - 2}" /></span>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <div class="artist-btn-div">
        <c:url value="/user/${param.userId}" var="profileUrl" />
        <a href="${profileUrl}">
            <button class="artist-profile-btn">
                <spring:message code="artists.moreInfo"/>
            </button>
        </a>
    </div>
</div>
</body>
</html>
