<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title><spring:message code="title.viewprofile"/></title>
    <c:import url="../config/generalHead.jsp"/>
    <c:import url="../config/materializeHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/selectApplicant.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/applicants.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/profile.css" />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/invertImg.js" />"></script>
</head>
<body>


<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${4}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
<main>
    <div class="bg-gray-100">
        <div class="main-box">
            <div class="md:flex no-wrap justify-center md:-mx-2 ">
                <!-- Left Side -->
                <div class="left-side">
                    <%--          ProfileCard      --%>
                    <div class="profile-card-view">
                        <%--                    Image--%>
                        <div class="image overflow-hidden">
                            <div class="profile-image-container">
                                <img class="profileImage"
                                <spring:message code="profile.img.alt" var="img"/>
                                     src="<c:url value="/user/${user.id}/profile-image"/>"
                                     alt="${img}">
                                <c:if test="${user.available}">
                                    <spring:message code="available.img.alt" var="available"/>
                                    <img class="top-image-big" src="<c:url value="/resources/images/available.png"/>" alt="${available}"/>
                                </c:if>
                            </div>
                        </div>
                        <%--                    Info--%>
                        <div class="profile-left-info">
                            <h1 class="full-name">
                                <c:out value=" ${user.name}" />
                                <c:if test="${!user.band}">
                                    <c:out value=" ${user.surname}"/>
                                </c:if>
                            </h1>
                            <c:if test="${not empty location}">
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

                                    <p><c:out value="${location.name}" /></p>
                                </div>
                            </c:if>
                            <c:if test="${!user.band}">
                            <span
                                    class="account-type-label-artist"><spring:message code="register.artist_word"/> </span>
                            </c:if>
                            <c:if test="${user.band}">
                                <span
                                        class="account-type-label-band"><spring:message code="register.band_word"/> </span>
                            </c:if>

                        </div>
                    </div>
                </div>
                <!-- Right Side -->
                <div class="w-full md:w-6/12 mx-2 h-64">
                    <%--  About --%>
                    <div class="user-data" style="display: flex; flex-direction: column; gap: 1rem;">
                        <span class="welcome-title"><spring:message code="applicants.confirmation.title"/>
                        </span>

                        <hr class="rounded">

                        <div class="confirmation-container">
                            <div class="header-welcome-artist">
                                <span class="welcome-title-2">
                                    <spring:message code="applicants.confirmation.artist1" arguments="${user.name},${user.surname}"/>
                                </span>
                            </div>
                            <c:url value="/user/${id}/addToBand" var="addToBandActionUrl" />
                            <%--@elvariable id="membershipForm" type="ar.edu.itba.paw.webapp.form.MembershipForm"--%>

                            <form:form method="post" acceptCharset="utf-8"
                                       action="${addToBandActionUrl}" id="membershipForm"
                                       modelAttribute="membershipForm"
                                       cssClass="gapped-form"
                            >
                                <div class="roles-selection">
                                    <form:label class="membership-form-description-label" path="roles">
                                        <spring:message code="applicants.confirmation.roles"/>
                                    </form:label>
                                    <form:select
                                            class="multiple-select"
                                            path="roles"
                                            multiple="true"
                                    >
                                        <form:option value="" disabled="true" selected="true"><spring:message code="edituser.lookingFor.maxSelect" arguments="${auditionRoles.size()}"/></form:option>
                                        <c:forEach var="role" items="${auditionRoles}" varStatus="loop">
                                            <form:option value="${role.name}"><c:out value="${role.name}"/></form:option>
                                        </c:forEach>

                                    </form:select>
                                    <form:errors path="roles" element="p" cssClass="error">
                                    </form:errors>
                                </div>
                                <div class="description-artist">
                                    <form:label class="membership-form-description-label" path="description">
                                        <spring:message code="applicants.confirmation.description"/>
                                    </form:label>
                                    <spring:message code="audition.form.description.placeholder" arguments="100" var="descriptionplaceholder" />
                                    <form:textarea
                                            maxlength="100" placeholder="${descriptionplaceholder}"
                                            class="membership-form-description-textarea"
                                            type="text"
                                            id="description"
                                            path="description"
                                    />
                                        <%--                            <p id="emptyDescription" class="error" style="display: none"><spring:message code="NotBlank.auditionForm.description" arguments="0"/> </p>--%>
                                        <%--                            <p id="longDescription" class="error" style="display: none"><spring:message code="audition.form.description.maxSize" arguments="100"/> </p>--%>
                                    <form:errors path="description" element="p" cssClass="error">
                                    </form:errors>
                                </div>
                                <div class="end-addtoband-btn">
                                    <div class="add-to-band-button">
                                        <button class="artist-profile-btn" id="addToBandBtn"
                                                onmouseover="invertImg()"
                                                onmouseout="invertImgBack()"
                                                type="submit"
                                                value="submit"
                                                form="membershipForm"
                                                class="artist-profile-btn"
                                        >
                                            <spring:message code="applicants.addToBand"/>
                                            <spring:message code="audition.applicants.alt" var="altAddToBand"/>
                                            <img src="<c:url value="/resources/icons/add-user.svg"/>"
                                                 id="addToBandBtnImg"
                                                 class="audition-icon" alt="${altAddToBand}" />
                                        </button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>


                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../components/footer.jsp">
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

</body>
</html>