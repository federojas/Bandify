<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib
        prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="title.noApplicants"/></title>
    <style>
        .application-box {
            display: flex;
            flex-direction: row;
            margin: 1rem;
            padding: 1rem;
            border: 1px solid #ccc;
            border-radius: 5px;
            justify-content: space-around;
            background-color: white;
            /*width: 0%;*/
            align-self: center;
        }


        .no-applicants {
            text-align: center;
            font-size: 1rem;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="application-box">
    <div class="no-applicants">
        <spring:message code="noapplicants.title" var="title1"/>
        <c:out value="${title1}"/>
    </div>
</div>

</body>
</html>
