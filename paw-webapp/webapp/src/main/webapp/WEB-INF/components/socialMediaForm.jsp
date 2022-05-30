<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
    <title><spring:message code="socialMediaForm.title"/> </title>
</head>
<body>


<span id="toggleSocialMediaContainer" >
                         <div class="social-media-toggle-button-container">
                             <div class="social-media-button-text">
                                <spring:message code="editProfile.form.socialMediaToggle"/>
                             </div>

                            <div class="arrow-icon" id="arrowIcon">
                                <spring:message code="editProfile.arrow.alt" var="arrow"/>
                                <img src="<c:url value="/resources/icons/arrow-down.svg" />" alt="${arrow}" class="application-icon"/>
                            </div>
                        </div>
                    </span>
<div  id="socialMediaContainer" class="social-media-form">

    <div class="social-media-input-container">
        <div class="social-media-icons social-media-input-icon">

            <img class="social-media-icons"
            <spring:message code="profile.twitter.alt" var="twitter"/>
                 src="<c:url value="/resources/images/twitter.png"/>"
                 alt="${twitter}">

        </div>
        <div class="social-media-input">

            <form:label class="form-label" path="twitterUrl">
                <spring:message code="editProfile.twitter"/>
            </form:label>
            <form:input type="text" id="artistSurname" maxlength="50" placeholder="${surnameplaceholder}"
                        class="form-input"
                        path="twitterUrl"/>
            <form:errors path="twitterUrl" element="p" cssClass="error"> </form:errors>
        </div>

    </div>
    <div class="social-media-input-container">
        <div class="social-media-icons social-media-input-icon">

            <img class="social-media-icons"
            <spring:message code="profile.facebook.alt" var="facebook"/>
                 src="<c:url value="/resources/images/facebook.png"/>"
                 alt="${facebook}">

        </div>
        <div class="social-media-input">

            <form:label class="form-label" path="facebookUrl">
                <spring:message code="editProfile.facebook"/>
            </form:label>
            <form:input type="text" id="artistSurname" maxlength="50" placeholder="${surnameplaceholder}"
                        class="form-input"
                        path="facebookUrl"/>
            <form:errors path="facebookUrl" element="p" cssClass="error"> </form:errors>
        </div>

    </div>
    <div class="social-media-input-container">
        <div class="social-media-icons social-media-input-icon">

            <img class="social-media-icons"
            <spring:message code="profile.instagram.alt" var="instagram"/>
                 src="<c:url value="/resources/images/instagram.png"/>"
                 alt="${instagram}">

        </div>
        <div class="social-media-input">

            <form:label class="form-label" path="instagramUrl">
                <spring:message code="editProfile.instagram"/>
            </form:label>
            <form:input type="text" id="artistSurname" maxlength="50" placeholder="${surnameplaceholder}"
                        class="form-input"
                        path="instagramUrl"/>
            <form:errors path="instagramUrl" element="p" cssClass="error"> </form:errors>
        </div>

    </div>
    <div class="social-media-input-container">
        <div class="social-media-icons social-media-input-icon">

            <img class="social-media-icons"
            <spring:message code="profile.youtube.alt" var="youtube"/>
                 src="<c:url value="/resources/images/youtube.png"/>"
                 alt="${youtube}">

        </div>
        <div class="social-media-input">

            <form:label class="form-label" path="youtubeUrl">
                <spring:message code="editProfile.youtube"/>
            </form:label>
            <form:input type="text" id="artistSurname" maxlength="50" placeholder="${surnameplaceholder}"
                        class="form-input"
                        path="youtubeUrl"/>
            <form:errors path="youtubeUrl" element="p" cssClass="error"> </form:errors>
        </div>

    </div>
    <div class="social-media-input-container">
        <div class="social-media-icons social-media-input-icon">

            <img class="social-media-icons"
            <spring:message code="profile.spotify.alt" var="spotify"/>
                 src="<c:url value="/resources/images/spotify.png"/>"
                 alt="${spotify}">

        </div>
        <div class="social-media-input">

            <form:label class="form-label" path="spotifyUrl">
                <spring:message code="editProfile.spotify"/>
            </form:label>
            <form:input type="text" id="artistSurname" maxlength="50" placeholder="${surnameplaceholder}"
                        class="form-input"
                        path="spotifyUrl"/>
            <form:errors path="spotifyUrl" element="p" cssClass="error"> </form:errors>
        </div>

    </div>
    <div class="social-media-input-container">
        <div class="social-media-icons social-media-input-icon">

            <img class="social-media-icons"
            <spring:message code="profile.soundcloud.alt" var="soundcloud"/>
                 src="<c:url value="/resources/images/soundcloud.png"/>"
                 alt="${soundcloud}">

        </div>
        <div class="social-media-input">

            <form:label class="form-label" path="soundcloudUrl">
                <spring:message code="editProfile.soundcloud"/>
            </form:label>
            <form:input type="text" id="artistSurname" maxlength="50" placeholder="${surnameplaceholder}"
                        class="form-input"
                        path="soundcloudUrl"/>
            <form:errors path="soundcloudUrl" element="p" cssClass="error"> </form:errors>
        </div>

    </div>


</div>
</body>
</html>
