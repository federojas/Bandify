<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/postCard.css" />"/>

</head>
<body>
<%--    Card --%>
<div class="postCard-div-0 shadow-lg">
    <div class="postCard-div-1">
        <h2 class="postCard-h2-0">
            <b>
                <c:out value="${param.auditionTitle}"/>
            </b>
        </h2>
        <p class="postCard-p-0">
            <c:out value="${param.auditionDate}"/>
        </p>
    </div>

    <ul>
        <li class="flex flex-row">
            <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 24 24"
                    width="14"
                    height="25"
                    class="mr-2"
            >
                <path d="M13.987,6.108c-.039.011-7.228,2.864-7.228,2.864a2.76,2.76,0,0,0,.2,5.212l2.346.587.773,2.524A2.739,2.739,0,0,0,12.617,19h.044a2.738,2.738,0,0,0,2.532-1.786s2.693-7.165,2.7-7.2a3.2,3.2,0,0,0-3.908-3.907ZM15.97,9.467,13.322,16.51a.738.738,0,0,1-.692.49c-.1-.012-.525-.026-.675-.378l-.908-2.976a1,1,0,0,0-.713-.679l-2.818-.7a.762.762,0,0,1-.027-1.433l7.06-2.8a1.149,1.149,0,0,1,1.094.32A1.19,1.19,0,0,1,15.97,9.467ZM12,0A12,12,0,1,0,24,12,12.013,12.013,0,0,0,12,0Zm0,22A10,10,0,1,1,22,12,10.011,10.011,0,0,1,12,22Z"/>
            </svg>
            <c:out value="${param.auditionLocation}"/>
        </li>
        <li class="flex flex-row mb-10 flex-wrap">
            <c:forEach
                    var="item"
                    items="${requestScope.lookingFor}"
                    varStatus="loop"
            >
                <div class="bg-gray-200 p-2 rounded-md justify-center tag m-2">
                    <c:out value="${item.name}"/>
                </div>

            </c:forEach>
        </li>
    </ul>

    <div class="postCard-div-3">
        <a href="<c:url value="/auditions/${param.id}"/>">
            <button
                    class="postCard-button-0 hover:bg-sky-700"
                    type="button"
            >
                <spring:message code="postCard.button"/>
            </button>
        </a>
    </div>
</div>
</body>
</html>
