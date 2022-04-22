<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>

<head>
    <c:import url="../config/generalHead.jsp"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/forms.css" />"/>

    <style>
        .register-content {
            display: flex;
            justify-content: center;
            padding: 24px;
        }

        .box {
            background-color: #efefef;
            opacity: 0.9;
            width: 60%;
            padding: 0.5rem 1.5rem;
            border-color: #6c0c8436;
            border-radius: 0.75rem;
            border-width: 1px;
            border-style: dotted;
            font-size: 1.25rem;
            line-height: 1.5rem;
            font-weight: 600;
        }
        .register-content {
            display: flex;
            justify-content: center;
            padding: 24px;
        }

        .box {
            background-color: #efefef;
            opacity: 0.9;
            width: 60%;
            padding: 0.5rem 1.5rem;
            border-color: #6c0c8436;
            border-radius: 0.75rem;
            border-width: 1px;
            border-style: dotted;
            font-size: 1.25rem;
            line-height: 1.5rem;
            font-weight: 600;
        }

        .header {
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: 0 12px;
            align-items: center;
            font-size: 1.5rem;
            font-weight: 600;
        }

        .forms-buttons {
            display: flex;
            flex-direction: row;
            justify-content: center;
            font-size: 1.25rem;
            font-weight: 500;
        }

        .forms-buttons > button {
            margin: 1rem 2rem;
            border-radius: 1rem;
            border-width: 1px;
            padding: 1rem 2.5rem;
            background-color: #6c0c84;
            color: white;
        }

    </style>

    <script>
        function toggleForm() {
            let artistForm = document.getElementById("artist-form");
            let bandForm = document.getElementById("band-form");
            let artistButton = document.getElementById("artist-button");
            let bandButton = document.getElementById("band-button");

            if (artistForm.style.display === "none") {
                artistForm.style.display = "block";
                bandForm.style.display = "none";
                artistButton.style.backgroundColor = "#6c0c84";
                bandButton.style.backgroundColor = "rgba(108, 12, 132, 0.6)";
            } else {
                artistForm.style.display = "none";
                bandForm.style.display = "block";
                artistButton.style.backgroundColor = "rgba(108, 12, 132, 0.6)";
                bandButton.style.backgroundColor = "#6c0c84";
            }

        }
    </script>
</head>
<body>
<!-- Navbar -->
<jsp:include page="../components/navbar.jsp">
    <jsp:param name="navItem" value="${1}"/>
    <jsp:param name="name" value="Bandify"/>
</jsp:include>

<div class="register-content flex flex-col">

    <div class="header">
        <spring:message code="register.header" />
        <div class="forms-buttons">
            <button id="artist-button" onclick="toggleForm()">
                <spring:message code="register.artist_word" />
            </button>
            <button id="band-button" onclick="toggleForm()" style="background-color: rgba(108, 12, 132, 0.69);">
                <spring:message code="register.band_word" />
            </button>
        </div>

    </div>

    <div id="artist-form" style="display: block;">
        <jsp:include page="../components/registerArtistForm.jsp"/>
    </div>

    <div id="band-form" style="display: none; ">
        <jsp:include page="../components/registerBandForm.jsp"/>
    </div>


</div>


</body>
</html>