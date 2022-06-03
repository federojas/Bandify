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

    <c:if test="${param.isBand}">
        <div class="imageDiv-band">
            <spring:message code="artists.img.alt" var="artistImgAlt"/>
            <img class="artist-img"
                 src="<c:url value='/user/${param.userId}/profile-image'/>" alt="${artistImgAlt}"/>
        </div>
    </c:if>
    <c:if test="${!param.isBand}">
        <div class="imageDiv-artist">
            <spring:message code="artists.img.alt" var="artistImgAlt"/>
            <img class="artist-img"
                 src="<c:url value='/user/${param.userId}/profile-image'/>" alt="${artistImgAlt}"/>
        </div>
    </c:if>


    <div class="artist-content">
        <div>
            <p class="artist-name">
                <c:out value="${param.userName}" />
                <c:out value="${param.userSurname}"/>
            </p>

            <p class="artist-description">
                <c:if test="${param.isBand}">
                    <span class="account-type-label-band">
                        <spring:message code="register.band_word"/>
                    </span>
                </c:if>
                <c:if test="${!param.isBand}">
                    <span class="account-type-label-artist">
                        <spring:message code="register.artist_word"/>
                    </span>
                </c:if>
            </p>
            <c:if test="${not empty param.location}">
                <div class="location">
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            viewBox="0 0 24 24"
                            width="14"
                            height="25"
                            class="locationImg"
                    >
                        <path d="M13.987,6.108c-.039.011-7.228,2.864-7.228,2.864a2.76,2.76,0,0,0,.2,5.212l2.346.587.773,2.524A2.739,2.739,0,0,0,12.617,19h.044a2.738,2.738,0,0,0,2.532-1.786s2.693-7.165,2.7-7.2a3.2,3.2,0,0,0-3.908-3.907ZM15.97,9.467,13.322,16.51a.738.738,0,0,1-.692.49c-.1-.012-.525-.026-.675-.378l-.908-2.976a1,1,0,0,0-.713-.679l-2.818-.7a.762.762,0,0,1-.027-1.433l7.06-2.8a1.149,1.149,0,0,1,1.094.32A1.19,1.19,0,0,1,15.97,9.467ZM12,0A12,12,0,1,0,24,12,12.013,12.013,0,0,0,12,0Zm0,22A10,10,0,1,1,22,12,10.011,10.011,0,0,1,12,22Z"/>
                    </svg>
                    <p><c:out value="${param.location}" /></p>
                </div>
            </c:if>
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
