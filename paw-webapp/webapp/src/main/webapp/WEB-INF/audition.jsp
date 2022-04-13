<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Questrial">
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title><spring:message code="home.title"/></title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="public/styles/home.css"/>
    <link rel="stylesheet" href="public/styles/postCard.css"/>
    <style>
        body {
            font-family: 'Questrial', sans-serif;
        }
    </style>
</head>
<body class="flex flex-col">
<%--Navbar--%>
<jsp:include page="navbar2.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>
<div
        class="outline-none focus:outline-none justify-center items-center"
>
    <div class="relative w-auto my-6 mx-auto max-w-3xl">
        <!--content-->
        <div class="postCard-div-6">
            <!--header-->
            <div class="postCard-div-7">
                <h3 class="postCard-h3-0">
                    <c:out value="${audition.title}"/>
                </h3>

            </div>
            <!--body-->
            <div class="postCard-div-8">
                <div class="even-columns">
                    <div>
                        <p class="mb-5"><c:out value="${audition.description}"/></p>
                        <ul>
                            <li>
                                <b> Ubicación </b>
                                <br/>
                                <div class="bg-gray-200 p-2 rounded-md justify-center tag m-2 w-fit">
                                    <c:out value="${audition.location.name}"/></div>
                            </li>
                            <li>
                                <b> Instrumentos deseados </b>
                                <br/>
                                <div class="flex flex-row flex-wrap"><c:forEach
                                        var="item"
                                        items="${audition.lookingFor}"
                                        varStatus="loop"
                                >
                                    <div class="bg-gray-200 p-2 rounded-md justify-center tag m-2">
                                            ${item.name}
                                    </div>
                                </c:forEach></div>

                            </li>
                            <li>
                                <b> Interes en géneros </b>
                                <br/>
                                <div class="flex flex-row flex-wrap">
                                    <c:forEach
                                            var="item"
                                            items="${audition.musicGenres}"
                                            varStatus="loop"
                                    >
                                        <div class="bg-gray-200 p-2 rounded-md justify-center tag m-2">
                                                ${item.name}
                                        </div>
                                    </c:forEach></div>
                            </li>
                        </ul>
                    </div>
                    <div>
                        <jsp:include page="applicationForm.jsp">
                            <jsp:param name="auditionForm" value="${1}"/>
                            <jsp:param name="auditionEmail" value="${audition.email}"  />
                            <jsp:param name="auditionFormId" value="${audition.id}"/>
                        </jsp:include>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>
