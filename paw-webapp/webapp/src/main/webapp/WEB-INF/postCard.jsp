<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page
        contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
<link rel="stylesheet" href="public/styles/postCard.css"/>
    <style>
        .container{
            display: flex;
            flex-direction: row;
            justify-content: space-evenly;
        }
    </style>
    <script>
        function toggleModal(modalID) {
            document.getElementById(modalID).classList.toggle("hidden");
            document.getElementById(modalID + "-backdrop").classList.toggle("hidden");
            document.getElementById(modalID).classList.toggle("flex");
            document.getElementById(modalID + "-backdrop").classList.toggle("flex");
        }
    </script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
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
    <div class="postCard-div-2">
        <h2 class="postCard-h2-1">
            <b>
                Banda
                <c:out value="${param.bandName}"/>

            </b>
        </h2>
        <p class="postCard-p-1">
            <c:out value="${param.auditionDescription}"/>

        </p>
    </div>
    <ul>
        <li>
            <b>
                Ubicación
            </b>
            <br/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <c:out value="${param.auditionLocation}"/>

        </li>
    </ul>
    <div class="postCard-div-3">
        <button class="postCard-button-0 bg-sky-600 hover:bg-sky-700" type="button"
                onclick="toggleModal('modal_${param.Id}')">
            Aplicar
        </button>
    </div>
</div>
<div class="hidden overflow-x-hidden overflow-y-auto fixed inset-0 z-50 outline-none focus:outline-none justify-center items-center" id="modal_${param.Id}">
    <div class="relative w-auto my-6 mx-auto max-w-3xl">
        <!--content-->
        <div class="postCard-div-6">
            <!--header-->
            <div class="postCard-div-7">
                <h3 class="postCard-h3-0">
                    Aplicación para Banda
                    <c:out value="${param.bandName}"/>

                </h3>
            </div>
            <!--body-->
            <div class="postCard-div-8">
                <div class="container">
                    <div>
                        <ul>
                            <li>
                                <b>
                                    Ubicación
                                </b>
                                <br/>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <c:out value="${param.auditionLocation}"/>
                            </li>
                            <li>
                                <b>
                                    Instrumentos deseados
                                </b>
                                <br/>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <c:forEach var="item" items="${requestScope.lookingFor}" varStatus="loop">
                                    ${item.name}
                                </c:forEach>

                            </li>
                            <li>
                                <b>
                                    Interes en géneros
                                </b>
                                <br/>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <c:forEach var="item" items="${requestScope.MusicGenres}" varStatus="loop">
                                    ${item.name}
                                </c:forEach>
                            </li>
                        </ul>
                    </div>
                    <div>
                        <jsp:include page="applicationForm.jsp">
                            <jsp:param name="auditionForm" value="${1}"/>

                            <jsp:param name="auditionFormId" value="${param.Id}"/>

                            <jsp:param name="bandName" value="${param.bandName}"/>

                        </jsp:include>
                    </div>
                </div>
            </div>
            <!--footer-->
            <div class="postCard-div-9">
                <button class="postCard-button-1" type="button" onclick="toggleModal('modal_${param.Id}')">
                    Cerrar
                </button>

            </div>
        </div>
    </div>
</div>
</body>
</htmL>






